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

    @Column(nullable = true)
    private LocalDateTime echeanceReservation = null;

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setTypeDocument(int typeDocument) {
        this.typeDocument = typeDocument;
    }

    public void setReserveur(UtilisateurEntity reserveur) {
        this.reserveur = reserveur;
    }

    public void setEcheanceReservation(LocalDateTime echeanceReservation) {
        this.echeanceReservation = echeanceReservation;
    }

    public UtilisateurEntity getEmprunteur() {
        return reserveur;
    }
}
