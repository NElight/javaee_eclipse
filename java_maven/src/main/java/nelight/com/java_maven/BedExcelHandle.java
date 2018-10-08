package nelight.com.java_maven;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import java.lang.System;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class BedExcelHandle {
	
	public static String path = "D:\\用户目录\\我的文档\\Downloads\\F_182018-8-9-8-40-21.xls";
	public static String outpath = "D:\\用户目录\\我的文档\\Downloads\\aa.xls";
	
	
	
	public static void main(String args[]) throws IOException {
		
		PrintStream ps=new PrintStream(new FileOutputStream(outpath));
		System.setOut(ps);
		InputStream iStream = new FileInputStream(new File(path));
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(iStream);
//		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(iStream);
		HSSFSheet hssfSheet = hssfWorkbook.getSheet("第1页");
//		HashMap<String, ArrayList<Integer>> classtable = new HashMap<String, ArrayList<Integer>>();
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		
//		System.out.println("" + hssfSheet.getLastRowNum());
		
		for (int i = 1; i < hssfSheet.getLastRowNum() + 1; i ++) {
			HSSFRow hssfRow = hssfSheet.getRow(i);
			HSSFCell roomCell = hssfRow.getCell(0);
			HSSFCell louyuCell = hssfRow.getCell(1);
			HSSFCell loucengCell = hssfRow.getCell(2);
			HSSFCell bedCell = hssfRow.getCell(4);
			HSSFCell moneyCell = hssfRow.getCell(5);
			HSSFCell sexCell = hssfRow.getCell(6);
			
			String roomName = roomCell.getStringCellValue();
			String louyu = louyuCell.getStringCellValue();
			String louceng = loucengCell.getStringCellValue();
			int bedNum = Integer.parseInt(bedCell.getStringCellValue());
			String money = moneyCell.getStringCellValue();
			String sex = sexCell.getStringCellValue();
			
			for (int j = 0; j < bedNum; j++) {
				ArrayList<String> temp = new ArrayList<String>();
				temp.add("湖南商务职业技术学院");
				temp.add("学生公寓");
				temp.add(louyu);
				temp.add(louceng);
				temp.add(roomName);
				temp.add(String.valueOf(j + 1));
				temp.add(sex);
				temp.add(money);
				list.add(temp);
				
				System.out.println("湖南商务职业技术学院" + "\t" + "学生公寓" + "\t" + louyu + "\t"
						+ louceng + "\t" + roomName + "\t" + String.valueOf(j + 1) + "\t"
						+ sex + "\t" + money);
			}
			
		}
		
		hssfWorkbook.close();
	}
}
