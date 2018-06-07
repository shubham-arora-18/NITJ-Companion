package com.warkahot.nitjcompanion;

/**
 * Created by warkahot on 18-Sep-16.
 */
public class article_headline_list  {

    String url,id,headline,writer,date,detail;

    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }

    public String getHeadline() {
        return headline;
    }

    public String getWriter() {
        return writer;
    }

    public article_headline_list() {

    }

    public String getDate() {
        return date;
    }

    public String getDetail() {
        return detail;
    }

    public article_headline_list( String id, String headline, String writer,String url,String date,String detail) {


        this.url = url;
        this.id = id;
        this.headline = headline;
        this.writer = writer;
        this.date = date;
        this.detail = detail;
    }
}
