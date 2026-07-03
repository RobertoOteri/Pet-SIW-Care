package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.repository.VolontarioRepository;
import it.uniroma3.siw.model.Volontario;

@Service
public class VolontarioService {
	
	private VolontarioRepository volontarioRepository;
	
	public VolontarioService(VolontarioRepository volontarioRepository) {
		this.volontarioRepository = volontarioRepository;
	}
	
	@Transactional(readOnly = true)
	public Volontario findById(Long id) {
		return this.volontarioRepository.findById(id).get();
	}
	
	@Transactional(readOnly = true)
	public List<Volontario> findAll(){
		return (List<Volontario>) this.volontarioRepository.findAll();
	}
	
	@Transactional
	public Volontario save(Volontario volontario) {
		return this.volontarioRepository.save(volontario);
	}
	
	@Transactional
	public void deleteById(Long id) {
		this.volontarioRepository.deleteById(id);
	}
	
}
