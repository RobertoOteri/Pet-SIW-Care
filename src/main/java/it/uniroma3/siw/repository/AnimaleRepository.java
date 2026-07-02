package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Animale;
import it.uniroma3.siw.model.Area;

public interface AnimaleRepository extends CrudRepository<Animale, Long> {
	
	public List<Animale> findByAreaNotOrAreaIsNull(Area area);
	
	@Override
    @EntityGraph(attributePaths = {"area", "volontario", "cartellaClinica"})
	public List<Animale> findAll();

}
