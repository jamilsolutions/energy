package com.energy.controller;

import com.energy.enums.Type;
import com.energy.model.Event;
import com.energy.model.Meter;
import com.energy.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EventController {
	
   private static final Logger log = LoggerFactory.getLogger(EventController.class);
		
	private EventService eventService;
	
	public EventController(EventService eventService) {
		this.eventService = eventService;
	}

	/**
	 *  Get consumption for a non-existing meter
	 * Request: GET /consumption?meter_number=1234
	 * Response: 404 0
	 *
	 *  Get consumption for an existing meter
	 * Request: GET /consumption?meter_number=1714A6
	 * Response: 200 377
	 */
	@RequestMapping(value = "/consumption", method = RequestMethod.GET)
	public ResponseEntity<String> consumption(@RequestParam String meter_number) throws Exception {
		Meter meter = new Meter("0");
		meter = eventService.find(meter_number);
		if ( meter == null) {
			log.info("Get consumption for a non-existing meter. Not Found!");
			return ResponseEntity.notFound().build();
		} else {
			log.info("Get consumption for an existing meter. Meter={}", meter.toString());
			return ResponseEntity.status(HttpStatus.OK).body(String.valueOf(meter.getConsumption()));
		}
	}

	/**
	 *  Get microgeneration for an existing meter
	 * Request: GET /microgeneration?meter_number=1714A6
	 * Response: 200 47
	 */
	@RequestMapping(value = "/microgeneration", method = RequestMethod.GET)
	public ResponseEntity<String> microgeneration(@RequestParam String meter_number) throws Exception {
		Meter meter = null;
		meter = eventService.find(meter_number);
		if ( meter == null) {
			log.info("Get microgeneration for an existing meter. Not Found!");
			return ResponseEntity.notFound().build();
		} else {
			log.info("Get microgeneration for an existing meter. Meter={}", meter.toString());
			return ResponseEntity.status(HttpStatus.OK).body(String.valueOf(meter.getMicrogeneration()));
		}
	}

	/**
	 *  Create meter
	 * Request: POST /event {"type":"import", "meterNumber":"1714A6"}
	 * Response: 201 {"meter": {"meterNo": "1714A6", "consumption": 0, "microgeneration": 0}}
	 *
	 *  Receive energy data consumption and microgeneration from an existing meter
	 * Request: POST /event {"type": "push", "meterNumber": "1714A6", "activeEnergy": 377,  "injectedEnergy": 47}
	 * Response: 201 {"meter": {"meterNo": "1714A6", "consumption": 377, "microgeneration": 47}}
	 *
	 *  Bill energy consumption from a non-existing meter
	 * Request: POST /event {"type": "billing",  "meterNo": "123", "unit": 0.42}
	 * Response: 404 0
	 *
	 *  Bill energy consumption from an existing meter
	 * Request: POST /event {"type": "billing",  "meterNumber": "1714A6", "unit": 0.42}
	 * Response: 201 {"meter": {"meterNo": "1714A6", "cash": 138.6}}
	 */
	@RequestMapping(value = "/event", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Meter> event(@Validated @RequestBody Event event) throws Exception {
		Meter meter = new Meter("0");
		if ( event != null ) {
			if (Type.IMPORT.getType().contains(event.getType())) {
				meter = eventService.create(event);
				log.info("Create meter. Meter={}", meter.toString());
			} else if (Type.PUSH.getType().contains(event.getType())) {
                meter = eventService.push(event);
				log.info("Receive energy data consumption and microgeneration from an existing meter. Meter={}", meter.toString());
			} else if (Type.BILLING.getType().contains(event.getType())) {
				if ( event != null) {
					if ( event.getMeterNumber() != null) {
						meter = eventService.billing(event.getMeterNumber(), event.getUnit());
					} else if ( event.getMeterNo() != null) {
						meter = eventService.findByMeterNo(event.getMeterNo());
					}
					if ( meter == null ) {
						log.info("Bill energy consumption from a non-existing meter. Not Found");
						return ResponseEntity.notFound().build();
					} else {
						log.info("Bill energy consumption from an existing meter. Meter={}", meter.toString());
					}
				}
			}

		}
		return ResponseEntity.ok(meter);
	}

	@GetMapping("/health")
	public String health() {
		return "I am live!";
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(HttpClientErrorException.NotFound.class)
	public String handleValidationExceptions(HttpClientErrorException ex) {
		return ex.getMessage();
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<String, String>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public Map<String, String> handleValidationExceptions(HttpMessageNotReadableException ex) {
		Map<String, String> errors = new HashMap<String, String>();
		FieldError error = new FieldError("HttpMessageNotReadableException", "Energy", ex.getMessage());
		String fieldName = ((FieldError) error).getField();
		String errorMessage = error.getDefaultMessage();
		errors.put(fieldName, errorMessage);
		return errors;
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public Map<String, String> handleValidationExceptions(Exception ex) {
		Map<String, String> errors = new HashMap<String, String>();
		FieldError error = new FieldError("Exception", "Energy", ex.getMessage());
		String fieldName = ((FieldError) error).getField();
		String errorMessage = error.getDefaultMessage();
		errors.put(fieldName, errorMessage);
		return errors;
	}
}
