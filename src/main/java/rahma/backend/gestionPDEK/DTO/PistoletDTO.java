package rahma.backend.gestionPDEK.DTO;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rahma.backend.gestionPDEK.Entity.PDEK;
import rahma.backend.gestionPDEK.Entity.Pistolet;
import rahma.backend.gestionPDEK.Entity.TypePistolet;
import rahma.backend.gestionPDEK.Entity.TypesOperation;

@Getter
@Setter
public class PistoletDTO {

		private Long id;
	    private String date;
	    private TypePistolet typePistolet ; 
	    private int  numeroPistolet ; 
	    private String limiteInterventionMax ; 
	    private String limiteInterventionMin ; 
	    private PDEK pdek ;
	    
		public PistoletDTO(Long id, String date, TypePistolet typePistolet, int numeroPistolet,
				 String limiteInterventionMax, String limiteInterventionMin, PDEK pdek) {
			super();
			this.id = id;
			this.date = date;
			this.typePistolet = typePistolet;
			this.numeroPistolet = numeroPistolet;
			this.limiteInterventionMax = limiteInterventionMax;
			this.limiteInterventionMin = limiteInterventionMin;
			this.pdek = pdek;
		} 
	    
	
	    
}
