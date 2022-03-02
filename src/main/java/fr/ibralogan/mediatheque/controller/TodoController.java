package fr.ibralogan.mediatheque.controller;

import fr.ibralogan.mediatheque.mediatek2022.Document;
import fr.ibralogan.mediatheque.mediatek2022.Utilisateur;
import fr.ibralogan.mediatheque.persistance.DocumentRepository;
import fr.ibralogan.mediatheque.persistance.MediathequeData;
import fr.ibralogan.mediatheque.persistance.UtilisateurEntity;
import fr.ibralogan.mediatheque.persistance.UtilisateurRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private UtilisateurRepository utilisateurRepository;
    private DocumentRepository documentRepository;
    private MediathequeData modele;

    public TodoController(UtilisateurRepository utilisateurRepository, DocumentRepository documentRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.documentRepository = documentRepository;

        // quickfix
        this.modele = new MediathequeData(documentRepository, utilisateurRepository);
    }



    @GetMapping("/documents")
    public List<Document> getTousDocuments() {
        return modele.tousLesDocumentsDisponibles();
    }

    /**
     * @see UtilisateurController#getUtilisateur(String, String)
     */
    @GetMapping("/{login}/{password}")
    @ResponseStatus(HttpStatus.OK)
    public Utilisateur getUtilisateur(@PathVariable("login") String login, @PathVariable("password") String password) {
        return this.modele.getUser(login, password);
    }
}
