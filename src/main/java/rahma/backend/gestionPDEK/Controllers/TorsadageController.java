package rahma.backend.gestionPDEK.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rahma.backend.gestionPDEK.DTO.PdekDTO;
import rahma.backend.gestionPDEK.DTO.TorsadageDTO;
import rahma.backend.gestionPDEK.Entity.*;
import rahma.backend.gestionPDEK.Repository.*;
import rahma.backend.gestionPDEK.ServicesImplementation.PDEK_ServiceImplimenetation;
import rahma.backend.gestionPDEK.ServicesImplementation.TorsadageServiceImplimentation;

@RestController
@RequestMapping("/operations/torsadage")
public class TorsadageController {

	@Autowired  private TorsadageServiceImplimentation serviceTorsadage;
    @Autowired  private TorsadageRepository torsadageRepository ; 
    @Autowired  private PDEK_ServiceImplimenetation servicePDEK ; 
    @Autowired  private PdekRepository repositoryPDEK ; 


    @GetMapping("/specificationsMesure")
    public List<String> getSectionsFils() {
        return Torsadage.SPECIFICATIONS_MESURES;
    }

    @GetMapping("/codesControles")
    public List<String> getCodesControles() {
        return Torsadage.CODES_CONTROLES;
    }
    
    @GetMapping("/controle/{code}")
    public String getDescriptionForCode(@PathVariable String code) {
        return Torsadage.getDescriptionForCode(code);
    }

    /******************************* Partie PDEK *******************************/
     @GetMapping("/verifier-pdek")
    public boolean verifierPDEK(
            @RequestParam String specificationMesure,
            @RequestParam int  segment,
            @RequestParam Plant nomPlant,
            @RequestParam String nomProjet) {
        return servicePDEK.verifierExistencePDEK_Torsadage(specificationMesure,segment , nomPlant ,  nomProjet);
    }
    
    @GetMapping("/pdekExiste")
    public PdekDTO recupererNumCycleSiPDEKExist(
    		  @RequestParam String specificationMesure,
              @RequestParam int  segment,
              @RequestParam Plant nomPlant,
              @RequestParam String nomProjet) {
    	return  servicePDEK.recupererPdekTorsadag(specificationMesure, segment , nomPlant , nomProjet);     
    }
    
  @GetMapping("/numCycleMax")
    public int recupererMaxCycle(@RequestParam String specificationMesure,
    		                     @RequestParam int segment ,
    		                     @RequestParam Plant nomPlant , 
    		                     @RequestParam String nomProjet ) {
        Optional<PDEK> pdekExiste = repositoryPDEK.findUniquePDEK_Torsadage(specificationMesure,segment , nomPlant , nomProjet);

        if (pdekExiste.isPresent()) {
            PDEK pdek = pdekExiste.get();
            return torsadageRepository.findByPdekTorsadage_Id(pdek.getId()).stream()
                    .mapToInt(Torsadage::getNumeroCycle )
                    .max()
                    .orElse(0); 
        } else {
            return 0;
        }
    }

    @GetMapping("/torsadage-par-pdek")
    public List<TorsadageDTO> getSouduresParPdek(
            @RequestParam String sectionFil,
            @RequestParam String nomProjet,
            @RequestParam int segment,
            @RequestParam Plant plant) {
        return serviceTorsadage.recupererTorsadagesParPDEK(sectionFil,segment , plant ,  nomProjet);
    }
    
     @PostMapping("/ajouterPDEK")
    public ResponseEntity<String> ajouterTorsadageAvecPdek(
            @RequestBody Torsadage torsadage, 
            @RequestParam int matriculeOperateur, 
            @RequestParam String projet) {

        try {
            serviceTorsadage.ajoutPDEK_Torsadage(torsadage, matriculeOperateur, projet);
            return ResponseEntity.ok("Torsadage et PDEK ajoutés avec succès !");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de l'ajout : " + e.getMessage());
        }
    }

}
