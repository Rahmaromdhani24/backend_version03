package rahma.backend.gestionPDEK.ServicesInterfaces;

import java.util.List;
import rahma.backend.gestionPDEK.DTO.SoudureDTO;
import rahma.backend.gestionPDEK.DTO.TorsadageDTO;
import rahma.backend.gestionPDEK.Entity.*;

public interface ServiceTorsadage {

	public void ajoutPDEK_Torsadage (Torsadage instanceTorsadage ,  int matriculeOperateur , String projet) ; 
	public List<TorsadageDTO> recupererTorsadagesParPDEK(String specificationMesure, int segment ,Plant plant ,   String nomProjet) ; 
}
