package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.uniroma3.siw.repository.AnimaleRepository;
import it.uniroma3.siw.model.*;

@Service
public class AnimaleService {
	
	private AnimaleRepository animaleRepository;
	
	public AnimaleService(AnimaleRepository animaleRepository) {
		this.animaleRepository = animaleRepository;
	}
	
	public Animale save(Animale animale) {
		return this.animaleRepository.save(animale);
	}
	
	public List<Animale> findAll(){
		return (List<Animale>) this.animaleRepository.findAll();
	}
	
	public Animale findById(Long id) {
		return animaleRepository.findById(id).get();
	}
	
	public void deleteById(Long id) {
		animaleRepository.deleteById(id);
	}
	
	public List<Animale> findAllNotInArea(Area area){
		return this.animaleRepository.findByAreaNotOrAreaIsNull(area);
	}
	
	public List<Animale> findAllById(List<Long> ids){
		return (List<Animale>) this.animaleRepository.findAllById(ids);
	}
}
