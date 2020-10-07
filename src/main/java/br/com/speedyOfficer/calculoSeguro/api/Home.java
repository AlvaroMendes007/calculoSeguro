package br.com.speedyOfficer.calculoSeguro.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "<html> <body> <a href='/calculo?cpfCnpj=&sexo=&dataNascimento=&codigoVeiculo=&codigoCupom='> Calculo </a> </body> </html>";
	}	

}
