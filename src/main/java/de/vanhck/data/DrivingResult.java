package de.vanhck.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lotti on 6/23/2017.
 */
public class DrivingResult {
    private final String fin;
    private final String driversName;
    private final Double drivenKM;
    private Map<KeyValues, Double> values;

    public DrivingResult(String fin, String driversName, Double drivenKM) {
        this.fin = fin;
        this.driversName = driversName;
        this.drivenKM = drivenKM;
        this.values = new HashMap<>();
    }

    public void addValue(KeyValues name, double value){
        values.put(name,value);
    }

    public Map<KeyValues, Double> getValues() {
        return values;
    }

    public String getDriversName() {
        return driversName;
    }

    public String getFin() {
        return fin;
    }
}
