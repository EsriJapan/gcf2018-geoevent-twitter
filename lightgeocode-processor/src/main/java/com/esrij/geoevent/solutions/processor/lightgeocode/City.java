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
//import org.apache.log4j.Logger;

import com.esri.ges.framework.i18n.BundleLogger;
import com.esri.ges.framework.i18n.BundleLoggerFactory;

public class City implements Address {

//	private static final Logger logger = Logger.getLogger(City.class);
	private static final BundleLogger logger    = BundleLoggerFactory.getLogger(City.class);

	private String key_code;
	private String ken_name;
	private String shicho_name;
	private String shi_name;
	private String ku_name;
	private String gun_name;
	private String choson_name;
	private Double x;
	private Double y;
	private String text_index;

	public String toString() {
		return key_code
				 + "," + ken_name
				 + ","+ shicho_name
				 + ","+ shi_name
				 + ","+ ku_name
				 + ","+ gun_name
				 + ","+ choson_name
				 + ","+ x
				 + ","+ y
				;
	}

	public static City line2bean(String line) {
		String[] elems = StringUtils.split(line, ",");
		int idx = 0;
		City bean = new City();
		bean.setKey_code(SNSUtils.removeChar(elems[idx++]));
		bean.setKen_name(SNSUtils.removeChar(elems[idx++]));
		bean.setShicho_name(SNSUtils.removeChar(elems[idx++]));
		bean.setShi_name(SNSUtils.removeChar(elems[idx++]));
		bean.setKu_name(SNSUtils.removeChar(elems[idx++]));
		bean.setGun_name(SNSUtils.removeChar(elems[idx++]));
		bean.setChoson_name(SNSUtils.removeChar(elems[idx++]));
		bean.setX(Double.valueOf(elems[idx++]));
		bean.setY(Double.valueOf(elems[idx++]));
		String text_index = bean.getKen_name()
				 + (StringUtils.isEmpty(bean.getShicho_name()) ? "" : "|" + bean.getShicho_name())
				 + (StringUtils.isEmpty(bean.getShi_name()) ? "" : "|" + bean.getShi_name())
				 + (StringUtils.isEmpty(bean.getKu_name()) ? "" : "|" + bean.getKu_name())
				 + (StringUtils.isEmpty(bean.getGun_name()) ? "" : "|" + bean.getGun_name())
				 + (StringUtils.isEmpty(bean.getChoson_name()) ? "" : "|" + bean.getChoson_name())
				 ;
		bean.setText_index(text_index);
		return bean;
	}

	public static City rs2bean(ResultSet rs) throws SQLException {
		City bean = new City();
		bean.setKey_code(rs.getString("key_code"));
		bean.setKen_name(rs.getString("ken_name"));
		bean.setShicho_name(rs.getString("shicho_name"));
		bean.setShi_name(rs.getString("shi_name"));
		bean.setKu_name(rs.getString("ku_name"));
		bean.setGun_name(rs.getString("gun_name"));
		bean.setChoson_name(rs.getString("choson_name"));
		bean.setX(rs.getDouble("x"));
		bean.setY(rs.getDouble("y"));
		return bean;
	}

	public String getAddressText() {
		String ret = this.getKen_name()
				 + (StringUtils.isEmpty(this.getShicho_name()) ? "" : "" + this.getShicho_name())
				 + (StringUtils.isEmpty(this.getShi_name()) ? "" : "" + this.getShi_name())
				 + (StringUtils.isEmpty(this.getKu_name()) ? "" : "" + this.getKu_name())
				 + (StringUtils.isEmpty(this.getGun_name()) ? "" : "" + this.getGun_name())
				 + (StringUtils.isEmpty(this.getChoson_name()) ? "" : "" +this.getChoson_name())
				 ;
		return ret;
	}

	public String createInsSql() {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO city values(");
		sb.append("'"+this.key_code+"',");
		sb.append("'"+this.ken_name+"',");
		sb.append("'"+this.shicho_name+"',");
		sb.append("'"+this.shi_name+"',");
		sb.append("'"+this.ku_name+"',");
		sb.append("'"+this.gun_name+"',");
		sb.append("'"+this.choson_name+"',");
		sb.append("'"+this.text_index+"',");
		sb.append(this.x+",");
		sb.append(this.y+") ");
		return sb.toString();
	}

	public Short getAddressLevel()  {
		return 2;
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
	public String getShicho_name() {
		return shicho_name;
	}
	public void setShicho_name(String shicho_name) {
		this.shicho_name = shicho_name;
	}
	public String getShi_name() {
		return shi_name;
	}
	public void setShi_name(String shi_name) {
		this.shi_name = shi_name;
	}
	public String getKu_name() {
		return ku_name;
	}
	public void setKu_name(String ku_name) {
		this.ku_name = ku_name;
	}
	public String getGun_name() {
		return gun_name;
	}
	public void setGun_name(String gun_name) {
		this.gun_name = gun_name;
	}
	public String getChoson_name() {
		return choson_name;
	}
	public void setChoson_name(String choson_name) {
		this.choson_name = choson_name;
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
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("dic/city_h27.csv");
			SNSUtils.initAddress(conn, AddressLevel.CITY, is);

//			String searchSQL = "SELECT * FROM city WHERE text_index MATCH '川崎市* AND 多摩区*';";
//			String searchSQL = "SELECT * FROM city WHERE text_index MATCH '川崎市*';";
//			String searchSQL = "SELECT * FROM city WHERE text_index MATCH '秦野*';";
//			String searchSQL = "SELECT * FROM city WHERE text_index MATCH '市*';";
			String searchSQL= "	SELECT * FROM city WHERE text_index MATCH ?;";
			List<City> hitList = SNSUtils.searchCity(conn, searchSQL, "高松*");
			for(City bean : hitList) {
				logger.info("City:"+bean.toString());
			}

		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
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
