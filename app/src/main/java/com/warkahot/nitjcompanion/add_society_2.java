package com.warkahot.nitjcompanion;

import java.util.HashMap;

/**
 * Created by warkahot on 14-Oct-16.
 */
public class add_society_2  {
    String Contact_info;
    String Detail;
    String name;

    public add_society_2(String name,String detail,String contact_info) {
        Contact_info = contact_info;
        Detail = detail;
        this.name = name;
    }

    public String getContact_info() {
        return Contact_info;
    }

    public String getDetail() {
        return Detail;
    }

    public String getName() {
        return name;
    }

    public add_society_2(String contact_info, String detail) {

        Contact_info = contact_info;

        Detail = detail;
    }

    public add_society_2() {
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("Contact_info", Contact_info);
        hm.put("Detail", Detail);
        return hm;
    }

}
