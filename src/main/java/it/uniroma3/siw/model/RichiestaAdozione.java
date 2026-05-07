package it.uniroma3.siw.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class RichiestaAdozione {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private LocalDateTime dataOra;
	
	@Column(nullable = false)
	private Stato stato;
	
	@Column(nullable = false)
	private String descrizione;
	
	@ManyToOne
	private Animale animale;
	
	public RichiestaAdozione() {
		
	}

	public RichiestaAdozione(LocalDateTime dataOra, Stato stato, String descrizione) {
		super();
		this.dataOra = dataOra;
		this.stato = stato;
		this.descrizione = descrizione;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDataOra() {
		return dataOra;
	}

	public void setDataOra(LocalDateTime dataOra) {
		this.dataOra = dataOra;
	}

	public Stato getStato() {
		return stato;
	}

	public void setStato(Stato stato) {
		this.stato = stato;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Animale getAnimale() {
		return animale;
	}

	public void setAnimale(Animale animale) {
		this.animale = animale;
	}
	
	//da fare has e equals su utente e animale
	
	
	
	
	
	
	
}
