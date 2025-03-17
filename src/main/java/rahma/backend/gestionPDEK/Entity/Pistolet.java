package rahma.backend.gestionPDEK.Entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pistolet extends Operation {
    
	private int numerocycle ; 
	private int numeroPistolet;
    private String nbrCollierTester;
    private int axeSerrage;
    private int semaine;
    private String specificationMesure;
    private int limiteInterventionMax;
    private int limiteInterventionMin;
    private LocalDate dateCreation ; 
    
    @Enumerated(EnumType.STRING)
    private TypePistolet type; 

    private int ech1;
    private int ech2;
    private int ech3;
    private int ech4;
    private int ech5;
    private double moyenne;
    private int etendu;

    @ManyToOne   // relation "saisir" 
    @JoinColumn(name = "user_matricule")
    private User userPistolet;
    
    
    @ManyToOne   // relation "associer"  PDEK a torsadage 
    @JoinColumn(name = "pdek_id")
    private PDEK pdekPistolet;

    
    @ManyToOne
    @JoinColumn(name = "page_pdek_id")
    private PagePDEK pagePDEK;

   
	public TypePistolet getTypePistolet() {
		// TODO Auto-generated method stub
		return type;
	}
	
    

}

