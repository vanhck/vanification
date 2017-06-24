package de.vanhck.data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Lotti on 6/24/2017.
 */
@Entity
@Table(name = "score")
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Date date;

    private Double score;

    private double course;

    @ManyToOne(cascade = CascadeType.ALL)
    public User user;

    public Date getDate() {
        return date;
    }

    public Double getScore() {
        return score;
    }

    public double getCourse() {
        return course;
    }

    public Score(Double score, Double course){
        this.score = score;
        this.course = course;
    }
}
