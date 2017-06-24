package de.vanhck.data;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Lotti on 6/24/2017.
 */
@Entity
@Table (name = "tbl_keyname")
public class KeyNameValue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String keyName;

    public String getKeyName() {
        return keyName;
    }
}
