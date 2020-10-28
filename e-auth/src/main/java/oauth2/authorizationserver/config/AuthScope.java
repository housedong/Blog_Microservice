package oauth2.authorizationserver.config;

import java.util.Map;
import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 系统中所有权限范围及其包含的服务端点集合
 *
 */
@Component
@ConfigurationProperties(prefix="auth.scope")
public class AuthScope {
	
	private Map<String, Set<String>> scopeMap;
	
	public Map<String, Set<String>> getScopeMap() {
		return scopeMap;
	}

	public void setScopeMap(Map<String, Set<String>> scopeMap) {
		this.scopeMap = scopeMap;
	}

	public String toString() {
		return this.scopeMap.toString();
	}
	
}
