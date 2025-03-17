package rahma.backend.gestionPDEK.DTO;

import java.time.LocalDate;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class PdekDTO {
    private Long id;
    private String sectionFil;
    private String nomProjet;
    private LocalDate dateCreation;
    private long frequenceControle;

    // Constructeur avec tous les paramètres nécessaires
    public PdekDTO(Long id, String sectionFil, String nomProjet , LocalDate dateCreation, long frequenceControle) {
        this.id = id;
        this.sectionFil = sectionFil;
        this.nomProjet = nomProjet;
        this.dateCreation = dateCreation;
        this.frequenceControle = frequenceControle;
    }

  
}


