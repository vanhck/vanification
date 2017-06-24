package de.vanhck;

import de.vanhck.data.*;
import javafx.util.Pair;

import java.util.Date;
import java.util.Optional;

/**
 * class to calc scoring
 * Created by Lotti on 6/24/2017.
 */
public class Scoring {

    public static Score getScore(DrivingResult result, DrivingKeyValueDAO dao) {
        double a_fuelConsumption = 1.0;
        Double fuelConsumption = getValueForName(result, "fuelConsumption");
        double sum_efficiency = 0.0d;
        if (fuelConsumption != null) {
             sum_efficiency += a_fuelConsumption * 5 * Math.pow(10, 2 - ((fuelConsumption / result.getDrivenKM()) * 100) / 7.0d) * result.getDrivenKM();
        }
        double a_hardStopCount = 0.5;
        Double hardStopCount = getValueForName(result, "hardStopCount");
        if (hardStopCount != null) {
            sum_efficiency +=  a_hardStopCount * -50 * hardStopCount;
        }
        double a_hardAccelerationCount = 0.5;
        Double hardAccelerationCount = getValueForName(result, "hardAccelerationCount");
        if(hardAccelerationCount!= null) {
            sum_efficiency +=  a_hardAccelerationCount * -5 * Math.exp(hardAccelerationCount / 2.0);
        }
        double a_sidewaysAccelerationS = 0.7;
        Double sidewaysAccelerationS = getValueForName(result, "sidewaysAccelerationS");
        if(sidewaysAccelerationS!=null) {
            sum_efficiency += a_sidewaysAccelerationS * -100 * sidewaysAccelerationS;
        }
        double a_constantVelocityKm = 0.5;
        Double constantVelocityKm = getValueForName(result, "constantVelocityKm");
        if(constantVelocityKm!=null) {
            sum_efficiency += a_constantVelocityKm * 80 * (constantVelocityKm - 0.4);
        }

        double e_stops = 2;
        Double stops = getValueForName(result, "stops");
        if(stops!= null) {
            double w_stops = 10 * (1 / (Math.abs(1 - stops / (e_stops * result.getDrivenKM())) + 0.01));
            return new Score(0.6 * sum_efficiency + 0.4 * w_stops, result.getDrivenKM(),  result.getDriver(), new Date(System.currentTimeMillis()));
        }else{
            return new Score(sum_efficiency, result.getDrivenKM(), result.getDriver(), new Date(System.currentTimeMillis()));
        }
    }

    private static Double getValueForName(DrivingResult result, String name) {
        Optional<DrivingKeyValue> any = result.getValues().stream().filter(drivingKeyValue -> drivingKeyValue.getKeyNameValue().getKeyName().toLowerCase().equals(name.toLowerCase())).findAny();
        if (any.isPresent()) {
            return any.get().getValue();
        }
        return null;

    }

}
