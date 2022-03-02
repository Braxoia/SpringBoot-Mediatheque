package fr.ibralogan.mediatheque.persistance;

import fr.ibralogan.mediatheque.mediatek2022.Document;
import fr.ibralogan.mediatheque.mediatek2022.Utilisateur;

import java.util.Optional;

public class DocumentObject implements Document {
    private DocumentEntity docRow;
    private DocumentRepository db;
    public DocumentObject(DocumentEntity docRow, DocumentRepository db) {
        this.docRow = docRow;
        this.db = db;
    }

    public boolean disponible() {
        return this.docRow.getEmprunteur() == null;
    }

    public void emprunt(Utilisateur u) throws Exception {
        // todo Appel ORM pour ajouter emprunteur
    }
    public void retour() {
        // todo Appel ORM pour retirer emprunteur
    }
}
