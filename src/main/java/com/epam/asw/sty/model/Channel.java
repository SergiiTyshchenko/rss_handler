package com.epam.asw.sty.model;

public class Channel extends com.sun.syndication.feed.rss.Channel {


    private long id;

    private String user;


    public Channel(){
        id=0;
    }


    public Channel(String type, long id, String user) {
        super(type);
        this.id = id;
        this.user = user;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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
        if (!(obj instanceof Channel))
            return false;
        Channel other = (Channel) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Request [id=" + id + ", user=" + user + ", description=" + super.getDescription()
                + ", link=" + super.getLink() + ", language=" + super.getLanguage() + ", pubDate=" + super.getPubDate()
                + ", item=" + super.getItems() + "]";
    }



}