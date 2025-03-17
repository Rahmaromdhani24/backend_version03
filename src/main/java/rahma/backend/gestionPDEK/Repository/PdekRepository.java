package rahma.backend.gestionPDEK.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rahma.backend.gestionPDEK.Entity.*;

@Repository

public interface PdekRepository extends JpaRepository<PDEK , Long> {

	@Query("SELECT p FROM PDEK p WHERE p.sectionFil = :sectionFilSelectionne " +
		       "AND p.typeOperation = 'Soudure' " +
		       "AND p.segment = :segment " +
		       "AND p.plant = :nomPlant " +
		       "AND EXISTS (SELECT pr FROM Projet pr JOIN pr.pdeks pp WHERE pr.nom = :projetName AND pp.id = p.id)")
		Optional<PDEK> findUniquePDEK_SoudureUtrason(
		    @Param("sectionFilSelectionne") String sectionFilSelectionne, 
		    @Param("segment") int segment, 
		    @Param("nomPlant") Plant nomPlant,
		    @Param("projetName") String projetName
		);


	@Query("SELECT p FROM PDEK p WHERE p.sectionFil = :specificationMesurer " +
		       "AND p.typeOperation = 'Torsadage' " +
		       "AND p.segment = :segment " +
		       "AND p.plant = :nomPlant " +
		       "AND EXISTS (SELECT pr FROM Projet pr JOIN pr.pdeks pp WHERE pr.nom = :projetName AND pp.id = p.id)")
		Optional<PDEK> findUniquePDEK_Torsadage(
		    @Param("specificationMesurer") String specificationMesurer, 
		    @Param("segment") int segment, 
		    @Param("nomPlant") Plant nomPlant,
		    @Param("projetName") String projetName
		);

}