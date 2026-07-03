package it.uniroma3.siw.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.CartellaClinica;
import it.uniroma3.siw.repository.CartellaClinicaRepository;

@Service
public class CartellaClinicaService {
	
	private CartellaClinicaRepository cartellaClinicaRepository;
	
	public CartellaClinicaService(CartellaClinicaRepository cartellaClinicaRepository) {
		this.cartellaClinicaRepository = cartellaClinicaRepository;
	}
	
	@Transactional(readOnly = true)
	public CartellaClinica findById(Long id) {
		return this.cartellaClinicaRepository.findById(id).get();
	}
	
	@Transactional
	public CartellaClinica save(CartellaClinica cartellaClinica) {
		return this.cartellaClinicaRepository.save(cartellaClinica);
	}

}
