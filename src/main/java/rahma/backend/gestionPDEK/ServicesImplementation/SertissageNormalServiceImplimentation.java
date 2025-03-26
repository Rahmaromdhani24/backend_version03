package rahma.backend.gestionPDEK.ServicesImplementation;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rahma.backend.gestionPDEK.DTO.SertissageNormal_DTO;
import rahma.backend.gestionPDEK.Entity.OutilContact;
import rahma.backend.gestionPDEK.Entity.PDEK;
import rahma.backend.gestionPDEK.Entity.PagePDEK;
import rahma.backend.gestionPDEK.Entity.Plant;
import rahma.backend.gestionPDEK.Entity.SertissageNormal;
import rahma.backend.gestionPDEK.Entity.TypesOperation;
import rahma.backend.gestionPDEK.Entity.User;
import rahma.backend.gestionPDEK.Repository.OutilsContactRepository;
import rahma.backend.gestionPDEK.Repository.PdekPageRepository;
import rahma.backend.gestionPDEK.Repository.PdekRepository;
import rahma.backend.gestionPDEK.Repository.ProjetRepository;
import rahma.backend.gestionPDEK.Repository.SertissageNormalRepository;
import rahma.backend.gestionPDEK.Repository.UserRepository;

@Service
public class SertissageNormalServiceImplimentation {

	 @Autowired private OutilsContactRepository  outilContactRepository;
	 @Autowired    private PdekRepository pdekRepository;
	 @Autowired    private SertissageNormalRepository sertissageNormalRepository;	
	 @Autowired    private UserRepository userRepository;	
	 @Autowired    private ProjetRepository projetRepository;	
	 @Autowired    private PdekPageRepository pdekPageRepository;	
	 
	 // Récupération liste des contacts depuis numero  de outil 
	 public List<String> getDistinctContactsByNumeroOutil(String numeroOutil) {
	        List<String> contacts = outilContactRepository.findContactsByNumeroOutil(numeroOutil);
	        return contacts.stream().distinct().collect(Collectors.toList());  
	    }
	 
	 // Récupération des sections de fil uniques à partir du numéro d'outil et du numéro de contact
	 public List<String> getSectionsByOutilAndContact(String numeroOutil, String numeroContact) {
		    // Récupérer toutes les instances de OutilContact en fonction des paramètres
		    List<OutilContact> outilsContacts = outilContactRepository.findSectionsFilByOutilAndContact(numeroOutil, numeroContact);

		    if (outilsContacts.isEmpty()) {
		        throw new RuntimeException("Aucun OutilContact trouvé pour les paramètres fournis");
		    }

		    // Récupérer toutes les sections de fil à partir des instances OutilContact
		    return outilsContacts.stream()
		            .map(OutilContact::getSectionFil)           // Récupérer la sectionFil de chaque OutilContact
		            .filter(sectionFil -> sectionFil != null && !sectionFil.isEmpty())  // Filtrer les sections non nulles et non vides
		            .flatMap(sectionFil -> Arrays.stream(sectionFil.split(",")))  // Séparer chaque sectionFil par des virgules
		            .map(String::trim)  // Enlever les espaces supplémentaires
		            .distinct()         // Enlever les doublons
		            .collect(Collectors.toList());  // Collecter dans une liste
		}
	 
	 /// Récuperer hauteur de sertissage depuis num outil , num contact et une section de fil 
	 public String getHauteurSertissage(String numeroOutil, String numeroContact, String sectionFil) {
	        // Récupère un OutilContact en fonction des trois critères
	        OutilContact outilContact = outilContactRepository
	                .findByNumeroOutilAndNumeroContactAndSectionFil(numeroOutil, numeroContact, sectionFil)
	                .orElseThrow(() -> new RuntimeException("OutilContact non trouvé"));

	        // Retourne la hauteur de sertissage associée
	        return outilContact.getHauteurSertissage();
	    }
	 
