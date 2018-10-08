package com.yzg;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LoggingAnno {
	
	@Pointcut("execution (* com.yzg.Student.*(..))")
	public void selectAll() {}
	
	@Before("selectAll()")
	public void beforeActive() {
		System.out.println("before active");
	}
	
	

}
