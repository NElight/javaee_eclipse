package com.yzg.struts;

import java.util.*;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;



public class MyInterceptor extends AbstractInterceptor {
	
	public String intercept(ActionInvocation invocation) throws Exception{
		
		String output = "Pre-Processing";
		System.out.println(output);
		
		String result = invocation.invoke();
		
		
		output = "post-processing";
		System.out.println(output);
		
		return result;
	}

}
