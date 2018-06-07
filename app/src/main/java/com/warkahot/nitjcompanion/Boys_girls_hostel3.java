package com.warkahot.nitjcompanion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by warkahot on 20-Sep-16.
 */
public class Boys_girls_hostel3 extends AppCompatActivity
{
    Toolbar toolbar;
    private GridLayoutManager lLayout;
    RecyclerView rView;

    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.recy_view);

        toolbar = (Toolbar) findViewById(R.id.rec_v__toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//enable up button ot go the parent activity
        //List<Events_list> rowListItem = getAllItemList();
        lLayout = new GridLayoutManager(getApplicationContext(), 2);

        rView = (RecyclerView) findViewById(R.id.rec_v_recyclerview);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);

       /* MobileAds.initialize(getApplicationContext(), "ca-app-pub-8386931136442571/9661656246");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/

        Boys_girls_hostel_2_3_adapter adap;
        Intent i = getIntent();
        String hostel_name = i.getStringExtra("hostel");
        String type = i.getStringExtra("type");

        if(type.equals("boys"))
            setTitle("B-"+hostel_name+" Mess");
        else
            setTitle("G-"+hostel_name+" Mess");

        adap = new Boys_girls_hostel_2_3_adapter(getApplicationContext(),week_days(),type,1,hostel_name);
        rView.setAdapter(adap);
    }

    public List<String> week_days()
    {
        List<String> item_list = new ArrayList<>();
        item_list.add("Monday");
        item_list.add("Tuesday");
        item_list.add("Wednesday");
        item_list.add("Thursday");
        item_list.add("Friday");
        item_list.add("Saturday");
        item_list.add("Sunday");

        return item_list;
    }
}
