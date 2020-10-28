package com.ju.ezuul;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class AuthFilter extends ZuulFilter {
	
	private static final Log log = LogFactory.getLog(AuthFilter.class);
	
	public static final String tokenUri = "/oauth/token"; // 客户端从授权服务器获取token的网址uri
	
	// token在请求头Authorization中, 而且前面有字符串"Bearer "
	private static final String strBeforeToken = "Bearer "; 

	@Value("${auth.checkAuthUrl}")
	private String checkAuthUrl;
	
	@Override
	public boolean shouldFilter() {
		HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
		String uri = request.getRequestURI(); // 客户端要调用的服务端点的uri
		log.info("客户端要访问的uri: " + uri);
		
		// 除调用获取token的服务端点外, 调用任何端点都需鉴权
		return !uri.equals(AuthFilter.tokenUri);
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		String uri = request.getRequestURI();
		
		try {
			String token = null;
			String authorization = request.getHeader("Authorization");
			if(authorization != null && authorization.contains(AuthFilter.strBeforeToken)) {
				token = authorization.substring(AuthFilter.strBeforeToken.length());
			}
			if(null == token || token.trim().equals("")) {
				throw new ZuulRuntimeException(new ZuulException(null, "Client unauthenticated", 
					HttpStatus.UNAUTHORIZED.value(), null));
			}
			
			String authUrl = this.checkAuthUrl + "?access_token=" + token + "&uri=" + uri;
			boolean canCall = new RestTemplate().getForObject(authUrl, Boolean.class);
			if(!canCall) {
				throw new ZuulRuntimeException(new ZuulException(null, "Service endpoint unauthorized: "
					+ uri, HttpStatus.UNAUTHORIZED.value(), null));
			}
			
			return null;
		}catch(ZuulRuntimeException e) {
			throw e;
		}catch(Exception e) {
			throw new ZuulRuntimeException(new ZuulException(e, "Checking authorization error", 
				HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
		}
	}

	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1;
	}
	
}
