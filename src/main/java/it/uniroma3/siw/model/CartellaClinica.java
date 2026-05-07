package it.uniroma3.siw.model;


import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class CartellaClinica {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	@Column(nullable = false)
	private LocalDate dataVisita;
	
	@NotBlank
	@Column(nullable = false)
	private String diagnosi;
	
	private String terapia;
	
	public CartellaClinica() {
		
	}

	public CartellaClinica(LocalDate dataVisita, String diagnosi, String terapia) {
		this.dataVisita = dataVisita;
		this.diagnosi = diagnosi;
		this.terapia = terapia;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDataVisita() {
		return dataVisita;
	}

	public void setDataVisita(LocalDate dataVisita) {
		this.dataVisita = dataVisita;
	}

	public String getDiagnosi() {
		return diagnosi;
	}

	public void setDiagnosi(String diagnosi) {
		this.diagnosi = diagnosi;
	}

	public String getTerapia() {
		return terapia;
	}

	public void setTerapia(String terapia) {
		this.terapia = terapia;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartellaClinica other = (CartellaClinica) obj;
		return Objects.equals(id, other.id);
	}
	
	

}
