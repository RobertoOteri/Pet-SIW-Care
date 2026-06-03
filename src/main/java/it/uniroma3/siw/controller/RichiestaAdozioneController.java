package it.uniroma3.siw.controller;

import java.time.LocalDateTime;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.RichiestaAdozione;
import it.uniroma3.siw.model.Stato;
import it.uniroma3.siw.service.AnimaleService;
import it.uniroma3.siw.service.CredenzialiService;
import it.uniroma3.siw.service.RichiestaAdozioneService;
import jakarta.validation.Valid;

@Controller
public class RichiestaAdozioneController {
	
	private RichiestaAdozioneService richiestaAdozioneService;
	private AnimaleService animaleService;
	private CredenzialiService credenzialiService;
	
	public RichiestaAdozioneController(RichiestaAdozioneService richiestaAdozioneService,AnimaleService animaleService) {
		this.richiestaAdozioneService = richiestaAdozioneService;
		this.animaleService = animaleService;
		this.credenzialiService = credenzialiService;
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
		model.addAttribute("animali", this.animaleService.findAll());
		return "utenti/richieste/form";
	}
	
	@PostMapping("/utenti/richieste/{id}/modifica")
	public String saveModificaRichiesta(@PathVariable ("id") Long id,@Valid @ModelAttribute("richiesta") RichiestaAdozione richiesta,
			BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("animali", this.animaleService.findAll());
			return "utenti/richieste/form";
		}
		else {
			richiesta.setId(id);
			richiesta.setDataOra(LocalDateTime.now());
			richiesta.setStato(Stato.IN_ATTESA);
			this.richiestaAdozioneService.save(richiesta);
			return "redirect:/admin/richieste" + id;
		}
	}
	
	@PostMapping("/admin/richieste/{id}/approva")
	public String approvaRichiesta(@PathVariable("id") Long id) {
		RichiestaAdozione richiestaAdozione = this.richiestaAdozioneService.findById(id);
		richiestaAdozione.setStato(Stato.APPROVATA);
		richiestaAdozione.setDataOraAccettazione(LocalDateTime.now());
		this.richiestaAdozioneService.save(richiestaAdozione);
		return "redirect:/admin/richieste";
	}
	
	@PostMapping("/admin/richieste/{id}/rifiuta")
	public String rifiutaRichiesta(@PathVariable("id") Long id) {
		RichiestaAdozione richiestaAdozione = this.richiestaAdozioneService.findById(id);
		richiestaAdozione.setStato(Stato.RIFIUTATA);
		richiestaAdozione.setDataOraRifiuto(LocalDateTime.now());
		this.richiestaAdozioneService.save(richiestaAdozione);
		return "redirect:/admin/richieste";
	}
	
	@GetMapping("/richieste-utente")
	public String listRichiesteUtente(Model model) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		
		Credenziali credenziali = this.credenzialiService.findByUsername(username);
		Long utenteId = credenziali.getUtente().getId();
		
		model.addAttribute("richieste", this.richiestaAdozioneService.findAllByUtenteId(utenteId));
		
		return "utenti/richieste/list";
	}
}
