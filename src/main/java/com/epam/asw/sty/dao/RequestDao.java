package com.epam.asw.sty.dao;

import com.epam.asw.sty.model.Request;

import java.util.List;

public interface RequestDao {

	List<Request> findByName(String requestor);
	
	List<Request> findAll();

	Object insertNewEntry(Request request);

	Object removeEntryByID(long id);

	Object updateEntry(Request request);
}