package com.epam.asw.sty.model;

public class Item extends com.sun.syndication.feed.rss.Item {


    private long id;

    private long channelID;


    public Item(){
        id=0;
    }


    public Item(long id, long channelID) {
        this.id = id;
        this.channelID = channelID;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getChannelID() {
        return channelID;
    }

    public void setChannelID(long channelID) {
        this.channelID = channelID;
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
        if (!(obj instanceof Item))
            return false;
        Item other = (Item) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Channel [id=" + id + ", channelID=" + channelID + ", description=" + super.getDescription()
                + ", link=" + super.getLink() + ", pubDate=" + super.getPubDate() + "]";
    }



}