package fr.ibralogan.mediatheque.persistance;

import java.util.List;
import java.util.Optional;
import java.util.LinkedList;

import fr.ibralogan.mediatheque.mediatek2022.*;

// classe mono-instance  dont l'unique instance est connue de la médiatheque
// via une auto-déclaration dans son bloc static

public class MediathequeData implements PersistentMediatheque {

    /*
    imposé :

    static {
        Mediatheque.getInstance().setData(new MediathequeData());
    }

    private MediathequeData() {}
    */

    // TODO : meilleur système
    public MediathequeData(DocumentRepository documentRepository, UtilisateurRepository utilisateurRepository) {
        this.documentRepository = documentRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    private DocumentRepository documentRepository;
    private UtilisateurRepository utilisateurRepository;

    @Override
    public List<Document> tousLesDocumentsDisponibles() {
        List<DocumentEntity> documents = documentRepository.findAll();
        // On convertis tous les DocumentsEntity pour qu'ils implémentent correctement l'interface Document
        List<Document> retval = new LinkedList<Document>();
        for (DocumentEntity doc: documents) {
            retval.add(new DocumentObject(doc, documentRepository));
        }
        return retval;
    }

    @Override
    public Utilisateur getUser(String login, String password) {
        Optional<UtilisateurEntity> utilisateur = utilisateurRepository.findByLogin(login);
        if (utilisateur.isPresent() && utilisateur.get().checkPassword(password)) {
            return utilisateur.get();
        }
        return null;
    }

    @Override
    public Document getDocument(int numDocument) {
        Optional<DocumentEntity> document = documentRepository.findById(numDocument);
        if (document.isPresent()) {
            return new DocumentObject(document.get(), documentRepository);
        }
        return null;
    }

    @Override
    public void ajoutDocument(int type, Object... args) {
        // TODO : vérification utilisateur est bibliothéquaire
        documentRepository.save(new DocumentEntity((String) args[0], type));
    }

}
