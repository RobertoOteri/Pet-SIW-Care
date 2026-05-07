package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Animale;
import it.uniroma3.siw.service.AnimaleService;

@Controller
public class AnimaleController {
	
	private AnimaleService animaleService;
	
	@GetMapping("/admin/animali/form")
	public String formNuovoAnimale(Model model) {
		model.addAttribute("animale", new Animale());
		return "admin/animali/form";
	}
	
	@PostMapping("/admin/animali")
	public String newAnimale(@ModelAttribute("animale") Animale animale) {
		this.animaleService.save(animale);
		return "redirect:/animali";
	}
	
	@GetMapping("/animali")
	public String getAnimali(Model model) {
		List<Animale> animali = this.animaleService.findAll();
		model.addAttribute("animali", animali);
		return "animali/list";
	}
	
	@GetMapping("/animale/{id}")
	public String getAnimale(@PathVariable("id") Long id, Model model) {
		Animale animale = this.animaleService.findById(id);
		model.addAttribute("animale", animale);
		return "animali/show";
	}

}
