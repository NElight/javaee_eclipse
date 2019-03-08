package com.light.thread;

import java.util.Random;

public class Thread1 extends Thread {
	private String name;
	public Thread1(String name) {
		this.name = name;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 5; i++) {
			System.out.println(name + ":" + i);
			try {
				sleep((int)Math.random()*10);
			} catch (InterruptedException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	
	
	public static void main(String[] args) throws InterruptedException {
//		Thread1 thread1 = new Thread1("t1");
//		Thread1 thread2 = new Thread1("t2");
//		thread1.start();
//		thread2.start();
//		new Thread(new Thread2("r1")).start();
//		new Thread(new Thread2("r2")).start();
		Object a = new Object();
		Object b = new Object();
		Object c = new Object();
		Thread3 t1 = new Thread3("A", c, a);
		Thread3 t2 = new Thread3("B", a, b);
		Thread3 t3 = new Thread3("C", b, c);
		new Thread(t1).start();
		Thread.sleep(100);
		new Thread(t2).start();
		Thread.sleep(100);
		new Thread(t3).start();
		Thread.sleep(100);
		
	}
}

class Thread2 implements Runnable{
	
	private String name;
	public Thread2(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 5; i++) {
			System.out.println(name + ":" + i);
			try {
				Thread.sleep((int)Math.random()*10);
			} catch (InterruptedException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
}

class Thread3 implements Runnable {
	
	private String name;
	private Object prev;
	private Object self;
	
	public Thread3(String name, Object prev, Object self) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.prev = prev;
		this.self = self;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		int count = 10;
		while (count > 0) {
			synchronized (prev) {
				synchronized (self) {
					System.out.println(name);
					count --;
					
					self.notify();
				}
				try {
					prev.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
