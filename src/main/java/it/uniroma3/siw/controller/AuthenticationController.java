package it.uniroma3.siw.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import it.uniroma3.siw.model.*;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CredenzialiService;
import it.uniroma3.siw.service.UtenteService;

@Controller
public class AuthenticationController {
	
	private CredenzialiService credenzialiService;
	private UtenteService utenteService;
	private PasswordEncoder passwordEncoder;

	public AuthenticationController(UtenteService utenteService, CredenzialiService credenzialiService,
									PasswordEncoder passwordEncoder) {
		this.credenzialiService = credenzialiService;
		this.utenteService = utenteService;
		this.passwordEncoder = passwordEncoder;
	}
	
	
	@GetMapping("/login")
	public String mostraFormLogin() {
		return "login";
	}
	
	@GetMapping("/register")
	public String mostraFormRegistrazione(Model model) {
		model.addAttribute("credenziali", new Credenziali());
		model.addAttribute("utente", new Utente());
		return "register";
	}
	
	@PostMapping("/register")
	public String salvaUtenteRegistrato(@Valid @ModelAttribute("credenziali") Credenziali credenziali,
										BindingResult credenzialiBindingResult,
										@Valid @ModelAttribute("utente") Utente utente,
										BindingResult utenteBindingResult, Model model) {

		if(credenzialiService.findByUsername(credenziali.getUsername())!=null) {
			credenzialiBindingResult.rejectValue("username", "duplicate", "Questo username esiste già");
		}
		
		if(utenteService.findByEmail(utente.getEmail()) != null) {
			utenteBindingResult.rejectValue("email", "duplicate",  "Questa email è gia associata a un account");
		}
		
		if(utenteBindingResult.hasErrors() || credenzialiBindingResult.hasErrors()) {
			return "register";
		}
		
		String password = credenziali.getPassword();
		String passwordCifrata = this.passwordEncoder.encode(password);
		credenziali.setRuolo("DEFAULT");
		credenziali.setUtente(utente);
		credenziali.setPassword(passwordCifrata);
		this.utenteService.save(utente);
		this.credenzialiService.save(credenziali);
		return "redirect:/login";
		
	}
	
	@GetMapping("/success")
	public String successoLogin() {
		return "redirect:/";
	}
}
