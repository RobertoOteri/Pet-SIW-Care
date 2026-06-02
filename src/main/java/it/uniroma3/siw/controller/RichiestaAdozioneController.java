package it.uniroma3.siw.controller;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.RichiestaAdozione;
import it.uniroma3.siw.model.Stato;
import it.uniroma3.siw.service.AnimaleService;
import it.uniroma3.siw.service.RichiestaAdozioneService;
import jakarta.validation.Valid;

@Controller
public class RichiestaAdozioneController {
	
	private RichiestaAdozioneService richiestaAdozioneService;
	private AnimaleService animaleService;
	
	public RichiestaAdozioneController(RichiestaAdozioneService richiestaAdozioneService,AnimaleService animaleService) {
		this.richiestaAdozioneService = richiestaAdozioneService;
		this.animaleService = animaleService;
	}
	
	@GetMapping("/admin/richieste")
	public String list(Model model) {
		model.addAttribute("richieste", this.richiestaAdozioneService.findAll());
		return "admin/richieste/list";
	}
	
	@GetMapping("/admin/richieste/{id}")
	public String show(@PathVariable("id") Long id, Model model) {
		RichiestaAdozione richiesta = this.richiestaAdozioneService.findById(id);
		model.addAttribute("richiesta",richiesta);
		return "admin/richieste/show";
	}
	
	@GetMapping("/utenti/richieste/new")
	public String createForm(Model model) {
		model.addAttribute("richiesta", new RichiestaAdozione());
		model.addAttribute("animali", animaleService.findAll());
		return "utenti/richieste/form";
	}
	

	@PostMapping("/utenti/richieste")
	public String form(@Valid @ModelAttribute("richiesta") RichiestaAdozione richiesta, BindingResult bindingResult,Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("animali",animaleService.findAll());
			return "utenti/richieste/form";
		}
		else {
			richiesta.setDataOra(LocalDateTime.now());
			richiesta.setStato(Stato.IN_ATTESA);
			this.richiestaAdozioneService.save(richiesta);
			return "redirect:/admin/richieste";
		}
	}
	
	@GetMapping("/utenti/richieste/{id}/elimina")
	public String delete(@PathVariable ("id") Long id) {
		this.richiestaAdozioneService.deleteById(id);
		return "redirect:/admin/richieste";
	}
	
	@GetMapping("/utenti/richieste/{id}/modifica")
	public String formModificaRichiesta(@PathVariable ("id") Long id, Model model) {
		model.addAttribute("richiesta", this.richiestaAdozioneService.findById(id));
		return "utenti/richieste/form";
	}
	
	@PostMapping("/utenti/richieste/{id}/modifica")
	public String saveModificaRichiesta(@PathVariable ("id") Long id,@Valid @ModelAttribute("richiesta") RichiestaAdozione richiesta,
			BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "utenti/richieste/form";
		}
		else {
			richiesta.setId(id);
			this.richiestaAdozioneService.save(richiesta);
			return "redirect:/admin/richieste";
		}
	}
}
