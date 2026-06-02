package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Area;
import it.uniroma3.siw.service.AreaService;
import jakarta.validation.Valid;

@Controller
public class AreaController {

	private AreaService areaService;
	
	public AreaController(AreaService areaService) {
		this.areaService = areaService;
	}
	
	@GetMapping("/aree")
	public String getAree(Model model) {
		List<Area> aree = this.areaService.findAll();
		model.addAttribute("aree", aree);
		return "aree/list";
	}
	
	@GetMapping("/aree/{id}")
	public String getArea(@PathVariable("id") Long id, Model model) {
		Area area = this.areaService.findById(id);
		model.addAttribute("area", area);
		return "aree/show";
	}
	
	@GetMapping("/admin/aree/form")
	public String formNuovaArea(Model model) {
		model.addAttribute("area", new Area());
		return "admin/aree/form";
	}
	@PostMapping("/admin/aree")
	public String newArea(@Valid @ModelAttribute("area") Area area,BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "admin/aree/form";
		}
		else {
			this.areaService.save(area);
			return "redirect:/aree";
		}

	}
	
	@GetMapping("/admin/aree/{id}/elimina")
	public String deleteArea(@PathVariable("id") Long id) {
		this.areaService.deleteById(id);
		return "redirect:/aree";
	}
	
	@GetMapping("/admin/aree/{id}/modifica")
	public String modificaArea(@PathVariable("id") Long id, Model model) {
		Area area = this.areaService.findById(id);
		model.addAttribute("area", area);
		return "admin/aree/form";
	}
	
	@PostMapping("/admin/aree/{id}/modifica")
	public String salvaModificaArea(@PathVariable("id") Long id,
									@Valid @ModelAttribute("area") Area area,
									BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			return "admin/aree/form";
		}
		area.setId(id);
		this.areaService.save(area);
		return "redirect:/aree/" + id;
	}
}
