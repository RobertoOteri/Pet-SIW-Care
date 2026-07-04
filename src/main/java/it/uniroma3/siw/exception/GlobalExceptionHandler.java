package it.uniroma3.siw.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(AccessoVietatoException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public String handleAccessoVietato(AccessoVietatoException e , Model model) {
		model.addAttribute("errorMessage", "Non hai i permessi necessari per accedere a questa pagina");
		return "error/403";
	}
}
