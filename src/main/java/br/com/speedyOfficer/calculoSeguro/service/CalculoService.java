package br.com.speedyOfficer.calculoSeguro.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.speedyOfficer.calculoSeguro.model.Calculo;
import br.com.speedyOfficer.calculoSeguro.repositories.CalculoRepository;

@Service
public class CalculoService {
	
	@Autowired
	CalculoRepository calculo;
	
	public void insert(Calculo calculoInseridos){
		System.out.println("inserindo calculo...");
		calculo.saveAll(Arrays.asList(calculoInseridos));
		System.out.println("inseriu calculo...");
	}
}
