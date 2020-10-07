package br.com.speedyOfficer.calculoSeguro.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Marca implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private int codigoMarca;
	private String descricaoMarca;
	
	@OneToMany(mappedBy = "marca")	
	private List<Veiculo> veiculos;
	
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
	
}
