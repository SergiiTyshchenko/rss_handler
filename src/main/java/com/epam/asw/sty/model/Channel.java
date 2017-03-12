package com.epam.asw.sty.model;

public class Channel {


    private long id;

    private String title;

    private String link;

    private String description;

    private String language;

    private String pubDate;

    private String dcDate;

    private String dcLanguage;

    private String item;

    public Channel(){
        id=0;
    }

    public Channel(long id, String title, String description, String link, String language, String pubDate, String dcDate, String dcLanguage, String item) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.language = language;
        this.pubDate = pubDate;
        this.dcDate = dcDate;
        this.dcLanguage = dcLanguage;
        this.item = item;
        this.link = link;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getDcDate() {
        return dcDate;
    }

    public void setDcDate(String dcDate) {
        this.dcDate = dcDate;
    }

    public String getDcLanguage() {
        return dcLanguage;
    }

    public void setDcLanguage(String dcLanguage) {
        this.dcLanguage = dcLanguage;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
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
        return "Request [id=" + id + ", title=" + title + ", description=" + description
                + ", link=" + link + ", language=" + language + ", pubDate=" + pubDate
                + ", item=" + item + "]";
    }



}