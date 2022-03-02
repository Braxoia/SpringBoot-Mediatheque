package fr.ibralogan.mediatheque.controller;

import fr.ibralogan.mediatheque.exceptions.ResourceNotFoundException;
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

    @GetMapping()
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

}
