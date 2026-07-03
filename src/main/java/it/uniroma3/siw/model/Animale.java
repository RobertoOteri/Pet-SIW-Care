package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Animale {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Specie specie;
	
	private String razza;
	
	private LocalDate dataNascita;
	
	@NotNull
	@Column(nullable = false)
	private LocalDate dataArrivo;
	
	private String descrizione;
	
	private String immagineUrl;
	
	@ManyToOne(cascade = {CascadeType.PERSIST})
	private Volontario volontario;
	
	@ManyToOne
	private Area area;
	
	@OneToOne(cascade =  CascadeType.ALL)
	private CartellaClinica cartellaClinica;
	
	@OneToMany(mappedBy = "animale", cascade = {CascadeType.REMOVE})
	private List<RichiestaAdozione> richiesteDiAdozione;
	
	// Costruttori //
	
	public Animale() {}
	
	public Animale(String nome, Specie specie, String razza, LocalDate dataNascita, LocalDate dataArrivo, String descrizione, String immagineUrl) {

		this.nome = nome;
		this.specie = specie;
		this.razza = razza;
		this.dataNascita = dataNascita;
		this.dataArrivo = dataArrivo;
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

	public Specie getSpecie() {
		return specie;
	}

	public void setSpecie(Specie specie) {
		this.specie = specie;
	}

	public String getRazza() {
		return razza;
	}

	public void setRazza(String razza) {
		this.razza = razza;
	}

	public LocalDate getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}

	public LocalDate getDataArrivo() {
		return dataArrivo;
	}

	public void setDataArrivo(LocalDate dataArrivo) {
		this.dataArrivo = dataArrivo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Volontario getVolontario() {
		return volontario;
	}

	public void setVolontario(Volontario volontario) {
		this.volontario = volontario;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public CartellaClinica getCartellaClinica() {
		return cartellaClinica;
	}

	public void setCartellaClinica(CartellaClinica cartellaClinica) {
		this.cartellaClinica = cartellaClinica;
	}

	public List<RichiestaAdozione> getRichiesteDiAdozione() {
		return richiesteDiAdozione;
	}

	public void setRichiesteDiAdozione(List<RichiestaAdozione> richiesteDiAdozione) {
		this.richiesteDiAdozione = richiesteDiAdozione;
	}
	
	public String getImmagineUrl() {
		return immagineUrl;
	}

	public void setImmagineUrl(String immagineUrl) {
		this.immagineUrl = immagineUrl;
	}

	// HashCode And Equals //

	@Override
	public int hashCode() {
		return Objects.hash(dataNascita, nome, razza, specie);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Animale other = (Animale) obj;
		return Objects.equals(dataNascita, other.dataNascita) && Objects.equals(nome, other.nome)
				&& Objects.equals(razza, other.razza) && specie == other.specie;
	}


}
