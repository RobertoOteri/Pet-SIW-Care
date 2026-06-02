package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Animale;
import it.uniroma3.siw.model.Specie;
import it.uniroma3.siw.service.AnimaleService;
import jakarta.validation.Valid;

@Controller
public class AnimaleController {
	
	private AnimaleService animaleService;
	
	public AnimaleController(AnimaleService animaleService) {
		this.animaleService = animaleService;
	}
	
	@GetMapping("/admin/animali/form")
	public String formNuovoAnimale(Model model) {
		model.addAttribute("animale", new Animale());
		model.addAttribute("specie",Specie.values());
		return "admin/animali/form";
	}
	
	@PostMapping("/admin/animali")
	public String newAnimale(@Valid @ModelAttribute("animale") Animale animale) {
		this.animaleService.save(animale);
		return "redirect:/animali";
	}
	
	@GetMapping("/animali")
	public String getAnimali(Model model) {
		List<Animale> animali = this.animaleService.findAll();
		model.addAttribute("animali", animali);
		return "animali/list";
	}
	
	@GetMapping("/animali/{id}")
	public String getAnimale(@PathVariable("id") Long id, Model model) {
		Animale animale = this.animaleService.findById(id);
		model.addAttribute("animale", animale);
		model.addAttribute("cartellaClinica", animale.getCartellaClinica());
		return "animali/show";
	}
	
	@GetMapping("/admin/animali/{id}/elimina")
	public String deleteAnimale(@PathVariable("id") Long id) {
		this.animaleService.deleteById(id);
		return "redirect:/animali/";
	}
	
	@GetMapping("/admin/animali/{id}/modifica")
	public String formModificaAnimale(@PathVariable("id") Long id, Model model) {
		Animale animale = this.animaleService.findById(id);
		model.addAttribute("animale", animale);
		model.addAttribute("specie",Specie.values());
		return "admin/animali/form";
	}
	
	@PostMapping("/admin/animali/{id}/modifica")
	public String salvaModificaAnimale(@PathVariable("id") Long id,
									   @Valid @ModelAttribute("animale") Animale animale,
									   BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("specie",Specie.values());
			return "admin/animali/form";
		}
		animale.setId(id);
		this.animaleService.save(animale);
		return "redirect:/animali/" + id;
	}

}
