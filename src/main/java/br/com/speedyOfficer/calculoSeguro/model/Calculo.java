package br.com.speedyOfficer.calculoSeguro.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Calculo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idCalculo;
	
	public int getIdCalculo() {
		return idCalculo;
	}

	public void setIdCalculo(int idCalculo) {
		this.idCalculo = idCalculo;
	}

	private Double valorBase;
	private Double valorTotal;
	private String codigoCupom;
	private Double percentualDesconto;
	private int parcela;

	@ManyToOne
	@JoinColumn(name = "fk_idCliente", referencedColumnName = "idCliente")
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "fk_veiculo", referencedColumnName = "codigoVeiculo")
	private Veiculo veiculo;

	public Calculo() {
	}

	public Calculo(Double valorBase, Double valorTotal, String codigoCupom, Double percentualDesconto,
			int parcela, Cliente clienteId, Veiculo veiculo) {
		super();
		this.valorBase = valorBase;
		this.valorTotal = valorTotal;
		this.codigoCupom = codigoCupom;
		this.percentualDesconto = percentualDesconto;
		this.parcela = parcela;
		this.cliente = clienteId;
		this.veiculo = (Veiculo) veiculo;
	}

	public Double getValorBase() {
		return valorBase;
	}

	public void setValorBase(Double valorBase) {
		this.valorBase = valorBase;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getCodigoCupom() {
		return codigoCupom;
	}

	public void setCodigoCupom(String codigoCupom) {
		this.codigoCupom = codigoCupom;
	}

	public Double getPercentualDesconto() {
		return percentualDesconto;
	}

	public void setPercentualDesconto(Double percentualDesconto) {
		this.percentualDesconto = percentualDesconto;
	}

	public int getParcela() {
		return parcela;
	}

	public void setParcela(int parcela) {
		this.parcela = parcela;
	}

	public Cliente getCliente() {
		return (Cliente) cliente;
	}

	public void setCliente(Cliente cpfCnpj) {
		this.cliente = cpfCnpj;
	}

	public Veiculo getveiculo() {
		return (Veiculo) veiculo;
	}

	public void setveiculo(Veiculo veiculo) {
		this.veiculo = (Veiculo) veiculo;
	}

	

}
