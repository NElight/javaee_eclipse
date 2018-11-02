package com.yzg.struts;


public class HelloWorldAction {
	
	private String name;
	
	public String execute() throws Exception{
		return "success";
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}
