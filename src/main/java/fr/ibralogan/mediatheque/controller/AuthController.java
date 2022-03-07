package fr.ibralogan.mediatheque.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import fr.ibralogan.mediatheque.persistance.entities.UtilisateurEntity;
import fr.ibralogan.mediatheque.persistance.repository.UtilisateurRepository;
import fr.ibralogan.mediatheque.security.SecurityConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    AuthenticationManager authenticationManager;
    UtilisateurRepository utilisateurRepository;

    public AuthController(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authentificationUtilisateur(@RequestBody UtilisateurEntity utilisateurEntity) {
        Optional<UtilisateurEntity> utilisateur = utilisateurRepository.findByUsername(utilisateurEntity.getUsername());

        if(! utilisateur.isPresent()) {
           return ResponseEntity.notFound().build();
        }

        if(!utilisateur.get().getPassword().equals(utilisateurEntity.getPassword())) {
            return new ResponseEntity<>("unauthorize", HttpStatus.UNAUTHORIZED);
        }

        String token = JWT.create()
                .withSubject(utilisateur.get().getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));

        return new ResponseEntity<>(token, HttpStatus.OK);

       /*
        res.addHeader("Access-Control-Expose-Headers", "Authorization");
        res.addHeader("Access-Control-Allow-Headers", "Authorization, X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, X-Custom-header");
        res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
        */
    }

    @PostMapping("/signup")
    public ResponseEntity<?> inscriptionUtilisateur(@RequestBody UtilisateurEntity utilisateurEntity) {
        Optional<UtilisateurEntity> utilisateur = utilisateurRepository.findByUsername(utilisateurEntity.getUsername());
        if(utilisateur.isPresent()) {
            return new ResponseEntity<>("user already exist", HttpStatus.FOUND);
        }

        utilisateurRepository.save(utilisateurEntity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
