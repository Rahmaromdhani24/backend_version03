package rahma.backend.gestionPDEK.Controllers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rahma.backend.gestionPDEK.DTO.PdekDTO;
import rahma.backend.gestionPDEK.Entity.*;
import rahma.backend.gestionPDEK.Repository.*;
import rahma.backend.gestionPDEK.ServicesImplementation.PDEK_ServiceImplimenetation;

@RestController
@RequestMapping("/operations/SertissageNormal")
public class SertissageNormalController {
	   @Autowired private SertissageNormalRepository sertissageNormalRepository ; 
       @Autowired private PDEK_ServiceImplimenetation servicePDEK ; 


       @GetMapping("/sectionsFils")
       public List<String> getSectionsFils() {
           return SertissageNormal.SECTIONS_FILS_SERTISSAGE.keySet().stream()
                   .sorted((s1, s2) -> {
                       // Extraire les valeurs numériques et comparer
                       double v1 = Double.parseDouble(s1.split(" ")[0]);
                       double v2 = Double.parseDouble(s2.split(" ")[0]);
                       return Double.compare(v1, v2);
                   })
                   .collect(Collectors.toList());
       }

    @GetMapping("/codesControles")
    public List<String> getCodesControles() {
        return SertissageNormal.CODES_CONTROLES;
    }
    
    @GetMapping("/controle/{code}")
    public String getDescriptionForCode(@PathVariable String code) {
        return SertissageNormal.getDescriptionForCode(code);
    }
  //sections/0.35 mm²/Hauteur_Sertissage
  @GetMapping("/sections/{section}/{element}")
  public String getElementValue(@PathVariable String section, @PathVariable String element) {
      // Vérifier si la section existe
      String[] values = SertissageNormal.SECTIONS_FILS_SERTISSAGE.get(section);
      if (values == null) {
          return "Section non trouvée";
      }

      // Rechercher l'élément demandé et extraire uniquement la valeur après ": "
      Optional<String> result = Stream.of(values)
              .filter(val -> val.startsWith(element + ":"))
              .map(val -> val.split(": ")[1]) // Extraction de la valeur
              .findFirst();

      return result.orElse("Élément non trouvé");
  }





/*

     @GetMapping("/pdekExiste")
    public PdekDTO recupererNumCycleSiPDEKExist(
    		  @RequestParam String sectionFil,
              @RequestParam int  segment,
              @RequestParam Plant nomPlant,
              @RequestParam String nomProjet) {
    	return  servicePDEK.recupererPdekDTO(sectionFil, segment , nomPlant , nomProjet);     
    }*/
}
