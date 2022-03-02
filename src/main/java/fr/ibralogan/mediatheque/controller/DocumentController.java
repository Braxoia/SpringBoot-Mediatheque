package fr.ibralogan.mediatheque.controller;

import fr.ibralogan.mediatheque.mediatek2022.Document;
import fr.ibralogan.mediatheque.mediatek2022.Mediatheque;
import fr.ibralogan.mediatheque.mediatek2022.PersistentMediatheque;
import fr.ibralogan.mediatheque.mediatek2022.Utilisateur;
import fr.ibralogan.mediatheque.persistance.DocumentEntity;
import fr.ibralogan.mediatheque.persistance.DocumentObject;
import fr.ibralogan.mediatheque.persistance.DocumentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController implements PersistentMediatheque {

    private DocumentRepository documentRepository;

    public DocumentController(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Document> tousLesDocumentsDisponibles() {
        List<DocumentEntity> matches = documentRepository.findAll();
        ArrayList<Document> retval = new ArrayList<>();
        for (DocumentEntity documentEntity : matches) {
            retval.add(new DocumentObject(documentEntity, documentRepository));
        }
        return retval;
    }

    @Override
    public Document getDocument(int numDocument) {
        return null;
    }

    @Override
    public Utilisateur getUser(String login, String password) {
        return null;
    }

    @Override
    public void ajoutDocument(int type, Object... args) {

    }
}
