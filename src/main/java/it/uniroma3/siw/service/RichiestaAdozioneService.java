package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.RichiestaAdozione;
import it.uniroma3.siw.repository.RichiestaAdozioneRepository;

@Service
public class RichiestaAdozioneService {
	
	private RichiestaAdozioneRepository richiestaAdozioneRepository;
	
	public RichiestaAdozioneService(RichiestaAdozioneRepository richiestaAdozioneRepository) {
		this.richiestaAdozioneRepository = richiestaAdozioneRepository;
	}
	
	@Transactional(readOnly = true)
	public RichiestaAdozione findById(Long id) {
		return this.richiestaAdozioneRepository.findById(id).get();
	}
	
	@Transactional(readOnly = true)
	public List<RichiestaAdozione> findAll(){
		return (List<RichiestaAdozione>) this.richiestaAdozioneRepository.findAll();
	}
	
	@Transactional
	public RichiestaAdozione save(RichiestaAdozione richiestaAdozione) {
		return this.richiestaAdozioneRepository.save(richiestaAdozione);
	}
	
	@Transactional
	public void deleteById(Long id) {
		this.richiestaAdozioneRepository.deleteById(id);
	}
	
	@Transactional(readOnly = true)
	public List<RichiestaAdozione> findAllByUtenteId(Long id){
		return this.richiestaAdozioneRepository.findAllByUtenteId(id);
	}
	
	
}
