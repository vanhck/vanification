package de.vanhck.data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Lotti on 6/24/2017.
 */
@Entity
public class DrivingKeyValue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private KeyNameValue keyNameValue;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private DrivingResult matchingResult;
    private Double value;

    public Double getValue() {
        return value;
    }

    public KeyNameValue getKeyNameValue() {
        return keyNameValue;
    }

    public DrivingKeyValue(@NotNull KeyNameValue keyNameValue, @NotNull Double value){
        this.keyNameValue = keyNameValue;
        this.value = value;
    }

    public DrivingKeyValue() {
    }

    public void setMatchingResult(DrivingResult matchingResult) {
        this.matchingResult = matchingResult;
    }
}
