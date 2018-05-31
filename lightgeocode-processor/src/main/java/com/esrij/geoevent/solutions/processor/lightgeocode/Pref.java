package com.esrij.geoevent.solutions.processor.lightgeocode;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.esri.ges.framework.i18n.BundleLogger;
import com.esri.ges.framework.i18n.BundleLoggerFactory;

public class Pref implements Address {

//	private static final Logger logger = Logger.getLogger(Pref.class);
	private static final BundleLogger logger    = BundleLoggerFactory.getLogger(Pref.class);

	private String key_code;
	private String ken_name;
	private Double x;
	private Double y;
	private String text_index;

	public String toString() {
		return key_code
				 + "," + ken_name
				 + ","+ x
				 + ","+ y
				;
	}

	public static Pref line2bean(String line) {
		String[] elems = StringUtils.split(line, ",");
		int idx = 0;
		Pref bean = new Pref();
		bean.setKey_code(SNSUtils.removeChar(elems[idx++]));
		bean.setKen_name(SNSUtils.removeChar(elems[idx++]));
		bean.setX(Double.valueOf(elems[idx++]));
		bean.setY(Double.valueOf(elems[idx++]));
		String text_index = bean.getKen_name()
				 ;
		bean.setText_index(text_index);
		return bean;
	}

	public static Pref rs2bean(ResultSet rs) throws SQLException {
		Pref bean = new Pref();
		bean.setKey_code(rs.getString("key_code"));
		bean.setKen_name(rs.getString("ken_name"));
		bean.setX(rs.getDouble("x"));
		bean.setY(rs.getDouble("y"));
		return bean;
	}

	public String getAddressText() {
		String ret = this.getKen_name()
				 ;
		return ret;
	}

	public String createInsSql() {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO pref values(");
		sb.append("'"+this.key_code+"',");
		sb.append("'"+this.ken_name+"',");
		sb.append("'"+this.text_index+"',");
		sb.append(this.x+",");
		sb.append(this.y+") ");
		return sb.toString();
	}

	public Short getAddressLevel()  {
		return 1;
	}

	public String getKey_code() {
		return key_code;
	}
	public void setKey_code(String key_code) {
		this.key_code = key_code;
	}
	public String getKen_name() {
		return ken_name;
	}
	public void setKen_name(String ken_name) {
		this.ken_name = ken_name;
	}
	public Double getX() {
		return x;
	}
	public void setX(Double x) {
		this.x = x;
	}
	public Double getY() {
		return y;
	}
	public void setY(Double y) {
		this.y = y;
	}
	public String getText_index() {
		return text_index;
	}
	public void setText_index(String text_index) {
		this.text_index = text_index;
	}

	public static void main(String[] args) {
        Connection conn = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite::memory:");
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("dic/pref_h27.csv");
			SNSUtils.initAddress(conn, AddressLevel.PREF, is);

			int countPref = LightGeocodeUtils.countPref(conn);
			logger.info("total:"+countPref);

//			String searchSQL = "SELECT * FROM pref WHERE text_index MATCH '神奈川*';";
//			String searchSQL = "SELECT * FROM pref WHERE text_index MATCH '千葉*';";
			String searchSQL = "SELECT * FROM pref WHERE text_index MATCH ?;";
			List<Pref> hitList = SNSUtils.searchPref(conn, searchSQL, "東京*");
			for(Pref bean : hitList) {
				logger.info("Pref:"+bean.toString());
			}

		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} finally {
			if(conn != null) {
				try { conn.close(); } catch(Exception ignore) {}
			}
		}

	}

}
