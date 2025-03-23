package rahma.backend.gestionPDEK.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rahma.backend.gestionPDEK.Entity.PagePDEK;
import rahma.backend.gestionPDEK.Entity.SertissageIDC;

import java.util.List;
import java.util.Optional;

@Repository
public interface SertissageIDCRepository extends JpaRepository<SertissageIDC, Long> {

    // Compter le nombre de SertissageIDC associés à une page donnée
    long countByPagePDEK(PagePDEK pagePDEK);

    // Trouver le dernier SertissageIDC sur une page donnée, trié par numCycle en ordre décroissant
    Optional<SertissageIDC> findTopByPagePDEKOrderByNumCycleDesc(PagePDEK pagePDEK);
    
     List<SertissageIDC> findByPdekSertissageIDC_Id(Long pdekId) ;
		// TODO Auto-generated method stub


}

