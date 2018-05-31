package com.esrij.geoevent.solutions.processor.lightgeocode;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.esri.ges.framework.i18n.BundleLogger;
import com.esri.ges.framework.i18n.BundleLoggerFactory;

public class Oaza implements Address {

//	private static final Logger logger = Logger.getLogger(Oaza.class);
	private static final BundleLogger logger    = BundleLoggerFactory.getLogger(Oaza.class);

	private String key_code;
	private String ken_name;
	private String shicho_name;
	private String shi_name;
	private String ku_name;
	private String gun_name;
	private String choson_name;
	private String oaza_name;
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
				 + ","+ oaza_name
				 + ","+ x
				 + ","+ y
				;
	}

	public static Oaza line2bean(String line) {
		String[] elems = StringUtils.split(line, ",");
		int idx = 0;
		Oaza bean = new Oaza();
		bean.setKey_code(SNSUtils.removeChar(elems[idx++]));
		bean.setKen_name(SNSUtils.removeChar(elems[idx++]));
		bean.setShicho_name(SNSUtils.removeChar(elems[idx++]));
		bean.setShi_name(SNSUtils.removeChar(elems[idx++]));
		bean.setKu_name(SNSUtils.removeChar(elems[idx++]));
		bean.setGun_name(SNSUtils.removeChar(elems[idx++]));
		bean.setChoson_name(SNSUtils.removeChar(elems[idx++]));
		bean.setOaza_name(SNSUtils.removeChar(elems[idx++]));
		bean.setX(Double.valueOf(elems[idx++]));
		bean.setY(Double.valueOf(elems[idx++]));
		String text_index = bean.getKen_name()
				 + (StringUtils.isEmpty(bean.getShicho_name()) ? "" : "|" + bean.getShicho_name())
				 + (StringUtils.isEmpty(bean.getShi_name()) ? "" : "|" + bean.getShi_name())
				 + (StringUtils.isEmpty(bean.getKu_name()) ? "" : "|" + bean.getKu_name())
				 + (StringUtils.isEmpty(bean.getGun_name()) ? "" : "|" + bean.getGun_name())
				 + (StringUtils.isEmpty(bean.getChoson_name()) ? "" : "|" + bean.getChoson_name())
				 + (StringUtils.isEmpty(bean.getOaza_name()) ? "" : "|" + bean.getOaza_name())
				 ;
		bean.setText_index(text_index);
		return bean;
	}

	public static Oaza rs2bean(ResultSet rs) throws SQLException {
		Oaza bean = new Oaza();
		bean.setKey_code(rs.getString("key_code"));
		bean.setKen_name(rs.getString("ken_name"));
		bean.setShicho_name(rs.getString("shicho_name"));
		bean.setShi_name(rs.getString("shi_name"));
		bean.setKu_name(rs.getString("ku_name"));
		bean.setGun_name(rs.getString("gun_name"));
		bean.setChoson_name(rs.getString("choson_name"));
		bean.setOaza_name(rs.getString("oaza_name"));
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
				 + (StringUtils.isEmpty(this.getOaza_name()) ? "" : "" + this.getOaza_name())
				 ;
		return ret;
	}

	public String createInsSql() {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO oaza values(");
		sb.append("'"+this.key_code+"',");
		sb.append("'"+this.ken_name+"',");
		sb.append("'"+this.shicho_name+"',");
		sb.append("'"+this.shi_name+"',");
		sb.append("'"+this.ku_name+"',");
		sb.append("'"+this.gun_name+"',");
		sb.append("'"+this.choson_name+"',");
		sb.append("'"+this.oaza_name+"',");
		sb.append("'"+this.text_index+"',");
		sb.append(this.x+",");
		sb.append(this.y+") ");
		return sb.toString();
	}

	public Short getAddressLevel()  {
		return 3;
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

	public String getOaza_name() {
		return oaza_name;
	}

	public void setOaza_name(String oaza_name) {
		this.oaza_name = oaza_name;
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
		// TODO 自動生成されたメソッド・スタブ
        Connection conn = null;
//        Statement stat = null;

		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite::memory:");

			String sql = "CREATE VIRTUAL TABLE IF NOT EXISTS oaza USING fts3(\n"
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
		    SNSUtils.execSQL(conn, sql);

			Path path = Paths.get(Thread.currentThread().getContextClassLoader().getResource("oaza_h27.csv").toURI());
			List<String> lineList = Files.readAllLines(path);
			List<String> sqlList = new ArrayList<String>();
//			Map<String, Oaza> map = new HashMap<String, Oaza>();
			int count = 0;
			for(String line : lineList) {
				Oaza bean = Oaza.line2bean(line);
//				logger.info("Oaza:"+bean.toString());
//				map.put(bean.getKey_code(), bean);
				sqlList.add(bean.createInsSql());

//				if(count > 100) break;
				count++;
			}
			SNSUtils.execSQL(conn, sqlList);
//			logger.info("size:"+map.size());

//			String searchSQL = "SELECT * FROM oaza WHERE text_index MATCH '菅北浦* OR 矢野口*';";
//			String searchSQL = "SELECT * FROM oaza WHERE text_index MATCH '菅北浦*';";
			String searchSQL = "SELECT * FROM oaza WHERE text_index MATCH ?;";
//			String searchSQL = "SELECT * FROM oaza WHERE text_index MATCH '菅仙谷 OR 矢野口 OR 等々力';";
//			String searchSQL = "SELECT * FROM oaza WHERE text_index MATCH '神奈川*';";
			List<Oaza> hitList = SNSUtils.searchOaza(conn, searchSQL, "神奈川*");
			for(Oaza bean : hitList) {
				logger.info("Oaza:"+bean.toString());
			}

		} catch (URISyntaxException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} finally {
//			if(stat != null) {
//				try { stat.close(); } catch(Exception ignore) {}
//			}
			if(conn != null) {
				try { conn.close(); } catch(Exception ignore) {}
			}
		}

	}

}
