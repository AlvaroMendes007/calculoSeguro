package br.com.speedyOfficer.calculoSeguro.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.speedyOfficer.calculoSeguro.model.Cliente;
import br.com.speedyOfficer.calculoSeguro.service.ClienteService;

@RestController
public class clienteController {

	@Autowired
	private ClienteService clienteService;

	@RequestMapping(value = "/cliente", method = RequestMethod.POST)
	public ResponseEntity<Cliente> salvarCliente(@RequestBody Cliente clienteInserido) throws Exception {
		
		String cpfCnpj = clienteInserido.getCpfCnpj();
		String sexo = clienteInserido.getSexo();
		String nome = clienteInserido.getNome();
		SimpleDateFormat formatPost = new SimpleDateFormat("dd-MM-yyyy"); 
		String dataNascimentoStr = formatPost.format(clienteInserido.getDataNascimento());
		Date dataNascimento = formatPost.parse(dataNascimentoStr);
		
		Cliente returnCli = new Cliente();
		returnCli.setCpfCnpj(cpfCnpj);
		returnCli.setSexo(sexo);
		returnCli.setNome(nome);
		returnCli.setDataNascimento(dataNascimento);
		
		String[] split = dataNascimentoStr.split("-");

		int dia = Integer.parseInt(split[0]);
		int mes = Integer.parseInt(split[1]);
		int ano = Integer.parseInt(split[2]);

		Period calculoIdade = Period.between(LocalDate.of(ano, mes, dia), LocalDate.now());
		
		int idade = calculoIdade.getYears();
		
		clienteInserido.setIdade(idade);
		Cliente cliente = new Cliente(cpfCnpj, nome, sexo, dataNascimento, idade);

		if (sexo.intern() == "masculino" || sexo.intern() == "feminino") {

			clienteService.addCliente(cliente);
			clienteService.insert(cliente);
			
			return ResponseEntity.ok(returnCli);
		} else {
			return  (ResponseEntity<Cliente>) ResponseEntity.badRequest();
		}
	}
}
