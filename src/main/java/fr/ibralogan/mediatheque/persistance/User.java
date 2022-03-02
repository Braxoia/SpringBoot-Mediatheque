package fr.ibralogan.mediatheque.persistance;

import org.hibernate.annotations.GenericGenerator;
import java.util.Set;
import javax.persistence.*;

/**
 * Entité User
 */
@Entity
public class User {

    /** Unique identifier of user */
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    /** Username */
    @Column(unique = true, length = 32)
    private String login;

    /** Password
     * TODO : Hash it
     */
    @Column()
    private String pass;

    /**
     * Type of user : "Abonne" or "Bibliothequaire"
     * TODO : Check if enum possible instead of String
     */
    @Column()
    private String type;

    @OneToMany
    private Set<DocumentEntity> documents;
}
