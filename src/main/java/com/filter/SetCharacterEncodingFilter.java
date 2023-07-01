package com.filter;



import jakarta.servlet.Filter;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;


@WebFilter(filterName = "main",urlPatterns = "/*")
public class SetCharacterEncodingFilter implements Filter {
	public static final Logger logger = LogManager.getLogger("main");
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		logger.info(request.getRemoteAddr().toString()+"access");
		chain.doFilter(request,response);      
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		logger.info("filter init");
		
	}

	

}
