package com.warkahot.nitjcompanion;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
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

/**
 * Created by warkahot on 19-Sep-16.
 */
public class article_main2 extends AppCompatActivity {

    ImageView article_img,like_button,add_to_fav;
    TextView article_detail,writer,date,heading,likes,seen_by_val;
    Toolbar toolbar;
    NestedScrollView nsv;
    ProgressDialog pd;

    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.articles_page);

        article_img = (ImageView) findViewById(R.id.article_image);
        article_detail = (TextView) findViewById(R.id.article_detail);
        heading = (TextView) findViewById(R.id.article_heading);
        writer = (TextView) findViewById(R.id.article_writer_name);
        date = (TextView) findViewById(R.id.article_date);
        nsv = (NestedScrollView) findViewById(R.id.nsv_article);

        like_button = (ImageView)findViewById(R.id.article_like_icon);
        add_to_fav = (ImageView)findViewById(R.id.article_fav_button);
        likes = (TextView)findViewById(R.id.article_like_value);
        seen_by_val = (TextView)findViewById(R.id.article_seen_by_val);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-8386931136442571/9661656246");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait...");

        Intent i = getIntent();
        final String art_id = i.getStringExtra("art_id");
        final String art_head = i.getStringExtra("art_head");
        final String art_url = i.getStringExtra("url");
        final String art_writer = i.getStringExtra("art_wri");
        final String art_detail = i.getStringExtra("art_det");
        final String art_date = i.getStringExtra("art_date");

        collapsingToolbar.setTitle("Article");
        Picasso.with(getApplicationContext()).load(art_url).into(article_img);
        writer.setText(art_writer);
        heading.setText(art_head);
        article_detail.setText(art_detail);
        date.setText(art_date);

        final DatabaseReference article_dr = FirebaseDatabase.getInstance().getReferenceFromUrl("https://nitj-companion-24386.firebaseio.com/Article headlines").child(art_id);
        final DatabaseReference my_id_dr = FirebaseDatabase.getInstance().getReferenceFromUrl("https://nitj-companion-24386.firebaseio.com/Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());


        if(new Network_available().isNetworkAvailable(getApplicationContext())) {
            pd.show();
        article_dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pd.dismiss();
                if (dataSnapshot.exists()) {
                    likes.setText(dataSnapshot.child("likes").getChildrenCount() + " Likes");
                }
                else
                {
                    likes.setText( "0 Likes");
                }
                if (dataSnapshot.child("seen by").exists()) {
                    seen_by_val.setText(dataSnapshot.child("seen by").getChildrenCount() + "");
                }
                else
                {
                    seen_by_val.setText("0");
                }
                if(!dataSnapshot.child("seen by").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).exists())
                    article_dr.child("seen by").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (pd.isShowing()) {
                        AppMsg amk = AppMsg.makeText(article_main2.this, "An error occured. Please try again in some time...", AppMsg.STYLE_ALERT);
                        amk.show();
                        pd.dismiss();
                    }

                }
            }, 60000);

        }
        else
        {
            AppMsg appMsg = AppMsg.makeText(article_main2.this,"Please check your Internet Connection",AppMsg.STYLE_ALERT);
            appMsg.show();
            pd.dismiss();
        }



        article_dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("likes").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).exists()) {
                    like_button.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.liked));
                } else {
                    like_button.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.like));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        my_id_dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("my articles").child(art_id).exists()) {
                    add_to_fav.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.star_selected));
                } else {
                    add_to_fav.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.star_unselected));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        like_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                article_dr.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.child("likes").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).exists()) {
                            article_dr.child("likes").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                            Toast.makeText(getApplicationContext(), "Liked", Toast.LENGTH_SHORT).show();
                        } else {
                            article_dr.child("likes").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                            Toast.makeText(getApplicationContext(), "Unliked", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

        add_to_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my_id_dr.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.child("my articles").child(art_id).exists()) {
                            article_headline_list ahl = new article_headline_list(art_id, art_head, art_writer, art_url, art_date, art_detail);
                            my_id_dr.child("my articles").child(art_id).setValue(ahl);
                            Toast.makeText(getApplicationContext(), "Added to favourites", Toast.LENGTH_SHORT).show();
                        } else {
                            my_id_dr.child("my articles").child(art_id).removeValue();
                            Toast.makeText(getApplicationContext(), "Removed from favourites", Toast.LENGTH_SHORT).show();
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