	 /// Récuperer largeur de sertissage depuis num outil , num contact et une section de fil 
	 public String getLargeurSertissage(String numeroOutil, String numeroContact, String sectionFil) {
	        // Récupère un OutilContact en fonction des trois critères
	        OutilContact outilContact = outilContactRepository
	                .findByNumeroOutilAndNumeroContactAndSectionFil(numeroOutil, numeroContact, sectionFil)
	                .orElseThrow(() -> new RuntimeException("OutilContact non trouvé"));

	        // Retourne la hauteur de sertissage associée
	        return outilContact.getLargeurSertissage();
	    }

	 /// Récuperer hauteur de isolant  depuis num outil , num contact et une section de fil 
	 public String getHauteurIsolant(String numeroOutil, String numeroContact, String sectionFil) {
	        // Récupère un OutilContact en fonction des trois critères
	        OutilContact outilContact = outilContactRepository
	                .findByNumeroOutilAndNumeroContactAndSectionFil(numeroOutil, numeroContact, sectionFil)
	                .orElseThrow(() -> new RuntimeException("OutilContact non trouvé"));

	        // Retourne la hauteur de sertissage associée
	        return outilContact.getHauteurIsolant();
	    }
	 
	 /// Récuperer largeur de isolant depuis num outil , num contact et une section de fil 
	 public String getLargeurIsolant(String numeroOutil, String numeroContact, String sectionFil) {
	        // Récupère un OutilContact en fonction des trois critères
	        OutilContact outilContact = outilContactRepository
	                .findByNumeroOutilAndNumeroContactAndSectionFil(numeroOutil, numeroContact, sectionFil)
	                .orElseThrow(() -> new RuntimeException("OutilContact non trouvé"));

	        // Retourne la hauteur de sertissage associée
	        return outilContact.getLargeurIsolant();
	    }

		/// Récuperer traction depuis num outil , num contact et une section de fil 
		public String getTractionValeur(String numeroOutil, String numeroContact, String sectionFil) {
	        // Récupère un OutilContact en fonction des trois critères
	        OutilContact outilContact = outilContactRepository
	                .findByNumeroOutilAndNumeroContactAndSectionFil(numeroOutil, numeroContact, sectionFil)
	                .orElseThrow(() -> new RuntimeException("OutilContact non trouvé"));

	        // Retourne la hauteur de sertissage associée
	        return outilContact.getTraction();
	    }

		public String getToleranceLargeurSertissage(String numeroOutil, String numeroContact, String sectionFil) {
	        // Récupère un OutilContact en fonction des trois critères
	        OutilContact outilContact = outilContactRepository
	                .findByNumeroOutilAndNumeroContactAndSectionFil(numeroOutil, numeroContact, sectionFil)
	                .orElseThrow(() -> new RuntimeException("OutilContact non trouvé"));

	        // Retourne la hauteur de sertissage associée
	        return outilContact.getTolerenceLargeurSertissage();
	    }
		public String getToleranceHauteurIsolant(String numeroOutil, String numeroContact, String sectionFil) {
	        // Récupère un OutilContact en fonction des trois critères
	        OutilContact outilContact = outilContactRepository
	                .findByNumeroOutilAndNumeroContactAndSectionFil(numeroOutil, numeroContact, sectionFil)
	                .orElseThrow(() -> new RuntimeException("OutilContact non trouvé"));

	        // Retourne la hauteur de sertissage associée
	        return outilContact.getTolerenceHauteurIsolant();
	    }
		public String getToleranceLargeurIsolant(String numeroOutil, String numeroContact, String sectionFil) {
	        // Récupère un OutilContact en fonction des trois critères
	        OutilContact outilContact = outilContactRepository
	                .findByNumeroOutilAndNumeroContactAndSectionFil(numeroOutil, numeroContact, sectionFil)
	                .orElseThrow(() -> new RuntimeException("OutilContact non trouvé"));

	        // Retourne la hauteur de sertissage associée
	        return outilContact.getTolerenceLargeurIsolant();
	    }

