package com.warkahot.nitjcompanion;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.devspark.appmsg.AppMsg;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.zcw.togglebutton.ToggleButton;

/**
 * Created by warkahot on 18-Sep-16.
 */
public class society_main_2 extends AppCompatActivity  {

    ImageView society_img;
    TextView detail,contact_info;
    TextView society_subs_value;
    ToggleButton soc_subs;
    ProgressDialog pd;

    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.society_page);

        society_img = (ImageView)findViewById(R.id.society_image1);
        detail = (TextView)findViewById(R.id.society_detail);
        contact_info = (TextView)findViewById(R.id.contact_info);
        society_subs_value = (TextView)findViewById(R.id.society_subs_value);
        soc_subs = (ToggleButton)findViewById(R.id.soc_noti);

        /* MobileAds.initialize(getApplicationContext(), "ca-app-pub-8386931136442571/9661656246");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        final String soc_name = i.getStringExtra("soc_name");
        String soc_url = i.getStringExtra("url");

        collapsingToolbar.setTitle(soc_name);
        Picasso.with(getApplicationContext()).load(soc_url).into(society_img);

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://nitj-companion-24386.firebaseio.com/Society details").child(soc_name);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    society_detail_list sdl = dataSnapshot.getValue(society_detail_list.class);

                    detail.setText(sdl.getDetail());
                    contact_info.setText(sdl.getContact_info());
                }
                if (dataSnapshot.child("Subscriptions").exists())
                    society_subs_value.setText(dataSnapshot.child("Subscriptions").getChildrenCount() + " Subscriptions");
                else
                    society_subs_value.setText("0 Subscriptions");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait...");


        final DatabaseReference ref1 = FirebaseDatabase.getInstance().getReferenceFromUrl("https://nitj-companion-24386.firebaseio.com/Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Subscriptions");
        if(new Network_available().isNetworkAvailable(getApplicationContext())) {
            pd.show();

            ref1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(soc_name).exists()) {
                        pd.dismiss();
                        soc_subs.setToggleOn();
                    } else
                    {
                        soc_subs.setToggleOff();
                        pd.dismiss();
                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (pd.isShowing()) {
                        AppMsg amk = AppMsg.makeText(society_main_2.this, "An error occured. Please try again in some time...", AppMsg.STYLE_ALERT);
                        amk.show();
                        pd.dismiss();
                    }

                }
            }, 60000);

        }
        else
        {
            AppMsg appMsg = AppMsg.makeText(society_main_2.this,"Please check your Internet Connection",AppMsg.STYLE_ALERT);
            appMsg.show();
            pd.dismiss();
        }


        soc_subs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.child(soc_name).exists()) {
                            {
                                ref1.child(soc_name).setValue("yes");
                                ref.child("Subscriptions").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue("yes");
                            }

                            Toast.makeText(getApplicationContext(), soc_name + " Subscribed", Toast.LENGTH_SHORT).show();
                        } else {
                            ref1.child(soc_name).removeValue();
                            ref.child("Subscriptions").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                            Toast.makeText(getApplicationContext(), soc_name + " Unsubscribed", Toast.LENGTH_SHORT).show();
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
