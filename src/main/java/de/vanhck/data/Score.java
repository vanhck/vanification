package de.vanhck.data;

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

    public Date getDate() {
        return date;
    }

    public int getScore() {
        return score;
    }

    private Date date;

    private int score;

    public double getCourse() {
        return course;
    }

    private double course;

    @ManyToOne(cascade = CascadeType.ALL)
    public User user;
}
