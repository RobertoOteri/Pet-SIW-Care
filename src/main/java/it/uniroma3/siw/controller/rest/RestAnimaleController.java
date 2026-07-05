package it.uniroma3.siw.controller.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.uniroma3.siw.model.Animale;
import it.uniroma3.siw.service.AnimaleService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/rest/animali")
public class RestAnimaleController {
	
	private AnimaleService animaleService;
	
	public RestAnimaleController(AnimaleService animaleService) {
		this.animaleService = animaleService;
	}
	
	@GetMapping
	public List<Animale> list(){
		return this.animaleService.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Animale> create(@Valid @RequestBody Animale animale){
		Animale saved = this.animaleService.save(animale);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Animale> update(@PathVariable Long id, @Valid @RequestBody Animale animale){
		Animale a = this.animaleService.findById(id);
		if(a == null) {
			return ResponseEntity.notFound().build();
		}
		animale.setId(id);
		this.animaleService.save(animale);
		return ResponseEntity.ok(animale);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		this.animaleService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
