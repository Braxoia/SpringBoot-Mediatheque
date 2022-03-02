package fr.ibralogan.mediatheque.persistance;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import java.util.Set;
import javax.persistence.*;

import fr.ibralogan.mediatheque.mediatek2022.Utilisateur;
/**
 * Entité User
 */
@Entity
@Table(name = "users")
public class UtilisateurEntity implements Utilisateur {
    // Implémentation de l'interface Utilisateur -------------------
    public boolean isBibliothecaire() {
        return this.type.equals("Bibliothequaire");
    }
    public Object[] data() {
        return new Object[]{this.additionalData};
    }

    // Schéma ORM de la table User : -------------------------------

    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "CHAR(32)")
    @Id
    private String id;
    private String username;
    private String password;

    /**
     * Type of user : "Abonne" or "Bibliothequaire"
     * TODO : Check if enum possible instead of String
     */
    @Column()
    private String type;

    /**
     * Additional data stored in JSON
     */
    @Column()
    @JsonProperty(defaultValue = "[]")
    private String additionalData;

    @OneToMany
    private Set<DocumentEntity> documentsEmpruntes;

    public UtilisateurEntity() {
    }

    public UtilisateurEntity(String id, String username, String password, String type, String additionalData, Set<DocumentEntity> documentsEmpruntes) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.type = type;
        this.additionalData = additionalData;
        this.documentsEmpruntes = documentsEmpruntes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String name() {
        return this.username;
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