package br.com.speedyOfficer.calculoSeguro.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Veiculo {

	@Id
	private int codigoVeiculo;
	private String descricaoVeiculo;
	private Double valorVeiculo;
	
	@OneToOne
	@JoinColumn(name = "fk_CodigoMarca",referencedColumnName = "codigoMarca")
	private Marca codigoMarca;
	
	@OneToOne(mappedBy = "codigoVeiculo")
	private Calculo calculo;
	
	public Veiculo() {
	}

	public Veiculo(int codigoVeiculo, String descricaoVeiculo, Double valorVeiculo, Marca codigoMarca) {
		super();
		this.codigoVeiculo = codigoVeiculo;
		this.descricaoVeiculo = descricaoVeiculo;
		this.valorVeiculo = valorVeiculo;
		this.codigoMarca = codigoMarca;
	}

	public int getCodigoVeiculo() {
		return codigoVeiculo;
	}

	public void setCodigoVeiculo(int codigoVeiculo) {
		this.codigoVeiculo = codigoVeiculo;
	}

	public String getDescricaoVeiculo() {
		return descricaoVeiculo;
	}

	public void setDescricaoVeiculo(String descricaoVeiculo) {
		this.descricaoVeiculo = descricaoVeiculo;
	}

	public Double getValorVeiculo() {
		return valorVeiculo;
	}

	public void setValorVeiculo(Double valorVeiculo) {
		this.valorVeiculo = valorVeiculo;
	}

	public Marca getCodigoMarca() {
		return codigoMarca;
	}

	public void setCodigoMarca(Marca codigoMarca) {
		this.codigoMarca = codigoMarca;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codigoMarca.getId();
		result = prime * result + codigoVeiculo;
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
		Veiculo other = (Veiculo) obj;
		if (codigoMarca != other.codigoMarca)
			return false;
		if (codigoVeiculo != other.codigoVeiculo)
			return false;
		return true;
	}
	
	
}
