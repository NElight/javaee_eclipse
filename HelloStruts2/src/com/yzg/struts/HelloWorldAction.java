package com.yzg.struts;

import java.util.HashMap;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;
import com.sun.javafx.collections.MappingChange.Map;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class HelloWorldAction {
	
	private String name;
	
	public String execute() throws Exception{
		
		System.out.println("inside action");
		
		ValueStack stack = ActionContext.getContext().getValueStack();
		HashMap<String, Object> context =  new HashMap<String, Object>();
		context.put("key1", new String("this is key1"));
		context.put("key2", new String("this is key2"));
		stack.push(context);
		
		return "success";
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}
