package rahma.backend.gestionPDEK.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rahma.backend.gestionPDEK.Entity.PDEK;
import rahma.backend.gestionPDEK.Entity.PagePDEK;

@Repository
public interface PdekPageRepository extends JpaRepository<PagePDEK , Long> {

	Optional<PagePDEK> findTopByPdek_IdOrderByIdDesc(Long id);
    Optional<PagePDEK> findFirstByPdekOrderByPageNumberDesc(PDEK pdek);


    @Query("SELECT p FROM PagePDEK p WHERE p.pdek.id = :pdekId ORDER BY p.pageNumber DESC LIMIT 1")
    Optional<PagePDEK> findLastPageByPdek(@Param("pdekId") Long pdekId);


}

	

