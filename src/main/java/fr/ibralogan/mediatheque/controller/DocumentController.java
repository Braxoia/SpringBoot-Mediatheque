package fr.ibralogan.mediatheque.controller;

import fr.ibralogan.mediatheque.exceptions.ResourceNotFoundException;
import fr.ibralogan.mediatheque.mediatek2022.Document;
import fr.ibralogan.mediatheque.mediatek2022.Mediatheque;
import fr.ibralogan.mediatheque.mediatek2022.PersistentMediatheque;
import fr.ibralogan.mediatheque.mediatek2022.Utilisateur;
import fr.ibralogan.mediatheque.persistance.DocumentEntity;
import fr.ibralogan.mediatheque.persistance.DocumentObject;
import fr.ibralogan.mediatheque.persistance.DocumentRepository;
import fr.ibralogan.mediatheque.persistance.UtilisateurRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/documents")
public class DocumentController implements PersistentMediatheque {

    private DocumentRepository documentRepository;
    private UtilisateurRepository utilisateurRepository;

    public DocumentController(DocumentRepository documentRepository, UtilisateurRepository utilisateurRepository) {
        this.documentRepository = documentRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Document> tousLesDocumentsDisponibles() {
        List<DocumentEntity> documentsEntities = documentRepository.findAll();
        List<Document> documents = new ArrayList<>();
        for(DocumentEntity doc : documentsEntities) {
            documents.add(new DocumentObject(doc, documentRepository));
        }

        return documents;
    }

    @Override
    @GetMapping("/{numDocument}")
    @ResponseStatus(HttpStatus.OK)
    public Document getDocument(@PathVariable("numDocument") int numDocument) {
        Optional<DocumentEntity> document = documentRepository.findById(numDocument);

        return new DocumentObject(document.get(), documentRepository);
    }

    @Override
    public Utilisateur getUser(String login, String password) {
        throw new UnsupportedOperationException("On ne requête pas d'utilisateur !");
    }

    @Override
    @PostMapping("/addDocument")
    @ResponseStatus(HttpStatus.CREATED)
    public void ajoutDocument(int type, @RequestBody Object... args) {
        //args contient 4 cases
        DocumentEntity documentEntity = new DocumentEntity();
        documentEntity.setTitre((String) args[0]);
        documentEntity.setTypeDocument((int) args[1]);
        /*documentEntity.setReserveur(
                TODO: Récup le JWT, le décoder et récup le token
        );*/

        DateTimeFormatter dfmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        documentEntity.setEcheanceReservation(LocalDateTime.parse((String) args[3], dfmt));
        documentRepository.save(documentEntity);
    }
}
