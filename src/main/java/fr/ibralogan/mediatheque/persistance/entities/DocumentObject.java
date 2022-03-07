package fr.ibralogan.mediatheque.persistance.entities;

import fr.ibralogan.mediatheque.mediatek2022.Document;
import fr.ibralogan.mediatheque.mediatek2022.Utilisateur;
import fr.ibralogan.mediatheque.persistance.repository.DocumentRepository;

public class DocumentObject implements Document {
    private DocumentEntity documentEntity;
    private DocumentRepository db;
    public DocumentObject(DocumentEntity documentEntity, DocumentRepository db) {
        this.documentEntity = documentEntity;
        this.db = db;
    }

    public DocumentEntity getDocumentEntity() {
        return documentEntity;
    }

    public boolean disponible() {
        return this.documentEntity.getEmprunteur() == null;
    }

    public void emprunt(Utilisateur u) throws Exception {
        // todo Appel ORM pour ajouter emprunteur
    }
    public void retour() {
        // todo Appel ORM pour retirer emprunteur
    }
}
