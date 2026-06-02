package it.uniroma3.siw.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class RichiestaAdozione {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private LocalDateTime dataOra;
	

    @Enumerated(EnumType.STRING)
	private Stato stato;
	
	@NotBlank
	@Column(nullable = false)
	private String descrizione;
	
	@ManyToOne
	private Animale animale;
	
	private LocalDateTime dataOraRifiuto;
	
	private LocalDateTime dataOraAccettazione;
	
	// Costruttori //

	public RichiestaAdozione() {
		
	}

	public RichiestaAdozione(LocalDateTime dataOra, Stato stato, String descrizione) {
		this.dataOra = dataOra;
		this.stato = stato;
		this.descrizione = descrizione;
	}

	// Getters And Setters //
	
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
	
	public LocalDateTime getDataOraRifiuto() {
		return dataOraRifiuto;
	}

	public void setDataOraRifiuto(LocalDateTime dataOraRifiuto) {
		this.dataOraRifiuto = dataOraRifiuto;
	}

	public LocalDateTime getDataOraAccettazione() {
		return dataOraAccettazione;
	}

	public void setDataOraAccettazione(LocalDateTime dataOraAccettazione) {
		this.dataOraAccettazione = dataOraAccettazione;
	}
	//da fare has e equals su utente e animale

}
