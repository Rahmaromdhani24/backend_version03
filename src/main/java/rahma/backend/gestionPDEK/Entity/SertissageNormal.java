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
@Table(name = "Sertissage_Normal")
public class SertissageNormal extends Operation {
    public static final Map<String, String[]> SECTIONS_FILS_SERTISSAGE = Map.ofEntries(
        Map.entry("0.22 mm²", new String[] { "Hauteur_Sertissage: 1.10/±0.05", "Largeur_Sertissage: 1.65/±0.1", "Hauteur_Isolant: 3.10/±0.05", "Largeur_Isolant: 3.6/±0.1", "Traction: 60" }),
        Map.entry("0.25 mm²", new String[] { "Hauteur_Sertissage: 1.10/±0.05", "Largeur_Sertissage: 1.65/±0.1", "Hauteur_Isolant: 3.10/±0.05", "Largeur_Isolant: 3.6/±0.1", "Traction: 60" }),
        Map.entry("0.35 mm²", new String[] { "Hauteur_Sertissage: 1.10/±0.05", "Largeur_Sertissage: 1.65/±0.1", "Hauteur_Isolant: 3.10/±0.05", "Largeur_Isolant: 3.6/±0.1", "Traction: 60" }),
        Map.entry("0.5 mm²", new String[] {  "Hauteur_Sertissage: 1.10/±0.05", "Largeur_Sertissage: 1.65/±0.1", "Hauteur_Isolant: 3.10/±0.05", "Largeur_Isolant: 3.6/±0.1", "Traction: 60" }),
        Map.entry("0.75 mm²", new String[] { "Hauteur_Sertissage: 1.10/±0.05", "Largeur_Sertissage: 1.65/±0.1", "Hauteur_Isolant: 3.10/±0.05", "Largeur_Isolant: 3.6/±0.1", "Traction: 60" }),
        Map.entry("1 mm²",    new String[] { "Hauteur_Sertissage: 1.10/±0.05", "Largeur_Sertissage: 1.65/±0.1", "Hauteur_Isolant: 3.10/±0.05", "Largeur_Isolant: 3.6/±0.1", "Traction: 60" }),
        Map.entry("1.5 mm²",  new String[] { "Hauteur_Sertissage: 1.10/±0.05", "Largeur_Sertissage: 1.65/±0.1", "Hauteur_Isolant: 3.10/±0.05", "Largeur_Isolant: 3.6/±0.1", "Traction: 60" }),
        Map.entry("2 mm²",    new String[] { "Hauteur_Sertissage: 1.10/±0.05", "Largeur_Sertissage: 1.65/±0.1", "Hauteur_Isolant: 3.10/±0.05", "Largeur_Isolant: 3.6/±0.1", "Traction: 60" }),
        Map.entry("2.5 mm²",  new String[] { "Hauteur_Sertissage: 1.10/±0.05", "Largeur_Sertissage: 1.65/±0.1", "Hauteur_Isolant: 3.10/±0.05", "Largeur_Isolant: 3.6/±0.1", "Traction: 60" }),
        Map.entry("4 mm²",    new String[] { "Hauteur_Sertissage: 1.10/±0.05", "Largeur_Sertissage: 1.65/±0.1", "Hauteur_Isolant: 3.10/±0.05", "Largeur_Isolant: 3.6/±0.1", "Traction: 60" }),
        Map.entry("5 mm²",    new String[] { "Hauteur_Sertissage: 1.10/±0.05", "Largeur_Sertissage: 1.65/±0.1", "Hauteur_Isolant: 3.10/±0.05", "Largeur_Isolant: 3.6/±0.1", "Traction: 60" }),
        Map.entry("6 mm²",    new String[] { "Hauteur_Sertissage: 1.10/±0.05", "Largeur_Sertissage: 1.65/±0.1", "Hauteur_Isolant: 3.10/±0.05", "Largeur_Isolant: 3.6/±0.1", "Traction: 60" }),
        Map.entry("7 mm²",    new String[] { "Hauteur_Sertissage: 1.10/±0.05", "Largeur_Sertissage: 1.65/±0.1", "Hauteur_Isolant: 3.10/±0.05", "Largeur_Isolant: 3.6/±0.1", "Traction: 60" }),
        Map.entry("10 mm²",   new String[] { "Hauteur_Sertissage: 1.10/±0.05", "Largeur_Sertissage: 1.65/±0.1", "Hauteur_Isolant: 3.10/±0.05", "Largeur_Isolant: 3.6/±0.1", "Traction: 60" })
    );

    public static String[] getSertissageValues(String sectionFil) {
        return SECTIONS_FILS_SERTISSAGE.getOrDefault(sectionFil, new String[] { "Données non disponibles" });
    }
    public static final List<String> CODES_CONTROLES = List.of("A", "L","M", "R", "S", "w");

    public static final Map<String, String> CODES_CONTROLES_DESCRIPTION = Map.of(
        "A" , "Discontinuité du au fin matière" ,
        "L", "Fin de commande" ,
        "M", "Changement matière",
        "R", "Réparation ou réglage Outil/Machine",
        "S", "Démarrage de la commande",
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
