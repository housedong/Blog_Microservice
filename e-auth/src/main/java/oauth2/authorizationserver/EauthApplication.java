package oauth2.authorizationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EauthApplication {

	public static void main(String[] args) {
		SpringApplication.run(EauthApplication.class, args);
	}

}
