package cepsearcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CepsearcherApplication {
	public static void main(String[] args) {
		SpringApplication.run(CepsearcherApplication.class, args);
	}
}
