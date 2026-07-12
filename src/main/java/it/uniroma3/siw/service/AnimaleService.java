package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.repository.AnimaleRepository;
import it.uniroma3.siw.model.*;

@Service
public class AnimaleService {
	
	private AnimaleRepository animaleRepository;
	
	public AnimaleService(AnimaleRepository animaleRepository) {
		this.animaleRepository = animaleRepository;
	}
	
	@Transactional
	public Animale save(Animale animale) {
		return this.animaleRepository.save(animale);
	}
	
	@Transactional(readOnly = true)
	public List<Animale> findAll(){
		return (List<Animale>) this.animaleRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Animale findById(Long id) {
		return animaleRepository.findById(id).get();
	}
	
	@Transactional
	public void deleteById(Long id) {
		animaleRepository.deleteById(id);
	}
	
	@Transactional(readOnly = true)
	public List<Animale> findAllNotInArea(Area area){
		return this.animaleRepository.findByAreaNotOrAreaIsNull(area);
	}
	
	@Transactional(readOnly = true)
	public List<Animale> findAllById(List<Long> ids){
		return (List<Animale>) this.animaleRepository.findAllById(ids);
	}
	
	public List<Animale> findAllBySpecie(Specie specie){
		return this.animaleRepository.findAllBySpecie(specie);
	}
}
