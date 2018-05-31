package com.esrij.geoevent.solutions.processor.lightgeocode;

import com.esri.ges.core.property.PropertyDefinition;
import com.esri.ges.core.property.PropertyException;
import com.esri.ges.core.property.PropertyType;
import com.esri.ges.processor.GeoEventProcessorDefinitionBase;

public class LightGeocodeProcessorDefinition extends GeoEventProcessorDefinitionBase {

	public LightGeocodeProcessorDefinition() throws PropertyException {
		PropertyDefinition pdGEDName = new PropertyDefinition("gedName", PropertyType.String, "LightGeocodeDef", "${com.esrij.geoevent.solutions.processor.textanalysis.textanalysis-processor.LBL_GEOEVENT_DEFINITION_NAME}", "${com.esrij.geoevent.solutions.processor.textanalysis.textanalysis-processor.DESC_GEOEVENT_DEFINITION_NAME}", true, false);
		propertyDefinitions.put(pdGEDName.getPropertyName(), pdGEDName);

		PropertyDefinition pdLocationName = new PropertyDefinition("locationfield", PropertyType.String, "location_name", "${com.esrij.geoevent.solutions.processor.lightgeocode.lightgeocode-processor.LBL_LOCATION_FIELD}", "${com.esrij.geoevent.solutions.processor.lightgeocode.lightgeocode-processor.DESC_LOCATION_FIELD}", true, false);
		propertyDefinitions.put(pdLocationName.getPropertyName(), pdLocationName);

		PropertyDefinition pdXField = new PropertyDefinition("xField", PropertyType.String, "xcoord", "${com.esrij.geoevent.solutions.processor.lightgeocode.lightgeocode-processor.LBL_X_FIELD}", "${com.esrij.geoevent.solutions.processor.lightgeocode.lightgeocode-processor.DESC_X_FIELD}", true, false);
		propertyDefinitions.put(pdXField.getPropertyName(), pdXField);

		PropertyDefinition pdYField = new PropertyDefinition("yField", PropertyType.String, "ycoord", "${com.esrij.geoevent.solutions.processor.lightgeocode.lightgeocode-processor.LBL_Y_FIELD}", "${com.esrij.geoevent.solutions.processor.lightgeocode.lightgeocode-processor.DESC_Y_FIELD}", true, false);
		propertyDefinitions.put(pdYField.getPropertyName(), pdYField);

		PropertyDefinition pdAddressCodeField = new PropertyDefinition("addressCodeField", PropertyType.String, "addresscode", "${com.esrij.geoevent.solutions.processor.lightgeocode.lightgeocode-processor.LBL_ADDRESSCODE_FIELD}", "${com.esrij.geoevent.solutions.processor.lightgeocode.lightgeocode-processor.DESC_ADDRESSCODE_FIELD}", true, false);
		propertyDefinitions.put(pdAddressCodeField.getPropertyName(), pdAddressCodeField);

		PropertyDefinition pdAddressLevelField = new PropertyDefinition("addressLevelField", PropertyType.String, "addresslevel", "${com.esrij.geoevent.solutions.processor.lightgeocode.lightgeocode-processor.LBL_ADDRESSLEVEL_FIELD}", "${com.esrij.geoevent.solutions.processor.lightgeocode.lightgeocode-processor.DESC_ADDRESSLEVEL_FIELD}", true, false);
		propertyDefinitions.put(pdAddressLevelField.getPropertyName(), pdAddressLevelField);

	}

	@Override
	public String getName() {
		return "LightGeocodeProcessor";
	}

	@Override
	public String getDomain() {
		return "com.esrij.geoevent.solutions.processor.lightgeocode";
	}

	@Override
	public String getVersion() {
		return "10.6.0";
	}

	@Override
	public String getLabel() {
		return "${com.esrij.geoevent.solutions.processor.lightgeocode.lightgeocode-processor.PROCESSOR_LABEL}";
	}

	@Override
	public String getDescription() {
		return "${com.esrij.geoevent.solutions.processor.lightgeocode.lightgeocode-processor.PROCESSOR_DESCRIPTION}";
	}

	@Override
	public String getContactInfo() {
		return "mapdiscovery_system@esrij.com";
	}

}
