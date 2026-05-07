package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Area;
import it.uniroma3.siw.repository.AreaRepository;

@Service
public class AreaService {
	
	private AreaRepository areaRepository;
	
	public AreaService(AreaRepository areaRepository) {
		this.areaRepository = areaRepository;
	}
	
	public List<Area> findAll(){
		return (List<Area>) this.areaRepository.findAll();
	}
	
	public Area findById(Long id) {
		return this.areaRepository.findById(id).get();
	}
	
	public Area save(Area area) {
		return this.areaRepository.save(area);
	}
	
	public void deleteById(Long id) {
		this.areaRepository.deleteById(id);
	}

}
