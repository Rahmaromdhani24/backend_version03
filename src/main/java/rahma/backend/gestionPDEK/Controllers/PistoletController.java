package rahma.backend.gestionPDEK.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rahma.backend.gestionPDEK.ServicesImplementation.PistoletServiceImplimenetation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rahma.backend.gestionPDEK.DTO.PistoletDTO;
import rahma.backend.gestionPDEK.Entity.Pistolet;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/operations/pistolet")
@RequiredArgsConstructor
public class PistoletController {

	 @Autowired  private  PistoletServiceImplimenetation pistoletService;

	 @PostMapping("/ajouterPDEK/{matricule}")
	 public ResponseEntity<String> ajouterPDEK(@PathVariable int matricule, @RequestBody Pistolet pistolet) {
	     try {
	         pistoletService.ajouterPistolet(matricule, pistolet);
	         return ResponseEntity.status(HttpStatus.CREATED).body("PDEK ajouté avec succès");
	     } catch (Exception e) {
	         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur : " + e.getMessage());
	     }
	 }



    
}
