package rahma.backend.gestionPDEK.ServicesImplementation;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rahma.backend.gestionPDEK.DTO.PdekDTO;
import rahma.backend.gestionPDEK.Entity.PDEK;
import rahma.backend.gestionPDEK.Entity.Plant;
import rahma.backend.gestionPDEK.Entity.TypesOperation;
import rahma.backend.gestionPDEK.Repository.PdekRepository;
import rahma.backend.gestionPDEK.Repository.ProjetRepository;
import rahma.backend.gestionPDEK.ServicesInterfaces.PDEKService;

@Service
public class ServicePDEKImplimenetation implements PDEKService {

	
	@Autowired PdekRepository pdekRepository ;
	@Autowired ProjetRepository projetRepository  ;



	public boolean verifierExistencePDEK_soudureUltrason(String sectionFil, int segment ,Plant plant , String nomProjet ) {
		  Optional<PDEK> pdekExiste = pdekRepository.findUniquePDEK_SoudureUtrason(sectionFil , segment , plant , nomProjet );
	    return pdekExiste.isPresent();
	}

	public PdekDTO recupererPdekSoudureUltrason(String sectionFil, int segment ,Plant plant , String nomProjet) {
	    return pdekRepository.findUniquePDEK_SoudureUtrason(sectionFil , segment , plant , nomProjet )
	        .map(pdek -> new PdekDTO(
	                pdek.getId(),
	                pdek.getSectionFil(),
	                nomProjet , 
	                pdek.getDateCreation(),
	                pdek.getFrequenceControle()
	        ))
	        .orElse(null); // Ou Optional<PdekDTO> si tu veux gérer l'absence
	}

	
	public boolean verifierExistencePDEK_Torsadage(String sectionFil, int segment ,Plant plant , String nomProjet ) {
		  Optional<PDEK> pdekExiste = pdekRepository.findUniquePDEK_Torsadage(sectionFil , segment , plant , nomProjet );
	    return pdekExiste.isPresent();
	}

	public PdekDTO recupererPdekTorsadag(String sectionFil, int segment ,Plant plant , String nomProjet) {
	    return pdekRepository.findUniquePDEK_Torsadage(sectionFil , segment , plant , nomProjet )
	        .map(pdek -> new PdekDTO(
	                pdek.getId(),
	                pdek.getSectionFil(),
	                nomProjet , 
	                pdek.getDateCreation(),
	                pdek.getFrequenceControle()
	        ))
	        .orElse(null); // Ou Optional<PdekDTO> si tu veux gérer l'absence
	}
}
