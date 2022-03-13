package fr.ibralogan.mediatheque.persistance.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import java.util.Set;
import javax.persistence.*;

import fr.ibralogan.mediatheque.mediatek2022.Utilisateur;

/**
 * L'entité représentant un utilisateur permettant de mettre en place
 * la table en base de données via l'ORM de Spring Boot.
 */
@Entity(name = "utilisateur")
public class UtilisateurEntity implements Utilisateur {
    // Implémentation de l'interface Utilisateur obligatoire -------------------

    public boolean isBibliothecaire() {
        return this.type.equals("Bibliothequaire");
    }
    public Object[] data() {
        return new Object[]{this.additionalData};
    }

    // Schéma ORM de la table Utilisateur : -------------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;

    /**
     * Type of user : "Abonne" or "Bibliothequaire"
     */
    @Column
    @NotNull
    private String type;

    /**
     * Données additionnelles sous la forme d'un tableau JSON
     */
    @Column
    @JsonProperty(defaultValue = "[]")
    private String additionalData;

    //Un utilisateur possède 0 ou n documents empruntés
    @OneToMany
    private Set<DocumentEntity> documentsEmpruntes;

    public UtilisateurEntity() {
    }

    public UtilisateurEntity(int id, String username, String password, String type, String additionalData, Set<DocumentEntity> documentsEmpruntes) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.type = type;
        this.additionalData = additionalData;
        this.documentsEmpruntes = documentsEmpruntes;
    }

    public UtilisateurEntity(String username, String password, String type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}