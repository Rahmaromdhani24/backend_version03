package rahma.backend.gestionPDEK.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rahma.backend.gestionPDEK.Entity.PagePDEK;
import rahma.backend.gestionPDEK.Entity.Pistolet;
import rahma.backend.gestionPDEK.Entity.User;


@Repository
public interface PistoletRepository extends JpaRepository<Pistolet, Long> {

	long countByPagePDEK(PagePDEK pagePDEK);

	Optional<User> findMaxNumerocycleByPagePDEK(PagePDEK lastPage);
	
    
}