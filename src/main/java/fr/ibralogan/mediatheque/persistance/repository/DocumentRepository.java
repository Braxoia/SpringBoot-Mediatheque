package fr.ibralogan.mediatheque.persistance.repository;

import fr.ibralogan.mediatheque.persistance.entities.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocumentRepository extends JpaRepository<DocumentEntity, Integer> {
    Optional<DocumentEntity> findByTitre(String titre);
}
