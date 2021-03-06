package fr.ibralogan.mediatheque.security;

import fr.ibralogan.mediatheque.persistance.entities.UtilisateurEntity;
import fr.ibralogan.mediatheque.persistance.repository.UtilisateurRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Collections.emptyList;

/**
 * Classe permettant d'indiquer au framework comment trouver nos utilisateurs
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UtilisateurRepository utilisateurRepository;

    public UserDetailsServiceImpl(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UtilisateurEntity> utilisateur = utilisateurRepository.findByUsername(username);
        if(utilisateur.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        return new User(utilisateur.get().getUsername(), utilisateur.get().getPassword(), emptyList());
    }
}
