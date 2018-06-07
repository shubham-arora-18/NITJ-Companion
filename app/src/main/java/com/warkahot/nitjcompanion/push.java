package com.warkahot.nitjcompanion;

import android.support.multidex.MultiDexApplication;


import java.util.ArrayList;

/**
 * Created by sarthi on 13-Jul-16.
 */
public class push extends MultiDexApplication {

    private ArrayList<String> doubt_tags;

    public ArrayList<String> getDoubt_tags() {
        return doubt_tags;
    }

    public void setDoubt_tags(ArrayList<String> doubt_tags) {
        this.doubt_tags = doubt_tags;
    }


    public void onCreate()
    {
        super.onCreate();

    }

}
