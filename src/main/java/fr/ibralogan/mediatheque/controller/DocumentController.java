package fr.ibralogan.mediatheque.controller;

import fr.ibralogan.mediatheque.mediatek2022.Document;
import fr.ibralogan.mediatheque.mediatek2022.Mediatheque;
import fr.ibralogan.mediatheque.mediatek2022.PersistentMediatheque;
import fr.ibralogan.mediatheque.mediatek2022.Utilisateur;
import fr.ibralogan.mediatheque.persistance.MediathequeData;
import fr.ibralogan.mediatheque.persistance.dtos.CreateDocumentDTO;
import fr.ibralogan.mediatheque.persistance.entities.DocumentEntity;
import fr.ibralogan.mediatheque.persistance.entities.DocumentObject;
import fr.ibralogan.mediatheque.persistance.entities.UtilisateurEntity;
import fr.ibralogan.mediatheque.persistance.repository.DocumentRepository;
import fr.ibralogan.mediatheque.persistance.repository.UtilisateurRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private MediathequeData mediathequeData;

    public DocumentController(DocumentRepository documentRepository, UtilisateurRepository utilisateurRepository) {
        this.mediathequeData = new MediathequeData(documentRepository, utilisateurRepository);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> tousLesDocumentsDisponibles() {
        List<Document> documentsObject = mediathequeData.tousLesDocumentsDisponibles();

        if(documentsObject == null) {
            return ResponseEntity.notFound().build();
        }

        List<DocumentEntity> documents = new ArrayList<>();

        for(Document d : documentsObject) {
            documents.add(((DocumentObject)d).getDocumentEntity());
        }

        return new ResponseEntity<>(documents, HttpStatus.FOUND);
    }

    @GetMapping("/emprunter/{numDocument}")
    public ResponseEntity<?> getDocument(@PathVariable("numDocument") int numDocument,  Principal principal) {
        Document document = mediathequeData.getDocument(numDocument);
        Optional<UtilisateurEntity> emprunteur = mediathequeData.getUtilisateur(principal.getName());

        if(document == null || !emprunteur.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        DocumentEntity documentEntity = ((DocumentObject)document).getDocumentEntity();
        if(!documentEntity.empruntable()) {
            return new ResponseEntity<>("the document " + documentEntity.getTitre() + " is already borrowed", HttpStatus.BAD_REQUEST);
        }

        //mise à jour
        mediathequeData.nouveauEmprunteur(documentEntity, emprunteur.get());
        return new ResponseEntity<>("emprunt du document " + documentEntity.getTitre() + " par " + emprunteur.get().getUsername(),
                HttpStatus.OK);
    }

    @PostMapping("/addDocument")
    public ResponseEntity<?> ajoutDocument(@RequestBody CreateDocumentDTO document, Principal principal) {
        Optional<UtilisateurEntity> utilisateur = mediathequeData.getUtilisateur(principal.getName());
        // assume que l'utilisateur a été correctement identifié
        if(! utilisateur.get().isBibliothecaire()) {
            return new ResponseEntity<>("User who wishes to add a document is not a librarian", HttpStatus.EXPECTATION_FAILED);
        }

        mediathequeData.ajoutDocument(
                document.getTypeDocument(),
                document.getTitre(),
                null
        );

        return new ResponseEntity<>("document created successfully", HttpStatus.CREATED);
    }
}
