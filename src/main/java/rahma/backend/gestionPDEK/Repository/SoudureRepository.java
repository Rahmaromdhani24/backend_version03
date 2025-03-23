package rahma.backend.gestionPDEK.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rahma.backend.gestionPDEK.Entity.PagePDEK;
import rahma.backend.gestionPDEK.Entity.Soudure;


@Repository
public interface SoudureRepository extends JpaRepository<Soudure, Long> {

	@Query("SELECT MAX(s.numeroCycle) FROM Soudure s WHERE s.pdekSoudure.id = :pdekId")
	Integer findLastCycleByPdekSoudure_Id(@Param("pdekId") Long pdekId);
	
	 List<Soudure> findByPdekSoudure_Id(Long pdekId);
	 
	   // Compter le nombre de SertissageIDC associés à une page donnée
	    long countByPagePDEK(PagePDEK pagePDEK);
}

