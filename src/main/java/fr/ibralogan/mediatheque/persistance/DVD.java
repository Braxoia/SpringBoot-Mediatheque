package fr.ibralogan.mediatheque.persistance;

import javax.persistence.*;

@Entity
public class DVD {
    @Id
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private Document doc;

    @Column
    private int ageMin;
}
