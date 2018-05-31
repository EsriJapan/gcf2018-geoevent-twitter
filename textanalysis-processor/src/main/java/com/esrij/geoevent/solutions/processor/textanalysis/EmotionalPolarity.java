package com.esrij.geoevent.solutions.processor.textanalysis;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.esri.ges.framework.i18n.BundleLogger;
import com.esri.ges.framework.i18n.BundleLoggerFactory;

//import org.apache.commons.lang3.StringUtils;
//import org.apache.log4j.Logger;

public class EmotionalPolarity {

	private static final BundleLogger LOG    = BundleLoggerFactory.getLogger(EmotionalPolarity.class);
//	private static final Logger logger = Logger.getLogger(EmotionalPolarity.class);

	private String phrase;
	private String reading;
	private Part part;
	private Double value;

	public String toString() {
		return phrase + ","
				+ reading  + ","
				+ part  + ","
				+ value
				;
	}

	public static EmotionalPolarity linejapanese2bean(String line) {
//		String[] elems = StringUtils.split(line, ":");
		String[] elems = line.split(":");
		EmotionalPolarity bean = new EmotionalPolarity();
		bean.setPhrase(elems[0]);
		bean.setReading(elems[1]);
		bean.setPart(Part.createFromJapanese(elems[2]));
		bean.setValue(Double.valueOf(elems[3]));
		return bean;
	}

	public static EmotionalPolarity lineenglish2bean(String line) {
//		String[] elems = StringUtils.split(line, ":");
		String[] elems = line.split(":");
		EmotionalPolarity bean = new EmotionalPolarity();
		bean.setPhrase(elems[0]);
//		bean.setReading(elems[1]);
		bean.setPart(Part.createFromEnglish(elems[1]));
		bean.setValue(Double.valueOf(elems[2]));
		return bean;
	}

	public static Map<String, EmotionalPolarity> createEmotionalPolarityMap(List<String> lineList) throws URISyntaxException, IOException {
//		Path pnjaPath = Paths.get(Thread.currentThread().getContextClassLoader().getResource("dic/pn_ja_utf8.dic").toURI());
//		LOG.info(this.getClass().getClassLoader().getResource("pn_ja_utf8.dic").toString());
//		Path pnjaPath =  Paths.get(EmotionalPolarity.class.getResource("pn_ja_utf8.dic").toURI());
//		List<String> lineList = Files.readAllLines(path);
		Map<String, EmotionalPolarity> map = new HashMap<String, EmotionalPolarity>();
		for(String line : lineList) {
			EmotionalPolarity bean = EmotionalPolarity.linejapanese2bean(line);
//			logger.info("EmotionalPolarity:"+bean.toString());
			map.put(bean.getPhrase(), bean);
		}
		return map;
	}

	public static Map<String, EmotionalPolarity> createEmotionalPolarityMapEN(List<String> lineList) throws URISyntaxException, IOException {
//		Path path = Paths.get(Thread.currentThread().getContextClassLoader().getResource("dic/pn_en.dic").toURI());
//		LOG.info(EmotionalPolarity.class.getResource("pn_en.dic").toString());
//		Path path =  Paths.get(EmotionalPolarity.class.getResource("pn_en.dic").toURI());
//		List<String> lineList = Files.readAllLines(path);
		Map<String, EmotionalPolarity> map = new HashMap<String, EmotionalPolarity>();
		for(String line : lineList) {
			EmotionalPolarity bean = EmotionalPolarity.lineenglish2bean(line);
//			logger.info("EmotionalPolarity:"+bean.toString());
			map.put(bean.getPhrase(), bean);
		}
		return map;
	}

	public String getPhrase() {
		return phrase;
	}
	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}
	public String getReading() {
		return reading;
	}
	public void setReading(String reading) {
		this.reading = reading;
	}
	public Part getPart() {
		return part;
	}
	public void setPart(Part part) {
		this.part = part;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}

	public static void main(String[] args) {

		try {
			Path pnjaPath = Paths.get(Thread.currentThread().getContextClassLoader().getResource("./dic/pn_ja_utf8.dic").toURI());
			List<String> lineList = Files.readAllLines(pnjaPath);
			Map<String, EmotionalPolarity> map = new HashMap<String, EmotionalPolarity>();
			int count = 0;
			for(String line : lineList) {
				EmotionalPolarity bean = EmotionalPolarity.linejapanese2bean(line);
//				logger.info("EmotionalPolarity:"+bean.toString());
				map.put(bean.getPhrase(), bean);
//				if(count > 100) break;
				count++;
			}
		} catch (URISyntaxException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
}
