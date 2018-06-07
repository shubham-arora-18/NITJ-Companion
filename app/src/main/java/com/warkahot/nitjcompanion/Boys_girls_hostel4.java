package com.warkahot.nitjcompanion;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

/**
 * Created by warkahot on 20-Sep-16.
 */
public class Boys_girls_hostel4 extends AppCompatActivity {

    String type,hostel_name,day_clicked;
    ImageView like_break,like_lunch,like_snacks,like_din;
    TextView like_break_t,like_lunch_t,like_snacks_t,like_din_t,m_break,m_lunch,m_snacks,m_din;
    LinearLayout ll1,ll2,ll3,ll4;
    Toolbar toolbar;
    ProgressDialog pd;

    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.hostel_menu_main);

        toolbar = (Toolbar) findViewById(R.id.events_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//enable up button ot go the parent activity
        //List<Events_list> rowListItem = getAllItemList();

        /*MobileAds.initialize(getApplicationContext(), "ca-app-pub-8386931136442571/9661656246");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/

        like_break = (ImageView)findViewById(R.id.mess_like_icon_break);
        like_lunch = (ImageView)findViewById(R.id.mess_like_icon_lunch);
        like_snacks = (ImageView)findViewById(R.id.mess_like_icon_snacks);
        like_din = (ImageView)findViewById(R.id.mess_like_icon_din);

        like_break_t = (TextView)findViewById(R.id.mess_like_value_break);
        like_lunch_t = (TextView)findViewById(R.id.mess_like_value_lunch);
        like_snacks_t = (TextView)findViewById(R.id.mess_like_value_snacks);
        like_din_t = (TextView)findViewById(R.id.mess_like_value_din);

        m_break = (TextView)findViewById(R.id.mess_break);
        m_lunch = (TextView)findViewById(R.id.mess_lunch);
        m_snacks = (TextView)findViewById(R.id.mess_snacks);
        m_din = (TextView)findViewById(R.id.mess_din);

        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait...");

        ll1 = (LinearLayout)findViewById(R.id.ll1);
        ll2 = (LinearLayout)findViewById(R.id.ll2);
        ll3 = (LinearLayout)findViewById(R.id.ll3);
        ll4 = (LinearLayout)findViewById(R.id.ll4);

        Intent i = getIntent();
        type = i.getStringExtra("type");
        hostel_name = i.getStringExtra("hostel");
        day_clicked = i.getStringExtra("day");

        setTitle(day_clicked+" Mess");

        if(type.equals("boys"))
        {
            type = "Boys mess";

        }
        if(type.equals("girls"))
        {
            type = "Girls mess";
            ll1.setBackgroundColor(Color.parseColor("#2d801c"));
            ll2.setBackgroundColor(Color.parseColor("#2d801c"));
            ll3.setBackgroundColor(Color.parseColor("#2d801c"));
            ll4.setBackgroundColor(Color.parseColor("#2d801c"));
        }

        final DatabaseReference dr = FirebaseDatabase.getInstance().getReferenceFromUrl("https://nitj-companion-24386.firebaseio.com/").child(type).child(hostel_name).child(day_clicked);

        if(new Network_available().isNetworkAvailable(getApplicationContext())) {
            pd.show();
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               pd.dismiss();
                weakday_food wf = dataSnapshot.getValue(weakday_food.class);
                m_break.setText(wf.getBreakfast());
                m_lunch.setText(wf.getLunch());
                m_snacks.setText(wf.getSnack());
                System.out.println("Snacks = "+wf.getSnack());
                m_din.setText(wf.getDinner());

                if (dataSnapshot.child("breakfast_likes").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).exists()) {
                    like_break.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.liked));

                } else {
                    like_break.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.like));

                }

                if (dataSnapshot.child("lunch_likes").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).exists()) {
                    like_lunch.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.liked));

                } else {
                    like_lunch.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.like));

                }
                if (dataSnapshot.child("snacks_likes").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).exists()) {
                    like_snacks.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.liked));

                } else {
                    like_snacks.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.like));

                }
                if (dataSnapshot.child("dinner_likes").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).exists()) {
                    like_din.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.liked));

                } else {
                    like_din.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.like));

                }

                if(dataSnapshot.child("breakfast_likes").exists())
                {
                    like_break_t.setText(dataSnapshot.child("breakfast_likes").getChildrenCount() + " Likes");
                }
                else
                {
                    like_break_t.setText("0 Likes");
                }

                if(dataSnapshot.child("lunch_likes").exists())
                {
                    like_lunch_t.setText(dataSnapshot.child("lunch_likes").getChildrenCount() + " Likes");
                }
                else
                {
                    like_lunch_t.setText("0 Likes");
                }

                if(dataSnapshot.child("snacks_likes").exists())
                {
                    like_snacks_t.setText(dataSnapshot.child("snacks_likes").getChildrenCount() + " Likes");
                }
                else{
                    like_snacks_t.setText("0 Likes");
                }


                if(dataSnapshot.child("dinner_likes").exists()) {
                    like_din_t.setText(dataSnapshot.child("dinner_likes").getChildrenCount() + " Likes");
                }
                else
                {
                    like_din_t.setText("0 Likes");
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
                        AppMsg amk = AppMsg.makeText(Boys_girls_hostel4.this, "An error occured. Please try again in some time...", AppMsg.STYLE_ALERT);
                        amk.show();
                        pd.dismiss();
                    }

                }
            }, 60000);

        }
        else
        {
            AppMsg appMsg = AppMsg.makeText(Boys_girls_hostel4.this,"Please check your Internet Connection",AppMsg.STYLE_ALERT);
            appMsg.show();
            pd.dismiss();
        }




        like_break.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dr.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.child("breakfast_likes").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).exists()) {
                            dr.child("breakfast_likes").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                            Toast.makeText(getApplicationContext(), "Breakfast Liked", Toast.LENGTH_SHORT).show();
                        } else {
                            dr.child("breakfast_likes").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                            Toast.makeText(getApplicationContext(), "Breakfast Unliked", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });




        like_lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dr.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.child("lunch_likes").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).exists()) {
                            dr.child("lunch_likes").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                            Toast.makeText(getApplicationContext(), "Lunch Liked", Toast.LENGTH_SHORT).show();
                        } else {
                            dr.child("lunch_likes").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                            Toast.makeText(getApplicationContext(), "Lunch Unliked", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });


        like_snacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dr.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.child("snacks_likes").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).exists()) {
                            dr.child("snacks_likes").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                            Toast.makeText(getApplicationContext(), "Snacks Liked", Toast.LENGTH_SHORT).show();
                        } else {
                            dr.child("snacks_likes").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                            Toast.makeText(getApplicationContext(), "Snacks Unliked", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
                                       }

        );


        like_din.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                dr.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.child("dinner_likes").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).exists()) {
                            dr.child("dinner_likes").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                            Toast.makeText(getApplicationContext(), "Dinner Liked", Toast.LENGTH_SHORT).show();
                        } else {
                            dr.child("dinner_likes").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                            Toast.makeText(getApplicationContext(), "Dinner Unliked", Toast.LENGTH_SHORT).show();
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
