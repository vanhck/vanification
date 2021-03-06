package de.vanhck.data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Created by Lotti on 6/23/2017.
 */
@Entity
@Table(name = "drivingresult")
public class DrivingResult {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String fin;
    @ManyToOne(fetch = FetchType.LAZY)
    private User driver;
    private Double drivenKM;
    private Date time;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "matchingResult")
    private Collection<DrivingKeyValue> values;

    public Date getTime() {
        return time;
    }

    public DrivingResult(@NotNull String fin, @NotNull User driver, @NotNull Double drivenKM, @NotNull Date time) {
        this.fin = fin;
        this.driver = driver;
        this.drivenKM = drivenKM;
        this.time = time;
        this.values = new ArrayList<>();
    }

    public DrivingResult() {
    }

    public void addValue(@NotNull DrivingKeyValue value) {
        values.add(value);
    }

    public Collection<DrivingKeyValue> getValues() {
        return values;
    }

    public User getDriver() {
        return driver;
    }

    public String getFin() {
        return fin;
    }

    public Double getDrivenKM() {
        return drivenKM;
    }
}
