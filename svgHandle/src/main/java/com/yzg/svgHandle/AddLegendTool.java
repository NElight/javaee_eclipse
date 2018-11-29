package com.yzg.svgHandle;

import java.awt.Graphics2D;
import java.awt.List;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.svg.*;

import com.sun.prism.paint.Color;
import com.sun.xml.internal.bind.v2.runtime.Name;

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
	
	public Document addLegend(){
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
		
		
		handleTree(document);
		System.out.println(document.toString());
		
		
		
		JPEGTranscoder transcoder = new JPEGTranscoder();
		transcoder.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, new Float(.8));
		TranscoderInput input = new TranscoderInput(document);
		OutputStream oStream = null;
		try {
			oStream = new FileOutputStream("./out.jpg");
			TranscoderOutput output = new TranscoderOutput(oStream);
			transcoder.transcode(input, output);
			oStream.flush();
			oStream.close();
			System.exit(0);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch ( TranscoderException e2) {
			e2.printStackTrace();
		}catch (IOException e3) {
			e3.printStackTrace();
		}
		
		
		
		return document;
	}
	
	public void handleTree(Document document) {
		Element svgRoot = document.getDocumentElement();
		String width = svgRoot.getAttribute("width");
		String height = svgRoot.getAttribute("height");
		System.out.println(width + "\t" + height);
		svgRoot.setAttributeNS(null, "width", String.valueOf(Float.parseFloat(width) + 500));
		
		Element legendGroup = document.createElement("g");
		svgRoot.appendChild(legendGroup);
		
		
	}
	
	public void paint(Graphics2D g2d) {
		g2d.setPaint(java.awt.Color.red);
		g2d.fill(new Rectangle(10, 10, 100, 100));
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
