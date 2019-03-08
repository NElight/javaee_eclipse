package com.light.date;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TestDate {
	public static void main(String[] args) {
		Date date = new Date();
//		java.sql.Date d1 = new java.sql.Date(234234);
		System.out.println(date);
		
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = new GregorianCalendar();
		
		int year = c1.get(Calendar.YEAR);
		
		int minute = c1.get(Calendar.MINUTE);
		int secound = c1.get(Calendar.SECOND);
		System.out.println(year + minute + secound);
	}
}
