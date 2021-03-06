package fr.ibralogan.mediatheque.controller;

import fr.ibralogan.mediatheque.persistance.MediathequeData;
import fr.ibralogan.mediatheque.persistance.entities.UtilisateurEntity;
import fr.ibralogan.mediatheque.persistance.repository.UtilisateurRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {
    //Attribut se chargeant d'effectuer les requêtes HTTP, en l'occurrence sur les utilisateurs ici
    private final MediathequeData mediathequeData;

    public UtilisateurController(MediathequeData mediathequeData) {
        this.mediathequeData = mediathequeData;
    }

    /**
     * @return Renvoie la liste d'utilisateurs
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UtilisateurEntity> getUtilisateurs() {
        return mediathequeData.getUtilisateurs();
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUtilisateur(@PathVariable("username") String username) {
        Optional<UtilisateurEntity> utilisateur = mediathequeData.getUtilisateur(username);
        if(! utilisateur.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(utilisateur, HttpStatus.FOUND);
    }

    /**
     * Supprime un utilisateur
     * @param id de l'utilisateur
     * @return
     */
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") int id) {
        mediathequeData.supprimerUtilisateur(id);
        return new ResponseEntity<>("utilisateur supprimé avec succès", HttpStatus.OK);
    }
}
