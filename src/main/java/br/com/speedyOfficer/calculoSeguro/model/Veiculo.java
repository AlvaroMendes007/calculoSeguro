package br.com.speedyOfficer.calculoSeguro.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

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
	@Cascade(value = CascadeType.ALL)
	@JoinColumn(name = "fk_idMarca", referencedColumnName = "codigoMarca")
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
		this.codigoMarca = (Marca) codigoMarca;
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
		return (Marca) codigoMarca;
	}

	public void setCodigoMarca(Marca codigoMarca) {
		this.codigoMarca = (Marca) codigoMarca;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Marca) codigoMarca).getId();
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
