package rahma.backend.gestionPDEK.ServicesImplementation;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rahma.backend.gestionPDEK.DTO.SoudureDTO;
import rahma.backend.gestionPDEK.Entity.*;
import rahma.backend.gestionPDEK.Repository.*;
import rahma.backend.gestionPDEK.ServicesInterfaces.ServiceSoudure;

@Service
public class ServiceSoudureImplimentation implements ServiceSoudure {

	 @Autowired    private PdekRepository pdekRepository;
	 @Autowired    private SoudureRepository soudureRepository;	
	 @Autowired    private UserRepository userRepository;	
	 @Autowired    private ProjetRepository projetRepository;	
	 @Autowired    private PdekPageRepository pdekPageRepository;	

	 
	 @Override
	public void ajoutPDEKSoudure(Soudure instanceSoudure, int matriculeOperateur , String projet) {
	
    String sectionFilSelectionner = instanceSoudure.getSectionFil() ; 
    User user = userRepository.findByMatricule(matriculeOperateur).get() ; 
    Soudure instance1 = new Soudure() ; 
    instance1.setCode(instanceSoudure.getCode());
    instance1.setSectionFil(sectionFilSelectionner);
    instance1.setDate(instanceSoudure.getDate());
    instance1.setDistanceBC(instanceSoudure.getDistanceBC());
    instance1.setGrendeurLot(instanceSoudure.getGrendeurLot());
    instance1.setLimitePelage(instanceSoudure.getLimitePelage());
    instance1.setNombreKanban(instanceSoudure.getNombreKanban());
    instance1.setNombreNoeud(instanceSoudure.getNombreNoeud());
    instance1.setPelageX1(instanceSoudure.getPelageX1());
    instance1.setPelageX2(instanceSoudure.getPelageX2());
    instance1.setPelageX3(instanceSoudure.getPelageX3());
    instance1.setPelageX4(instanceSoudure.getPelageX4());
    instance1.setPelageX5(instanceSoudure.getPelageX5());
    instance1.setPliage(instanceSoudure.getPliage());
    instance1.setQuantiteAtteint(instanceSoudure.getQuantiteAtteint());
    instance1.setTraction(instanceSoudure.getTraction());

    instance1.setEtendu(instanceSoudure.getEtendu());
    instance1.setMoyenne(instanceSoudure.getMoyenne());

	instance1.setUserSoudure(user);
 /***** Recuperation PDEK ID s'il exise ****************/
  Optional<PDEK> pdekExiste = pdekRepository.findUniquePDEK_SoudureUtrason(sectionFilSelectionner , user.getSegment() , user.getPlant() , projet );

    if (pdekExiste.isPresent()) {
        // Si le Pdek existe, tu peux le récupérer et effectuer tes opérations
        PDEK pdek = pdekExiste.get();
        instance1.setPdekSoudure(pdek);
        instance1.setUserSoudure(user);
        Integer dernierCycle = soudureRepository.findLastCycleByPdekSoudure_Id(pdek.getId());
        int nouveauCycle = (dernierCycle != null) ? dernierCycle + 1 : 1;
        instance1.setNumeroCycle(nouveauCycle);
        soudureRepository.save(instance1) ;
        if (!pdek.getUsersRempliePDEK().contains(user)) {
            pdek.getUsersRempliePDEK().add(user);
            //pdekRepository.save(pdek); // Sauvegarde de la mise à jour du PDEK
        }
        if (!pdek.getProjets().contains(projetRepository.findByNom(projet).get())) {
            pdek.getProjets().add(projetRepository.findByNom(projet).get());
            //pdekRepository.save(pdek); // Sauvegarde la mise à jour
        }
        Optional<PagePDEK> dernierePagePDEK = pdekPageRepository.findTopByPdek_IdOrderByIdDesc(pdek.getId());
        if (dernierePagePDEK.isPresent()) {
            PagePDEK dernierePage = dernierePagePDEK.get();
            
            if (instance1.getNumeroCycle() < 25) {
                instance1.setPagePDEK_soudures(dernierePage);
            } else if (instance1.getNumeroCycle()  == 25) {
                int nouveauPageNumber = dernierePage.getPageNumber() + 1;                
                PagePDEK nouvellePage = new PagePDEK();
                nouvellePage.setPdek(dernierePage.getPdek());
                nouvellePage.setPageNumber(nouveauPageNumber);
                nouvellePage.setStatus(false); 
                
                pdekPageRepository.save(nouvellePage);

                // Associer la soudure à la nouvelle page
                instance1.setPagePDEK_soudures(nouvellePage);
            }
            // Sauvegarder la soudure avec la bonne page associée
            soudureRepository.save(instance1);
        }


    } else {
       
    	PDEK newPDEK = new PDEK() ; 
    	newPDEK.setSectionFil(sectionFilSelectionner);
    	newPDEK.setNombreEchantillons("5 Piéces ");
    	newPDEK.setFrequenceControle(3245); 
    	newPDEK.setSegment(user.getSegment());
    	newPDEK.setNumMachine(user.getMachine());
    	newPDEK.setDateCreation(instanceSoudure.getDate());
    	newPDEK.setTypeOperation(TypesOperation.Soudure);
    	newPDEK.setPlant(user.getPlant());
    	newPDEK.setUsersRempliePDEK(List.of(user));
    	newPDEK.setFrequenceControle(3100);
    	newPDEK.setTotalPages(1);
    	instance1.setNumeroCycle(1);
    	pdekRepository.save(newPDEK)  ;
	  if (!newPDEK.getProjets().contains(projetRepository.findByNom(projet).get())) {
		  newPDEK.getProjets().add(projetRepository.findByNom(projet).get()); // Ajouter le projet au PDEK
		  projetRepository.findByNom(projet).get().getPdeks().add(newPDEK);
    	projetRepository.save(projetRepository.findByNom(projet).get());
    	instance1.setPdekSoudure(newPDEK);
    	soudureRepository.save(instance1) ; 
    	pdekPageRepository.save(new PagePDEK( 1 , false, newPDEK  )) ;
    	

    /***************/
    	Optional<PagePDEK> dernierePagePDEK = pdekPageRepository.findTopByPdek_IdOrderByIdDesc(newPDEK.getId());
        if (dernierePagePDEK.isPresent()) {
            PagePDEK dernierePage = dernierePagePDEK.get();
            
            if (instance1.getNumeroCycle() < 25) {
                instance1.setPagePDEK_soudures(dernierePage);
            } else if (instance1.getNumeroCycle()  == 25) {
                int nouveauPageNumber = dernierePage.getPageNumber() + 1;                
                PagePDEK nouvellePage = new PagePDEK();
                nouvellePage.setPdek(dernierePage.getPdek());
                nouvellePage.setPageNumber(nouveauPageNumber);
                nouvellePage.setStatus(false); 
                
                pdekPageRepository.save(nouvellePage);

                // Associer la soudure à la nouvelle page
                instance1.setPagePDEK_soudures(nouvellePage);
            }
            // Sauvegarder la soudure avec la bonne page associée
            soudureRepository.save(instance1);

}
	  }
	  
    }
    }
	 

