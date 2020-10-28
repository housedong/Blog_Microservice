package oauth2.authorizationserver.web;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import oauth2.authorizationserver.config.AuthScope;

/**
 * 鉴权Controller
 */
@RestController
@RequestMapping("/oauth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthScope authScope;
    
    /**
                *  检查客户端是否有权限访问指定的服务端点uri
     *
     * @param auth 客户端的认证信息, 由spring自动注入
     * @param uri 客户端要访问的服务端点
     * @return 是否有权限访问
     */
    //TODO: 还应考虑请求的http方法
    @GetMapping("/check_auth")
    public boolean checkAuth(OAuth2Authentication auth, String uri) {
    	logger.info("client scopes: " + auth.getOAuth2Request().getScope() + ", uri: " + uri);
        
        Set<String> clientScopes = auth.getOAuth2Request().getScope(); // 客户端所拥有的权限范围
        Set<String> uriSet = new HashSet<>(); // 客户端可访问的所有服务端点集合
        for(String scope: clientScopes) {
        	Set<String> uris = authScope.getScopeMap().get(scope);
        	uriSet.addAll(uris);
        }
        
        // 看要访问的服务端点是否匹配该客户端有权限访问的服务端点
        boolean matched = false; // uri有这种情况: /wsv/cityId/123.
        for(String suri: uriSet) {
        	if(uri.contains(suri)) {
        		matched = true;
        		break;
        	}
        }
        
        return matched;
    }
   
}
