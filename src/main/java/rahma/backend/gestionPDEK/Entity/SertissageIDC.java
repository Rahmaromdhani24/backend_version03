package rahma.backend.gestionPDEK.Entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "Sertissage_IDC")
public class SertissageIDC extends Operation {
    public static final List<String> SECTIONS_FILS = List.of("0.35 mm" );
    public static final List<String> CODES_CONTROLES = List.of("A", "L","M", "R", "S", "w");

    public static final Map<String, String> CODES_CONTROLES_DESCRIPTION = Map.of(
        "A" , "Discontinuité du au fin matière" ,
        "L", "Fin de commande" ,
        "M", "Changement matière",
        "R", "Réparation ou réglage Outil/Machine",
        "S", "Démarrage du cycle",
        "W" ,"Démarrage apprentissage du systeme de surveillance"
      
      
    );

    @Column(name = "numero_outils")
    private long numeroOutils;
    
    @Column(name = "numero_contacts")
    private long numeroContacts;
   
    private double tolerance;
    
    @Column(name = "largeur_Sertisage")
    private double largeurSertissage;
    
    @Column(name = "hauteur_Sertissage")
    private double hauteurSertissage;
    
    @Column(name = "largeur_Isolant")
    private double largeurIsolant;
    
    @Column(name = "hauteur_Isolant")
    private double hauteurIsolant;
    

    private String  LGD;
    
    private String traction;
    
    @Column(name = "pos_gradant")
    private String  posGradant;
    
    
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;

    public static String getDescriptionForCode(String code) {
        return CODES_CONTROLES_DESCRIPTION.getOrDefault(code, "Description non trouvée");
    }
    
    
    @Column(name = "quantite_atteint")
    private int quantiteAtteint; 
    private String code; 
}
