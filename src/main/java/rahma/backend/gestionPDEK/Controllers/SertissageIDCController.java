package rahma.backend.gestionPDEK.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rahma.backend.gestionPDEK.Entity.*;
import rahma.backend.gestionPDEK.Repository.*;
import rahma.backend.gestionPDEK.ServicesImplementation.SertissageIDC_ServiceImplimenetation;

@RestController
@RequestMapping("/operations/SertissageIDC")
public class SertissageIDCController {
	
	   
       @Autowired private SertissageIDC_ServiceImplimenetation serviceSertissageIDC ; 
       @Autowired private PdekRepository pdekRepository  ; 
       @Autowired private SertissageIDCRepository sertissageIDCRepository ; 

    @GetMapping("/sectionsFils")
    public List<String> getSectionsFils() {
        return SertissageIDC.SECTIONS_FILS;
    }

    @GetMapping("/codesControles")
    public List<String> getCodesControles() {
        return SertissageIDC.CODES_CONTROLES;
    }
    
    @GetMapping("/controle/{code}")
    public String getDescriptionForCode(@PathVariable String code) {
        return SertissageIDC.getDescriptionForCode(code);
    }
    @GetMapping("/findUniquePDEK")
    public ResponseEntity<?> findUniquePDEK_SertissageIDC(
            @RequestParam String sectionFilSelectionne,
            @RequestParam int segment,
            @RequestParam String nomPlant,
            @RequestParam String projetName) {
        try {
            Optional<PDEK> pdek = pdekRepository.findUniquePDEK_SertissageIDC(
                    sectionFilSelectionne, segment, Plant.valueOf(nomPlant), projetName);
            
            if (pdek.isPresent()) {
                return ResponseEntity.ok(pdek.get());  // Retourne l'objet PDEK trouvé
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun PDEK trouvé");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Erreur : Plant invalide - " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Erreur serveur : " + e.getMessage());
        }
    }

    
    @PostMapping("/ajouterPdekSertissageIDC")
    public ResponseEntity<String> ajouterSertissageIDC(
    		 @RequestParam int matricule,
    		 @RequestParam String nomProjet,
             @RequestBody SertissageIDC sertissageIDC) {
        try {
        	serviceSertissageIDC.ajoutPDEK_SertissageIDC( sertissageIDC , matricule, nomProjet);
            return ResponseEntity.status(HttpStatus.CREATED).body("Sertissage IDC ajouté avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur : " + e.getMessage());
        }
    }

    @GetMapping("/numCycleMax")
    public int recupererMaxCycle(@RequestParam String sectionFilSelectionner,
    		                     @RequestParam int segment ,
    		                     @RequestParam Plant nomPlant , 
    		                     @RequestParam String nomProjet ) {
        Optional<PDEK> pdekExiste = pdekRepository.findUniquePDEK_SertissageIDC(sectionFilSelectionner,segment , nomPlant , nomProjet);

        if (pdekExiste.isPresent()) {
            PDEK pdek = pdekExiste.get();
            return sertissageIDCRepository.findByPdekSertissageIDC_Id(pdek.getId()).stream()
                    .mapToInt(SertissageIDC::getNumCycle )
                    .max()
                    .orElse(0); 
        } else {
            return 0;
        }
    }

}
