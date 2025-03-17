package rahma.backend.gestionPDEK.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rahma.backend.gestionPDEK.Entity.*;
import rahma.backend.gestionPDEK.Repository.*;
import rahma.backend.gestionPDEK.ServicesImplementation.ServicePDEKImplimenetation;

@RestController
@RequestMapping("/operations/SertissageIDC")
public class SertissageIDCController {
	   @Autowired private SertissageIDCRepository sertissageNormalRepository ; 
       @Autowired private ServicePDEKImplimenetation servicePDEK ; 


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
 /*    @GetMapping("/pdekExiste")
    public PdekDTO recupererNumCycleSiPDEKExist(
    		  @RequestParam String sectionFil,
              @RequestParam int  segment,
              @RequestParam Plant nomPlant,
              @RequestParam String nomProjet) {
    	return  servicePDEK.recupererPdekDTO(sectionFil, segment , nomPlant , nomProjet);     
    }*/
}
