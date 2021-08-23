package com.energy.service;

import com.energy.model.Event;
import com.energy.model.Meter;
import com.energy.repository.MeterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

	@Autowired
	private MeterRepository meterRepository;

	/**
	 * Response: 201 {"meter": {"meterNo": "1714A6", "consumption": 0, "microgeneration": 0}}
	 */
	@Override
	public Meter create(Event event) throws Exception {
        Meter meter = new Meter(event.getMeterNumber());
		meter.setConsumption(new BigInteger("0"));
		meter.setMicrogeneration(new BigInteger("0"));
		meterRepository.save(meter);
		return meter;
	}

	/**
	 * Response: 201 {"meter": {"meterNo": "1714A6", "consumption": 377, "microgeneration": 47}}
	 */
	@Override
	public Meter push(Event event) throws Exception {
		Optional<Meter> oMeter = meterRepository.findById(event.getMeterNumber());
		if ( oMeter.isPresent()) {
			Meter meter = oMeter.get();
			meter.setConsumption(event.getActiveEnergy());
			meter.setMicrogeneration(event.getInjectedEnergy());
			meterRepository.save(meter);
			return meter;
		}
		return null;
	}

	@Override
	public Meter find(String meterNumber) throws Exception {
		Optional<Meter> oMeter = meterRepository.findById(meterNumber);
		if ( oMeter.isPresent()) {
			Meter meter = oMeter.get();
			return meter;
		}
		return null;
	}

	@Override
	public Meter billing(String meterNumber, Double unit) throws Exception {
		Optional<Meter> oMeter = meterRepository.findById(meterNumber);
		if ( oMeter.isPresent()) {
			Meter meter = oMeter.get();
			meter.setCash((meter.getConsumption().doubleValue() - meter.getMicrogeneration().doubleValue()) * unit);
			Meter meterBilled = new Meter();
			meterBilled.setMeterNo(meter.getMeterNo());
			meterBilled.setCash(meter.getCash());
			return meterBilled;
		}
		return null;
	}

	@Override
	public Meter findByMeterNo(Long meterNo) throws Exception {
		Meter meter = meterRepository.findByMeterNo(meterNo);
		return meter;
	}
}
