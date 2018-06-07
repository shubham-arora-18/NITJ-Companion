package com.warkahot.nitjcompanion;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.devspark.appmsg.AppMsg;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

/**
 * Created by warkahot on 18-Sep-16.
 */
public class society_main extends AppCompatActivity {

    Toolbar toolbar;
    private GridLayoutManager lLayout;
    RecyclerView rView;
    ProgressDialog pd;

    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.recy_view);

        /* MobileAds.initialize(getApplicationContext(), "ca-app-pub-8386931136442571/9661656246");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/

        toolbar = (Toolbar) findViewById(R.id.rec_v__toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//enable up button ot go the parent activity
        //List<Events_list> rowListItem = getAllItemList();
        lLayout = new GridLayoutManager(society_main.this, 2);

        rView = (RecyclerView)findViewById(R.id.rec_v_recyclerview);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);


        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait...");

        if(new Network_available().isNetworkAvailable(getApplicationContext())) {
            pd.show();

            DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://nitj-companion-24386.firebaseio.com/Societies");
            FirebaseRecyclerAdapter<select_society_list, society_holder> adapter = new FirebaseRecyclerAdapter<select_society_list, society_holder>(select_society_list.class, R.layout.society_card, society_holder.class, ref) {
                @Override
                protected void populateViewHolder(final society_holder viewHolder, final select_society_list model, int position) {
                    pd.dismiss();

                    viewHolder.soc_name.setText(model.getSociety_name());
                    Log.d("article_main.java", " url = " + model.getImg_url());
                    Picasso.with(society_main.this).load(model.getImg_url()).into(viewHolder.soc_img);
                    viewHolder.soc_card.setCardBackgroundColor(Color.parseColor("#0853a8"));
                    viewHolder.soc_card.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent ix = new Intent(society_main.this, society_main_2.class);
                            ix.putExtra("soc_name", model.getSociety_name());
                            ix.putExtra("url", model.getImg_url());
                            startActivity(ix);
                        }
                    });

                }
            };

            rView.setAdapter(adapter);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (pd.isShowing()) {
                        AppMsg amk = AppMsg.makeText(society_main.this, "An error occured. Please try again in some time...", AppMsg.STYLE_ALERT);
                        amk.show();
                        pd.dismiss();
                    }

                }
            }, 60000);

        }
        else
        {
            AppMsg appMsg = AppMsg.makeText(society_main.this,"Please check your Internet Connection",AppMsg.STYLE_ALERT);
            appMsg.show();
            pd.dismiss();
        }
    }

    public void onBackPressed() {
        Intent ix = new Intent(getApplicationContext(),Navigation_drawer.class);
        startActivity(ix);
        finish();
    }
}
