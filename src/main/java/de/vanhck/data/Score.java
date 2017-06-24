package de.vanhck.data;

import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Lotti on 6/24/2017.
 */
@Entity
@Table (name = "score")
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Date date;

    private int score;

    @ManyToOne(cascade = CascadeType.ALL)
    public User user;
}
