package br.com.speedyOfficer.calculoSeguro.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

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
	private Cliente clienteId;

	@ManyToOne
	@JoinColumn(name = "fk_codigoVeiculo", referencedColumnName = "codigoVeiculo")
	private Veiculo codigoVeiculo;

	public Calculo() {
	}

	public Calculo(Double valorBase, Double valorTotal, String codigoCupom, Double percentualDesconto,
			int parcela, Cliente clienteId, Veiculo codigoVeiculo) {
		super();
		this.valorBase = valorBase;
		this.valorTotal = valorTotal;
		this.codigoCupom = codigoCupom;
		this.percentualDesconto = percentualDesconto;
		this.parcela = parcela;
		this.clienteId = clienteId;
		this.codigoVeiculo = (Veiculo) codigoVeiculo;
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
		return (Cliente) clienteId;
	}

	public void setCliente(Cliente cpfCnpj) {
		this.clienteId = cpfCnpj;
	}

	public Veiculo getCodigoVeiculo() {
		return (Veiculo) codigoVeiculo;
	}

	public void setCodigoVeiculo(Veiculo codigoVeiculo) {
		this.codigoVeiculo = (Veiculo) codigoVeiculo;
	}

	

}
