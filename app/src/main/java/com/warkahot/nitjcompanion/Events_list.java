package com.warkahot.nitjcompanion;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created by sarthi on 13-Jul-16.
 */
public class Events_list {

    public Events_list() {
    }

    public String society,event,special_event,venue,time,event_name,date,type,id;

    public Events_list(String event_name,String society,String event,String special_event,String venue,String time,String date,String type,String id) {
        this.society = society;
        this.event =  event;
        this.special_event = special_event;
        this.venue = venue;
        this.time= time;
        this.date = date;
        this.event_name = event_name;
        this.type = type;
        this.id = id;
    }

    public String getsociety() {
        return society;
    }
    public String getevent() {
        return event;
    }
    public String getvenue() {
        return venue;
    }
    public String getType() {
        return type;
    }
    public String getspecial_event() {
        return special_event;
    }
    public String gettime() {
        return time;
    }
    public String getevent_name() {
        return event_name;
    }
    public String getdate() {
        return date;
    }
    public String getId() {
        return id;
    }

    public HashMap<String,Object> toMap()
    {
        HashMap<String,Object> hm = new HashMap<>();
        hm.put("society",society);
        hm.put("event",event);
        hm.put("special_event",special_event);
        hm.put("venue",venue);
        hm.put("time",time);
        hm.put("date",date);
        hm.put("event_name",event_name);
        hm.put("type",type);
        hm.put("id",id);
        return hm;

    }
}
