package it.uniroma3.siw.model;


import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Area {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@Column(nullable = false)
	private String nome;
	
	@NotNull
	@Column(nullable = false)
	private Integer capacita;
	
	@NotBlank
	@Column(nullable = false)
	private String descrizione;
	
	private String immagineUrl;
	
	@OneToMany(mappedBy = "area",cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
	private List<Animale> animali;
	
	// Costruttori //
	
	public Area() {
		
	}

	public Area(String nome, Integer capacita, String descrizione, String immagineUrl) {
		this.nome = nome;
		this.capacita = capacita;
		this.descrizione = descrizione;
		this.immagineUrl = immagineUrl;
	}

	// Getters And Setters // 
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getCapacita() {
		return capacita;
	}

	public void setCapacita(Integer capacita) {
		this.capacita = capacita;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getImmagineUrl() {
		return immagineUrl;
	}

	public void setImmagineUrl(String immagineUrl) {
		this.immagineUrl = immagineUrl;
	}

	public List<Animale> getAnimali() {
		return animali;
	}

	public void setAnimali(List<Animale> animali) {
		this.animali = animali;
	}

	// Hash Code And Equals //
	
	@Override
	public int hashCode() {
		return Objects.hash(capacita, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Area other = (Area) obj;
		return Objects.equals(capacita, other.capacita) && Objects.equals(nome, other.nome);
	}
}	
	
	
	
	
	
	
	
