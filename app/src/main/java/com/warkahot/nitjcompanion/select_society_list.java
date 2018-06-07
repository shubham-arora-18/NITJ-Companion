package com.warkahot.nitjcompanion;

/**
 * Created by sarthi on 14-Jul-16.
 */
public class select_society_list  {
    String society_name;
    String img_url;

    public select_society_list() {
    }

    public select_society_list(String society_name,String img_url) {
        this.society_name = society_name;
        this.img_url = img_url;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getSociety_name() {
        return society_name;
    }
}
