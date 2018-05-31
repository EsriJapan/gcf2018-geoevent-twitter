package com.esrij.geoevent.solutions.processor.lightgeocode;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.esri.core.geometry.MapGeometry;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
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

public class LightGeocodeProcessor extends GeoEventProcessorBase {

	private static final BundleLogger LOG    = BundleLoggerFactory.getLogger(LightGeocodeProcessor.class);

	GeoEventDefinitionManager manager;
	Messaging messaging;
	private String locationFldName;
	private String gedName;
	private String xfield;
	private String yfield;
	private String addressCodeField;
	private String addressLevelField;
	private Connection conn = null;

	public LightGeocodeProcessor(GeoEventProcessorDefinition definition,  GeoEventDefinitionManager mgr, Messaging mes)
			throws ComponentException {
		super(definition);
		manager = mgr;
		messaging = mes;
//		LOG.info("### LightGeocodeProcessor called created ###");
	}

	@Override
	public void afterPropertiesSet()
	{
		locationFldName = properties.get("locationfield").getValueAsString();
		gedName = properties.get("gedName").getValueAsString();
		xfield = properties.get("xField").getValueAsString();
		yfield = properties.get("yField").getValueAsString();
		addressCodeField = properties.get("addressCodeField").getValueAsString();
		addressLevelField = properties.get("addressLevelField").getValueAsString();
	}

	@Override
	public void onServiceStart() {
		super.onServiceStart();
		LOG.info("### LightGeocodeProcessor called START ###");
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite::memory:");

			InputStream isPREF = this.getClass().getClassLoader().getResourceAsStream("dic/pref_h27.csv");
			InputStream isCITY = this.getClass().getClassLoader().getResourceAsStream("dic/city_h27.csv");
			InputStream isOAZA = this.getClass().getClassLoader().getResourceAsStream("dic/oaza_h27.csv");

			SNSUtils.initAddress(conn, AddressLevel.PREF, isPREF);
			SNSUtils.initAddress(conn, AddressLevel.CITY, isCITY);
			SNSUtils.initAddress(conn, AddressLevel.OAZA, isOAZA);

			int countPref = LightGeocodeUtils.countPref(conn);
			int countCity = LightGeocodeUtils.countCity(conn);
			int countOaza = LightGeocodeUtils.countOaza(conn);
			LOG.info("### LightGeocodeProcessor address data loaded! ### pref:"+countPref+" city:"+countCity+" oaza:"+countOaza);
		} catch (SQLException e) {
			LOG.error("[初期化失敗]", e);
		} catch (ClassNotFoundException e) {
			LOG.error("[初期化失敗]", e);
		} catch (URISyntaxException e) {
			LOG.error("[初期化失敗]", e);
		} catch (IOException e) {
			LOG.error("[初期化失敗]", e);
		}
	}

	@Override
	public void onServiceStop() {
		super.onServiceStop();
		LOG.info("### LightGeocodeProcessor called STOP ###");
		if(conn != null) {
			try { conn.close(); } catch(Exception ignore) {}
		}
	}

