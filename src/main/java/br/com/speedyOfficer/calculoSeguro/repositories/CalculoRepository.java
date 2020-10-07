package br.com.speedyOfficer.calculoSeguro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.speedyOfficer.calculoSeguro.model.Calculo;
import br.com.speedyOfficer.calculoSeguro.model.Cliente;

public interface CalculoRepository extends JpaRepository<Calculo, Long> {
	
	Calculo findCalculoBycliente(Cliente cliente);
}
