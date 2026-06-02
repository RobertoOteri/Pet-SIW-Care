package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Volontario;
import it.uniroma3.siw.service.VolontarioService;
import jakarta.validation.Valid;

@Controller
public class VolontarioController {
	
	private VolontarioService volontarioService;
	
	public VolontarioController(VolontarioService volontarioService) {
		this.volontarioService = volontarioService;
	}
	
	@GetMapping("/volontari")
	public String list(Model model) {
		model.addAttribute("volontari", this.volontarioService.findAll());
		return "volontari/list";
	}
	
	@GetMapping("/volontari/{id}")
	public String show(@PathVariable ("id")Long id, Model model) {
		Volontario v = this.volontarioService.findById(id);
		model.addAttribute("volontario", v);
		return "volontari/show";
	}
	
	@GetMapping("/admin/volontari/new")
	public String createForm(Model model) {
		model.addAttribute("volontario", new Volontario());
		return "admin/volontari/form";
	}
	
	@PostMapping("/admin/volontari")
	public String save(@Valid @ModelAttribute("volontario")Volontario volontario, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "admin/volontari/form";
		}
		else {
			this.volontarioService.save(volontario);
			return "redirect:/volontari";
		}

	}
	
	@GetMapping("/admin/volontari/{id}/elimina")
	public String delete(@PathVariable ("id") Long id) {
		this.volontarioService.deleteById(id);
		return "redirect:/volontari";
	}
	
	@GetMapping("/admin/volontari/{id}/modifica")
	public String formModificaVolontario(@PathVariable ("id") Long id,Model model) {
		model.addAttribute("volontario",this.volontarioService.findById(id));
		return "admin/volontari/form";
	}
	
	@PostMapping("/admin/volontari/{id}/modifica")
	public String saveVolontarioModificato(@PathVariable("id") Long id,@Valid @ModelAttribute("volontario") Volontario volontario, 
			BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "admin/volontari/form";
		}
		else {
			volontario.setId(id);
			this.volontarioService.save(volontario);
			return "redirect:/volontari/" + id;
		}
	}
	
	
}
