package fr.ibralogan.mediatheque.persistance.entities;

import fr.ibralogan.mediatheque.mediatek2022.Document;
import fr.ibralogan.mediatheque.mediatek2022.Utilisateur;
import fr.ibralogan.mediatheque.persistance.repository.DocumentRepository;

/**
 * Classe nous permettant d'utiliser l'interface Document sans ommettre notre classe d'entité représentant le document.
 * Ceci afin de respecter au maximum les consignes
 */
public class DocumentObject implements Document {
    private DocumentEntity documentEntity;
    private DocumentRepository db;
    public DocumentObject(DocumentEntity documentEntity, DocumentRepository db) {
        this.documentEntity = documentEntity;
        this.db = db;
    }

    public boolean disponible() {
        return this.documentEntity.getEmprunteur() == null;
    }

    public void emprunt(Utilisateur u) throws Exception {
        documentEntity.setEmprunteur((UtilisateurEntity) u);
        db.save(documentEntity);
    }
    public void retour() {
        documentEntity.setEmprunteur(null);
        db.save(documentEntity);
    }


    /// ---
    public DocumentEntity getDocumentEntity() {
        return documentEntity;
    }
    public int getId() {
        return this.documentEntity.getId();
    }
    public String getTitre() {
        return documentEntity.getTitre();
    }
    public int getTypeDocument() {
        return documentEntity.getTypeDocument();
    }
    public String getEmprunteur() {
        if (this.documentEntity.getEmprunteur() == null) {
            return "null";
        }
        return documentEntity.getEmprunteur().getUsername();
    }
    public String toString() {
        return
                "{\"id\":" + this.getId() +
                        ",\"titre\":\"" + this.getTitre() + "\"" +
                        ",\"typeDocument\":" + this.getTypeDocument() +
                        ",\"emprunteur\":" + this.getEmprunteur() +
                        "}";
    }
}
