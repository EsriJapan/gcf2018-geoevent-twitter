package com.esrij.geoevent.solutions.processor.textanalysis;

import com.esri.ges.core.component.ComponentException;
import com.esri.ges.core.property.PropertyException;
import com.esri.ges.framework.i18n.BundleLogger;
import com.esri.ges.framework.i18n.BundleLoggerFactory;
import com.esri.ges.manager.geoeventdefinition.GeoEventDefinitionManager;
import com.esri.ges.messaging.Messaging;
import com.esri.ges.processor.GeoEventProcessor;
import com.esri.ges.processor.GeoEventProcessorServiceBase;

public class TextAnalysisProcessorService extends GeoEventProcessorServiceBase {

	GeoEventDefinitionManager manager;
	Messaging messaging;
//	private static final Log LOG = LogFactory
//			.getLog(TextAnalysisProcessorService.class);
	private static final BundleLogger LOG    = BundleLoggerFactory.getLogger(TextAnalysisProcessorService.class);
	public TextAnalysisProcessorService() throws PropertyException {
		definition = new TextAnalysisProcessorDefinition();
	}

	public GeoEventProcessor create() throws ComponentException {
		// TODO Auto-generated method stub
		return new TextAnalysisProcessor(definition, manager, messaging);
	}


	public void setManager(GeoEventDefinitionManager m)
	{
		manager = m;
	}

	public void setMessaging(Messaging m)
	{
		messaging = m;
	}


}
