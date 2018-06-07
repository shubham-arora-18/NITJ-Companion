package com.warkahot.nitjcompanion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by warkahot on 20-Sep-16.
 */
public class Boys_girls_hostel1 extends AppCompatActivity
{
    Toolbar toolbar;
    RelativeLayout boys_hostel,girls_hostel;
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.hostel_boys_girls);

        toolbar = (Toolbar) findViewById(R.id.events_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//enable up button ot go the parent activity

        boys_hostel = (RelativeLayout)findViewById(R.id.boys_hostel);
        girls_hostel = (RelativeLayout)findViewById(R.id.girls_hostel);

        boys_hostel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Boys_girls_hostel2.class);
                i.putExtra("type","boys");
                startActivity(i);
            }
        });

        girls_hostel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Boys_girls_hostel2.class);
                i.putExtra("type","girls");
                startActivity(i);
            }
        });
    }

    public void onBackPressed() {
        Intent ix = new Intent(getApplicationContext(),Navigation_drawer.class);
        startActivity(ix);
        finish();
    }
}
