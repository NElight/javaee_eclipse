package com.light.internet;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.Resource;

public class TestInternet {
	public static void main(String[] args) {
		Locale locale = new Locale("zh", "CN");
		Locale locale2 = new Locale("en", "US");
		Locale locale_default = Locale.getDefault();
		ResourceBundle bundle = ResourceBundle.getBundle("com.light.internet.info");
		System.out.println(bundle.getString("system.name"));
	}
}
