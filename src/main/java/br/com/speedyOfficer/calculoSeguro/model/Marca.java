package br.com.speedyOfficer.calculoSeguro.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Marca implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private int codigoMarca;
	private String descricaoMarca;
	
	public Marca() {
	}

	public Marca(int id, String descricaoMarca) {
		super();
		this.codigoMarca = id;
		this.descricaoMarca = descricaoMarca;
	}

	public int getId() {
		return codigoMarca;
	}

	public void setId(int id) {
		this.codigoMarca = id;
	}

	public String getDescricaoMarca() {
		return descricaoMarca;
	}

	public void setDescricaoMarca(String descricaoMarca) {
		this.descricaoMarca = descricaoMarca;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codigoMarca;
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
		Marca other = (Marca) obj;
		if (codigoMarca != other.codigoMarca)
			return false;
		return true;
	}
	
	
}
