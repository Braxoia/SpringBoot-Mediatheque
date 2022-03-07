package fr.ibralogan.mediatheque.persistance.repository;

import fr.ibralogan.mediatheque.persistance.entities.UtilisateurEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<UtilisateurEntity, Integer> {
    Optional<UtilisateurEntity> findByUsername(String login);
    void deleteById(String id);
}
