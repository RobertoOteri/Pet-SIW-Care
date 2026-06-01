package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.RichiestaAdozione;
import it.uniroma3.siw.repository.RichiestaAdozioneRepository;

@Service
public class RichiestaAdozioneService {
	
	private RichiestaAdozioneRepository richiestaAdozioneRepository;
	
	public RichiestaAdozioneService(RichiestaAdozioneRepository richiestaAdozioneRepository) {
		this.richiestaAdozioneRepository = richiestaAdozioneRepository;
	}
	
	public RichiestaAdozione findById(Long id) {
		return this.richiestaAdozioneRepository.findById(id).get();
	}
	
	public List<RichiestaAdozione> findAll(){
		return (List<RichiestaAdozione>) this.richiestaAdozioneRepository.findAll();
	}
	
	public RichiestaAdozione save(RichiestaAdozione richiestaAdozione) {
		return this.richiestaAdozioneRepository.save(richiestaAdozione);
	}
	
}
