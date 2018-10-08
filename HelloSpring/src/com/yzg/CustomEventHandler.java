package com.yzg;

import org.springframework.context.ApplicationListener;

public class CustomEventHandler implements ApplicationListener<CustomEvent> {

	@Override
	public void onApplicationEvent(CustomEvent event) {
		// TODO Auto-generated method stub
		System.out.println(event.toString());
	}
	
	

}
