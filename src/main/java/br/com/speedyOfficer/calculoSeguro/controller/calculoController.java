package br.com.speedyOfficer.calculoSeguro.controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tempuri.IDescontoservice;

import br.com.speedyOfficer.calculoSeguro.model.Calculo;
import br.com.speedyOfficer.calculoSeguro.model.Cliente;
import br.com.speedyOfficer.calculoSeguro.model.Marca;
import br.com.speedyOfficer.calculoSeguro.model.Veiculo;
import br.com.speedyOfficer.calculoSeguro.service.CalculoService;
import br.com.speedyOfficer.calculoSeguro.service.ClienteService;

@RestController
public class calculoController {

	@Autowired
	private CalculoService calculoService;

	@Autowired
	private ClienteService clienteService;

	public JSONObject getDescontoCupomByCupom(String codCupom) throws IOException {

		IDescontoservice cupom = new IDescontoservice();
		JSONObject objCupom = (JSONObject) XML.toJSONObject(cupom.getIDescontoPort().obterDesconto(codCupom))
				.get("cupom");

		return objCupom;
	}

	@RequestMapping(value = "/calculo", method = RequestMethod.POST)
	public ResponseEntity<Calculo> salvarCalculo(@RequestBody Calculo calculoInserido) throws Exception {

		JSONObject calculoObj = new JSONObject();
		ArrayList<Calculo> listaCalculo = new ArrayList<Calculo>();
		
		Calculo returnCalc = new Calculo();
		String cpfCnpj = calculoInserido.getCliente().getCpfCnpj();
		int codigoVeiculo = calculoInserido.getCodigoVeiculo().getCodigoVeiculo();
		String codigoCupom = calculoInserido.getCodigoCupom();
		
		returnCalc.setCliente(clienteService.findClienteByCpf(cpfCnpj));		 
	
		veiculoController veiculoController = new veiculoController();
		Double valorVeiculo = veiculoController.getValorVeiculoByCod(codigoVeiculo);
		Double baseSeguro = valorVeiculo * 0.03;
		DecimalFormat decimalFormat = new DecimalFormat("####.00");

		Cliente cliente = new Cliente();
		cliente.setSexo(clienteService.findClienteByCpf(cpfCnpj).getSexo());
		cliente.setIdade(clienteService.findClienteByCpf(cpfCnpj).getIdade());
		cliente.setId(clienteService.findClienteByCpf(cpfCnpj).getId());
		cliente.setNome(clienteService.findClienteByCpf(cpfCnpj).getNome());
		cliente.setDataNascimento(clienteService.findClienteByCpf(cpfCnpj).getDataNascimento());
		cliente.setCpfCnpj(clienteService.findClienteByCpf(cpfCnpj).getCpfCnpj());
		
		int idade = cliente.getIdade();
		String sexo = cliente.getSexo();

		String descricaoVeiculo = veiculoController.getDescricaoVeiculoByCod(codigoVeiculo);
		int codigoMarcaVeiculo = veiculoController.getMarcaVeiculoByCod(codigoVeiculo);
		Marca marcaVeiculo = new Marca();
		marcaVeiculo.setId(codigoMarcaVeiculo);

		Veiculo veiculo = new Veiculo(codigoVeiculo, descricaoVeiculo, valorVeiculo, marcaVeiculo);

		String mensagemSucessoCupom = (codigoCupom == null ? "false"
				: getDescontoCupomByCupom(codigoCupom).get("sucesso").toString());

		Double percentualDescontoCupom = 0.0;

		if (mensagemSucessoCupom.equals("true")) {

			String[] split = getDescontoCupomByCupom(codigoCupom).get("validade").toString().split("-");
			int ano = Integer.parseInt(split[0]);
			int mes = Integer.parseInt(split[1]);
			int dia = Integer.parseInt(split[2]);
			Period calculoCupom = Period.between(LocalDate.now(), LocalDate.of(ano, mes, dia));

			percentualDescontoCupom = calculoCupom.getDays() >= 0 ? Double.parseDouble(
					"0.0" + getDescontoCupomByCupom(codigoCupom).get("percentualDesconto").toString().replace(",", ""))
					: 0.0;
		}

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

			Calculo calculo = new Calculo(baseSeguro, valorTotalSeguro, codigoCupom, percentualDescontoCupom, parcela,
					cliente, veiculo);
			
			calculoObj.put("baseSeguro", baseSeguro);
			calculoObj.put("valorTotalSeguro", valorTotalSeguro);
			calculoObj.put("codigoCupom", codigoCupom);
			calculoObj.put("percentualDescontoCupom", percentualDescontoCupom);
			calculoObj.put("parcela", parcela);
			calculoObj.put("idCliente", cliente.getId());
			calculoObj.put("codigoVeiculo", veiculo.getCodigoVeiculo());

			listaCalculo.add(calculo);
			
			calculoService.insert(calculo);
		}
		
		System.out.println("calculo = " + listaCalculo);
		
		return new ResponseEntity (listaCalculo.toArray(), HttpStatus.OK);

	}
}
