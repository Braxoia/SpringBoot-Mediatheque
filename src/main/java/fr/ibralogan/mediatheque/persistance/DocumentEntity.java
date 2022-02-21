package fr.ibralogan.mediatheque.persistance;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name="document")
public class DocumentEntity {

    public DocumentEntity() {}
    public DocumentEntity(String titre, int typeDocument) {
        this.titre = titre;
        this.typeDocument = typeDocument;
    }

    @Id
    @GeneratedValue()
    private int id;

    @Column(nullable = false)
    private String titre;

    @Column(nullable = false)
    private int typeDocument;

    // TODO : Plus d'informations à stocker, genre l'auteur du document et une description

    @OneToOne(optional = true)
    private UtilisateurEntity reserveur = null;

    public UtilisateurEntity getEmprunteur() {
        return this.reserveur;
    }
    @Column(nullable = true)
    private LocalDateTime echeanceReservation = null;
}
