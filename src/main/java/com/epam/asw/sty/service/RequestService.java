package com.epam.asw.sty.service;

import com.epam.asw.sty.model.Request;

import java.util.List;


public interface RequestService {

    Request findById(long id);

    Request findByRequestor(String requestor);

    void saveRequest(Request request);

    void updateRequest(Request request);

    void deleteRequestById(long id);

    List<Request> findAllRequests();

    void deleteAllRequests();

    public boolean isRequestExist(Request request);

    List<Request> populateRequestsFromDB();

    Object insertEntrytoDB(String requestor);

}