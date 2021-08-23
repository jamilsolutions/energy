package com.energy.model;

import com.energy.enums.Type;
import org.springframework.data.annotation.Id;

import java.math.BigInteger;

public class Event {

    String type;
    String meterNumber;
    @Id
    private Long meterNo;
    private BigInteger activeEnergy;
    private BigInteger injectedEnergy;
    private Double unit;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMeterNumber() {
        return meterNumber;
    }

    public void setMeterNumber(String meterNumber) {
        this.meterNumber = meterNumber;
    }

    public Long getMeterNo() {
        return meterNo;
    }

    public void setMeterNo(Long meterNo) {
        this.meterNo = meterNo;
    }

    public BigInteger getActiveEnergy() {
        return activeEnergy;
    }

    public void setActiveEnergy(BigInteger activeEnergy) {
        this.activeEnergy = activeEnergy;
    }

    public BigInteger getInjectedEnergy() {
        return injectedEnergy;
    }

    public void setInjectedEnergy(BigInteger injectedEnergy) {
        this.injectedEnergy = injectedEnergy;
    }

    public Double getUnit() {
        return unit;
    }

    public void setUnit(Double unit) {
        this.unit = unit;
    }
}
