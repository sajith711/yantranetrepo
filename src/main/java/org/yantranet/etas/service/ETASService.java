package org.yantranet.etas.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yantranet.etas.model.CabRequest;
import org.yantranet.etas.model.Cabs;
import org.yantranet.etas.repo.CabRepository;
import org.yantranet.etas.repo.CabRequestRepository;

@Service
public class ETASService {
	
	@Autowired
	private CabRepository cabrepo;
	
	@Autowired
	private CabRequestRepository cabreqrepo;
	
	public enum RequestStatus{
		INVALID_TRIP_TIME,
		CAB_NOT_AVAILABLE,
		SOURCE_INVALID,
		REQUEST_NOT_POSSIBLE,
		PROCESSED,
		CANCELLED,
		REQUESTID_NOT_AVAILABLE,
		ERROR_CODE,
		CAB_STATUS_NOT_UPDATED,
		requestid;
	}
	
	public Object updateCabStatus(Long cabid, String status) {
		Cabs cab = cabrepo.findById(cabid).get();
		if(null != cab) {
			cab.setCabsatus(status);
			return cabrepo.save(cab);
		}else {
			Map<String, String> error = new HashMap<>();
			error.put(RequestStatus.ERROR_CODE.toString(), RequestStatus.CAB_STATUS_NOT_UPDATED.toString());
			return error;
		}
	}
	
	public Object createCabRequest(CabRequest request) throws ParseException{
		List<Cabs> cabs = (List<Cabs>) cabrepo.findAll();
		Map<String, String> response = new HashMap<>();
		if(!ETASService.checkForSupportedHours(request.getDatetimeofjourney())) {
			response.put(RequestStatus.ERROR_CODE.toString(), RequestStatus.INVALID_TRIP_TIME.toString());
			return response;
		}
		else if(cabs.stream().filter(c -> (request.getSourcelocation().equalsIgnoreCase(c.getSourcelocation()) && c.getVacancy()>0)).noneMatch(c -> request.getDatetimeofjourney().equals(c.getTimeslot()))) {
			response.put(RequestStatus.ERROR_CODE.toString(), RequestStatus.CAB_NOT_AVAILABLE.toString());
			return response;
		}
		else if(cabs.stream().noneMatch(c -> request.getSourcelocation().equalsIgnoreCase(c.getSourcelocation()))) {
			response.put(RequestStatus.ERROR_CODE.toString(), RequestStatus.SOURCE_INVALID.toString());
			return response;
		}
		else {
			List<Cabs> filtered = cabs.stream().filter(c ->(request.getSourcelocation().equalsIgnoreCase(c.getSourcelocation()) && c.getVacancy()>0 
					&& request.getDatetimeofjourney().equalsIgnoreCase(c.getTimeslot()))).collect(Collectors.toList());
			if(ETASService.checkForRequestTime(filtered)) {
				response.put(RequestStatus.ERROR_CODE.toString(), RequestStatus.REQUEST_NOT_POSSIBLE.toString());
				return response;
			}else {
				Cabs cab = filtered.get(0);
				cab.setVacancy(cab.getVacancy()-1);
				cabrepo.save(cab);
				request.setCabid(cab.getCabid());
				request.setRequestcreationdate(new Date(System.currentTimeMillis()).toString());
				request.setRequeststatus(RequestStatus.PROCESSED.toString());
				request.setBookingid(RandomStringUtils.random(5, true, false));
				CabRequest req = cabreqrepo.save(request);
				response.put(RequestStatus.requestid.toString(), String.valueOf(req.getRequestid()));
				return response;
			}
		}
	}

	public Object cancelRequest(Long requestid) {
		Optional<CabRequest> request = cabreqrepo.findById(requestid);
		if(request.isPresent()) {
			CabRequest req = request.get();
			req.setRequeststatus(RequestStatus.CANCELLED.toString());
			Cabs cab = cabrepo.findById(req.getCabid()).get();
			cab.setVacancy(cab.getVacancy()+1);
			cabrepo.save(cab);
			return cabreqrepo.save(req);
		}else {
			Map<String, String> response = new HashMap<>();
			response.put(RequestStatus.ERROR_CODE.toString(), RequestStatus.REQUESTID_NOT_AVAILABLE.toString());
			return response;
		}
	}
	
	public static boolean checkForSupportedHours(String rdate) throws ParseException{
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		Date date = null;
		date = formatter.parse(rdate);
		int day = date.getDay();
		SimpleDateFormat sdfHour = new SimpleDateFormat("HH:mm:ss");
		String hour = null;
		hour = sdfHour.format(sdfHour.parse(rdate.split(" ")[1]));
		if(day!=0 && day!=6 && hour.compareTo("22:00:00") >= 0 && hour.compareTo("01:00:00") <= 0)
			return true;
		else
			return false;
	}
	
	public static boolean checkForRequestTime(List<Cabs> checklist) {
		Long millis_per_hour = 12 *60 *60*1000L;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		if(checklist.stream().anyMatch(c -> {
			try {
				return ((Math.abs(System.currentTimeMillis() - formatter.parse(c.getTimeslot()).getTime()) >millis_per_hour)
						&& (TimeUnit.DAYS.convert(System.currentTimeMillis() - formatter.parse(c.getTimeslot()).getTime(), TimeUnit.MILLISECONDS) < 2));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return false;
		})) {
			return true;
		}else {
			return false;
		}
	}
}
