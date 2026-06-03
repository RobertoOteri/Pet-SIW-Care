package it.uniroma3.siw.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CredenzialiService;
import it.uniroma3.siw.service.UtenteService;

@Controller
public class UtenteController {
	
	private UtenteService utenteService;
	
	private CredenzialiService credenzialiService;
	
	public UtenteController(UtenteService utenteService, CredenzialiService credenzialiService) {
		this.utenteService = utenteService;
		this.credenzialiService = credenzialiService;
	}
	
	
	@GetMapping("/utenti")
	public String mostraProfilo(Model model) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		Credenziali credenziali = this.credenzialiService.findByUsername(username);
		Utente utente = credenziali.getUtente();
		model.addAttribute("utente", utente);
		model.addAttribute("richieste", utente.getRichiesteAdozione());
		return "utenti/show";	
	}
	
	
	
	@GetMapping("/utenti/{id}")
	public String mostraUtente(@PathVariable("id") Long id, Model model) {
		Utente utente = this.utenteService.findById(id);
		model.addAttribute("utente", utente);
		model.addAttribute("richieste", utente.getRichiesteAdozione());
		return "utenti/show";
	}
	

}
