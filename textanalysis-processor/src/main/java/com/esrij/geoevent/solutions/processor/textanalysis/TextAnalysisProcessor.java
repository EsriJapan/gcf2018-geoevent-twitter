package com.esrij.geoevent.solutions.processor.textanalysis;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

import com.atilika.kuromoji.ipadic.Token;
import com.atilika.kuromoji.ipadic.Tokenizer;
import com.esri.ges.core.component.ComponentException;
import com.esri.ges.core.geoevent.DefaultFieldDefinition;
import com.esri.ges.core.geoevent.FieldDefinition;
import com.esri.ges.core.geoevent.FieldGroup;
import com.esri.ges.core.geoevent.FieldType;
import com.esri.ges.core.geoevent.GeoEvent;
import com.esri.ges.core.geoevent.GeoEventDefinition;
import com.esri.ges.framework.i18n.BundleLogger;
import com.esri.ges.framework.i18n.BundleLoggerFactory;
import com.esri.ges.manager.geoeventdefinition.GeoEventDefinitionManager;
import com.esri.ges.messaging.GeoEventCreator;
import com.esri.ges.messaging.Messaging;
import com.esri.ges.processor.GeoEventProcessorBase;
import com.esri.ges.processor.GeoEventProcessorDefinition;

public class TextAnalysisProcessor extends GeoEventProcessorBase {

	GeoEventDefinitionManager manager;
	Messaging messaging;
//	private static final Log LOG = LogFactory
//			.getLog(TextAnalysisProcessor.class);
	private static final BundleLogger LOG    = BundleLoggerFactory.getLogger(TextAnalysisProcessor.class);
	private String invalidText = "com.esrij.geoevent.solutions.processor.textanalysis.textanalysis-processor.ERROR_INVALID_TEXT";
	private String textFldName;
	private String gedName;
	private String locGrpField;
	private String pnscoreField;
//	private String zfield;
	private Map<String, EmotionalPolarity> mapJP;
	private Map<String, EmotionalPolarity> mapEN;
	public TextAnalysisProcessor(GeoEventProcessorDefinition definition,  GeoEventDefinitionManager mgr, Messaging mes)
			throws ComponentException {
		super(definition);

		manager = mgr;
		messaging = mes;

		try {
//			Path pathJP = Paths.get( this.getClass().getClassLoader().getResource("dic/pn_ja_utf8.dic").toURI() );
//			Path pathEN = Paths.get( this.getClass().getClassLoader().getResource("dic/pn_en.dic").toURI() );
			InputStream isJP = this.getClass().getClassLoader().getResourceAsStream("dic/pn_ja_utf8.dic");
			InputStream isEN = this.getClass().getClassLoader().getResourceAsStream("dic/pn_en.dic");
			mapJP = EmotionalPolarity.createEmotionalPolarityMap(TextAnalysisUtils.getLineList(isJP));
			mapEN = EmotionalPolarity.createEmotionalPolarityMapEN(TextAnalysisUtils.getLineList(isEN));
		} catch (URISyntaxException e) {
//			LOG.error(e.getMessage());
			e.getMessage();
		} catch (IOException e) {
//			LOG.error(e.getMessage());
			e.getMessage();
		}

	}

	@Override
	public void afterPropertiesSet()
	{
		textFldName = properties.get("textfield").getValueAsString();
		gedName = properties.get("gedName").getValueAsString();
		locGrpField = properties.get("locGrpField").getValueAsString();
		pnscoreField = properties.get("pnscorefield").getValueAsString();
//		zfield = properties.get("zfield").getValueAsString();
	}

	public GeoEvent process(GeoEvent evt) throws Exception {
		try
		{
			String textData = (String) evt.getField(textFldName);
//			LOG.info("twitter textData:"+textData);
			Tokenizer tokenizer = new Tokenizer();
	        List<Token> tokens = tokenizer.tokenize(textData);

//			Double score = 0.0d;
			Double score = 100.0d; //マイナスにならないように初期値を100とする.
			List<String> locNameList = new ArrayList<String>();
	        for (Token token : tokens) {
	        	String key = token.getSurface();
	        	EmotionalPolarity p = null;
	        	if(CharacterUtil.isAlpha(key)) {
	        		if(mapEN != null) {
	        			p = mapEN.get(key);
	        		}
	        	} else {
	        		if(mapJP != null) {
	        			p = mapJP.get(key);
	        		}
	        	}

	        	if(p != null) {
	            	score += p.getValue();
	            }

	            if("地域".equalsIgnoreCase(token.getPartOfSpeechLevel3())) {
	            	LOG.debug("##### Location : " + token.getSurface());
//	            	locNameList.add(new String(token.getSurface().getBytes("EUC_JP"), "UTF-8"));
//	            	locNameList.add(token.getSurface());
	            	locNameList.add(TextAnalysisUtils.convertString( token.getSurface() ));
	            }
	        }

			GeoEventDefinition geDef = evt.getGeoEventDefinition();
			GeoEventDefinition outDef = null;
			Collection<GeoEventDefinition> defs = manager.searchGeoEventDefinitionByName(gedName);
			int locationSize = 30;
			if(defs.size() == 0)
			{
				List<FieldDefinition> newPropertyDefs = new ArrayList<FieldDefinition>();
				FieldDefinition pnscoreDef = new DefaultFieldDefinition(pnscoreField, FieldType.Double);
				FieldDefinition lfDef = new DefaultFieldDefinition(locGrpField, FieldType.Group);
//				FieldDefinition zfDef = new DefaultFieldDefinition(zfield, FieldType.Double);

//				int locationSize = locNameList.size();
				FieldDefinition locCountDef = new DefaultFieldDefinition("location_count", FieldType.Short);
				lfDef.addChild(locCountDef);
				for(int i=0;i<locationSize;i++) {
					FieldDefinition locDef = new DefaultFieldDefinition("location_name"+(i+1), FieldType.String);
					lfDef.addChild(locDef);
				}

				newPropertyDefs.add(pnscoreDef);
				newPropertyDefs.add(lfDef);
//				newPropertyDefs.add(zfDef);

				outDef = geDef.augment(newPropertyDefs);
				outDef.setOwner(geDef.getOwner());
				outDef.setName(gedName);
				manager.addGeoEventDefinition(outDef);
			}
			else
			{
				outDef = (GeoEventDefinition) defs.toArray()[0];
			}
			GeoEventCreator creator = messaging.createGeoEventCreator();
			GeoEvent newEvent = creator.create(outDef.getGuid());

			newEvent.setField(pnscoreField, score);

			FieldGroup fieldGroup = newEvent.createFieldGroup(locGrpField);
			int locNameCount = (locNameList.size() > locationSize ? locationSize : locNameList.size());
			fieldGroup.setField("location_count", locNameCount);
//			for(int i=0;i<locNameList.size();i++) {
			for(int i=0;i<locNameCount;i++) {
//				fieldGroup.setField("location_name"+(i+1), new String(locNameList.get(i)));
				fieldGroup.setField("location_name"+(i+1), (String)locNameList.get(i));
			}
			newEvent.setField(locGrpField, fieldGroup);

			List<FieldDefinition> fldDefs = geDef.getFieldDefinitions();
			for(FieldDefinition fd: fldDefs)
			{
				String name = fd.getName();
				newEvent.setField(name, evt.getField(name));
			}
			return newEvent;
		}
		catch(Exception e)
		{
			LOG.error(e.getMessage());
			throw(e);
		}
	}

}
