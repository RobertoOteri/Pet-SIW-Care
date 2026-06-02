package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.uniroma3.siw.repository.VolontarioRepository;
import it.uniroma3.siw.model.Volontario;

@Service
public class VolontarioService {
	
	private VolontarioRepository volontarioRepository;
	
	public VolontarioService(VolontarioRepository volontarioRepository) {
		this.volontarioRepository = volontarioRepository;
	}
	
	public Volontario findById(Long id) {
		return this.volontarioRepository.findById(id).get();
	}
	
	public List<Volontario> findAll(){
		return (List<Volontario>) this.volontarioRepository.findAll();
	}
	
	public Volontario save(Volontario volontario) {
		return this.volontarioRepository.save(volontario);
	}
	
	public void deleteById(Long id) {
		this.volontarioRepository.deleteById(id);
	}
	
}
