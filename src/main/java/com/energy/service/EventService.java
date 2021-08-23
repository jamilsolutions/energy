package com.energy.service;

import com.energy.model.Event;
import com.energy.model.Meter;

import java.math.BigInteger;

public interface EventService {

    Meter create(Event event) throws Exception;
    Meter push(Event event) throws Exception;
    Meter find(String meter_number) throws Exception;
    Meter billing(String meterNumber, Double unit) throws Exception;
    Meter findByMeterNo(Long meterNo) throws Exception;;

}
