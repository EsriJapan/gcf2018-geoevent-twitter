package com.esrij.geoevent.solutions.processor.lightgeocode;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.esri.ges.framework.i18n.BundleLogger;
import com.esri.ges.framework.i18n.BundleLoggerFactory;

public class SNSUtils {

	private static final BundleLogger LOG    = BundleLoggerFactory.getLogger(SNSUtils.class);

	private static final String SQL_OAZA_TABLE = "CREATE VIRTUAL TABLE IF NOT EXISTS oaza USING fts3(\n"
            + "	key_code text PRIMARY KEY,\n"
            + "	ken_name text NOT NULL,\n"
            + "	shicho_name text,\n"
            + "	shi_name text,\n"
            + "	ku_name text,\n"
            + "	gun_name text,\n"
            + "	choson_name text,\n"
            + "	oaza_name text,\n"
            + "	text_index text,\n"
            + "	x real,\n"
            + "	y real\n"
            + ");";

	private static final String SQL_CITY_TABLE = "CREATE VIRTUAL TABLE IF NOT EXISTS city USING fts3(\n"
            + "	key_code text PRIMARY KEY,\n"
            + "	ken_name text NOT NULL,\n"
            + "	shicho_name text,\n"
            + "	shi_name text,\n"
            + "	ku_name text,\n"
            + "	gun_name text,\n"
            + "	choson_name text,\n"
            + "	text_index text,\n"
            + "	x real,\n"
            + "	y real\n"
            + ");";

	private static final String SQL_PREF_TABLE = "CREATE VIRTUAL TABLE IF NOT EXISTS pref USING fts3(\n"
            + "	key_code text PRIMARY KEY,\n"
            + "	ken_name text NOT NULL,\n"
            + "	text_index text,\n"
            + "	x real,\n"
            + "	y real\n"
            + ");";

	public static void initAddress(Connection conn, AddressLevel level, InputStream is)
			throws URISyntaxException, ClassNotFoundException, SQLException, IOException {

		String sql = SQL_OAZA_TABLE;
//		Path path = Paths.get(Thread.currentThread().getContextClassLoader().getResource("oaza_h27.csv").toURI());

		if(level == AddressLevel.CITY) {
			sql = SQL_CITY_TABLE;
//			path = Paths.get(Thread.currentThread().getContextClassLoader().getResource("city_h27.csv").toURI());
		} else if(level == AddressLevel.PREF) {
			sql = SQL_PREF_TABLE;
//			path = Paths.get(Thread.currentThread().getContextClassLoader().getResource("pref_h27.csv").toURI());
		}

		SNSUtils.execSQL(conn, sql);

//		List<String> lineList = Files.readAllLines(path);
		List<String> lineList = LightGeocodeUtils.getLineList(is);
//		LOG.info("lineList size:"+lineList.size());
		List<String> sqlList = new ArrayList<String>();

		for(String line : lineList) {
			Address bean = null;
			if(level == AddressLevel.OAZA) {
				bean = Oaza.line2bean(line);
			} else if(level == AddressLevel.CITY) {
				bean = City.line2bean(line);
			} else if(level == AddressLevel.PREF) {
				bean = Pref.line2bean(line);
				LOG.info("Pref:"+bean.toString());
			}
			sqlList.add(bean.createInsSql());
		}
		SNSUtils.execSQL(conn, sqlList);

	}

	public static String removeChar(String text) {
		String ret =  StringUtils.replace(text, "\"", "");
		return ret;
	}

	public static void execSQL(Connection conn, String sql) throws SQLException {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
	        stmt.execute(sql);
		} finally {
			if(stmt != null) {
				try { stmt.close(); } catch(Exception ignore) {}
			}
		}
	}

	public static void execSQL(Connection conn, List<String> sqlList) throws SQLException {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			int count = 0;
			for(String sql : sqlList) {
//				stmt.execute(sql);
				stmt.addBatch(sql);
				if(count % 300 == 0) {
					int result[]=stmt.executeBatch();
				}
			}
			int result[]=stmt.executeBatch();
		} finally {
			if(stmt != null) {
				try { stmt.close(); } catch(Exception ignore) {}
			}
		}
	}


	public static List<Oaza> searchOaza(Connection conn, String sql, String param) throws SQLException {

		PreparedStatement pstmt = null;
		List<Oaza> ret = new ArrayList<Oaza>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, param);
			ResultSet rs = pstmt.executeQuery();
			int i=0;
			while(rs.next()){
				Oaza bean = Oaza.rs2bean(rs);
				ret.add(bean);
				i++;
			}
			return ret;
		} finally {
			if(pstmt != null) {
				try { pstmt.close(); } catch(Exception ignore) {}
			}
		}

	}

	public static List<City> searchCity(Connection conn, String sql, String param) throws SQLException {

		PreparedStatement pstmt = null;
		List<City> ret = new ArrayList<City>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, param);
			ResultSet rs = pstmt.executeQuery();
			int i=0;
			while(rs.next()){
				City bean = City.rs2bean(rs);
				ret.add(bean);
				i++;
			}
			return ret;
		} finally {
			if(pstmt != null) {
				try { pstmt.close(); } catch(Exception ignore) {}
			}
		}

	}

	public static int execCountSQL(Connection conn, String sql) throws SQLException {
		PreparedStatement pstmt = null;
		int ret = -1;
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			int i=0;
			while(rs.next()){
				ret = rs.getInt("count");
				i++;
			}
			return ret;
		} finally {
			if(pstmt != null) {
				try { pstmt.close(); } catch(Exception ignore) {}
			}
		}
	}

	public static List<Pref> searchPref(Connection conn, String sql, String param) throws SQLException {

		PreparedStatement pstmt = null;
		List<Pref> ret = new ArrayList<Pref>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, param);
			ResultSet rs = pstmt.executeQuery();
			int i=0;
			while(rs.next()){
				Pref bean = Pref.rs2bean(rs);
				ret.add(bean);
				i++;
			}
			return ret;
		} finally {
			if(pstmt != null) {
				try { pstmt.close(); } catch(Exception ignore) {}
			}
		}

	}

}
