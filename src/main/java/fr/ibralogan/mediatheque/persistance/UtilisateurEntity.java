package fr.ibralogan.mediatheque.persistance;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import java.util.Set;
import javax.persistence.*;

import fr.ibralogan.mediatheque.mediatek2022.Utilisateur;
/**
 * Entité User
 */
@Entity(name="user")
public class UtilisateurEntity implements Utilisateur {
    // Implémentation de l'interface Utilisateur -------------------
    public String name() {
        return this.login;
    }
    public boolean isBibliothecaire() {
        return this.type.equals("Bibliothequaire");
    }
    public Object[] data() {
        return new Object[]{this.additionalData};
    }

    // Schéma ORM de la table User : -------------------------------

    /**
     * Unique identifier of user
     */
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    /**
     * Username
     */
    @Column(unique = true, length = 32)
    private String login;

    /**
     * Password
     * TODO : Hash it
     */
    @Column()
    private String password;
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public String getPassword() {
        return this.password;
    }
    /**
     * Type of user : "Abonne" or "Bibliothequaire"
     * TODO : Check if enum possible instead of String
     */
    @Column()
    private String type;

    /**
     * Additional data stored in JSON
     */
    @Column(name="data")
    @JsonProperty(defaultValue = "[]")
    private String additionalData;

    @OneToMany
    private Set<DocumentEntity> documentsEmpruntes;

}
