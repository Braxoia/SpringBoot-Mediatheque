package fr.ibralogan.mediatheque.persistance;

import java.util.List;
import java.util.Optional;
import java.util.LinkedList;

import fr.ibralogan.mediatheque.mediatek2022.*;

// classe mono-instance  dont l'unique instance est connue de la m�diatheque
// via une auto-d�claration dans son bloc static

public class MediathequeData implements PersistentMediatheque {

    /*
    impos� :

    static {
        Mediatheque.getInstance().setData(new MediathequeData());
    }

    private MediathequeData() {}
    */

    // TODO : meilleur syst�me
    public MediathequeData(DocumentRepository documentRepository, UtilisateurRepository utilisateurRepository) {
        this.documentRepository = documentRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    private DocumentRepository documentRepository;
    private UtilisateurRepository utilisateurRepository;

    @Override
    public List<Document> tousLesDocumentsDisponibles() {
        List<DocumentEntity> documents = documentRepository.findAll();
        // On convertis tous les DocumentsEntity pour qu'ils impl�mentent correctement l'interface Document
        List<Document> retval = new LinkedList<Document>();
        for (DocumentEntity doc: documents) {
            retval.add(new DocumentObject(doc, documentRepository));
        }
        return retval;
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
    public Utilisateur getUser(String login, String password) {
        return null;
    }

    @Override
    public void ajoutDocument(int type, Object... args) {
        // TODO : v�rification utilisateur est biblioth�quaire
        documentRepository.save(new DocumentEntity((String) args[0], type));
    }

}
