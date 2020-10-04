package br.com.speedyOfficer.calculoSeguro.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tempuri.IDescontoservice;
import org.apache.commons.collections.map.HashedMap;
import org.json.JSONObject;
import org.json.XML;

@RestController
public class webService {

	public Double getVeiculoByCod(int codVeiculo) throws IOException {

		URL obj = new URL(
				"http://www.speedyofficer.com.br/desenvVeiculos/wsRstSpeedyVeiculos_Case.dll/veiculo?cod_veiculo="
						+ codVeiculo);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("X-Aurum-Auth", "aW50ZWdyYWNhbzo0YTZkMjU5NGNjMDE2OGQ1NTg0YzE1NmQzYTYzZTFkNw==");
		con.setRequestProperty("Content-Language", "pt-BR");
		con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}

		in.close();

		JSONObject teste = new JSONObject(response.toString());
		Object teste1 = teste.toMap().get("veiculos");
		Map<String, Object> teste2 = new JSONObject(
				teste1.toString().replace("[", "").replace("=", ":").replace(",00", ".00")).toMap();

		return (Double) teste2.get("valor_veiculo");
	}

	@RequestMapping(value = "/cupom/{cupom}")
	public String getDescontoCupomByCupom(@PathVariable String codCupom) throws IOException {

		IDescontoservice cupom = new IDescontoservice();
		JSONObject objCupom = (JSONObject) XML.toJSONObject(cupom.getIDescontoPort().obterDesconto(codCupom))
				.get("cupom");

		return objCupom.get("percentualDesconto").toString().replace(",", ".");
	}

	@RequestMapping(value = "/calculo")
	public ResponseEntity<Object> calculo(@RequestParam String cpfCnpj, @RequestParam String sexo,
			@RequestParam String dataNascimento, @RequestParam int codigoVeiculo,
			@RequestParam(required = false) String codigoCupom) throws Exception {

		JSONObject sexoInvalido = new JSONObject(
				"{codigoHttp: 400, Mensagem: Verifique o sexo, Dica: Deve estar como masculino ou feminino}");
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Double valorVeiculo = getVeiculoByCod(codigoVeiculo);
		Double baseSeguro = valorVeiculo * 0.03;
		DecimalFormat decimalFormat = new DecimalFormat("####.00");
		
		String[] split = dataNascimento.split("/");	
		
		int dia = Integer.parseInt(split[0]);
		int mes = Integer.parseInt(split[1]);
		int ano = Integer.parseInt(split[2]);
						
		Period diff = Period.between(LocalDate.of(ano,mes,dia), LocalDate.now());
		
		System.out.println(diff.toString().substring(1, 3));

		Double acrescimoParcelas = 0.0;
		Map<Integer, Double> a = new HashMap<>();

		for (int parcela = 1; parcela <= 12; parcela++) {
			// a.put(1, (1395 + 1395 * 0.03) / 6);
			if (parcela >= 6 && parcela <= 9) {
				acrescimoParcelas = 0.03;
			}
			if (parcela > 9) {
				acrescimoParcelas = 0.05;
			}

			Double valorTotalSeguro = (baseSeguro + baseSeguro * acrescimoParcelas) / parcela;

			a.put(parcela, (valorTotalSeguro));
		}

		JSONObject b = new JSONObject("{parcelas: [" + a.toString().replace("=", ":") + "]}");

		return new ResponseEntity<>(b.toMap(), HttpStatus.OK);

	}

}

