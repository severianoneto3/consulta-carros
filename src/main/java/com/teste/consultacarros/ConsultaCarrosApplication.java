package com.teste.consultacarros;

import main.Main;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import services.ConsultaAPI;

@SpringBootApplication
public class ConsultaCarrosApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ConsultaCarrosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main();
		main.selecionarVeiculo();
	}

}
