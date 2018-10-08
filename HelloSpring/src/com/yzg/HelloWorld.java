package com.yzg;

public class HelloWorld {
	
	private String message;
	
	private SpellChecker spellChecker;
	private String name;
	
	public HelloWorld(SpellChecker spellChecker, String name) {
		System.out.println("inside helloWorld constructor");
		this.spellChecker = spellChecker;
		this.name = name;
	}
	
	public void spellCheck() {
		spellChecker.checkSpell();
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void getMessage() {
		System.out.println("message ---- " + this.message);
	}
	
	public void init() {
		System.out.println("after set property:" + "helloWorld");
	}
	
	public void destroy() {
		System.out.println("destroy : helloWorld");
	}
	
	
}
