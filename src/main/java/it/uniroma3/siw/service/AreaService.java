package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Area;
import it.uniroma3.siw.repository.AreaRepository;

@Service
public class AreaService {
	
	private AreaRepository areaRepository;
	
	public AreaService(AreaRepository areaRepository) {
		this.areaRepository = areaRepository;
	}
	
	@Transactional(readOnly = true)
	public List<Area> findAll(){
		return (List<Area>) this.areaRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Area findById(Long id) {
		return this.areaRepository.findById(id).get();
	}
	
	@Transactional
	public Area save(Area area) {
		return this.areaRepository.save(area);
	}
	
	@Transactional
	public void deleteById(Long id) {
		this.areaRepository.deleteById(id);
	}
	
}


