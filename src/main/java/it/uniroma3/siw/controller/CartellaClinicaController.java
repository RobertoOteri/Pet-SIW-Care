package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Animale;
import it.uniroma3.siw.model.CartellaClinica;
import it.uniroma3.siw.service.AnimaleService;
import it.uniroma3.siw.service.CartellaClinicaService;
import jakarta.validation.Valid;

@Controller
public class CartellaClinicaController {
	
	private CartellaClinicaService cartellaClinicaService;
	
	private AnimaleService animaleService;
	
	
	public CartellaClinicaController(CartellaClinicaService cartellaClinicaService, AnimaleService animaleService) {
		this.cartellaClinicaService = cartellaClinicaService;
		this.animaleService = animaleService;
	}
	
	@GetMapping("/admin/animali/{id}/nuova-cartella")
	public String formCartellaClinica(@PathVariable("id") Long id, Model model) {
		Animale animale = this.animaleService.findById(id);
		CartellaClinica cartellaClinica = new CartellaClinica();
		cartellaClinica.setAnimale(animale);
		model.addAttribute("animale", animale);
		model.addAttribute("cartellaClinica", cartellaClinica);
		return "admin/animali/formCartella";	
	}
	
	@PostMapping("/admin/animali/{id}/salva-cartella")
	public String salvaCartellaClinica(@PathVariable("id") Long id,
									   @Valid @ModelAttribute("cartellaClinica") CartellaClinica cartellaClinica,
									   BindingResult bindingResult, Model model) {
		Animale animale = this.animaleService.findById(id);
		if(bindingResult.hasErrors()) {
			model.addAttribute("animale", animale);
			return "admin/animali/formCartella";
		}
		animale.setCartellaClinica(cartellaClinica);
		cartellaClinica.setId(null);
		cartellaClinica.setAnimale(animale);
		this.cartellaClinicaService.save(cartellaClinica);
		this.animaleService.save(animale);
		
		return "redirect:/animali/" + id;
	}

}
