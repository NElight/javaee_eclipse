package nelight.com.java_maven;

import javax.servlet.*;

import java.io.IOException;
import java.util.*;

public class LogFilter implements Filter{

	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		System.out.println("logfilter");
		arg2.doFilter(arg0, arg1);
		
		
	}
	
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		String site = filterConfig.getInitParameter("Site");
		System.out.println("site: " + site);
	}
	
	

}
