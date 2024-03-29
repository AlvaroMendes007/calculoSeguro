package br.com.speedyOfficer.calculoSeguro.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


@Entity
public class Veiculo  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	private int codigoVeiculo;
	private String descricaoVeiculo;
	private Double valorVeiculo;
	
	@ManyToOne
	@JoinColumn(name = "fk_idMarca", referencedColumnName = "codigoMarca")
	private Marca marca;
		
	public Veiculo() {
	}

	public Veiculo(int codigoVeiculo, String descricaoVeiculo, Double valorVeiculo, Marca codigoMarca) {
		super();
		this.codigoVeiculo = codigoVeiculo;
		this.descricaoVeiculo = descricaoVeiculo;
		this.valorVeiculo = valorVeiculo;
		this.marca = (Marca) codigoMarca;
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
		return (Marca) marca;
	}

	public void setCodigoMarca(Marca codigoMarca) {
		this.marca = (Marca) codigoMarca;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Marca) marca).getId();
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
		if (marca != other.marca)
			return false;
		if (codigoVeiculo != other.codigoVeiculo)
			return false;
		return true;
	}
	
}
