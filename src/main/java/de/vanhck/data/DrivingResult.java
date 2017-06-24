package de.vanhck.data;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Lotti on 6/23/2017.
 */
@Entity
@Table(name = "drivingresult")
public class DrivingResult {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private  String fin;
    private  String driversName;
    private  Double drivenKM;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "matchingResult")
    private Collection<DrivingKeyValue> values;

    public DrivingResult(@NotNull  String fin,@NotNull String driversName,@NotNull Double drivenKM) {
        this.fin = fin;
        this.driversName = driversName;
        this.drivenKM = drivenKM;
        this.values = new ArrayList<>();
    }

    public DrivingResult() {
    }

    public void addValue(@NotNull DrivingKeyValue value){
        values.add(value);
    }

    public Collection<DrivingKeyValue> getValues() {
        return values;
    }

    public String getDriversName() {
        return driversName;
    }

    public String getFin() {
        return fin;
    }

    public Double getDrivenKM() {
        return drivenKM;
    }
}
