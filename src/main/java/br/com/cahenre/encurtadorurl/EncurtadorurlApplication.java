package br.com.cahenre.encurtadorurl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EncurtadorurlApplication {

	public static void main(String[] args) {
		SpringApplication.run(EncurtadorurlApplication.class, args);
	}

}
