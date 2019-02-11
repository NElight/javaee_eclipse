package com.light.classloader;

class Singleton{
	
	
	private static Singleton singleton = new Singleton();
	public static int counter1;
	public static int counter2 = 0;
	static {
		System.out.println("init4 ==== counter1 = " + counter1);
		System.out.println("init4 ==== counter2 = " + counter2);
	}
	
	
	private Singleton() {
		System.out.println("init ==== counter1 = " + singleton.counter1);
		System.out.println("init ==== counter2 = " + singleton.counter2);
		counter1 ++;
		counter2 ++;
		System.out.println("init2 ==== counter1 = " + singleton.counter1);
		System.out.println("init2 ==== counter2 = " + singleton.counter2);
	}
	
	public static Singleton getInstance() {
		System.out.println("init3 ==== counter1 = " + singleton.counter1);
		System.out.println("init3 ==== counter2 = " + singleton.counter2);
		return singleton;
	}
	
}


public class MyTest {
	public static void main(String[] args) {
		Singleton singleton = Singleton.getInstance();
		System.out.println("counter1 = " + singleton.counter1);
		System.out.println("counter2 = " + singleton.counter2);
	}
}
