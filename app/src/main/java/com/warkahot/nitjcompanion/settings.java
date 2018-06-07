package com.warkahot.nitjcompanion;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.devspark.appmsg.AppMsg;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by warkahot on 22-Sep-16.
 */
public class settings extends AppCompatActivity {

    com.zcw.togglebutton.ToggleButton articles,placement,events;
    Toolbar toolbar;
    ProgressDialog pd;

    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.settings);

        toolbar = (Toolbar) findViewById(R.id.events_toolbar);
        setSupportActionBar(toolbar);
        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait...");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//enable up button ot go the parent activity
        setTitle("Settings");

        articles = (com.zcw.togglebutton.ToggleButton)findViewById(R.id.articles_noti);
        placement = (com.zcw.togglebutton.ToggleButton)findViewById(R.id.placements_noti);
        events = (com.zcw.togglebutton.ToggleButton)findViewById(R.id.events_noti);


        final DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://nitj-companion-24386.firebaseio.com/Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Subscriptions");

        if(new Network_available().isNetworkAvailable(getApplicationContext())) {
            pd.show();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pd.dismiss();
                if(dataSnapshot.child("nitj_comp_events").exists())
                {
                    events.setToggleOn();
                }
                else
                    events.setToggleOff();
                if(dataSnapshot.child("nitj_comp_placements").exists())
                {
                    placement.setToggleOn();
                }
                else
                    placement.setToggleOff();
                if(dataSnapshot.child("nitj_comp_articles").exists())
                {
                    articles.setToggleOn();
                }
                else
                    articles.setToggleOff();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (pd.isShowing()) {
                        AppMsg amk = AppMsg.makeText(settings.this, "An error occured. Please try again in some time...", AppMsg.STYLE_ALERT);
                        amk.show();
                        pd.dismiss();
                    }

                }
            }, 60000);

        }
        else
        {
            AppMsg appMsg = AppMsg.makeText(settings.this,"Please check your Internet Connection",AppMsg.STYLE_ALERT);
            appMsg.show();
            pd.dismiss();
        }




        articles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.child("nitj_comp_articles").exists()) {
                            ref.child("nitj_comp_articles").setValue("yes");
                            Toast.makeText(getApplicationContext(), "Articles Subscribed", Toast.LENGTH_SHORT).show();
                        } else {
                            ref.child("nitj_comp_articles").removeValue();
                            Toast.makeText(getApplicationContext(), "Articles Unsubscribed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.child("nitj_comp_events").exists()) {
                            ref.child("nitj_comp_events").setValue("yes");
                            Toast.makeText(getApplicationContext(), "Events Subscribed", Toast.LENGTH_SHORT).show();
                        } else {
                            ref.child("nitj_comp_events").removeValue();
                            Toast.makeText(getApplicationContext(), "Events Unsubscribed ", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        placement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.child("nitj_comp_placements").exists()) {
                            ref.child("nitj_comp_placements").setValue("yes");
                            Toast.makeText(getApplicationContext(), "Placements Subscribed", Toast.LENGTH_SHORT).show();
                        } else {
                            ref.child("nitj_comp_placements").removeValue();
                            Toast.makeText(getApplicationContext(), "Placements Unsubscribed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });


    }
}
