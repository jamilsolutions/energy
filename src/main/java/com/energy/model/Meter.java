package com.energy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;

@AllArgsConstructor
@Document(collection = "Meter")
public class Meter {

	@Id 
	private String meterNo;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private BigInteger consumption;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private BigInteger microgeneration;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private BigInteger activeEnergy;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private BigInteger injectedEnergy;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Double unit;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Double cash;

	public Meter() {
	}

	public Meter(String meterNo) {
       this.meterNo = meterNo;
	}

	public String getMeterNo() {
		return meterNo;
	}

	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}

	public BigInteger getConsumption() {
		return consumption;
	}

	public void setConsumption(BigInteger consumption) {
		this.consumption = consumption;
	}

	public BigInteger getMicrogeneration() {
		return microgeneration;
	}

	public void setMicrogeneration(BigInteger microgeneration) {
		this.microgeneration = microgeneration;
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

	public Double getCash() {
		return cash;
	}

	public void setCash(Double cash) {
		this.cash = cash;
	}

	@Override
	public String toString() {
		return "Meter{" +
				"meterNo='" + meterNo + '\'' +
				", consumption=" + consumption +
				", microgeneration=" + microgeneration +
				", activeEnergy=" + activeEnergy +
				", injectedEnergy=" + injectedEnergy +
				", unit=" + unit +
				", cash=" + cash +
				'}';
	}
}
