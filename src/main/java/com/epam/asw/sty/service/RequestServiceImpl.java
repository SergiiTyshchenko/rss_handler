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
    private List<Request> requests;

    public RequestServiceImpl() {
        this.requests = new ArrayList<Request>();
    }

    //private static List<Request> requests;


/*    static{
        requests = populateDummyRequests();
    }*/




    public List<Request> findAllRequests() {
        return populateRequestsFromDB();
    }


    public Request findById(long id) {
        for(Request request : populateRequestsFromDB()){
            if(request.getId() == id){
                return request;
            }
        }
        return null;
    }

    public Request findByRequestor(String requestor) {
        for(Request request : populateRequestsFromDB()){
            if(request.getRequestor().equalsIgnoreCase(requestor)){
                return request;
            }
        }
        return null;
    }

    public void saveRequest(Request request) {
        RequestRulesChecker checker = new RequestRulesChecker();
        requests = populateRequestsFromDB();
        counter.set(requests.get(requests.size()-1).getId());
        request.setId(counter.incrementAndGet());
        checker.superRequestCheck(request);
        requestDao.insertNewEntry(request);
    }

    public void updateRequest(Request request) {
        requestDao.updateEntry(request);
    }

    public void deleteRequestById(long id) {

        requests = populateRequestsFromDB();
        for (Iterator<Request> iterator = requests.iterator(); iterator.hasNext(); ) {
            Request request = iterator.next();
            if (request.getId() == id) {
                requestDao.removeEntryByID(id);
                break;
            }
        }
    }

    public boolean isRequestExist(Request request) {
        return findByRequestor(request.getRequestor())!=null;
    }

    public void deleteAllRequests(){
        populateRequestsFromDB().clear();
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
    public Object insertEntrytoDB(Request request) {
        requests = populateRequestsFromDB();
        counter.set(requests.get(requests.size()-1).getId());
        request.setId(counter.incrementAndGet());
        Object result = requestDao.insertNewEntry(request);
        return result;
    }


}