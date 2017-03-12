package com.epam.asw.sty.model;




public class Transformer {

    Request request;
    Channel channel;
    Transformer transform;

    public Transformer(Request request, Channel channel) {
        this.request = request;
        this.channel = channel;
    }

    public Transformer() {
    }

    public Transformer getTransform() {
        return transform;
    }

    public void setTransform(Transformer transform) {
        transform = transformate(request, channel);
        this.transform = transform;
    }

    public Transformator transformate(Request request, Channel channel) {
     Transformator transformator = new Transformator();
     //transformator.id = request.getId();
     //transformator.name = request.getRequestor();
     return  transformator;
    }

}

class Transformator extends Transformer {

    private long id;

    private String title;

    private String link;

    private String description;

    private String language;

    private String pubDate;

    private String dcDate;

    private String dcLanguage;

    private String item;

    public Transformator(){
        id=0;
    }

    public Transformator(long id, String title, String description, String link, String language, String pubDate, String dcDate, String dcLanguage, String item) {
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
}
