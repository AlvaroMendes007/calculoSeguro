package br.com.speedyOfficer.calculoSeguro.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return   "<html> "
				+ "	<body style='text-align: center'> "
				+ "		<h3>(Metódo POST) - Para inserção de cliente path('/cliente') basta inserir no corpo da requisição os campos {'cpfCnpj': valor, 'nome': valor, 'dataNascimento': valor, 'sexo': valor} <br><br><br>"
				+ "		(Metódo POST) - Para inserção de calculo path ('/calculo') basta inserir no corpo da requisição os campos {'cpfCnpj': valor, 'codigoVeiculo': valor, (Opcional) 'codigoCupom': valor} se caso o cupom estiver expirado não haverá desconto!<br><br><br>"
				+ "     (Metódo GET) - Para consulta de todos calculos path ('/calculos') </h3>"
				+ " </body> "
				+"</html>";
	}

}
