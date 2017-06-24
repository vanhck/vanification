package de.vanhck.data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Jonas on 24.06.2017.
 *
 * @author Jonas Kett
 * @version 1.0
 */
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String pwHash;

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Collection<Score> scores;

    public User(String name, String pwHash) {
        this.name = name;
        this.pwHash = pwHash;
        scores = new ArrayList<>();
    }

    public User() {
        scores = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getPwHash() {
        return pwHash;
    }

    public Collection<Score> getScores() {
        return scores;
    }
    public void addScore( Score score){
        scores.add(score);
    }
}
