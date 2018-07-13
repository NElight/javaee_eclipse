package nelight.com.java_maven;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;  
import java.io.IOException;  
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.IntPredicate;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

  


public class ExcelHandle {
	
	public static String path = "D:\\广西卫职院\\新生信息导入\\校外办学点分班信息\\人员分班 - 副本.xlsx";
	
	public static void main(String args[]) throws IOException {
		InputStream iStream = new FileInputStream(new File(path));
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(iStream);
		System.out.println(xssfWorkbook);
		XSSFSheet xssfSheet = xssfWorkbook.getSheet("Sheet1");
		HashMap<String, ArrayList<Integer>> classtable = new HashMap<String, ArrayList<Integer>>();
		
		for (int i = 0; i < xssfSheet.getLastRowNum(); i ++) {
			XSSFRow xssfRow = xssfSheet.getRow(i);
			XSSFCell cnameCell = xssfRow.getCell(3);
			XSSFCell zhuanyeCell = xssfRow.getCell(5);
			XSSFCell seXssfCell = xssfRow.getCell(9);
			String cname = cnameCell.getStringCellValue();
			String zhuanye = zhuanyeCell.getStringCellValue();
			String sex = null;
			sex = seXssfCell.getStringCellValue();
			
			if (classtable.containsKey(cname)) {
				ArrayList<Integer> temp = classtable.get(cname);
				temp.set(0, temp.get(0) + 1);
				if (sex.equals("男")) {
					temp.set(1, temp.get(1) + 1);
				}	
			} else {
				ArrayList<Integer> arrayList = new ArrayList<Integer>();
				arrayList.add(0, 1);
				if (sex.equals("男")) {
					arrayList.add(1, 1);
				}else {
					arrayList.add(1, 0);
				}
				classtable.put(cname, arrayList);	
			}
			
//			System.out.println(cname + "\t" + zhuanye + "\t" + sex);
//			System.out.printf("%s	%s	%s", cnameCell.getStringCellValue(), zhuanyeCell.getStringCellValue(), seXssfCell.getStringCellValue());	
		}
		
		
		for (String key : classtable.keySet()) {
			System.out.println(key + "\t" + classtable.get(key).get(0).toString() + "\t" + classtable.get(key).get(1).toString());
		}
		
		
		
		xssfWorkbook.close();
	}
	
	
	
	

}
