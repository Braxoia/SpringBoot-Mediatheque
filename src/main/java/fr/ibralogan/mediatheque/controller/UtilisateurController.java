package fr.ibralogan.mediatheque.controller;

import fr.ibralogan.mediatheque.persistance.UtilisateurEntity;
import fr.ibralogan.mediatheque.persistance.UtilisateurRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurController(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<UtilisateurEntity> getUtilisateurs() {
        return utilisateurRepository
                .findAll();
    }

    // TODO : fonction temporaire
    @PostMapping("/testPost")
    @ResponseBody
    public String testpost(@RequestParam Map<String,String> allParams) {
        return "Parameters are " + allParams.entrySet();
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public UtilisateurEntity getUtilisateur(@RequestParam(name="login") String login, @RequestParam(name="password") String password) {
        UtilisateurEntity utilisateur = utilisateurRepository
                .findByLogin(login)
                .orElseThrow(() -> new RuntimeException(String.format("Aucun utilisateur avec ce login : %s", login)));
        if (! utilisateur.checkPassword(password)) {
            throw new RuntimeException(String.format("Mot de passe incorrect pour l'utilisateur : %s", login));
        }
        return utilisateur;
    }

}
