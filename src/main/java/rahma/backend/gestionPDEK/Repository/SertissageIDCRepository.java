package rahma.backend.gestionPDEK.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rahma.backend.gestionPDEK.Entity.SertissageIDC;
import rahma.backend.gestionPDEK.Entity.Torsadage;

@Repository

public interface SertissageIDCRepository extends JpaRepository<SertissageIDC, Long> {

}

