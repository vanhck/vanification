package de.vanhck.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

    public User(String name, String pwHash) {
        this.name = name;
        this.pwHash = pwHash;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public boolean hasEqualPassword(String inHash) {
        return pwHash.equals(inHash);
    }
}
