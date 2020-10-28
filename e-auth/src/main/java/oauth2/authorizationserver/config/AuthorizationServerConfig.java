package oauth2.authorizationserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()"); // 客户端可直接访问获取token的端点, 不需认证和权限
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    	// 实际中应从数据库读取客户端配置, 这里只是为了演示.
        clients.inMemory()
                .withClient("ysd-blog")
                .secret(new BCryptPasswordEncoder().encode("123456"))
                .authorizedGrantTypes("client_credentials") // 授权模式是客户端模式
                .scopes("report") // 客户端的权限范围: 即允许调用的服务端点集合, 此处对应值是: [/csv/cities, /wsv/cityId].
                .resourceIds(ResourceServerConfig.RESOURCE_ID) // 授权服务本身, 用于调用鉴权服务
                .accessTokenValiditySeconds(1200); // token的有效期, 单位: 秒
    }

    @Bean
    PasswordEncoder PasswordEncoder() {
    	return new BCryptPasswordEncoder(); // 密码的加密算法
    }
}
