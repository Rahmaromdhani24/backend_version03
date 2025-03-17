package rahma.backend.gestionPDEK.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rahma.backend.gestionPDEK.Entity.Soudure;
import rahma.backend.gestionPDEK.Entity.Torsadage;

@Repository

public interface TorsadageRepository extends JpaRepository<Torsadage, Long> {


	@Query("SELECT MAX(s.numeroCycle) FROM Torsadage s WHERE s.pdekTorsadage.id = :pdekId")
	Integer findLastCycleByPdekTorsadage_Id(@Param("pdekId") Long pdekId);
	
	 List<Torsadage> findByPdekTorsadage_Id(Long pdekId);
}