//	@Override
//	public void shutdown() {
//		super.shutdown();
//		LOG.info("### LightGeocodeProcessor called shutdown ###");
//	}

	public GeoEvent process(GeoEvent evt) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

		try {
//			int countPref = LightGeocodeUtils.countPref(conn);
//			int countCity = LightGeocodeUtils.countCity(conn);
//			int countOaza = LightGeocodeUtils.countOaza(conn);
//			LOG.info("### address data master ### pref:"+countPref+" city:"+countCity+" oaza:"+countOaza);

//			String textData = (String) evt.getField(textFldName);
			Address hitAddress = null;
			FieldGroup fg = evt.getFieldGroup("location_group");
			Short loc_count = (Short)fg.getField("location_count");
			for(int i=0;i<loc_count;i++) {
				String loc_name = (String) fg.getField("location_name"+(i+1));
//				LOG.info("##### LightGeocodeProcessor loc_name:"+loc_name);

				List<Pref> hitPrefList = LightGeocodeUtils.searchPref(conn, loc_name);
				List<City> hitCityList = LightGeocodeUtils.searchCity(conn, loc_name);
				List<Oaza> hitOazaList = LightGeocodeUtils.searchOaza(conn, loc_name);

//				LOG.info("##### LightGeocodeProcessor hitOazaList size:"+hitOazaList.size());
//				LOG.info("##### LightGeocodeProcessor hitCityList size:"+hitCityList.size());
//				LOG.info("##### LightGeocodeProcessor hitPrefList size:"+hitPrefList.size());

				if(hitOazaList.size() > 0) {
//					hitAddress = hitOazaList.get(0);
//					LOG.info("##### LightGeocodeProcessor hitOazaAddress:"+hitOazaList.get(0).toString());
				} else if(hitCityList.size() > 0) {
//					hitAddress = hitCityList.get(0);
//					LOG.info("##### LightGeocodeProcessor hitCityAddress:"+hitCityList.get(0).toString());
				} else if(hitPrefList.size() > 0) {
//					hitAddress = hitPrefList.get(0);
//					LOG.info("##### LightGeocodeProcessor hitPreAddress:"+hitPrefList.get(0).toString());
				}

				if(hitPrefList.size() == 0 && hitCityList.size() == 0 &&
						hitOazaList.size() > 0 && hitOazaList.size() <=5) {
					hitAddress = hitOazaList.get(0);
//					LOG.info("##### LightGeocodeProcessor HIT loc_name:"+loc_name+"#####");
					break;
				} else if(hitPrefList.size() == 0 && hitCityList.size() > 0 && hitCityList.size() <= 5) {
					hitAddress = hitCityList.get(0);
//					LOG.info("##### LightGeocodeProcessor HIT loc_name:"+loc_name+"#####");
					break;
				}

			}

			GeoEventDefinition geDef = evt.getGeoEventDefinition();
			GeoEventDefinition outDef = null;
			Collection<GeoEventDefinition> defs = manager.searchGeoEventDefinitionByName(gedName);
			if(defs.size() == 0) {
				List<FieldDefinition> newPropertyDefs = new ArrayList<FieldDefinition>();

				FieldDefinition locDef = new DefaultFieldDefinition(locationFldName, FieldType.String);
				FieldDefinition xDef = new DefaultFieldDefinition(xfield, FieldType.Double);
				FieldDefinition yDef = new DefaultFieldDefinition(yfield, FieldType.Double);
				FieldDefinition addressCodeDef = new DefaultFieldDefinition(addressCodeField, FieldType.String);
				FieldDefinition addressLevelDef = new DefaultFieldDefinition(addressLevelField, FieldType.Short);

				newPropertyDefs.add(locDef);
				newPropertyDefs.add(xDef);
				newPropertyDefs.add(yDef);
				newPropertyDefs.add(addressCodeDef);
				newPropertyDefs.add(addressLevelDef);

				outDef = geDef.augment(newPropertyDefs);
				outDef.setOwner(geDef.getOwner());
				outDef.setName(gedName);
				manager.addGeoEventDefinition(outDef);
			} else {
				outDef = (GeoEventDefinition) defs.toArray()[0];
			}

			GeoEventCreator creator = messaging.createGeoEventCreator();
			GeoEvent newEvent = creator.create(outDef.getGuid());

			if(hitAddress != null) {
				newEvent.setField(xfield, hitAddress.getX());
				newEvent.setField(yfield, hitAddress.getY());
				newEvent.setField(locationFldName, hitAddress.getAddressText());
				newEvent.setField(addressCodeField, hitAddress.getKey_code());
				newEvent.setField(addressLevelField, hitAddress.getAddressLevel());
				LOG.info("##### LightGeocodeProcessor HIT getAddressText:"+hitAddress.getAddressText()+"#####");
			}

			List<FieldDefinition> fldDefs = geDef.getFieldDefinitions();
			for(FieldDefinition fd: fldDefs)
			{
				String name = fd.getName();
				newEvent.setField(name, evt.getField(name));
			}

			Boolean geolocated = (Boolean)newEvent.getField("geolocated");
			if(geolocated != null && !geolocated && hitAddress != null) {
//				MapGeometry geom = newEvent.getGeometry();
				Point p = new Point(hitAddress.getX(), hitAddress.getY());
				MapGeometry newGeom = new MapGeometry(p, SpatialReference.create(4326));
				newEvent.setGeometry(newGeom);
				LOG.info("##### LightGeocodeProcessor set Geometry:"+newGeom.toString()+"#####");
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
