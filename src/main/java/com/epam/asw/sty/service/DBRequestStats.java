package com.epam.asw.sty.service;

import com.epam.asw.sty.model.Request;

import java.util.ArrayList;
import java.util.List;

public class DBRequestStats {

    public Integer DBRequestCount(List<Request> requests){
        Integer count = 0;
        for (Request req : requests){
            count++;
        }
        return count;
    }

    public List<String> RequestsPerRequestor(List<Request> requests){
        List<String> stats = new ArrayList<String>();
        Integer count_sergii=0;
        Integer count_stas=0;
        for (Request req : requests){
            if (req.getRequestor().equals("Sergii")){
                count_sergii++;
            }

            if (req.getRequestor().equals("Stas")){
                count_stas++;
            }
        }
        stats.add(0,"Sergii Requests: " + count_sergii);
        stats.add(1,"Stas Requests: " + count_stas);
        return stats;
    }
}
