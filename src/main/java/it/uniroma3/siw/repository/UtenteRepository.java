package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.*;
public interface UtenteRepository extends CrudRepository<Utente, Long> {

	public Utente findByEmail(String email);
}
