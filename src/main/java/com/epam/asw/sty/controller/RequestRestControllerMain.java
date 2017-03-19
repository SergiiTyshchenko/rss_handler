package com.epam.asw.sty.controller;


import com.epam.asw.sty.dao.RequestDao;
import com.epam.asw.sty.model.Request;
import com.epam.asw.sty.service.DBRequestStats;
import com.epam.asw.sty.service.RequestService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



@Controller
public class RequestRestControllerMain {

	@Resource(name="requestService")
	private RequestService requestService;  //Service which will do all data retrieval/manipulation work



	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name="requestDaoImpl")
	private RequestDao requestDao;
	
/*	@RequestMapping(value = "/db/", method = RequestMethod.GET)
	public String welcome(Model model) {

		String logDebugMessage = "epam_sergii";

		logger.debug("DEBUG message {}.", logDebugMessage);

		List<Request> request = requestDao.findByName("Sergii");
		
		List<Request> requests = requestDao.findAll();

		logger.info("INFO message {}.", requests);
		model.addAttribute("request", requests);
		
		return "welcome";

	}*/

	//-------------------Retrieve Requests Statistic Data--------------------------------------------------------

	@RequestMapping(value = "/db/request/stats", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> DBRequestsStats() {
		List<Request> requests = requestService.populateRequestsFromDB();
		Integer requests_count;
		if(requests.isEmpty()){
			return new ResponseEntity<Map<String,Object>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
		}
		else {
			DBRequestStats statsTest = new DBRequestStats();
			Map<String,Object> stats = new java.util.HashMap<>();
			requests_count = statsTest.DBRequestCount(requests);
			stats.put("DB Request count: ", requests_count);
			List<String> request_count_per_requestor = new ArrayList<String>();
			request_count_per_requestor=statsTest.RequestsPerRequestor(requests);
			stats.put("DB Request count per requestor: ",request_count_per_requestor);
			return new ResponseEntity<Map<String,Object>>(stats, HttpStatus.OK);
		}
	}


	//-------------------Add New Request--------------------------------------------------------


	@RequestMapping(value = "/db/request/insert", method = RequestMethod.GET)
	public ResponseEntity<Object> DBRequestsInsert() {
		Request request = new Request();
		request.setRequestor("SERGIIIIIII");
		Object result = requestService.insertEntrytoDB(request);
		if(result==null){
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}

}
