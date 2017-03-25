package com.epam.asw.sty.model;

import java.util.Arrays;
import java.util.UUID;

public class Item extends com.sun.syndication.feed.rss.Item {


    private String id;

    private String channelID;


    public Item(){
        id=UUID.randomUUID().toString();
    }


    public Item(String id, String channelID) {
        this.id = id;
        this.channelID = channelID;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChannelID() {
        return channelID;
    }

    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Item))
            return false;
        Item other = (Item) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Item [id=" + id + ", channelID=" + channelID + ", description=" + super.getDescription()
                + ", link=" + super.getLink() + ", pubDate=" + super.getPubDate() + "]";
    }



}