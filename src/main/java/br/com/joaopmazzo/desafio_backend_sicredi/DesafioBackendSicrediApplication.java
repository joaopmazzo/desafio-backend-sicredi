package br.com.joaopmazzo.desafio_backend_sicredi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class DesafioBackendSicrediApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioBackendSicrediApplication.class, args);
	}

}
