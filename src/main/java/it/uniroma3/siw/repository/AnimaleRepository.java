package it.uniroma3.siw.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Animale;
import it.uniroma3.siw.model.Area;
import it.uniroma3.siw.model.Specie;

public interface AnimaleRepository extends CrudRepository<Animale, Long> {
	
	public List<Animale> findByAreaNotOrAreaIsNull(Area area);
	
	@Override
	@EntityGraph(attributePaths = {"area", "volontario", "cartellaClinica"})
	public Optional<Animale> findById(Long id);

}
