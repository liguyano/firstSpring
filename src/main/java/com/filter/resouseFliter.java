package com.filter;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;


@WebFilter(filterName = "resour",urlPatterns = {"/file.html","/outFIle/*"})
public class resouseFliter implements Filter {
    public static final Logger logger = LogManager.getLogger("file");
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest=(HttpServletRequest) servletRequest;
        HttpSession session=((HttpServletRequest) servletRequest).getSession();

        Object user=session.getAttribute("user");
        if (user==null){
            logger.info("work");
            logger.info(session.getAttribute("user"));
            servletRequest.getRequestDispatcher("./index.html").forward(servletRequest,servletResponse);
            return;
        }else {
            //繼續往下訪問
            logger.info(session.getAttribute("user"));
            logger.info(((HttpServletRequest) servletRequest).getRequestURI());
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }
}
