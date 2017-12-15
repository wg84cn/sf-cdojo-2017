package com.sf.iguess.survey.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * ClassName: CorsFilter <br/>
 * Function: 跨域过滤器. <br/>
 * date: 2017年12月15日 下午4:49:41 <br/>
 *
 * @author 618721
 * @version 
 * @since JDK 1.8
 */
@Component
public class CorsFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(CorsFilter.class);

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		chain.doFilter(req, res);
	}

	public void init(FilterConfig filterConfig) {
		logger.info("cors filter init");
	}

	public void destroy() {
		logger.info("cors filter destory");
	}
}