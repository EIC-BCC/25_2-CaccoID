package br.com.cefet.caccoId;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CaccoIdApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaccoIdApplication.class, args);
	}

}
