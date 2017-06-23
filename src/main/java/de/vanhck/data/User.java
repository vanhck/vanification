package de.vanhck.data;

/**
 * Created by Jonas on 24.06.2017.
 *
 * @author Jonas Kett
 * @version 1.0
 */
public class User {
    private String name;
    private String pwHash;

    public User(String name, String pwHash) {
        this.name = name;
        this.pwHash = pwHash;
    }

    public String getName() {
        return name;
    }

    public boolean hasEqualPassword(String inHash) {
        return pwHash.equals(inHash);
    }
}
