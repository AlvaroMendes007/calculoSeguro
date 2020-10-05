package br.com.speedyOfficer.calculoSeguro.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Calculo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idCalculo;
	private Double valorBase;
	private Double valorTotal;
	private String codigoCupom;
	private Double percentualDesconto;
	private int parcela;

	@OneToOne
	@JoinColumn(name = "fk_cpfCnpj", referencedColumnName = "cpfCnpj")
	private Cliente cpfCnpj;

	@OneToOne
	@JoinColumn(name = "fk_codigoVeiculo", referencedColumnName = "codigoVeiculo")
	private Veiculo codigoVeiculo;

	public Calculo() {
	}

	public Calculo(int idCalculo, Double valorBase, Double valorTotal, String codigoCupom, Double percentualDesconto,
			int parcela, Cliente cpfCnpj, Veiculo codigoVeiculo) {
		super();
		this.idCalculo = idCalculo;
		this.valorBase = valorBase;
		this.valorTotal = valorTotal;
		this.codigoCupom = codigoCupom;
		this.percentualDesconto = percentualDesconto;
		this.parcela = parcela;
		this.cpfCnpj = cpfCnpj;
		this.codigoVeiculo = codigoVeiculo;
	}

	public int getIdCalculo() {
		return idCalculo;
	}

	public void setIdCalculo(int idCalculo) {
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

	public Cliente getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(Cliente cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
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
		result = prime * result + ((cpfCnpj == null) ? 0 : cpfCnpj.hashCode());
		result = prime * result + idCalculo;
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
		if (cpfCnpj == null) {
			if (other.cpfCnpj != null)
				return false;
		} else if (!cpfCnpj.equals(other.cpfCnpj))
			return false;
		if (idCalculo != other.idCalculo)
			return false;
		return true;
	}
	
	

}
