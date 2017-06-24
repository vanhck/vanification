package de.vanhck.data;

import javax.persistence.*;

/**
 * Created by renx on 24.06.17.
 */
@Entity
@Table(name = "option_tbl")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Double effiency;
    public Double getEffiency() { return effiency; }
    public void setEffiency(Double value) {effiency = value;}

    private Double aFuelConsumption;
    public Double getaFuelConsumption() { return aFuelConsumption; }
    public void setaFuelConsumption(Double value) {aFuelConsumption = value;}

    private Double aHardStopCount;
    public Double getaHardStopCount() { return aHardStopCount; }
    public void setaHardStopCount(Double value) {aHardStopCount = value;}

    private Double aHardAccelerationCount;
    public Double getaHardAccelerationCount() { return aHardAccelerationCount; }
    public void setaHardAccelerationCount(Double value) {aHardAccelerationCount = value;}

    private Double aSidewaysAcceleration;
    public Double getaSidewaysAcceleration() {return aSidewaysAcceleration; }
    public void setaSidewaysAcceleration(Double value) {aSidewaysAcceleration = value;}

    private Double aConstantVelocity;
    public Double getaConstantVelocity() {return aConstantVelocity;}
    public void setaConstantVelocity(Double value) {aConstantVelocity = value;}

    private Double productivity;
    public Double getProductivity() {return productivity;}
    public void setProductivity(Double value) {productivity = value;}

    private Integer eStops;
    public Integer getEStops() {return eStops;}
    public void setEStops(Integer value) {eStops = value;}

    public Option(){
        effiency = 0.6;
        aFuelConsumption = 1.0;
        aHardStopCount = 0.5;
        aHardAccelerationCount = 0.5;
        aConstantVelocity = 0.5;
        aSidewaysAcceleration = 0.7;
        productivity = 0.4;
        eStops = 20;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if ( obj == null || getClass() != obj.getClass())
            return false;
        Option that = (Option) obj;
        return id== that.id;
    }
    @Override
    public int hashCode() {
        return  ((Long)id).hashCode();
    }
}
