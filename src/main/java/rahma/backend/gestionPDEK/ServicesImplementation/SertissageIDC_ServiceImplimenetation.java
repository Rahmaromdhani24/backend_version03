package rahma.backend.gestionPDEK.ServicesImplementation;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import rahma.backend.gestionPDEK.DTO.SertissageIDC_DTO;
import rahma.backend.gestionPDEK.DTO.SoudureDTO;
import rahma.backend.gestionPDEK.Entity.*;
import rahma.backend.gestionPDEK.Repository.*;

@Service
@RequiredArgsConstructor
public class SertissageIDC_ServiceImplimenetation {

	 @Autowired    private PdekRepository pdekRepository;
	 @Autowired    private SertissageIDCRepository sertissageIDCRepository;	
	 @Autowired    private UserRepository userRepository;	
	 @Autowired    private ProjetRepository projetRepository;	
	 @Autowired    private PdekPageRepository pdekPageRepository;	

	 
	public void ajoutPDEK_SertissageIDC(SertissageIDC sertissageIDC, int matriculeOperateur , String projet) {
	
    String sectionFilSelectionner = sertissageIDC.getSectionFil() ; 
    User user = userRepository.findByMatricule(matriculeOperateur).get() ; 
    
    SertissageIDC  instance1 = new SertissageIDC() ; 
    instance1.setCodeControle(sertissageIDC.getCodeControle());
    instance1.setSectionFil(sectionFilSelectionner);
    instance1.setDate(sertissageIDC.getDate());
    instance1.setForceTraction(sertissageIDC.getForceTraction());
    instance1.setHauteurSertissageC1Ech1(sertissageIDC.getHauteurSertissageC1Ech1());
    instance1.setHauteurSertissageC1Ech2(sertissageIDC.getHauteurSertissageC1Ech2());
    instance1.setHauteurSertissageC1Ech3(sertissageIDC.getHauteurSertissageC1Ech3());
    instance1.setHauteurSertissageC1EchFin(sertissageIDC.getHauteurSertissageC1EchFin());
    instance1.setHauteurSertissageC2Ech1(sertissageIDC.getHauteurSertissageC2Ech1());
    instance1.setHauteurSertissageC2Ech2(sertissageIDC.getHauteurSertissageC2Ech2());
    instance1.setHauteurSertissageC2Ech3(sertissageIDC.getHauteurSertissageC2Ech3());
    instance1.setHauteurSertissageC2EchFin(sertissageIDC.getHauteurSertissageC2EchFin());
    instance1.setHauteurSertissageMax(sertissageIDC.getHauteurSertissageMax());
    instance1.setHauteurSertissageMin(sertissageIDC.getHauteurSertissageMin());
    instance1.setQuantiteCycle(sertissageIDC.getQuantiteCycle());
    instance1.setSerieProduit(sertissageIDC.getSerieProduit());
    instance1.setProduit(sertissageIDC.getProduit());
    instance1.setSegment(user.getSegment());
    instance1.setForceTractionC1Ech1(sertissageIDC.getForceTractionC1Ech1());
    instance1.setForceTractionC1Ech2(sertissageIDC.getForceTractionC1Ech2());
    instance1.setForceTractionC1Ech3(sertissageIDC.getForceTractionC1Ech3());
    instance1.setForceTractionC1EchFin(sertissageIDC.getForceTractionC1EchFin());
    instance1.setNumeroMachine(sertissageIDC.getNumeroMachine());
    instance1.setForceTractionC2Ech1(sertissageIDC.getForceTractionC2Ech1());
    instance1.setForceTractionC2Ech2(sertissageIDC.getForceTractionC2Ech2());
    instance1.setForceTractionC2Ech3(sertissageIDC.getForceTractionC2Ech3());
    instance1.setForceTractionC2EchFin(sertissageIDC.getForceTractionC2EchFin());

    //instance1.setNumeroMachine(Integer.parseInt(user.getMachine()));


	instance1.setUserSertissageIDC(user);
 /***** Recuperation PDEK ID s'il exise ****************/
  Optional<PDEK> pdekExiste = pdekRepository.findUniquePDEK_SertissageIDC(sectionFilSelectionner , user.getSegment() , user.getPlant() , projet );

    if (pdekExiste.isPresent()) {
        // Si le Pdek existe, tu peux le récupérer et effectuer tes opérations
        PDEK pdek = pdekExiste.get();
        
        // ajout instance dans la table remplissage pdek 
        if (!pdek.getUsersRempliePDEK().contains(user)) {
            pdek.getUsersRempliePDEK().add(user);
        }
        // remplissage instance dans la table projet_pdek 
        if (!pdek.getProjets().contains(projetRepository.findByNom(projet).get())) {
            pdek.getProjets().add(projetRepository.findByNom(projet).get());
        }
        
        // 3. Trouver la dernière page du PDEK
        PagePDEK pagePDEK = pdekPageRepository.findFirstByPdekOrderByPageNumberDesc(pdek).get() ; 
            
        // 4. Compter le nombre de pistolets sur la page actuelle
        long nombreSertissageIDCDansPage = sertissageIDCRepository.countByPagePDEK(pagePDEK);
        int numeroCycle;

        if (nombreSertissageIDCDansPage < 8) {
            // Ajouter le pistolet à la même page
            numeroCycle = (int) nombreSertissageIDCDansPage + 1;
        }
        else {
            // Si la page est pleine, créer une nouvelle page
            pagePDEK = new PagePDEK(pdek.getTotalPages() + 1, false, pdek);
            pdekPageRepository.save(pagePDEK);
            // Mettre à jour le total de pages du PDEK
            pdek.setTotalPages(pdek.getTotalPages() + 1);
            pdekRepository.save(pdek);
            numeroCycle = 1; // Réinitialiser le cycle pour la nouvelle page
        }

        
         instance1.setPdekSertissageIDC(pdek);
         instance1.setPagePDEK(pagePDEK);
         instance1.setNumCycle(numeroCycle);
         sertissageIDCRepository.save(instance1) ;
        
   
      


    } else {
       
    	PDEK newPDEK = new PDEK() ; 
    	newPDEK.setSectionFil(sectionFilSelectionner);
    	newPDEK.setNombreEchantillons("3 Piéces ");
    	newPDEK.setSegment(user.getSegment());
    	newPDEK.setNumMachine(user.getMachine());
    	newPDEK.setDateCreation(sertissageIDC.getDate());
    	newPDEK.setTypeOperation(TypesOperation.Sertissage_IDC);
    	newPDEK.setPlant(user.getPlant());
    	newPDEK.setUsersRempliePDEK(List.of(user));
    	newPDEK.setTotalPages(1);
    	instance1.setNumCycle(1);
    	pdekRepository.save(newPDEK)  ;
    	PagePDEK newPage = new PagePDEK(1, false, newPDEK);
        pdekPageRepository.save(newPage);
        instance1.setPagePDEK(newPage);
	  if (!newPDEK.getProjets().contains(projetRepository.findByNom(projet).get())) {
		  newPDEK.getProjets().add(projetRepository.findByNom(projet).get()); // Ajouter le projet au PDEK
		  projetRepository.findByNom(projet).get().getPdeks().add(newPDEK);
    	projetRepository.save(projetRepository.findByNom(projet).get());
    	instance1.setPdekSertissageIDC(newPDEK);
    	sertissageIDCRepository.save(instance1) ;   	

	      }
    }
	 }

