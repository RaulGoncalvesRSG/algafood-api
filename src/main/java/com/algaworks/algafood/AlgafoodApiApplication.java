package com.algaworks.algafood;

import com.algaworks.algafood.infrasctrure.CustomJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.TimeZone;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)			//Especifica o respotory base. O padrão é SimpleJpaRepository
public class AlgafoodApiApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));		//Altera o TimeZone do sistema. Qnd usar o LocalDateTime, vai pegar com horário UTC: +3hrs
		SpringApplication.run(AlgafoodApiApplication.class, args);
	}
}
