package org.yantranet.etas.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.yantranet.etas.model.CabRequest;
import org.yantranet.etas.service.ETASService;

@CrossOrigin(origins = "*")
@RestController
public class ETASController {
	
	@Autowired
	private ETASService etasservice;
	
	@RequestMapping(value = "cabs/{cabid}/{status}" , method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> update(@PathVariable final Long cabid,@PathVariable final String status) {
		return new ResponseEntity<>(etasservice.updateCabStatus(cabid, status), HttpStatus.valueOf(200));
	}
	
	@RequestMapping(value = "request" , method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> postRequest(@Valid @RequestBody CabRequest request) throws ParseException{
		return new ResponseEntity<>(etasservice.createCabRequest(request), HttpStatus.valueOf(200));
	}
	
	@RequestMapping(value = "cancelrequest/{requestid}" , method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> cancelRequest(@PathVariable final Long requestid) {
		return new ResponseEntity<>(etasservice.cancelRequest(requestid), HttpStatus.valueOf(200));
	}
}
