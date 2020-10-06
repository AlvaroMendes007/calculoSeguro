package br.com.speedyOfficer.calculoSeguro.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
public class Calculo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCalculo;
	private Double valorBase;
	private Double valorTotal;
	private String codigoCupom;
	private Double percentualDesconto;
	private int parcela;

	@ManyToOne(fetch = FetchType.LAZY)
	@Cascade(value = CascadeType.ALL)
	@JoinColumn(name = "fk_idCliente", referencedColumnName = "idCliente")
	private Cliente clienteId;

	@OneToOne(fetch = FetchType.LAZY)
	@Cascade(value = CascadeType.ALL)
	@JoinColumn(name = "fk_codigoVeiculo", referencedColumnName = "codigoVeiculo")
	private Veiculo codigoVeiculo;

	public Calculo() {
	}

	public Calculo(Long idCalculo, Double valorBase, Double valorTotal, String codigoCupom, Double percentualDesconto,
			int parcela, Cliente clienteId, Veiculo codigoVeiculo) {
		super();
		this.idCalculo = idCalculo;
		this.valorBase = valorBase;
		this.valorTotal = valorTotal;
		this.codigoCupom = codigoCupom;
		this.percentualDesconto = percentualDesconto;
		this.parcela = parcela;
		this.clienteId = clienteId;
		this.codigoVeiculo = codigoVeiculo;
	}

	public Long getIdCalculo() {
		return idCalculo;
	}

	public void setIdCalculo(Long idCalculo) {
		this.idCalculo = idCalculo;
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
		return codigoVeiculo;
	}

	public void setCodigoVeiculo(Veiculo codigoVeiculo) {
		this.codigoVeiculo = codigoVeiculo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoVeiculo == null) ? 0 : codigoVeiculo.hashCode());
		result = prime * result + ((clienteId == null) ? 0 : clienteId.hashCode());
		result = (int) (prime * result + idCalculo);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Calculo other = (Calculo) obj;
		if (codigoVeiculo == null) {
			if (other.codigoVeiculo != null)
				return false;
		} else if (!codigoVeiculo.equals(other.codigoVeiculo))
			return false;
		if (clienteId == null) {
			if (other.clienteId != null)
				return false;
		} else if (!clienteId.equals(other.clienteId))
			return false;
		if (idCalculo != other.idCalculo)
			return false;
		return true;
	}

}
