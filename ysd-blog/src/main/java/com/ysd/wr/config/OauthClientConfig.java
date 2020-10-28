package com.ysd.wr.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

@SuppressWarnings("deprecation")
@Configuration
public class OauthClientConfig {

	@Bean
	@Qualifier("oauthRest")
	public OAuth2RestTemplate restClient(@Value("${oauth.tokenUrl}") String tokenUrl, 
		@Value("${oauth.clientId}") String clientId, @Value("${oauth.clientSecret}") String clientSecret) {
		ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();
		resource.setAccessTokenUri(tokenUrl);
		resource.setClientId(clientId);
		resource.setClientSecret(clientSecret);
		resource.setGrantType("client_credentials");

		return new OAuth2RestTemplate(resource);
	}

}
