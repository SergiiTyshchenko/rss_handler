package com.epam.asw.sty.service;

import com.epam.asw.sty.dao.RequestDao;
import com.epam.asw.sty.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service("requestService")
@Transactional
public class RequestServiceImpl implements RequestService {

    @Resource(name="requestDaoImpl")
    RequestDao requestDao;

    private static final AtomicLong counter = new AtomicLong();


    private static List<Request> requests;

    static{
        requests = populateDummyRequests();
    }

    public List<Request> findAllRequests() {
        return requests;
    }

    public Request findById(long id) {
        for(Request request : requests){
            if(request.getId() == id){
                return request;
            }
        }
        return null;
    }

    public Request findByRequestor(String requestor) {
        for(Request request : requests){
            if(request.getRequestor().equalsIgnoreCase(requestor)){
                return request;
            }
        }
        return null;
    }

    public void saveRequest(Request request) {
        RequestRulesChecker checker = new RequestRulesChecker();
        request.setId(counter.incrementAndGet());
        checker.superRequestCheck(request);
        requests.add(request);
    }

    public void updateRequest(Request request) {
        int index = requests.indexOf(request);
        requests.set(index, request);
    }

    public void deleteRequestById(long id) {

        for (Iterator<Request> iterator = requests.iterator(); iterator.hasNext(); ) {
            Request request = iterator.next();
            if (request.getId() == id) {
                iterator.remove();
            }
        }
    }

    public boolean isRequestExist(Request request) {
        return findByRequestor(request.getRequestor())!=null;
    }

    public void deleteAllRequests(){
        requests.clear();
    }

    private static List<Request> populateDummyRequests(){
        List<Request> requests = new ArrayList<Request>();
        requests.add(new Request(counter.incrementAndGet(),"Sergii", "Create Jenkins unit test job", "sergantty@gmai.com", "Stas", "", 1));
        requests.add(new Request(counter.incrementAndGet(),"Stas", "Introduce Ansible usage", "kronverk@hotmail.com", "Sergii", "", 2));
        requests.add(new Request(counter.incrementAndGet(),"Dummy", "Summy task", "dummy_request@gmail.com", "whatever new", "", 3));
        return requests;
    }

    public List<Request> populateRequestsFromDB(){
        List<Request> requests = requestDao.findAll();
        return requests;
    }

    @Override
    public Object insertEntrytoDB(String requestor) {
         Object result = requestDao.insertNewEntry(requestor);
        return result;
    }


}