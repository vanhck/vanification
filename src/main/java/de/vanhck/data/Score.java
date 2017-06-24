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

    private double averageFuelConsumption;

    private double hardStopCount;

    private double hardAccelerationCount;

    private double constantVelocityKm;

    public double getAverageFuelConsumption() {
        return averageFuelConsumption;
    }

    public double getHardStopCount() {
        return hardStopCount;
    }

    public double getHardAccelerationCount() {
        return hardAccelerationCount;
    }

    public double getConstantVelocityKm() {
        return constantVelocityKm;
    }

    public double getStopCount() {
        return stopCount;
    }

    private double stopCount;

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

    public Score(Double score, Double course, User driver, Date creationDate, Double averageFuelConsumption,
                 Double hardStopCount, Double hardAccelerationCount, Double constantVelocityKm, Double stopCount){
        this.score = score;
        this.course = course;
        this.user = driver;
        this.date = creationDate;
        this.averageFuelConsumption = averageFuelConsumption;
        this.hardStopCount = hardStopCount;
        this.hardAccelerationCount = hardAccelerationCount;
        this.constantVelocityKm = constantVelocityKm;
        this.stopCount = stopCount;
    }

   public  Score(){}
}