		public String getToleranceValue(String numeroOutil, String numeroContact, String sectionFil) {
	        // Récupère un OutilContact en fonction des trois critères
	        OutilContact outilContact = outilContactRepository
	                .findByNumeroOutilAndNumeroContactAndSectionFil(numeroOutil, numeroContact, sectionFil)
	                .orElseThrow(() -> new RuntimeException("OutilContact non trouvé"));

	        // Retourne la hauteur de sertissage associée
	        return outilContact.getTolerance();
	    }
		
		public String getLGDeValue(String numeroOutil, String numeroContact, String sectionFil) {
	        // Récupère un OutilContact en fonction des trois critères
	        OutilContact outilContact = outilContactRepository
	                .findByNumeroOutilAndNumeroContactAndSectionFil(numeroOutil, numeroContact, sectionFil)
	                .orElseThrow(() -> new RuntimeException("OutilContact non trouvé"));

	        // Retourne la hauteur de sertissage associée
	        return outilContact.getLgd();
	    }

		/******************************************************************************************************************************/
		public void ajoutPDEK_SertissageNormal (SertissageNormal sertissageNormal, int matriculeOperateur , String projet) {
			
		    String sectionFilSelectionner = sertissageNormal.getSectionFil() ; 
		    User user = userRepository.findByMatricule(matriculeOperateur).get() ; 
		    
		    SertissageNormal  instance1 = new SertissageNormal() ; 
		    instance1.setCodeControle(sertissageNormal.getCodeControle());
		    instance1.setSectionFil(sectionFilSelectionner);
		    instance1.setDate(sertissageNormal.getDate());
		    instance1.setHauteurSertissageEch1(sertissageNormal.getHauteurSertissageEch1());
		    instance1.setHauteurSertissageEch2(sertissageNormal.getHauteurSertissageEch2());
		    instance1.setHauteurSertissageEch3(sertissageNormal.getHauteurSertissageEch3());
		    instance1.setHauteurSertissageEchFin(sertissageNormal.getHauteurSertissageEch1());
		    instance1.setLargeurSertissage(sertissageNormal.getLargeurSertissage());
		    instance1.setLargeurSertissageEchFin(sertissageNormal.getLargeurSertissageEchFin());
		    instance1.setHauteurIsolant(sertissageNormal.getHauteurIsolant());
		    instance1.setHauteurIsolantEchFin(sertissageNormal.getHauteurIsolantEchFin());
		    instance1.setLargeurIsolant(sertissageNormal.getLargeurIsolant());
		    instance1.setLargeurIsolantEchFin(sertissageNormal.getLargeurIsolantEchFin());
		    instance1.setTraction(sertissageNormal.getTraction());
		    instance1.setTractionFinEch(sertissageNormal.getTractionFinEch());
		    instance1.setSerieProduit(sertissageNormal.getSerieProduit());
		    instance1.setProduit(sertissageNormal.getProduit());
		    instance1.setNumeroMachine(sertissageNormal.getNumeroMachine());
		    instance1.setQuantiteCycle(sertissageNormal.getQuantiteCycle());
		    instance1.setSegment(user.getSegment());
		    instance1.setTolerance(sertissageNormal.getTolerance());
		    instance1.setNumeroOutils(sertissageNormal.getNumeroOutils());
			instance1.setNumeroContacts(sertissageNormal.getNumeroContacts());

			instance1.setUserSertissageNormal(user);
		 /************************** Recuperation PDEK ID s'il exise ****************/
		  Optional<PDEK> pdekExiste = pdekRepository.findUniquePDEK_SertissageNormal(sectionFilSelectionner , user.getSegment() , user.getPlant() , projet );

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
		        long nombreSertissageNormalDansPage = sertissageNormalRepository.countByPagePDEK(pagePDEK);
		        int numeroCycle;

		        if (nombreSertissageNormalDansPage < 8) {
		            // Ajouter le pistolet à la même page
		            numeroCycle = (int) nombreSertissageNormalDansPage + 1;
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

		        
		         instance1.setPdekSertissageNormal(pdek);
		         instance1.setPagePDEK(pagePDEK);
		         instance1.setNumCycle(numeroCycle);
		         sertissageNormalRepository.save(instance1) ;
		        
		   
		      


		    } else {
		       
		    	PDEK newPDEK = new PDEK() ; 
		    	newPDEK.setSectionFil(sectionFilSelectionner);
		    	newPDEK.setNombreEchantillons("3 Piéces ");
		    	newPDEK.setSegment(user.getSegment());
		    	newPDEK.setNumMachine(user.getMachine());
		    	newPDEK.setDateCreation(sertissageNormal.getDate());
		    	newPDEK.setTypeOperation(TypesOperation.Sertissage_Normal);
		    	newPDEK.setPlant(user.getPlant());
		    	newPDEK.setUsersRempliePDEK(List.of(user));
		    	newPDEK.setTotalPages(1);
		    	newPDEK.setNumeroOutils(instance1.getNumeroOutils());
		    	newPDEK.setNumeroContacts(instance1.getNumeroContacts());
		    	newPDEK.setTolerance(instance1.getTolerance());
		    	newPDEK.setLGD(getLGDeValue(instance1.getNumeroOutils() ,instance1.getNumeroContacts() , instance1.getSectionFil() ));
		    	instance1.setNumCycle(1);
		    	pdekRepository.save(newPDEK)  ;
		    	PagePDEK newPage = new PagePDEK(1, false, newPDEK);
		        pdekPageRepository.save(newPage);
		        instance1.setPagePDEK(newPage);
			  if (!newPDEK.getProjets().contains(projetRepository.findByNom(projet).get())) {
				  newPDEK.getProjets().add(projetRepository.findByNom(projet).get()); // Ajouter le projet au PDEK
				  projetRepository.findByNom(projet).get().getPdeks().add(newPDEK);
		    	projetRepository.save(projetRepository.findByNom(projet).get());
		    	instance1.setPdekSertissageNormal(newPDEK);
		    	sertissageNormalRepository.save(instance1) ;   	

			      }
		    }
			 }

