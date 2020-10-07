package br.com.speedyOfficer.calculoSeguro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.speedyOfficer.calculoSeguro.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	Cliente findClienteBycpfCnpj(String cpf);
}
