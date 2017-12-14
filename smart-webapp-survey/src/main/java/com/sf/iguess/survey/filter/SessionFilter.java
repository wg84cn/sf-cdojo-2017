package com.sf.iguess.survey.filter;

import java.io.IOException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smart.platform.constant.HttpConstant;
import com.smart.platform.formative.RSACryptor;
import com.smart.platform.toolkit.RegularMatcher;
import com.smart.platform.toolkit.StringUtil;

@WebFilter(filterName = "sessionFilter", urlPatterns = "/*")
public class SessionFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(SessionFilter.class);
	
	private static final String STATIC_PATH = "^.*(\\.js|\\.css|\\.html|\\.jpg|\\.gif|\\.png|\\.ico|/login)$";
	private static final String  NO_NEED_FILTER_PATH = 
	    "^.*(/druid|/view.*\\.html|/question/findBySurveyType|/userScore/findByScoreId|/answer/findAnswerInfo).*$";

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		String uri = request.getRequestURI(); 
		String url = request.getHeader(HttpConstant.REFERER);
		// 验证refer
		// User do not login can access the following page
		Boolean isLogin = (Boolean) request.getSession().getAttribute(HttpConstant.ISLOGIN);
		sessionKeyPairSet(request.getSession());
		if(isNeedLoginPath(uri, isLogin)) {
			logger.info("request refer {} url {}", url, uri);
			// User do not login can access the following page
			String requestPath = request.getContextPath().equals("/") ? "" : request.getContextPath();
			response.sendRedirect(requestPath + "/login");
			return;
		} 
		filterChain.doFilter(req, resp);
	}

	private void sessionKeyPairSet(HttpSession session) {
		if (session == null) {
			return;
		}

		try {
			String publicKeyModulusHex = (String) session.getAttribute(HttpConstant.RSA_KEY_MODULE_HEX);
			if (!StringUtil.isEmpty(publicKeyModulusHex)) {
				return;
			}

			Map<String, Object> keyPairMap = RSACryptor.createKey();
			RSAPublicKey publicKey = (RSAPublicKey) keyPairMap.get(HttpConstant.RSA_PUBLIC_KEY);
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPairMap.get(HttpConstant.RSA_PRIVATE_KEY);
			publicKeyModulusHex = publicKey.getModulus().toString(16);
			/*
			 * js通过模和公钥指数获取公钥对字符串进行加密，注意必须转为16进制 - 模 java中的模和私钥指数不需要转16进制，但是js中的需要转换为16进制
			 */
			session.setAttribute(HttpConstant.RSA_KEY_MODULE_HEX, publicKeyModulusHex);
			session.setAttribute(HttpConstant.RSA_PUBLIC_KEY_EXPONENT_HEX, publicKey.getPublicExponent().toString(16));
			session.setAttribute(HttpConstant.RSA_PRIVATE_KEY, privateKey);
		} catch (Exception e) {
			logger.error(" session set key pair erorr, {}", e.toString());
		}
	}
	
	private boolean isNeedLoginPath(String uri, Boolean isLogin) {
		boolean isStaticPath = RegularMatcher.isRegularMatch(uri, STATIC_PATH);
		boolean isNoFilterPath = RegularMatcher.isRegularMatch(uri, NO_NEED_FILTER_PATH);
		return !(isStaticPath || isNoFilterPath) && (isLogin == null || !isLogin.booleanValue());
	}

	@Override
	public void destroy() {
		logger.info("过滤器销毁");
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		logger.info("过滤器初始化");
	}
}