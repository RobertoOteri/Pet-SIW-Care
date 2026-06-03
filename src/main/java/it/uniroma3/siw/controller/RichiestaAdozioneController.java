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

import it.uniroma3.siw.model.Animale;
import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.RichiestaAdozione;
import it.uniroma3.siw.model.Stato;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.AnimaleService;
import it.uniroma3.siw.service.CredenzialiService;
import it.uniroma3.siw.service.RichiestaAdozioneService;
import jakarta.validation.Valid;

@Controller
public class RichiestaAdozioneController {
	
	private RichiestaAdozioneService richiestaAdozioneService;
	private AnimaleService animaleService;
	private CredenzialiService credenzialiService;
	
	public RichiestaAdozioneController(RichiestaAdozioneService richiestaAdozioneService,AnimaleService animaleService, CredenzialiService credenzialiService) {
		this.richiestaAdozioneService = richiestaAdozioneService;
		this.animaleService = animaleService;
		this.credenzialiService = credenzialiService;
	}
	
	@GetMapping("/admin/richieste")
	public String list(Model model) {
		model.addAttribute("richieste", this.richiestaAdozioneService.findAll());
		return "admin/richieste/list";
	}
	
	@GetMapping("/richieste/{id}")
	public String show(@PathVariable("id") Long id, Model model) {
		RichiestaAdozione richiesta = this.richiestaAdozioneService.findById(id);
		model.addAttribute("utente", richiesta.getUtente());
		model.addAttribute("richiesta",richiesta);
		return "richieste/show";
	}
	
	@GetMapping("/utenti/animali/{id}/nuova-richiesta")
	public String createForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("animale", this.animaleService.findById(id));
		model.addAttribute("richiesta", new RichiestaAdozione());
		return "utenti/richieste/form";
	}
	

	@PostMapping("/utenti/animali/{id}/salva-richiesta")
	public String form(@PathVariable("id") Long id, 
					   @Valid @ModelAttribute("richiesta") RichiestaAdozione richiesta,
					   BindingResult bindingResult,Model model) {
		
		Animale animale =this.animaleService.findById(id);
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		
		Credenziali credenziali = this.credenzialiService.findByUsername(username);
		Utente utente = credenziali.getUtente();
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("animale", animale);
			return "utenti/richieste/form";
		}
		else {
			richiesta.setDataOra(LocalDateTime.now());
			richiesta.setStato(Stato.IN_ATTESA);
			richiesta.setAnimale(animale);
			richiesta.setUtente(utente);
			this.richiestaAdozioneService.save(richiesta);
			return "redirect:/animali";
		}
	}
	
	@PostMapping("/utenti/richieste/{id}/elimina")
	public String delete(@PathVariable ("id") Long id) {
		this.richiestaAdozioneService.deleteById(id);
		return "redirect:/utenti";
	}
	
	@GetMapping("/utenti/richieste/{id}/modifica")
	public String formModificaRichiesta(@PathVariable ("id") Long id, Model model) {
		RichiestaAdozione richiesta = this.richiestaAdozioneService.findById(id);
		model.addAttribute("richiesta", richiesta);
		model.addAttribute("animale", richiesta.getAnimale());
		return "utenti/richieste/form";
	}
	
	@PostMapping("/utenti/richieste/{id}/modifica")
	public String saveModificaRichiesta(@PathVariable ("id") Long id,@Valid @ModelAttribute("richiesta") RichiestaAdozione richiesta,
			BindingResult bindingResult, Model model) {
		
		RichiestaAdozione richiestaOriginale = this.richiestaAdozioneService.findById(id);
		Animale animale = richiestaOriginale.getAnimale();
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		
		Credenziali credenziali = this.credenzialiService.findByUsername(username);
		Utente utente = credenziali.getUtente();
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("animale", animale);
			return "utenti/richieste/form";
		}
		else {
			richiesta.setId(id);
			richiesta.setDataOra(LocalDateTime.now());
			richiesta.setStato(Stato.IN_ATTESA);
			richiesta.setAnimale(animale);
			richiesta.setUtente(utente);
			this.richiestaAdozioneService.save(richiesta);
			return "redirect:/richieste/" + id;
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
}
