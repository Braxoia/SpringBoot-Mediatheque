package fr.ibralogan.mediatheque.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<UtilisateurEntity, Integer> {
    Optional<UtilisateurEntity> findByLogin(String login);
}
