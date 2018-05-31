package com.esrij.geoevent.solutions.processor.textanalysis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class TextAnalysisUtils {

//	public static String convertString(byte[] eucjp) throws UnsupportedEncodingException {
//		String str = new String(eucjp, "EUC-JP");
//		byte[] utf8 = str.getBytes("UTF-8");
//		return new String(utf8);
//	}

	public static String convertString(String unicode) throws UnsupportedEncodingException {
//		String str = new String(unicode, "EUC-JP");
		byte[] tempBytes = unicode.getBytes("UTF-8");
//		byte[] utf8 = unicode.getBytes("UTF-8");
		return new String(tempBytes ,0 ,tempBytes.length, "UTF-8");
	}

	public static List<String> getLineList(InputStream is) throws IOException {
//		String content = "";
		List<String> lineList  = new ArrayList<String>();
		BufferedReader br = null;
//		InputStream is = this.getClass().getClassLoader().getResourceAsStream(filepath);
		try {
			   //String name = this.getClass().getName();
//		       InputStream is = this.getClass().getClassLoader().getResourceAsStream("ReportTemplate.html");
		       //FileInputStream is = new FileInputStream(file);
		   br = new BufferedReader(new InputStreamReader(is));
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
}
