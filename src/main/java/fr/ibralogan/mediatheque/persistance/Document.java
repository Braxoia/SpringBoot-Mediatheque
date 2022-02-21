package fr.ibralogan.mediatheque.persistance;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Document {
    private static long DUREE_RESERVAION = 2;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(nullable = false)
    private String titre;

    @OneToOne(optional = true)
    private User reserveur;

    @Column(nullable = true)
    private LocalDateTime echeanceReservation = null;


    @ManyToOne
}
