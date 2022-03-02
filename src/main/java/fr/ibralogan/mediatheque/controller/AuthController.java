package fr.ibralogan.mediatheque.controller;

import fr.ibralogan.mediatheque.persistance.UtilisateurEntity;
import fr.ibralogan.mediatheque.persistance.UtilisateurRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    AuthenticationManager authenticationManager;
    UtilisateurRepository userRepository;
    PasswordEncoder encoder;

    /*
    todo : remettre en fonction
    @PostMapping("/signin")
    public ResponseEntity<?> authentificationUtilisateur(UtilisateurEntity utilisateurEntity) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(utilisateurEntity.name(), utilisateurEntity.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }*/
}
