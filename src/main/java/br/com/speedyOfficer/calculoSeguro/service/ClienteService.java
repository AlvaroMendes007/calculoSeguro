package br.com.speedyOfficer.calculoSeguro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.speedyOfficer.calculoSeguro.model.Cliente;
import br.com.speedyOfficer.calculoSeguro.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository cliente;

	public void insert(Cliente clienteInserido) {
		
			cliente.save(clienteInserido);		
	}

	public Cliente findClienteByCpf(String cpf) {

		return cliente.findClienteBycpfCnpj(cpf);
	}
	
	public Cliente addCliente(Cliente newCliente) {
		return newCliente;
	}
}
