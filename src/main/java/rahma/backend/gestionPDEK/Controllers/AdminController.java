package rahma.backend.gestionPDEK.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    public AdminController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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

}
