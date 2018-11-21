package com.yzg.svgHandle;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class AddLegendTool {
	
	private String svgFilePath;
	private String legendFilePath;
	private String fontSize;
	public String getSvgFilePath() {
		return svgFilePath;
	}
	public void setSvgFilePath(String svgFilePath) {
		this.svgFilePath = svgFilePath;
	}
	public String getLegendFilePath() {
		return legendFilePath;
	}
	public void setLegendFilePath(String legendFilePath) {
		this.legendFilePath = legendFilePath;
	}
	public String getFontSize() {
		return fontSize;
	}
	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}
	
	public AddLegendTool(String svgPath, String legendPath, String fontSize) {
		this.svgFilePath = svgPath;
		this.legendFilePath = legendPath;
		this.fontSize = fontSize;
	}
	
	public Document addLegend() {
		Document document = this.getDOMDocument();
		System.out.println(document);
		ArrayList<ArrayList<String>> legendList = null;
		try {
			legendList = this.parseLegend();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println(legendList);
		
		Element element = document.getDocumentElement();
		String width = element.getAttribute("width");
		String height = element.getAttribute("height");
		System.out.println(width + "\t" + height);
		
		return document;
	}
	
	public Document getDOMDocument() {
		Document document = null;
		try {
			String parser = XMLResourceDescriptor.getXMLParserClassName();
			SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);
			File file = new File(svgFilePath);
			URI uri = file.toURI();
			document = factory.createDocument(uri.toString());
		}catch(IOException e) {
			e.printStackTrace();
		}
		return document;
	}
	
	public ArrayList<ArrayList<String>> parseLegend() throws IOException{
		ArrayList<ArrayList<String>> reList = new ArrayList<ArrayList<String>>();
		FileInputStream fileInputStream = new FileInputStream(new File(legendFilePath));
		InputStreamReader reader = new InputStreamReader(fileInputStream, "UTF-8");
		BufferedReader bufferedReader = new BufferedReader(reader);
		String oneline = "";
		while ((oneline = bufferedReader.readLine()) != null) {
			reList.add(parseLegendLine(oneline));
		}
		
		return reList;
	}
	
	private ArrayList<String> parseLegendLine(String line) {
		Pattern pattern = Pattern.compile("\n");
		Matcher matcher = pattern.matcher(line);
		String trimString = matcher.replaceAll(line);
		String sep[] = trimString.split("\t");
		return new ArrayList<String>(Arrays.asList(sep));
	}
	
	
	
	

}
