package fr.ibralogan.mediatheque.controller;

import fr.ibralogan.mediatheque.mediatek2022.Document;
import fr.ibralogan.mediatheque.mediatek2022.Utilisateur;
import fr.ibralogan.mediatheque.persistance.repository.DocumentRepository;
import fr.ibralogan.mediatheque.persistance.MediathequeData;
import fr.ibralogan.mediatheque.persistance.repository.UtilisateurRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TodoController
 * Classe contrôlleur pour faire des essais très basiques.
 */
@RestController
@RequestMapping("/api/todos")
public class TodoController {
    @GetMapping("/string")
    public String index() {
        return "Hello World";
    }

    @GetMapping("/json")
    public Map<String, String> getHelloWorld() {
        Map<String, String> map = new HashMap<>();
        map.put("get", "Hello World");
        return map;
    }
}
