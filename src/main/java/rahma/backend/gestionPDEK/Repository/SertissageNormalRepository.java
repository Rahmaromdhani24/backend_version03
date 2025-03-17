package rahma.backend.gestionPDEK.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rahma.backend.gestionPDEK.Entity.SertissageNormal;
import rahma.backend.gestionPDEK.Entity.Torsadage;

@Repository

public interface SertissageNormalRepository extends JpaRepository<SertissageNormal, Long> {

}

