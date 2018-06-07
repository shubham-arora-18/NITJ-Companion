package com.warkahot.nitjcompanion;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created by sarthi on 14-Jul-16.
 */
public class add_society_1 {
    String society_name;
    String img_url;

    public add_society_1() {
    }

    public add_society_1(String society_name, String img_url) {
        this.society_name = society_name;
        this.img_url = img_url;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getSociety_name() {
        return society_name;
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("society_name", society_name);
        hm.put("img_url", img_url);
        return hm;
    }
}
