package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Volontario {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private String cognome;

	private String codiceFiscale;
	private String specializzazione;
	
	@Column(nullable = false)
	private LocalDate dataDiNascita;
	
	@OneToMany(mappedBy = "volontario")
	private List<Animale> listaAnimali;
	
	// Costruttori //
	
	public Volontario() {}
	
	public Volontario(String nome, String cognome, String codiceFiscale, String specializzazione, LocalDate dataDiNascita) {

		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
		this.specializzazione = specializzazione;
		this.dataDiNascita = dataDiNascita;
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

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getSpecializzazione() {
		return specializzazione;
	}

	public void setSpecializzazione(String specializzazione) {
		this.specializzazione = specializzazione;
	}

	public LocalDate getDataDiNascita() {
		return dataDiNascita;
	}

	public void setDataDiNascita(LocalDate dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}

	public List<Animale> getListaAnimali() {
		return listaAnimali;
	}

	public void setListaAnimali(List<Animale> listaAnimali) {
		this.listaAnimali = listaAnimali;
	}

	// HashCode And Equals //
	
	@Override
	public int hashCode() {
		return Objects.hash(codiceFiscale, cognome, dataDiNascita, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Volontario other = (Volontario) obj;
		return Objects.equals(codiceFiscale, other.codiceFiscale) && Objects.equals(cognome, other.cognome)
				&& Objects.equals(dataDiNascita, other.dataDiNascita) && Objects.equals(nome, other.nome);
	}
	
	
}
