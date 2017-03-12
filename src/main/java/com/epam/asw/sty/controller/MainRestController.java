package com.epam.asw.sty.controller;

import com.epam.asw.sty.service.RequestService;
import com.epam.asw.sty.model.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class MainRestController {

    private static final Logger logger = LoggerFactory.getLogger(MainRestController.class);

    @Resource(name="requestService")
    private RequestService requestService;  //Service which will do all data retrieval/manipulation work


    //-------------------Retrieve All Requests--------------------------------------------------------

    @RequestMapping(value = "/request/", method = RequestMethod.GET)
    public ResponseEntity<List<Request>> listAllRequests() {
        List<Request> requests = requestService.populateRequestsFromDB();
        if(requests.isEmpty()){
            return new ResponseEntity<List<Request>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Request>>(requests, HttpStatus.OK);
    }


    //-------------------Retrieve Single Request--------------------------------------------------------

    @RequestMapping(value = "/request/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Request> getRequest(@PathVariable("id") long id) {
        String logDebugMessage = "Fetching Request with id " + id;
        logger.debug("DEBUG message {}.", logDebugMessage);
        Request request = requestService.findById(id);
        if (request == null) {
            logDebugMessage = "Request with id " + id + " not found";
            logger.debug("DEBUG message {}.", logDebugMessage);
            return new ResponseEntity<Request>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Request>(request, HttpStatus.OK);
    }



    //-------------------Create a Request--------------------------------------------------------

    @RequestMapping(value = "/request/", method = RequestMethod.POST)
    public ResponseEntity<Void> createRequest(@RequestBody Request request, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating Request " + request.getRequestor());

/*        if (requestService.isRequestExist(request)) {
            System.out.println("Requestor " + request.getRequestor() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }*/

        requestService.saveRequest(request);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/request/{id}").buildAndExpand(request.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }



    //------------------- Update Request --------------------------------------------------------

    @RequestMapping(value = "/request/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Request> updateRequest(@PathVariable("id") long id, @RequestBody Request request) {
        String logDebugMessage = "Updating Request " + id;
        logger.debug("DEBUG message {}.", logDebugMessage);
        Request currentRequest = requestService.findById(id);

        if (currentRequest==null) {
            logDebugMessage = "Request with id " + id + " not found";
            logger.debug("DEBUG message {}.", logDebugMessage);
            return new ResponseEntity<Request>(HttpStatus.NOT_FOUND);
        }

        currentRequest.setRequestor(request.getRequestor());
        currentRequest.setDescription(request.getDescription());
        currentRequest.setEmail(request.getEmail());
        currentRequest.setAssignee(request.getAssignee());
        currentRequest.setStatus(request.getStatus());
        currentRequest.setPriority(request.getPriority());

        requestService.updateRequest(currentRequest);
        return new ResponseEntity<Request>(currentRequest, HttpStatus.OK);
    }



    //------------------- Delete a Request --------------------------------------------------------

    @RequestMapping(value = "/request/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Request> deleteRequest(@PathVariable("id") long id) {
        String logDebugMessage = "Fetching & Deleting Request with id " + id;
        logger.debug("DEBUG message {}.", logDebugMessage);
        Request request = requestService.findById(id);
        if (request == null) {
            logDebugMessage = "Unable to delete. Request with id " + id + " not found";
            logger.debug("DEBUG message {}.", logDebugMessage);
            return new ResponseEntity<Request>(HttpStatus.NOT_FOUND);
        }

        requestService.deleteRequestById(id);
        return new ResponseEntity<Request>(HttpStatus.NO_CONTENT);
    }



    //------------------- Delete All Requests --------------------------------------------------------

    @RequestMapping(value = "/request/", method = RequestMethod.DELETE)
    public ResponseEntity<Request> deleteAllRequests() {
        String logDebugMessage = "Deleting All Requests";
        logger.debug("DEBUG message {}.", logDebugMessage);
        requestService.deleteAllRequests();
        return new ResponseEntity<Request>(HttpStatus.NO_CONTENT);
    }

}