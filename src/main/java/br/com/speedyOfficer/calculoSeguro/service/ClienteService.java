package br.com.speedyOfficer.calculoSeguro.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.speedyOfficer.calculoSeguro.model.Cliente;
import br.com.speedyOfficer.calculoSeguro.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository cliente;

	public void insert(Cliente clienteInserido) {
		System.out.println("inserindo cliente...");
		cliente.saveAll(Arrays.asList(clienteInserido));
		System.out.println("inseriu cliente...");
	}

	public int findIdadeByCpf(String cpf) {

		return cliente.findIdadeBycpfCnpj(cpf).getIdade();
	}

	public String findCpfbyCpf(String cpf) {

		return cliente.findCpfBycpfCnpj(cpf).getCpfCnpj();
	}

	public String findSexobyCpf(String cpf) {

		return cliente.findSexoBycpfCnpj(cpf).getSexo();
	}
	
	public int findIdbyCpf(String cpf) {

		return cliente.findSexoBycpfCnpj(cpf).getId();
	}
}
