package java0630;

import java.beans.PropertyChangeEvent;
import java.util.Date;
import java.util.Properties;

public class main {
	public static void main(String[] args) {
		System.out.println(new Date());
		Properties properties = System.getProperties();
		properties.list(System.out);
		
		Runtime runtime = Runtime.getRuntime();
		System.out.println(runtime.totalMemory() + "\nuse" + runtime.freeMemory());
	}
}
