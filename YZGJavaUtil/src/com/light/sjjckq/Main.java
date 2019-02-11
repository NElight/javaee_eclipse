package com.light.sjjckq;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.jar.Attributes.Name;

import javax.print.DocFlavor.STRING;
import javax.sql.rowset.RowSetWarning;
import javax.swing.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.impl.STTabTlcImpl;

class MainFrame extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JFileChooser jFileChooser;
	private JButton jButton;
	
	private int FRAMEWIDTH = 680;
	private int FRAMEHEIGHT = 390;

	public MainFrame() {
		Container cp = getContentPane();
		cp.setLayout(new FlowLayout());
		jFileChooser = new JFileChooser();
		cp.add(jFileChooser);
		jButton = new JButton("计算");
		jButton.addActionListener(this);
		cp.add(jButton);
		
		setTitle("inner use");
		setSize(FRAMEWIDTH, FRAMEHEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - FRAMEWIDTH) / 2, (screenSize.height - FRAMEHEIGHT) / 2);
		setVisible(true);
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		File file = jFileChooser.getSelectedFile();
		Calculation.cal(file);
	}
		
}

class Calculation{
	
	static String AMDAKAKEY = "AMDAKAKEY";
	static String PMDAKAKEY = "PMDAKAKEY";
	static String AMCHIDAOKEY = "AMCHIDAOKEY";
	static String PMZAOTUIKEY = "PMZAOTUIKEY";
	static String REQDAKATOTAL = "REQDAKATOTAL";
	static String DAKALIWAIKEY = "DAKALIWAIKEY";
	static String AMKEY = "上午";
	static String PMKEY = "下午";
	
	static XSSFRow row;
	static HashMap<String, HashMap<String, HashMap<String, Integer>>> result = new HashMap<>();
	
	public static boolean isNull(XSSFCell cell) {
		if (cell == null) {
			return true;
		}else {
			return cell.getStringCellValue().isEmpty();
		}
	}
	
	public static void pushResult(String name, String ampmString, String key, boolean bool) {
		if (!result.get(name).get(ampmString).containsKey(key)) {
			if (bool) {
				result.get(name).get(ampmString).put(key, 1);
			}else {
				result.get(name).get(ampmString).put(key, 0);
			}
		}else {
			if (bool) {
				result.get(name).get(ampmString).put(key, result.get(name).get(ampmString).get(key) + 1);
			}
		}
	}
	
	
	public static void cal(File file) {
		System.out.println(file.getPath());
		FileInputStream fileInputStream= null;
		try {
			fileInputStream = new FileInputStream(file);
			
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
			Iterator<Row> rowIterator = xssfSheet.rowIterator();
			rowIterator.next();
			
			while (rowIterator.hasNext()) {
				row = (XSSFRow)rowIterator.next();
//				Iterator<Cell> cellIterator = row.cellIterator();
//				
//				while (cellIterator.hasNext()) {
//					Cell cell = cellIterator.next();
////					System.out.println(cell.getCellType());
//					if (cell.getCellType().equals("NUMERIC")) {
//						System.out.println("-------" +cell.getNumericCellValue());
//						
//						System.exit(1);
//					}
//				}
				
				String name = row.getCell(0).getStringCellValue();
//				System.out.println(name);
				String dateString = row.getCell(1).getStringCellValue();
				String ampmString = row.getCell(2).getStringCellValue();
//				System.out.println(row.getCell(5).getStringCellValue());
				boolean isAMdaka = ! (isNull(row.getCell(5)) && isNull(row.getCell(11)));
				boolean isPMdaka = ! (isNull(row.getCell(6)) && isNull(row.getCell(11)));
				boolean isAMchidao = ! (isNull(row.getCell(7)));
				boolean isPMzaotui = ! (isNull(row.getCell(8)));
				boolean isDakaliwai = ! (isNull(row.getCell(11)));
				
				if (!result.containsKey(name)) {
					result.put(name, new HashMap<String, HashMap<String,Integer>>());
				}
				
				if (!result.get(name).containsKey(ampmString)) {
					result.get(name).put(ampmString, new HashMap<String, Integer>());
				}
				
				
				pushResult(name, ampmString, REQDAKATOTAL, true);
				pushResult(name, ampmString, AMDAKAKEY, isAMdaka);
				pushResult(name, ampmString, PMDAKAKEY, isPMdaka);
				pushResult(name, ampmString, AMCHIDAOKEY, isAMchidao);
				pushResult(name, ampmString, PMZAOTUIKEY, isPMzaotui);
				pushResult(name, ampmString, DAKALIWAIKEY, isDakaliwai);
						
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fileInputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println(result);
		
		String dir = file.getParent();
		String filename = file.getName().substring(0, file.getName().length() - 5) + "_result" + ".xlsx";
		String newPath = dir + File.separator + filename;
		System.out.println(newPath);
		FileOutputStream fStream = null;
		
		try {
			fStream = new FileOutputStream(newPath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet();
		
		int rowNum = 0;
		XSSFRow headRow = sheet.createRow(rowNum++);
		int colNum = 0;
		
		Cell nameCellh = headRow.createCell(colNum++);
		nameCellh.setCellValue("名字");
		
		Cell dakaTotalCellh = headRow.createCell(colNum++);
		dakaTotalCellh.setCellValue("应打卡总次数");
		
		Cell amdakaCellh = headRow.createCell(colNum++);
		amdakaCellh.setCellValue("早上打卡次数");
		
		Cell amchidaoCellh = headRow.createCell(colNum++);
		amchidaoCellh.setCellValue("早上迟到次数");
		
		Cell pmdakaCellh = headRow.createCell(colNum++);
		pmdakaCellh.setCellValue("下午打卡次数");
		
		Cell pmzaotuiCellh = headRow.createCell(colNum++);
		pmzaotuiCellh.setCellValue("下午早退次数");
		
		
		
		
		for (String name : result.keySet()) {
			XSSFRow row = sheet.createRow(rowNum++);
			colNum = 0;
			Cell nameCell = row.createCell(colNum++);
			nameCell.setCellValue(name);
			
			Cell dakaTotalCell = row.createCell(colNum++);
			dakaTotalCell.setCellValue(result.get(name).get(AMKEY).get(REQDAKATOTAL));
			
			Cell amdakaCell = row.createCell(colNum++);
			amdakaCell.setCellValue(result.get(name).get(AMKEY).get(AMDAKAKEY) - result.get(name).get(AMKEY).get(DAKALIWAIKEY));
			
			Cell amchidaoCell = row.createCell(colNum++);
			amchidaoCell.setCellValue(result.get(name).get(AMKEY).get(AMCHIDAOKEY));
			
			Cell pmdakaCell = row.createCell(colNum++);
			pmdakaCell.setCellValue(result.get(name).get(PMKEY).get(PMDAKAKEY)  - result.get(name).get(PMKEY).get(DAKALIWAIKEY));
			
			Cell pmzaotuiCell = row.createCell(colNum++);
			pmzaotuiCell.setCellValue(result.get(name).get(PMKEY).get(PMZAOTUIKEY));
			
			
		}
		
		try {
			workbook.write(fStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			result = null;
			result = new HashMap<>();
			try {
				workbook.close();
				fStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				new MainFrame();
			}
		});
	}
}
