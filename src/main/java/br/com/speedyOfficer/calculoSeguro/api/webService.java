package br.com.speedyOfficer.calculoSeguro.api;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tempuri.IDescontoservice;

import br.com.speedyOfficer.calculoSeguro.controller.veiculoController;
import br.com.speedyOfficer.calculoSeguro.model.Calculo;
import br.com.speedyOfficer.calculoSeguro.model.Cliente;
import br.com.speedyOfficer.calculoSeguro.model.Marca;
import br.com.speedyOfficer.calculoSeguro.model.Veiculo;
import br.com.speedyOfficer.calculoSeguro.service.CalculoService;
import br.com.speedyOfficer.calculoSeguro.service.ClienteService;

@RestController
public class webService {

	@Autowired
	private CalculoService calculoService;

	@Autowired
	private ClienteService clienteService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "<html> <body> <a href='/calculo?cpfCnpj=&sexo=&dataNascimento=&codigoVeiculo=&codigoCupom='> Calculo </a> </body> </html>";
	}

	public JSONObject getDescontoCupomByCupom(String codCupom) throws IOException {

		IDescontoservice cupom = new IDescontoservice();
		JSONObject objCupom = (JSONObject) XML.toJSONObject(cupom.getIDescontoPort().obterDesconto(codCupom))
				.get("cupom");

		return objCupom;
	}

	@RequestMapping(value = "/calculo")
	public ResponseEntity<Object> calculo(@RequestParam String nome, String cpfCnpj, @RequestParam String sexo,
			@RequestParam String dataNascimento, @RequestParam int codigoVeiculo,
			@RequestParam(required = false) String codigoCupom) throws Exception {

		JSONObject sexoInvalido = new JSONObject(
				"{codigoHttp: 400, Mensagem: Verifique o sexo, Dica: Deve estar como masculino ou feminino}");

		JSONObject codigoVeiculoInvalido = new JSONObject(
				"{codigoHttp: 400, Mensagem: Verifique o código do veículo, Dica: O código digitado pode não estar correto}");
		
		
		veiculoController veiculoController = new veiculoController();
		new SimpleDateFormat("dd/MM/yyyy");
		Double valorVeiculo = veiculoController.getValorVeiculoByCod(codigoVeiculo);
		Double baseSeguro = valorVeiculo * 0.03;
		DecimalFormat decimalFormat = new DecimalFormat("####.00");

		String[] split = dataNascimento.split("/");
		
		String descricaoVeiculo = veiculoController.getDescricaoVeiculoByCod(codigoVeiculo);
		int codigoMarcaVeiculo = veiculoController.getMarcaVeiculoByCod(codigoVeiculo);
		Marca marcaVeiculo = new Marca();
		marcaVeiculo.setId(codigoMarcaVeiculo);
		
		Veiculo veiculo = new Veiculo(codigoVeiculo, descricaoVeiculo, valorVeiculo, marcaVeiculo);

		int dia = Integer.parseInt(split[0]);
		int mes = Integer.parseInt(split[1]);
		int ano = Integer.parseInt(split[2]);

		Period calculoIdade = Period.between(LocalDate.of(ano, mes, dia), LocalDate.now());

		int idade = Integer.parseInt(calculoIdade.toString().substring(1, 3));
		
		//String dataFormatada = ano + "-" + mes + "-" + dia;
		
		Cliente cliente = new Cliente(cpfCnpj, nome, sexo, new SimpleDateFormat("dd/MM/yyyy").parse(dataNascimento), idade);

		String mensagemSucessoCupom = (codigoCupom == null ? "false"
				: getDescontoCupomByCupom(codigoCupom).get("sucesso").toString());

		Double percentualDescontoCupom = (mensagemSucessoCupom.equals("true")
				? Double.parseDouble("0.0"
						+ getDescontoCupomByCupom(codigoCupom).get("percentualDesconto").toString().replace(",", ""))
				: 0.0);

		Double acrescimoSexo = (sexo.intern() == "masculino") ? 0.10 : 0.0;

		Double acrescimoIdade = (idade >= 18 && idade <= 25 ? 0.10
				: (idade >= 26 && idade <= 30 ? 0.05 : (idade >= 31 && idade <= 35 ? 0.02 : 0.0)));

		Double acrescimoTotal = acrescimoSexo + acrescimoIdade;

		Double totalBase = (baseSeguro + baseSeguro * acrescimoTotal);

		Map<Integer, Double> parcelaMap = new HashMap<>();

		for (int parcela = 1; parcela <= 12; parcela++) {

			Double acrescimoParcelas = (parcela >= 6 && parcela <= 9 ? 0.03 : (parcela > 9) ? 0.05 : 0.0);

			Double valorTotalSeguro = (totalBase + totalBase * acrescimoParcelas) / parcela;

			valorTotalSeguro -= (valorTotalSeguro * percentualDescontoCupom);

			parcelaMap.put(parcela, Double.parseDouble(decimalFormat.format(valorTotalSeguro).replace(",", ".")));
			
			Calculo calculo = new Calculo(1, 200.00, 400.00, "XABLAU", 0.03, 1, cliente, veiculo);
		}

		JSONObject parcelasObj = new JSONObject("{parcelas: [" + parcelaMap.toString().replace("=", ":") + "]}");

		if (sexo.intern() == "masculino" || sexo.intern() == "feminino") {
			if (baseSeguro == 0.0) {
				return new ResponseEntity<>(codigoVeiculoInvalido.toMap(), HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<>(parcelasObj.toMap(), HttpStatus.OK);
			}
		} else
			return new ResponseEntity<>(sexoInvalido.toMap(), HttpStatus.BAD_REQUEST);
	}

}

