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
public class Boys_girls_hostel2 extends AppCompatActivity {

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

        /*MobileAds.initialize(getApplicationContext(), "ca-app-pub-8386931136442571/9661656246");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/

        lLayout = new GridLayoutManager(getApplicationContext(), 1);

        rView = (RecyclerView) findViewById(R.id.rec_v_recyclerview);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);

        Boys_girls_hostel_2_3_adapter adap;

        Intent i = getIntent();
        if(i.getStringExtra("type").equals("boys"))
        {
            adap = new Boys_girls_hostel_2_3_adapter(getApplicationContext(),boys_hostel_list(),"boys",0,null);
            setTitle("B-Hostel Mess");
        }
        else
        {
            adap = new Boys_girls_hostel_2_3_adapter(getApplicationContext(),girls_hostel_list(),"girls",0,null);
            setTitle("G-Hostel Mess");
        }
        rView.setAdapter(adap);
    }

    public List<String> boys_hostel_list()
    {
        List<String> item_list = new ArrayList<>();
        item_list.add("Hostel 1");
        item_list.add("Hostel 2");
        item_list.add("Hostel 3");
        item_list.add("Hostel 4");
        item_list.add("Hostel 5");
        item_list.add("Hostel 6");
        item_list.add("Hostel 7");
        item_list.add("Mega Hostel");
        return item_list;
    }

    public List<String> girls_hostel_list()
    {
        List<String> item_list = new ArrayList<>();
        item_list.add("Hostel 1");
        item_list.add("Hostel 2");
        item_list.add("Mega Hostel");
        return item_list;
    }
}
