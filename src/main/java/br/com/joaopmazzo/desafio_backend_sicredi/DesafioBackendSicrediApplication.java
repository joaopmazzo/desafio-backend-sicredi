package br.com.joaopmazzo.desafio_backend_sicredi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DesafioBackendSicrediApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioBackendSicrediApplication.class, args);
	}

}
