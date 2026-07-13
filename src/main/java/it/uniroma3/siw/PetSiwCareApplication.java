package it.uniroma3.siw;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import it.uniroma3.siw.model.Animale;
import it.uniroma3.siw.repository.AnimaleRepository;

@SpringBootApplication
public class PetSiwCareApplication implements CommandLineRunner {

	private AnimaleRepository animaleRepository;
	
	public PetSiwCareApplication(AnimaleRepository animaleRepository) {
		this.animaleRepository = animaleRepository;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(PetSiwCareApplication.class, args);
	}
	
	@Override
	@Transactional
	public void run(String... args) throws Exception{
		prestazioni();
	}
	
	private void prestazioni() {
		StopWatch watch = new StopWatch();
		watch.start("prestazioni");
		Animale animale = this.animaleRepository.findById(1L).get();
		animale.getArea().getNome();
		watch.stop();
		System.out.println(watch.prettyPrint());
	}
	
	//Prestazioni: 100% 0.0668276 seconds metodo: LAZY   (Con EntityGraph)
	//Prestazioni: 100% 0.0766634 seconds metodo: EAGER  (Con EntityGraph)
	
	//Prestazioni: 100% 0.0658747 seconds metodo: LAZY
	//Prestazioni: 100% 0.0736177 seconds metodo: EAGER

}
