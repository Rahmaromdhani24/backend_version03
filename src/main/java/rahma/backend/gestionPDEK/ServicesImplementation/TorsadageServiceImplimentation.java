package rahma.backend.gestionPDEK.ServicesImplementation;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rahma.backend.gestionPDEK.DTO.SoudureDTO;
import rahma.backend.gestionPDEK.DTO.TorsadageDTO;
import rahma.backend.gestionPDEK.Entity.*;
import rahma.backend.gestionPDEK.Repository.*;
import rahma.backend.gestionPDEK.ServicesInterfaces.ServiceSoudure;
import rahma.backend.gestionPDEK.ServicesInterfaces.ServiceTorsadage;

@Service
public class TorsadageServiceImplimentation implements ServiceTorsadage {

	 @Autowired    private PdekRepository pdekRepository;
	 @Autowired    private TorsadageRepository torsadageRepository;	
	 @Autowired    private UserRepository userRepository;	
	 @Autowired    private ProjetRepository projetRepository;	
	 @Autowired    private PdekPageRepository pdekPageRepository;	

	 
	 @Override
	public void ajoutPDEK_Torsadage(Torsadage instanceTorsadage, int matriculeOperateur , String projet) {
	
    String specificationMesureSelectionner = instanceTorsadage.getSpecificationMesure() ; 
    User user = userRepository.findByMatricule(matriculeOperateur).get() ; 
    
    Torsadage  instance1 = new Torsadage() ; 
    instance1.setCode(instanceTorsadage.getCode());
    instance1.setSpecificationMesure(specificationMesureSelectionner);
    instance1.setDate(instanceTorsadage.getDate());
    instance1.setNumCommande(instanceTorsadage.getNumCommande());
    instance1.setLongueurFinalDebutCde(instanceTorsadage.getLongueurFinalDebutCde());
    instance1.setLongueurBoutDebutCdeC1(instanceTorsadage.getLongueurBoutDebutCdeC1());
    instance1.setLongueurBoutDebutCdeC2(instanceTorsadage.getLongueurBoutDebutCdeC2());
    instance1.setLongueurBoutFinCdeC1(instanceTorsadage.getLongueurBoutFinCdeC1());
    instance1.setLongueurBoutFinCdeC2(instanceTorsadage.getLongueurBoutFinCdeC2());
    instance1.setDecalageMaxDebutCdec1(instanceTorsadage.getDecalageMaxDebutCdec1());
    instance1.setDecalageMaxDebutCdec2(instanceTorsadage.getDecalageMaxDebutCdec2());
    instance1.setNumerofil(instanceTorsadage.getNumerofil());
    instance1.setLongueurFinalFinCde(instanceTorsadage.getLongueurFinalFinCde());
    instance1.setEch1(instanceTorsadage.getEch1());
    instance1.setEch2(instanceTorsadage.getEch2());
    instance1.setEch3(instanceTorsadage.getEch3());
    instance1.setEch4(instanceTorsadage.getEch4());
    instance1.setEch5(instanceTorsadage.getEch5());
    instance1.setDecalageMaxFinCdec1(instanceTorsadage.getDecalageMaxFinCdec1());
    instance1.setDecalageMaxFinCdec2(instanceTorsadage.getDecalageMaxFinCdec2());
    instance1.setLongueurPasFinCde(instanceTorsadage.getLongueurPasFinCde());
    instance1.setQuantiteAtteint(instanceTorsadage.getQuantiteAtteint());
    instance1.setQuantiteTotale(instanceTorsadage.getQuantiteTotale());
    instance1.setEtendu(instanceTorsadage.getEtendu());
    instance1.setMoyenne(instanceTorsadage.getMoyenne());

	instance1.setUserTorsadage(user);
 /***** Recuperation PDEK ID s'il exise ****************/
  Optional<PDEK> pdekExiste = pdekRepository.findUniquePDEK_Torsadage(specificationMesureSelectionner , user.getSegment() , user.getPlant() , projet );

    if (pdekExiste.isPresent()) {
        // Si le Pdek existe, tu peux le récupérer et effectuer tes opérations
        PDEK pdek = pdekExiste.get();
        instance1.setPdekTorsadage(pdek);
        instance1.setUserTorsadage(user);
        Integer dernierCycle = torsadageRepository.findLastCycleByPdekTorsadage_Id(pdek.getId());
        if(dernierCycle == 25) {
        	  instance1.setNumeroCycle(1);
        }
        else if(dernierCycle < 25) {
        	  int nouveauCycle = (dernierCycle != null) ? dernierCycle + 1 : 1;
              instance1.setNumeroCycle(nouveauCycle);
              }
        torsadageRepository.save(instance1) ;
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
                instance1.setPagePDEK_torsadage(dernierePage);
            } else if (instance1.getNumeroCycle() >25) {
                int nouveauPageNumber = dernierePage.getPageNumber() + 1;                
                PagePDEK nouvellePage = new PagePDEK();
                nouvellePage.setPdek(dernierePage.getPdek());
                nouvellePage.setPageNumber(nouveauPageNumber);
                nouvellePage.setStatus(false); 
                
                pdekPageRepository.save(nouvellePage);

                // Associer la soudure à la nouvelle page
                instance1.setPagePDEK_torsadage(nouvellePage);
            }
            // Sauvegarder la soudure avec la bonne page associée
            torsadageRepository.save(instance1);
        }


    } else {
       
    	PDEK newPDEK = new PDEK() ; 
    	newPDEK.setSectionFil(specificationMesureSelectionner);
    	newPDEK.setNombreEchantillons("3 Piéces ");
    	newPDEK.setSegment(user.getSegment());
    	newPDEK.setNumMachine(user.getMachine());
    	newPDEK.setDateCreation(instanceTorsadage.getDate());
    	newPDEK.setTypeOperation(TypesOperation.Torsadage);
    	newPDEK.setPlant(user.getPlant());
    	newPDEK.setUsersRempliePDEK(List.of(user));
    	newPDEK.setTotalPages(1);
    	instance1.setNumeroCycle(1);
    	pdekRepository.save(newPDEK)  ;
	  if (!newPDEK.getProjets().contains(projetRepository.findByNom(projet).get())) {
		  newPDEK.getProjets().add(projetRepository.findByNom(projet).get()); // Ajouter le projet au PDEK
		  projetRepository.findByNom(projet).get().getPdeks().add(newPDEK);
    	projetRepository.save(projetRepository.findByNom(projet).get());
    	instance1.setPdekTorsadage(newPDEK);
    	torsadageRepository.save(instance1) ; 
    	pdekPageRepository.save(new PagePDEK( 1 , false, newPDEK  )) ;
    	

    /***************/
    	Optional<PagePDEK> dernierePagePDEK = pdekPageRepository.findTopByPdek_IdOrderByIdDesc(newPDEK.getId());
        if (dernierePagePDEK.isPresent()) {
            PagePDEK dernierePage = dernierePagePDEK.get();
            
            if (instance1.getNumeroCycle() < 25) {
                instance1.setPagePDEK_torsadage(dernierePage);
            } else if (instance1.getNumeroCycle()  == 25) {
                int nouveauPageNumber = dernierePage.getPageNumber() + 1;                
                PagePDEK nouvellePage = new PagePDEK();
                nouvellePage.setPdek(dernierePage.getPdek());
                nouvellePage.setPageNumber(nouveauPageNumber);
                nouvellePage.setStatus(false); 
                
                pdekPageRepository.save(nouvellePage);

                // Associer la soudure à la nouvelle page
                instance1.setPagePDEK_torsadage(nouvellePage);
            }
            // Sauvegarder la soudure avec la bonne page associée
            torsadageRepository.save(instance1);

}
	  }
	  
    }
    }
	 

	 @Override
	 public List<TorsadageDTO> recupererTorsadagesParPDEK(String specificationMesurer, int segment ,Plant plant ,   String nomProjet) {
		  Optional<PDEK> pdekExiste = pdekRepository.findUniquePDEK_Torsadage(specificationMesurer , segment , plant , nomProjet );

	     if (pdekExiste.isPresent()) {
	         PDEK pdek = pdekExiste.get();
	         List<Torsadage> soudures = torsadageRepository.findByPdekTorsadage_Id(pdek.getId());

	         return soudures.stream()
	                 .map(s -> new TorsadageDTO(
	                         s.getId(),
	                         s.getCode(),
	                         s.getSpecificationMesure(),
	                         s.getDate().toString(), // adapte si c'est LocalDate
	                         s.getNumeroCycle()))
	                 .toList();
	     } else {
	         return List.of();
	     }
	 }


}
