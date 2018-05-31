package com.esrij.geoevent.solutions.processor.lightgeocode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.esri.ges.framework.i18n.BundleLogger;
import com.esri.ges.framework.i18n.BundleLoggerFactory;

public class LightGeocodeUtils {

	private static final BundleLogger LOG    = BundleLoggerFactory.getLogger(LightGeocodeUtils.class);

	public static List<String> getLineList(InputStream is) throws IOException {
//		String content = "";
		List<String> lineList  = new ArrayList<String>();
		BufferedReader br = null;
//		InputStream is = this.getClass().getClassLoader().getResourceAsStream(filepath);
		try {
			   //String name = this.getClass().getName();
//		       InputStream is = this.getClass().getClassLoader().getResourceAsStream("ReportTemplate.html");
		       //FileInputStream is = new FileInputStream(file);
		   br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		   String ln;
		   while ((ln = br.readLine()) != null) {
//			   	content += ln;
			   lineList.add(ln);
			}

//		   } catch (IOException e) {
//		       e.printStackTrace();
		   } finally {
			   if(br != null) {
				   try {  br.close(); }catch(Exception ignore) {}
			   }
		   }
		   return lineList;

	}

	public static int countPref(Connection conn) throws SQLException {
		String sql = "SELECT count(*) as count FROM pref;";
		int count = SNSUtils.execCountSQL(conn, sql);
		return count;
	}

	public static int countCity(Connection conn) throws SQLException {
		String sql = "SELECT count(*) as count FROM city;";
		int count = SNSUtils.execCountSQL(conn, sql);
		return count;
	}

	public static int countOaza(Connection conn) throws SQLException {
		String sql = "SELECT count(*) as count FROM oaza;";
		int count = SNSUtils.execCountSQL(conn, sql);
		return count;
	}

	public static String unicode2utf8(String unicode) throws UnsupportedEncodingException {
	    // Unicode → UTF-8
	    byte[] utf8 = unicode.getBytes("UTF-8");
	    // UTF-8 → Unicode
	    String ret = new String(utf8, "UTF-8");
	    return ret;
	}

	public static List<Pref> searchPref(Connection conn, String locName) throws SQLException {
		String searchSQL = "SELECT * FROM pref WHERE text_index MATCH ?;";
//		LOG.info(searchSQL);
		List<Pref> hitList = SNSUtils.searchPref(conn, searchSQL, locName+"*");
		return hitList;
	}

	public static List<City> searchCity(Connection conn, String locName) throws SQLException {
		String searchSQL = "SELECT * FROM city WHERE text_index MATCH ?;";
//		LOG.info(searchSQL);
		List<City> hitList = SNSUtils.searchCity(conn, searchSQL, locName+"*");
		return hitList;
	}

	public static List<Oaza> searchOaza(Connection conn, String locName) throws SQLException {
		String searchSQL = "SELECT * FROM oaza WHERE text_index MATCH ?;";
//		LOG.info(searchSQL);
		List<Oaza> hitList = SNSUtils.searchOaza(conn, searchSQL, locName+"*");
		return hitList;
	}

}
