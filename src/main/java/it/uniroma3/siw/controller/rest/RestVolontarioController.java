package it.uniroma3.siw.controller.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.uniroma3.siw.model.Volontario;
import it.uniroma3.siw.service.VolontarioService;

@RestController
@RequestMapping("/rest/volontari")
public class RestVolontarioController {
	
	private VolontarioService volontarioService;
	
	public RestVolontarioController(VolontarioService volontarioService) {
		this.volontarioService = volontarioService;
	}
	
	@GetMapping
	public List<Volontario> list(){
		return this.volontarioService.findAll();
	}

}
