package br.com.speedyOfficer.calculoSeguro.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.speedyOfficer.calculoSeguro.model.Marca;
import br.com.speedyOfficer.calculoSeguro.model.Veiculo;
import br.com.speedyOfficer.calculoSeguro.repositories.VeiculoRepository;

@Configuration
@Profile("dev")
public class veiculoController implements CommandLineRunner {

	@Autowired
	private VeiculoRepository veiculoRepository;
	
	public void run(String... args) throws Exception {
		
		Marca marcaFord = new Marca();
		marcaFord.setId(10);

		// Marca Ford
		Veiculo Ka = new Veiculo(105063, "Ka", 45000.00, marcaFord);
		Veiculo Fusion = new Veiculo(104587, "Fusion", 150000.00, marcaFord);
		Veiculo Fiesta = new Veiculo(106354, "Fiesta", 51000.00, marcaFord);
		Veiculo Ranger = new Veiculo(108954, "Ranger", 128250.00, marcaFord);
		Veiculo Ecosport = new Veiculo(102540, "Ecosport", 72000.00, marcaFord);

		Marca marcaVolkswagen = new Marca();
		marcaVolkswagen.setId(20);

		// Marca Volkswagen
		Veiculo Gol = new Veiculo(205123, "Gol", 43500.00, marcaVolkswagen);
		Veiculo Jetta = new Veiculo(204178, "Jetta", 110000.00, marcaVolkswagen);
		Veiculo Voyage = new Veiculo(203410, "Voyage", 44000.00, marcaVolkswagen);
		Veiculo Polo = new Veiculo(202435, "Polo", 50000.00, marcaVolkswagen);
		Veiculo Golf = new Veiculo(201021, "Golf", 92000.00, marcaVolkswagen);

		Marca marcaChevrolet = new Marca();
		marcaChevrolet.setId(30);

		// Marca Chevrolet
		Veiculo Onix = new Veiculo(305111, "Onix", 42500.00, marcaChevrolet);
		Veiculo PrismaLT = new Veiculo(308621, "Prisma LT", 35590.00, marcaChevrolet);
		Veiculo CruzeLT = new Veiculo(301478, "Cruze LT", 92000.00, marcaChevrolet);
		Veiculo TrackerLT = new Veiculo(302541, "Tracker LT", 82000.00, marcaChevrolet);
		Veiculo CobaltLTZ = new Veiculo(301477, "Cobalt LTZ", 48600.00, marcaChevrolet);

		Marca marcaFiat = new Marca();
		marcaFiat.setId(40);

		// Marca Fiat
		Veiculo Argo = new Veiculo(406621, "Argo", 49500.00, marcaFiat);
		Veiculo Cronos = new Veiculo(407782, "Cronos", 59000.00, marcaFiat);
		Veiculo Uno = new Veiculo(403210, "Uno", 46500.00, marcaFiat);
		Veiculo Doblo = new Veiculo(405471, "Doblò", 95500.00, marcaFiat);
		Veiculo Weekend = new Veiculo(404126, "Weekend", 69000.00, marcaFiat);

		Marca marcaHyundai = new Marca();
		marcaHyundai.setId(50);

		// Marca Hyundai
		Veiculo HB20 = new Veiculo(505326, "HB20", 46500.00, marcaHyundai);
		Veiculo HB20S = new Veiculo(507230, "HB20S", 55500.00, marcaHyundai);

		//veiculoRepository.saveAll(Arrays.asList(Ka, Gol, Onix, Argo, HB20));
		
		veiculoRepository.saveAll(Arrays.asList(Ka, Fusion, Fiesta, Ranger, Ecosport, Gol, Jetta, Voyage, Polo, Golf,
				Onix, PrismaLT, CruzeLT, TrackerLT, CobaltLTZ, Argo, Cronos, Uno, Doblo, Weekend, HB20, HB20S));

	}

	public Double getValorVeiculoByCod(int codVeiculo) throws IOException {

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

		JSONObject jsonObj = new JSONObject(response.toString());
		Object responseVeiculos = jsonObj.toMap().get("veiculos");
		Map<String, Object> dadosVeiculos = new JSONObject(
				responseVeiculos.toString().replace("[", "").replace("=", ":").replace(",00", ".00")).toMap();

		return (Double) dadosVeiculos.get("valor_veiculo");
	}
	
	public String getDescricaoVeiculoByCod(int codVeiculo) throws IOException {

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

		JSONObject jsonObj = new JSONObject(response.toString());
		Object responseVeiculos = jsonObj.toMap().get("veiculos");
		Map<String, Object> dadosVeiculos = new JSONObject(
				responseVeiculos.toString().replace("[", "").replace("=", ":").replace(",00", ".00")).toMap();

		return (String) dadosVeiculos.get("descricao_veiculo");
	}
	
	public int getMarcaVeiculoByCod(int codVeiculo) throws IOException {

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

		JSONObject jsonObj = new JSONObject(response.toString());
		Object responseVeiculos = jsonObj.toMap().get("veiculos");
		Map<String, Object> dadosVeiculos = new JSONObject(
				responseVeiculos.toString().replace("[", "").replace("=", ":").replace(",00", ".00")).toMap();

		return (int) dadosVeiculos.get("cod_marca");
	}

}