	 public Map<Integer, List<SertissageIDC_DTO>> recupererSertissagesParPDEKGroupéesParPage(String sectionFil, int segment, Plant plant, String nomProjet) {
	     Optional<PDEK> pdekExiste = pdekRepository.findUniquePDEK_SertissageIDC(sectionFil, segment, plant, nomProjet);

	     if (pdekExiste.isPresent()) {
	         PDEK pdek = pdekExiste.get();
	         List<SertissageIDC> sertissages = sertissageIDCRepository.findByPdekSertissageIDC_Id(pdek.getId());

	         // Grouper les soudures par numéro de page
	         return sertissages.stream()
	                 .collect(Collectors.groupingBy(
	                         s -> s.getPagePDEK().getPageNumber(), // groupement par numéro de page
	                         Collectors.mapping(
	                                 s -> new SertissageIDC_DTO(
	                                         s.getId(),
	                                         s.getCodeControle(),
	                                         s.getSectionFil(),
	                                         s.getDate().toString(),
	                                         s.getNumCycle(),
											 s.getUserSertissageIDC().getMatricule()),
	                                 Collectors.toList()
	                         )
	                 ));
	     } else {
	         return Map.of();
	     }
	 }

	 ///////
	 public int getLastNumeroCycle(String sectionFil ,  int segment, Plant nomPlant, String projetName) {
		 // 1️⃣ Récupérer le PDEK correspondant
		 Optional<PDEK> pdekOpt = pdekRepository.findUniquePDEK_SertissageIDC(sectionFil , segment, nomPlant, projetName);
	 
		 if (pdekOpt.isEmpty()) {
			 // Aucun PDEK trouvé → retourner 0
			 return 0;
		 }
	 
		 PDEK pdek = pdekOpt.get();
	 
		 // 2️⃣ Récupérer la dernière page associée au PDEK
		 Optional<PagePDEK> lastPageOpt = pdekPageRepository.findFirstByPdekOrderByPageNumberDesc(pdek);
	 
		 if (lastPageOpt.isEmpty()) {
			 // Le PDEK existe, mais aucune page n'est encore créée → retourner 0
			 return 0;
		 }
	 
		 PagePDEK lastPage = lastPageOpt.get();
	 
		 // 3️⃣ Vérifier s'il existe des soudures dans cette page
		 long nombreSertissageDansPage = sertissageIDCRepository.countByPagePDEK(lastPage);
	 
		 if (nombreSertissageDansPage == 0) {
			 // Si la page est vide, retourner 0
			 return 0;
		 }
	 
		 // 4️⃣ Récupérer le dernier numéro de cycle
		 Optional<SertissageIDC> lastSertissageOpt = sertissageIDCRepository.findTopByPagePDEK_IdOrderByNumCycleDesc(lastPage.getId());
	 
		 if (lastSertissageOpt.isPresent()) {
			 // Si une soudure est présente, retourner son numéro de cycle
			 return lastSertissageOpt.get().getNumCycle();
		 }
	 
		 // Si aucune soudure n'est trouvée malgré les vérifications, retourner 0
		 return 0;
	 }
	 
			}