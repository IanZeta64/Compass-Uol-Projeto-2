package br.com.compass.adoptionapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AdoptionDocumentApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdoptionDocumentApiApplication.class, args);
	}

}
