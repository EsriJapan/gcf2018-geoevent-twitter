package com.esrij.geoevent.solutions.processor.textanalysis;

import com.esri.ges.core.property.PropertyDefinition;
import com.esri.ges.core.property.PropertyException;
import com.esri.ges.core.property.PropertyType;
import com.esri.ges.processor.GeoEventProcessorDefinitionBase;

public class TextAnalysisProcessorDefinition extends GeoEventProcessorDefinitionBase {

	public TextAnalysisProcessorDefinition() throws PropertyException {
		PropertyDefinition pdTextFieldName = new PropertyDefinition("textfield", PropertyType.String, "", "${com.esrij.geoevent.solutions.processor.textanalysis.textanalysis-processor.LBL_TEXT_FIELD}", "${com.esrij.geoevent.solutions.processor.textanalysis.textanalysis-processor.DESC_TEXT_FIELD}", true, false);
		propertyDefinitions.put(pdTextFieldName.getPropertyName(), pdTextFieldName);

		PropertyDefinition pdGEDName = new PropertyDefinition("gedName", PropertyType.String, "TextAnalysisDef", "${com.esrij.geoevent.solutions.processor.textanalysis.textanalysis-processor.LBL_GEOEVENT_DEFINITION_NAME}", "${com.esrij.geoevent.solutions.processor.textanalysis.textanalysis-processor.DESC_GEOEVENT_DEFINITION_NAME}", true, false);
		propertyDefinitions.put(pdGEDName.getPropertyName(), pdGEDName);

		PropertyDefinition pdPNSCOREField = new PropertyDefinition("pnscorefield", PropertyType.String, "pnscore", "${com.esrij.geoevent.solutions.processor.textanalysis.textanalysis-processor.LBL_PNSCORE_FIELD}", "${com.esrij.geoevent.solutions.processor.textanalysis.textanalysis-processor.DESC_PNSCORE_FIELD}", true, false);
		propertyDefinitions.put(pdPNSCOREField.getPropertyName(), pdPNSCOREField);

//		PropertyDefinition pdYField = new PropertyDefinition("yfield", PropertyType.String, "y", "${com.esri.geoevent.solutions.processor.addxyz.addxyz-processor.LBL_Y_FIELD}", "${com.esri.geoevent.solutions.processor.addxyz.addxyz-processor.DESC_Y_FIELD}", true, false);
//		propertyDefinitions.put(pdYField.getPropertyName(), pdYField);
//
//		PropertyDefinition pdZField = new PropertyDefinition("zfield", PropertyType.String, "z", "${com.esri.geoevent.solutions.processor.addxyz.addxyz-processor.LBL_Z_FIELD}", "${com.esri.geoevent.solutions.processor.addxyz.addxyz-processor.DESC_Z_FIELD}", false, false);
//		propertyDefinitions.put(pdZField.getPropertyName(), pdZField);

		PropertyDefinition pdLocGrpField = new PropertyDefinition("locGrpField", PropertyType.String, "location_group", "${com.esrij.geoevent.solutions.processor.textanalysis.textanalysis-processor.LBL_LOCATIONGRP_FIELD}", "${com.esrij.geoevent.solutions.processor.textanalysis.textanalysis-processor.DESC_LOCATIONGRP_FIELD}", true, false);
		propertyDefinitions.put(pdLocGrpField.getPropertyName(), pdLocGrpField);
	}

	@Override
	public String getName() {
		return "TextAnalysisProcessor";
	}

	@Override
	public String getDomain() {
		return "com.esrij.geoevent.solutions.processor.textanalysis";
	}

	@Override
	public String getVersion() {
		return "10.6.0";
	}

	@Override
	public String getLabel() {
		return "${com.esrij.geoevent.solutions.processor.textanalysis.textanalysis-processor.PROCESSOR_LABEL}";
	}

	@Override
	public String getDescription() {
		return "${com.esrij.geoevent.solutions.processor.textanalysis.textanalysis-processor.PROCESSOR_DESCRIPTION}";
	}

	@Override
	public String getContactInfo() {
		return "mapdiscovery_system@esrij.com";
	}

}
