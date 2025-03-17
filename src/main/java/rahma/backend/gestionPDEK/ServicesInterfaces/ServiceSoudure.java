package rahma.backend.gestionPDEK.ServicesInterfaces;

import java.util.List;
import rahma.backend.gestionPDEK.DTO.SoudureDTO;
import rahma.backend.gestionPDEK.Entity.*;

public interface ServiceSoudure {

	public void ajoutPDEKSoudure (Soudure instanceSoudure ,  int matriculeOperateur , String projet) ; 
	public List<SoudureDTO> recupererSouduresParPDEK(String sectionFil, int segment ,Plant plant ,   String nomProjet) ; 
}
