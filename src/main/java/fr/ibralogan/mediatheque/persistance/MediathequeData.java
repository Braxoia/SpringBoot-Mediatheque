package fr.ibralogan.mediatheque.persistance;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.LinkedList;

import fr.ibralogan.mediatheque.mediatek2022.*;
import fr.ibralogan.mediatheque.persistance.entities.DocumentEntity;
import fr.ibralogan.mediatheque.persistance.entities.DocumentObject;
import fr.ibralogan.mediatheque.persistance.entities.UtilisateurEntity;
import fr.ibralogan.mediatheque.persistance.repository.DocumentRepository;
import fr.ibralogan.mediatheque.persistance.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
@Service
public class MediathequeData implements PersistentMediatheque {
    private final DocumentRepository documentRepository;
    private final UtilisateurRepository utilisateurRepository;

    public MediathequeData(DocumentRepository documentRepository, UtilisateurRepository utilisateurRepository) {
        this.documentRepository = documentRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

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
        return document.map(documentEntity -> new DocumentObject(documentEntity, documentRepository)).orElse(null);
    }

    @Override
    public Utilisateur getUser(String login, String password) {
        throw new UnsupportedOperationException("Vérification de l'utilisateur dans /api/auth/signin");
    }

    @Override
    public void ajoutDocument(int type, Object... args) {
        DocumentEntity documentEntity = new DocumentEntity();
        documentEntity.setTitre((String) args[0]);
        documentEntity.setTypeDocument(type);
        documentEntity.setEmprunteur((UtilisateurEntity) args[1]);
        documentRepository.save(documentEntity);
    }



    // NON DEMANDE

    public Optional<UtilisateurEntity> getUtilisateur(String username) {
        return this.utilisateurRepository.findByUsername(username);
    }

    public Optional<UtilisateurEntity> getUtilisateur(int userId) {
        //me permet de récupérer le reserveur par son id afin de faire fonctionner l'ajout de document par récupération de celui-ci
        return this.utilisateurRepository.findById(userId);
    }

    public List<UtilisateurEntity> getUtilisateurs() {
        return this.utilisateurRepository.findAll();
    }

    public void supprimerUtilisateur(int userId) {
        utilisateurRepository.deleteById(userId);
    }
}
