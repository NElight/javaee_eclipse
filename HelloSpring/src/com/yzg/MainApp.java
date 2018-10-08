package com.yzg;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {
	
	public static void main(String args[]) {
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		HelloWorld helloWorld = (HelloWorld)context.getBean("helloWorld");
		helloWorld.getMessage();
		helloWorld.setMessage("i am a object a");
		HelloWorld helloWorld2 = (HelloWorld)context.getBean("helloWorld");
		helloWorld2.getMessage();
		helloWorld.spellCheck();
		System.out.println("--------------------------");
		
		CustomEventPublisher publisher = (CustomEventPublisher) context.getBean("customEventPublisher");
		publisher.publish();
		publisher.publish();
		System.out.println("--------------------------");
		
		Student student = (Student) context.getBean("student");
		student.getName();
		student.getAge();
//		student.printThrowException();
		System.out.println("----------------------------------");
		
		
		
		
	}
	
	
	
}
