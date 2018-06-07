package com.warkahot.nitjcompanion;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.devspark.appmsg.AppMsg;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
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
 * Created by warkahot on 18-Sep-16.
 */
public class article_main extends AppCompatActivity {

    Toolbar toolbar;
    private GridLayoutManager lLayout;
    RecyclerView rView;
    ProgressDialog pd;

    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.recy_view);

        toolbar = (Toolbar) findViewById(R.id.rec_v__toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//enable up button ot go the parent activity
        //List<Events_list> rowListItem = getAllItemList();

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-8386931136442571/9661656246");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait...");

        lLayout = new GridLayoutManager(article_main.this, 1);

        rView = (RecyclerView) findViewById(R.id.rec_v_recyclerview);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);
        DatabaseReference ref;

        if(getIntent().getStringExtra("from")==null)
        ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://nitj-companion-24386.firebaseio.com/Article headlines");
        else
        {
            ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://nitj-companion-24386.firebaseio.com/Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("my articles");
            setTitle("My Articles");
        }

        if(new Network_available().isNetworkAvailable(getApplicationContext())) {
            pd.show();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pd.dismiss();
                if (!dataSnapshot.exists())
                    Toast.makeText(getApplicationContext(), "No favourite articles", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (pd.isShowing()) {
                        AppMsg amk = AppMsg.makeText(article_main.this, "An error occured. Please try again in some time...", AppMsg.STYLE_ALERT);
                        amk.show();
                        pd.dismiss();
                    }

                }
            }, 60000);

        }
        else
        {
            AppMsg appMsg = AppMsg.makeText(article_main.this,"Please check your Internet Connection",AppMsg.STYLE_ALERT);
            appMsg.show();
            pd.dismiss();
        }

        FirebaseRecyclerAdapter<article_headline_list, article_holder> adapter = new FirebaseRecyclerAdapter<article_headline_list, article_holder>(article_headline_list.class, R.layout.article_card, article_holder.class, ref) {
            @Override
            public article_headline_list getItem(int position) {
                return super.getItem(getItemCount() - (position + 1));
            }

            @Override
            protected void populateViewHolder(final article_holder viewHolder,final article_headline_list model, int position) {
                pd.dismiss();
                viewHolder.article_heading.setText(model.getHeadline());
                Picasso.with(getApplicationContext()).load(model.getUrl()).into(viewHolder.article_img);
                Log.d("article_main.java", " url = " + model.getUrl());
                viewHolder.writer_name.setText(model.getWriter());
                viewHolder.article_card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in1 = new Intent(getApplicationContext(),article_main2.class);
                        in1.putExtra("art_id",model.getId());
                        in1.putExtra("art_head",model.getHeadline());
                        in1.putExtra("url",model.getUrl());
                        in1.putExtra("art_wri",model.getWriter());
                        in1.putExtra("art_date",model.getDate());
                        in1.putExtra("art_det",model.getDetail());
                        startActivity(in1);
                    }
                });


            }
        };

        rView.setAdapter(adapter);




    }

    @Override
    public void onBackPressed() {
        Intent ix = new Intent(getApplicationContext(),Navigation_drawer.class);
        startActivity(ix);
        finish();
    }
}
