package br.com.speedyOfficer.calculoSeguro.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.speedyOfficer.calculoSeguro.model.Marca;
import br.com.speedyOfficer.calculoSeguro.repositories.MarcaRepository;

@Configuration
@Profile("dev")
public class marcaController implements CommandLineRunner{
	
	@Autowired
	private MarcaRepository marcaRepository;

	public void run(String... args) throws Exception {
				
		Marca Ford = new Marca(10, "Ford");
		Marca Volkswagen = new Marca(20, "Volkswagen");
		Marca Chevrolet = new Marca(30, "Chevrolet");
		Marca Fiat = new Marca(40, "Fiat");
		Marca Hyundai = new Marca(50, "Hyundai");
		
		marcaRepository.saveAll(Arrays.asList(Ford, Volkswagen, Chevrolet, Fiat, Hyundai));
	}
}
