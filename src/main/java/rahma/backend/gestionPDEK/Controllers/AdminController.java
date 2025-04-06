package rahma.backend.gestionPDEK.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rahma.backend.gestionPDEK.EmailSenderService;
import rahma.backend.gestionPDEK.Configuration.EmailRequest;
import rahma.backend.gestionPDEK.Entity.*;
import rahma.backend.gestionPDEK.Repository.*;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200") 
public class AdminController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EmailSenderService emailSenderService;


    
    public AdminController(UserRepository userRepository, RoleRepository roleRepository , EmailSenderService emailSenderService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.emailSenderService = emailSenderService;
    }
    @GetMapping("/getUser/{matricule}")
    public ResponseEntity<?> getUser(@PathVariable int matricule) {
        Optional<User> userOptional = userRepository.findById(matricule);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Créer une réponse sous forme de Map (au lieu d'un DTO)
            Map<String, Object> response = new HashMap<>();
            response.put("matricule", user.getMatricule());
            response.put("plant", user.getPlant());
            response.put("nom", user.getNom());
            response.put("prenom", user.getPrenom());
            response.put("role", user.getRole().getNom());  // Juste le nom du rôle
            response.put("poste", user.getPoste());
            response.put("segment", user.getSegment());
            response.put("machine", user.getMachine());
            

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", "Utilisateur introuvable !"));
        }
    }

    
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<Map<String, Object>>> getAllUsers() {
        List<User> users = userRepository.findAll();

        // Convertir la liste d'utilisateurs en liste de Map
        List<Map<String, Object>> userResponses = users.stream().map(user -> {
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("matricule", user.getMatricule());
            userMap.put("opération", user.getTypeOperation());
            userMap.put("plant", user.getPlant());
            userMap.put("nom", user.getNom());
            userMap.put("prenom", user.getPrenom());
            userMap.put("role", user.getRole().getNom());  // Juste le nom du rôle
            userMap.put("poste", user.getPoste());
            userMap.put("segment", user.getSegment());
            userMap.put("machine", user.getMachine());

            return userMap;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(userResponses);
    }

    
    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        // Vérifier si un utilisateur avec le même matricule existe déjà
        if (userRepository.findById(user.getMatricule()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("utilisateur avec ce matricule existe déjà !");
        }

        // Vérifier si le rôle existe
        Optional<Role> role = roleRepository.findById(user.getRole().getId());
        if (role.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erreur : Rôle introuvable !");
        }

        // Assigner le rôle et sauvegarder l'utilisateur
        user.setRole(role.get());
        User savedUser = userRepository.save(user);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }


    @PutMapping("/updateUser/{matricule}")
    public ResponseEntity<User> updateUser(@PathVariable int matricule, @RequestBody User userDetails) {
        // Rechercher l'utilisateur par matricule
        Optional<User> userOptional = userRepository.findById(matricule);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            // Mise à jour des champs
            user.setPlant(userDetails.getPlant());
           // user.setOperation(Operation()) ;
            user.setNom(userDetails.getNom());
            user.setPrenom(userDetails.getPrenom());
            user.setPoste(userDetails.getPoste());
            user.setSegment(userDetails.getSegment());
            user.setMachine(userDetails.getMachine());
            user.setEmail(userDetails.getEmail());
            user.setRole(userDetails.getRole());
            User updatedUser = userRepository.save(user);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/deleteUser/{matricule}")
    public ResponseEntity<String> deleteUser(@PathVariable int matricule) {
        Optional<User> userOptional = userRepository.findById(matricule);

        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body("Utilisateur supprimé avec succès");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé");
        }
    }
    @PostMapping("/AgentQualiteSendMailErreur")
    public ResponseEntity<String> sendErrorNotification(@RequestBody EmailRequest request) {
        try {
            emailSenderService.sendEmailErreurToAgentQualite(
                request.getToEmail(),
                request.getNomResponsable(),
                request.getNomProcess() , 
                request.getPosteErreur(),
                request.getDescriptionErreur(),
                request.getLocalisation(),
                request.getValeurMesuree(),    // Nouveau paramètre
                request.getLimitesAcceptables() // Nouveau paramètre
            );
            return ResponseEntity.accepted().body("Notification d'alerte qualité envoyée avec succès");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Erreur lors de l'envoi de l'email d'alerte: " + e.getMessage());
        }
    }

    @PostMapping("/AgentQualiteSendMailWarning")
    public ResponseEntity<String> sendWarningNotification(@RequestBody EmailRequest request) {
        try {
            emailSenderService.sendEmailWarningToAgentQualite(
                request.getToEmail(),
                request.getNomResponsable(),
                request.getNomProcess() , 
                request.getPosteErreur(),
                request.getDescriptionErreur(),
                request.getLocalisation(),
                request.getValeurMesuree(),    // Nouveau paramètre
                request.getLimitesAcceptables() // Nouveau paramètre
            );
            return ResponseEntity.accepted().body("Notification d'alerte qualité envoyée avec succès");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Erreur lors de l'envoi de l'email d'alerte: " + e.getMessage());
        }
    }
    @PostMapping("/chefLigneSendMailErreur")
    public ResponseEntity<String> sendErrorNotificationChefDeLigne(@RequestBody EmailRequest request) {
        try {
            emailSenderService.sendEmailErreurToChefDeLigne(
                request.getToEmail(),
                request.getNomResponsable(),
                request.getNomProcess() , 
                request.getPosteErreur(),
                request.getDescriptionErreur(),
                request.getLocalisation(),
                request.getValeurMesuree(),    // Nouveau paramètre
                request.getLimitesAcceptables() // Nouveau paramètre
            );
            return ResponseEntity.accepted().body("Notification d'alerte qualité envoyée avec succès");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Erreur lors de l'envoi de l'email d'alerte: " + e.getMessage());
        }
    }

    @PostMapping("/chefLigneSendMailWarning")
    public ResponseEntity<String> sendEmailWarningToChefDeLigne(@RequestBody EmailRequest request) {
        try {
            emailSenderService.sendEmailWarningToAgentQualite(
                request.getToEmail(),
                request.getNomResponsable(),
                request.getNomProcess() , 
                request.getPosteErreur(),
                request.getDescriptionErreur(),
                request.getLocalisation(),
                request.getValeurMesuree(),    // Nouveau paramètre
                request.getLimitesAcceptables() // Nouveau paramètre
            );
            return ResponseEntity.accepted().body("Notification d'alerte qualité envoyée avec succès");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Erreur lors de l'envoi de l'email d'alerte: " + e.getMessage());
        }
    }
/*
 * @PostMapping("/sendEmailAdminEtMedecin")
	public ResponseEntity<?> sendEmailMedecin(@RequestBody Map<String, String> requestData) throws Exception {
	    String email = requestData.get("email");
	    if (repository.existsByEmail(email) == false) {
	        return ResponseEntity
	                    .status(500)
	                    .body(new Message("Email n'existe pas !!!!!"));
	    } else {
	        User user = repository.findByEmail(email);
	        nom = user.getNom();
	        prenom = user.getPrenom();
	        password = user.getReservePassword();
	        username = user.getUsername();
	        senderService.sendEmailWithImagesAndButtonMedecin(email, "rahmaromdhani08@gmail.com",
	                "Objet : Bienvenue à notre Application Clinique NAR!", EmailSenderService.getReserveBody());
	        System.out.println("Email Wsellll  !!!");
	        return ResponseEntity.ok(new Message("Email send !!!!"));
	    }
	}
 */

    
}
