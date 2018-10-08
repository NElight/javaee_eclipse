package nelight.com.java_maven;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import org.apache.commons.codec.digest.DigestUtils;

public class Md5Util {
	
	public static String path = "C:\\Users\\Administrator.USER-20180325TR\\Desktop\\mima.txt";
	
	public static void main(String args[]) throws IOException {
		
//		FileInputStream inputStream = new FileInputStream(new File(path));
//		BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
//		String tmp = null;
//		while((tmp = reader.readLine()) != null) {
//			String[] arrayList = null;
//			arrayList = tmp.trim().split("\t");
//			String md5str = DigestUtils.md5Hex(arrayList[1]);
//			System.out.print(arrayList[0] + "\t" + arrayList[1] + "\t" + md5str + "\n");
//		}
		
		System.out.println("---------------");
		System.out.println(DigestUtils.md5Hex("hxm138"));
		
	}

}
