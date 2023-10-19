package com.filter;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
        HttpServletResponse httpServletResponse=(HttpServletResponse ) servletResponse;
        HttpSession session=((HttpServletRequest) servletRequest).getSession();

        Object user=session.getAttribute("user");
        Cookie[]cookies=httpServletRequest.getCookies();
        String cookieValue=null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user")) {
                    cookieValue = cookie.getValue();
                    logger.info("cookie:"+cookieValue);
                    filterChain.doFilter(httpServletRequest,httpServletResponse);
                    logger.info(((HttpServletRequest) servletRequest).getRequestURI());
                    // Do something with the cookie value
                    return;
                }
            }
        }else
        {
            logger.info("cookoe is null");
        }

        if (user==null && (cookieValue!=null)){
            logger.info("can't pass");
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
