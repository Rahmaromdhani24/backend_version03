package rahma.backend.gestionPDEK.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "PDEK")
public class PDEK {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private int totalPages;
    
    @Column(name = "section_Fil")
    private String sectionFil;
    
    @Column(name = "nombres_Echantillons")
    private String nombreEchantillons;
    
    @Column(name = "frequence_Controle")
    private long frequenceControle;
    
    private int segment; 
    
    @Column(name = "numero_machine")
    private String numMachine; 
    
    @Column(name = "date_creation")
    private String dateCreation;
    
    @Enumerated(EnumType.STRING)
	   @Column(name = "type_operation")
	    private TypesOperation typeOperation;
    
    @Enumerated(EnumType.STRING)
	   @Column(name = "type_pistole")
	    private TypePistolet typePistolet;
  
    
    @Enumerated(EnumType.STRING)
    @Column(name = "plant")
    private Plant plant;  

    @Column(name = "numero_outils")
    private String numeroOutils;
    
    @Column(name = "numero_contacts")
    private String numeroContacts;
   
    private String  LGD;
    
    private double tolerance;
    
    @Column(name = "pos_gradant")
    private String  posGradant;
    
    @OneToMany(mappedBy = "pdekSoudure", cascade = CascadeType.ALL) // relation "associer"  a operation soudure 
    private List<Soudure> pdekSoudures = new ArrayList<>();
    
    
    @OneToMany(mappedBy = "pdekTorsadage", cascade = CascadeType.ALL) // relation "associer"  a operation torsadage 
    private List<Torsadage> pdekTorsadages  = new ArrayList<>();
    
    @OneToMany(mappedBy = "pdekPistolet", cascade = CascadeType.ALL) // relation "associer"  a operation torsadage 
    private List<Pistolet> pdekPistoles  = new ArrayList<>();
    
    @OneToMany(mappedBy = "pdekSertissageIDC", cascade = CascadeType.ALL) // relation "associer"  a operation torsadage 
    private List<SertissageIDC> pdekSertissageIDC  = new ArrayList<>();
    
    @OneToMany(mappedBy = "pdekSertissageNormal", cascade = CascadeType.ALL) // relation "associer"  a operation torsadage 
    private List<SertissageNormal> pdekSertissageNormal  = new ArrayList<>();
    
    @ManyToMany  // association user remplissage de pdek  ==> nommer "creer"
    @JoinTable(
        name = "user_remplissage_pdek",
        joinColumns = @JoinColumn(name = "pdek_id"),
        inverseJoinColumns = @JoinColumn(name = "user_matricule")
    )
    private List<User> usersRempliePDEK;
    
    @ManyToMany  // Association avec les projets
    @JoinTable(
        name = "pdek_projet",
        joinColumns = @JoinColumn(name = "pdek_id"),
        inverseJoinColumns = @JoinColumn(name = "projet_id")
    )
    private List<Projet> projets = new ArrayList<>();

    
    
    @OneToMany(mappedBy = "pdek", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // relation avec PagePDEK 
    private List<PagePDEK> pages;

    public List<Pistolet> getPdekPistoles() {
        return pdekPistoles;
    
}
}