			 public List<SertissageNormal_DTO> recupererSertissageNormalParPDEK(String sectionFilSelectionner, int segment ,Plant plant ,   String nomProjet) {
				  Optional<PDEK> pdekExiste = pdekRepository.findUniquePDEK_SertissageNormal(sectionFilSelectionner , segment , plant , nomProjet );

			     if (pdekExiste.isPresent()) {
			         PDEK pdek = pdekExiste.get();
			         List<SertissageNormal> sertissageNormales = sertissageNormalRepository.findByPdekSertissageNormal_Id(pdek.getId());

			         return sertissageNormales.stream()
			                 .map(s -> new SertissageNormal_DTO(
			                         s.getId(),
			                         s.getCodeControle(),
			                         s.getSectionFil(),
			                         s.getNumeroOutils() ,
			                         s.getNumeroContacts() ,  
			                         s.getDate(), 
			                         s.getNumCycle()))
			                 .toList();
			     } else {
			         return List.of();
			     }
			 }

			 ///////
			 public Optional<Integer> getLastNumeroCycle(String sectionFilSelectionne, int segment, Plant nomPlant, String projetName) {
			        // 1️⃣ Récupérer le PDEK correspondant
			        Optional<PDEK> pdekOpt = pdekRepository.findUniquePDEK_SertissageNormal(sectionFilSelectionne, segment, nomPlant, projetName);

			        if (pdekOpt.isEmpty()) {
			            return Optional.empty(); // Aucun PDEK trouvé
			        }

			        PDEK pdek = pdekOpt.get();

			        // 2️⃣ Récupérer la dernière page PDEK
			        Optional<PagePDEK> lastPageOpt = pdekPageRepository.findLastPageByPdek(pdek.getId());

			        if (lastPageOpt.isEmpty()) {
			            return Optional.empty(); // Aucune page trouvée
			        }

			        PagePDEK lastPage = lastPageOpt.get();

			        //  Récupérer le dernier numéro de cycle de sertissage normal
			        return sertissageNormalRepository.findLastNumCycleByPage(lastPage.getId());
			    }
			}
