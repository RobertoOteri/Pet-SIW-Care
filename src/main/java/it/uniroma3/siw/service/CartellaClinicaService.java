package it.uniroma3.siw.service;

import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.CartellaClinica;
import it.uniroma3.siw.repository.CartellaClinicaRepository;

@Service
public class CartellaClinicaService {
	
	private CartellaClinicaRepository cartellaClinicaRepository;
	
	public CartellaClinicaService(CartellaClinicaRepository cartellaClinicaRepository) {
		this.cartellaClinicaRepository = cartellaClinicaRepository;
	}
	
	public CartellaClinica findById(Long id) {
		return this.cartellaClinicaRepository.findById(id).get();
	}
	
	public CartellaClinica save(CartellaClinica cartellaClinica) {
		return this.cartellaClinicaRepository.save(cartellaClinica);
	}

}