	 @Override
	 public List<SoudureDTO> recupererSouduresParPDEK(String sectionFil, int segment ,Plant plant ,   String nomProjet) {
		  Optional<PDEK> pdekExiste = pdekRepository.findUniquePDEK_SoudureUtrason(sectionFil , segment , plant , nomProjet );

	     if (pdekExiste.isPresent()) {
	         PDEK pdek = pdekExiste.get();
	         List<Soudure> soudures = soudureRepository.findByPdekSoudure_Id(pdek.getId());

	         return soudures.stream()
	                 .map(s -> new SoudureDTO(
	                         s.getId(),
	                         s.getCode(),
	                         s.getSectionFil(),
	                         s.getDate().toString(), // adapte si c'est LocalDate
	                         s.getNumeroCycle()))
	                 .toList();
	     } else {
	         return List.of();
	     }
	 }

/*	 public Optional<SoudureDTO> recupererSoudureAvecMaxCycle(String sectionFil, int segment ,Plant plant , String nomProjet) {
		  Optional<PDEK> pdekExiste = pdekRepository.findUniquePDEKByCriteria(sectionFil , segment , plant , nomProjet );

		    if (pdekExiste.isPresent()) {
		        PDEK pdek = pdekExiste.get();
		        List<Soudure> soudures = soudureRepository.findByPdek_Id(pdek.getId());

		        return soudures.stream()
		                .max((s1, s2) -> Integer.compare(s1.getNumeroCycle(), s2.getNumeroCycle()))
		                .map(s -> new SoudureDTO(
		                        s.getId(),
		                        s.getCode(),
		                        s.getSectionFil(),
		                        s.getDate().toString(), // ajuste si nécessaire
		                        s.getNumeroCycle()));
		    } else {
		        return Optional.empty();
		    }
		}*/

}
