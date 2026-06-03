package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.RichiestaAdozione;

public interface RichiestaAdozioneRepository extends CrudRepository<RichiestaAdozione,Long>{
	
	public List<RichiestaAdozione> findAllByUtenteId(Long id);
}
