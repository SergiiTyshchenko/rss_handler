package com.epam.asw.sty.model;

public class Request {

    private long id;

    private String requestor;

    private String description;

    private String email;

    private String assignee;

    private String status;

    private Integer priority;

    public Request(){
        id=0;
    }

    public Request(long id, String requestor, String description, String email, String assignee, String status, Integer priority){
        this.id = id;
        this.requestor = requestor;
        this.description = description;
        this.email = email;
        this.assignee = assignee;
        this.status = status;
        this.priority = priority;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRequestor() {
        return requestor;
    }

    public void setRequestor(String requestor) {
        this.requestor = requestor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) { this.description = description; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAssignee() { return assignee; }

    public void setAssignee(String assignee) { this.assignee = assignee; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Request))
            return false;
        Request other = (Request) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Request [id=" + id + ", requestor=" + requestor + ", description=" + description
                + ", email=" + email + ", assignee=" + assignee + ", status=" + status
                + ", priority=" + priority + "]";
    }



}