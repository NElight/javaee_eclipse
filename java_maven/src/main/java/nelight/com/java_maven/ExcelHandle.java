package nelight.com.java_maven;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;  
import java.io.IOException;  
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;  
import java.util.List;  

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
		XSSFSheet xssfSheet = xssfWorkbook.getSheet("Sheet1");
		for (int i = 0; i < xssfSheet.getLastRowNum(); i ++) {
			XSSFRow xssfRow = xssfSheet.getRow(i);
			XSSFCell cnameCell = xssfRow.getCell(3);
			XSSFCell zhuanyeCell = xssfRow.getCell(5);
			XSSFCell seXssfCell = xssfRow.getCell(13);
			System.out.printf("%s	%s	%s", getvalue)
			
		}
		
	}
	
	
	
	

}
