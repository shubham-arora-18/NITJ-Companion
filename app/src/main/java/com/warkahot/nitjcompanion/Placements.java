package com.warkahot.nitjcompanion;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.devspark.appmsg.AppMsg;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by sarthi on 08-Jul-16.
 */
public class Placements extends ActionBarActivity {

    Toolbar toolbar;
    private GridLayoutManager lLayout;
    RecyclerView rView;
    ProgressDialog pd;

    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.recy_view);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-8386931136442571/9661656246");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        toolbar = (Toolbar) findViewById(R.id.rec_v__toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//enable up button ot go the parent activity
        //List<Events_list> rowListItem = getAllItemList();
        lLayout = new GridLayoutManager(Placements.this, 1);

        rView = (RecyclerView)findViewById(R.id.rec_v_recyclerview);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://nitj-companion-24386.firebaseio.com/Placements");

        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait...");
        if(new Network_available().isNetworkAvailable(getApplicationContext())) {
            pd.show();
        FirebaseRecyclerAdapter<Placements_list, Placements_holder> adapter = new FirebaseRecyclerAdapter<Placements_list, Placements_holder>(Placements_list.class, R.layout.placements_card, Placements_holder.class, ref) {
            @Override
            protected void populateViewHolder(final Placements_holder viewHolder,final Placements_list model, int position) {
                pd.dismiss();
                viewHolder.company_name.setText(model.getCompany_name());
                viewHolder.branch.setText(model.getBranch());
                viewHolder.lpa_val.setText(model.getLpa());
                viewHolder.post.setText(model.getPost());
                viewHolder.studennts.setText(model.getStudents());

            }

            @Override
            public Placements_list getItem(int position) {
                return super.getItem(getItemCount() - (position + 1));
            }
        };

        rView.setAdapter(adapter);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (pd.isShowing()) {
                        AppMsg amk = AppMsg.makeText(Placements.this, "An error occured. Please try again in some time...", AppMsg.STYLE_ALERT);
                        amk.show();
                        pd.dismiss();
                    }

                }
            }, 60000);

        }
        else
        {
            AppMsg appMsg = AppMsg.makeText(Placements.this,"Please check your Internet Connection",AppMsg.STYLE_ALERT);
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
