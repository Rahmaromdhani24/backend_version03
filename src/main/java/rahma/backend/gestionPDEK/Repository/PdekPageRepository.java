package rahma.backend.gestionPDEK.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rahma.backend.gestionPDEK.Entity.PDEK;
import rahma.backend.gestionPDEK.Entity.PagePDEK;

@Repository
public interface PdekPageRepository extends JpaRepository<PagePDEK , Long> {

	Optional<PagePDEK> findTopByPdek_IdOrderByIdDesc(Long id);
    Optional<PagePDEK> findFirstByPdekOrderByPageNumberDesc(PDEK pdek);


}

	

