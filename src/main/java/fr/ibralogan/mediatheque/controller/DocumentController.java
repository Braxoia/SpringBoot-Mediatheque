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
    //Attribut se chargeant d'effectuer les requêtes HTTP, en l'occurrence sur les utilisateurs ici
    private MediathequeData mediathequeData;

    public DocumentController(DocumentRepository documentRepository, UtilisateurRepository utilisateurRepository) {
        this.mediathequeData = new MediathequeData(documentRepository, utilisateurRepository);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Document>> tousLesDocumentsDisponibles() {
        List<Document> documentsObject = mediathequeData.tousLesDocumentsDisponibles();
        if(documentsObject == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(documentsObject, HttpStatus.OK);
    }

    /**
     * Permet l'emprunt d'un document par un utilisateur
     * @param numDocument
     * @param principal variable comportant toutes les informations de l'utilisateur atteignant l'endpoint
     * @return une réponse HTTP en fonction du déroulé
     * @throws Exception
     */
    @GetMapping("/emprunter/{numDocument}")
    public ResponseEntity<?> empruntDocument(@PathVariable("numDocument") int numDocument,  Principal principal) throws Exception {
        Document document = mediathequeData.getDocument(numDocument);
        // note : UtilisateurEntity implémente Utilisateur. faire une Optional<Utilisateur> est valide mais spring n'aime pas trop
        Optional<UtilisateurEntity> emprunteur = mediathequeData.getUtilisateur(principal.getName());

        if(document == null || !emprunteur.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        if(!document.disponible()) {
            // on remerciera l'interface Document de ne pas donner accès à une méthode getTitre
            return new ResponseEntity<>("the document " + ((DocumentObject) document).getTitre() + " is already borrowed", HttpStatus.BAD_REQUEST);
        }

        //mise à jour
        document.emprunt(emprunteur.get());
        return new ResponseEntity<>("emprunt du document " + ((DocumentObject) document).getTitre() + " par " + emprunteur.get().getUsername(),
                HttpStatus.OK);
    }

    @GetMapping("/retourner/{numDocument}")
    public ResponseEntity<?> retourDocument(@PathVariable("numDocument") int numDocument) throws Exception {
        Document document = mediathequeData.getDocument(numDocument);
        if(document.disponible()) {
            return new ResponseEntity<>("Retour du document " + ((DocumentObject) document).getTitre() + " inutile car dispo", HttpStatus.BAD_REQUEST);
        }
        // similaire à la fonction empruntDocument
        document.retour();
        return new ResponseEntity<>("Retour du document " + ((DocumentObject) document).getTitre() + " effectué ",
                HttpStatus.OK);
    }

    /**
     * Ajoute un document dans la médiatheque
     * @param document informations en JSON sur le document, le DTO permet de simplifié et de restreindre les infos
     * @param principal contexte de l'utilisateur effectuant la requête
     * @return une réponse HTTP en fonction du déroulé
     */
    @PostMapping("/addDocument")
    public ResponseEntity<?> ajoutDocument(@RequestBody CreateDocumentDTO document, Principal principal) {
        Optional<UtilisateurEntity> utilisateur = mediathequeData.getUtilisateur(principal.getName());
        // assume que l'utilisateur a été correctement identifié
        if(! utilisateur.get().isBibliothecaire()) {
            return new ResponseEntity<>("User who wishes to add a document is not a librarian", HttpStatus.EXPECTATION_FAILED);
        }

        //envoie des informations du document à notre classe de persistance
        mediathequeData.ajoutDocument(
                document.getTypeDocument(),
                document.getTitre(),
                null
        );

        return new ResponseEntity<>("document created successfully", HttpStatus.CREATED);
    }
}
