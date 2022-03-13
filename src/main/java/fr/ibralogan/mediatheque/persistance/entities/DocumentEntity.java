package fr.ibralogan.mediatheque.persistance.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * L'entité représentant un document permettant de mettre en place
 * la table en base de données via l'ORM de Spring Boot.
 */
@Entity(name="document")
public class DocumentEntity {

    public DocumentEntity() {}

    public DocumentEntity(String titre, int typeDocument, UtilisateurEntity emprunteur) {
        this.titre = titre;
        this.typeDocument = typeDocument;
        this.emprunteur = emprunteur;
    }

    @Id
    @GeneratedValue()
    private int id;

    @Column(nullable = false)
    private String titre;

    @Column(nullable = false)
    private int typeDocument;

    //un document est emprunté par 0 ou 1 seul emprunteur, d'où le optional
    @ManyToOne(optional = true)
    private UtilisateurEntity emprunteur;

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setTypeDocument(int typeDocument) {
        this.typeDocument = typeDocument;
    }

    public void setEmprunteur(UtilisateurEntity emprunteur) {
        this.emprunteur = emprunteur;
    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public int getTypeDocument() {
        return typeDocument;
    }

    public UtilisateurEntity getEmprunteur() {
        return emprunteur;
    }
}
