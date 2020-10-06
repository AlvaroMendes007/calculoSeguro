package br.com.speedyOfficer.calculoSeguro.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;

@Embeddable
public class CalculoID implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@GeneratedValue
	private int id_calculo;

	public int getId_calculo() {
		return id_calculo;
	}

	public void setId_calculo(int calculoID) {
		this.id_calculo = calculoID;
	}
	
	
}
