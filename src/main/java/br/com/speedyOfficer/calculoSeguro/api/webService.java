package br.com.speedyOfficer.calculoSeguro.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tempuri.IDescontoservice;
import org.json.JSONObject;
import org.json.XML;

@RestController
public class webService {

	@RequestMapping(value = "/veiculo/{codVeiculo}")
	public ResponseEntity<Object> callGet(@PathVariable int codVeiculo) throws IOException {

		URL obj = new URL(
				"http://www.speedyofficer.com.br/desenvVeiculos/wsRstSpeedyVeiculos_Case.dll/veiculo?cod_veiculo=" + codVeiculo);
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
		Map<String, Object> teste2 = new JSONObject(teste1.toString().replace("[", "").replace("=", ":").replace(",00", ".00")).toMap();
		
		System.out.println(teste2.get("valor_veiculo"));

		return new ResponseEntity<>(teste1, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/cupom")
	public String teste() throws IOException {
			
		IDescontoservice cupom = new IDescontoservice();
		JSONObject objCupom = (JSONObject) XML.toJSONObject(cupom.getIDescontoPort().obterDesconto("XTGSWI28")).get("cupom");
		
		return objCupom.get("percentualDesconto").toString().replace(",", ".");
	}

	@RequestMapping(value = "/calculo")
	public ResponseEntity<Object> calculo(@RequestParam Map<String, String> customParams) throws Exception {
		
		boolean cpfCnpj 	   = customParams.containsKey("cpfCnpj");
		boolean sexo 		   = customParams.containsKey("sexo");
		boolean dataNascimento = customParams.containsKey("dataNascimento");
		boolean codigoVeiculo  = customParams.containsKey("codigoVeiculo");
		boolean cupom 		   = customParams.containsKey("cupom");
		
		JSONObject respostaInvalida = new JSONObject("{codigoHttp: 400, Mensagem: Verifique os Campos}");
		
		if (cpfCnpj == false || sexo == false || dataNascimento == false || codigoVeiculo == false) {
			return new ResponseEntity<>(respostaInvalida.toMap() , HttpStatus.BAD_REQUEST);
		}
		else {
			return null;
		}
		
		
	}
}
