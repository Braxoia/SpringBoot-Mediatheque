package fr.ibralogan.mediatheque.persistance;

import java.util.ArrayList;
import java.util.List;

import fr.ibralogan.mediatheque.mediatek2022.*;

// classe mono-instance dont l'unique instance est connue de la médiatheque
// via une auto-déclaration dans son bloc static

public class MediathequeData implements PersistentMediatheque {

    static {
        Mediatheque.getInstance().setData(new MediathequeData());
    }

    private MediathequeData() {}

    // TODO : meilleur système
    public MediathequeData(DocumentRepository documentRepository, UtilisateurRepository utilisateurRepository) {
        this.documentRepository = documentRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    private DocumentRepository documentRepository;
    private UtilisateurRepository utilisateurRepository;

    @Override
    public List<Document> tousLesDocumentsDisponibles() {
        return new ArrayList<Document>();
    }

    @Override
    public Utilisateur getUser(String login, String password) {
        return null;
    }

    @Override
    public Document getDocument(int numDocument) {
        return null;
    }

    @Override
    public void ajoutDocument(int type, Object... args) {
    }

}
