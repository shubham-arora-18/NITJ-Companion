package com.warkahot.nitjcompanion;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.devspark.appmsg.AppMsg;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by sarthi on 13-Jul-16.
 */
public class Events_main extends AppCompatActivity{

    private GridLayoutManager lLayout;
    Toolbar toolbar;
    LinearLayout filter;
    private static final int Event_Tags = 11;
    ArrayList<String> filter_list = new ArrayList<>();
    RecyclerView rView;
    int child_count = 0;
    long total_child = 0;
    int child_processed = 0;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_page);

        toolbar = (Toolbar) findViewById(R.id.events_toolbar);
        setSupportActionBar(toolbar);
        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait...");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//enable up button ot go the parent activity

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-8386931136442571/9661656246");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

       filter = (LinearLayout)findViewById(R.id.filter_layout);
        //List<Events_list> rowListItem = getAllItemList();
        lLayout = new GridLayoutManager(Events_main.this, 1);

        rView = (RecyclerView)findViewById(R.id.events_recyclerview);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);



        if(new Network_available().isNetworkAvailable(getApplicationContext())) {
            pd.show();
            set_recy_view();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (pd.isShowing()) {
                        AppMsg amk = AppMsg.makeText(Events_main.this, "An error occured. Please try again in some time...", AppMsg.STYLE_ALERT);
                        amk.show();
                        pd.dismiss();
                    }

                }
            }, 60000);
        } else
        {
            AppMsg appMsg = AppMsg.makeText(Events_main.this,"Please check your Internet Connection",AppMsg.STYLE_ALERT);
            appMsg.show();
            pd.dismiss();
        }



        rView.setOnScrollListener(new Hiding_views() {
            @Override
            public void onHide() {
                hideViews();
            }

            @Override
            public void onShow() {
                showViews();
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                child_count = 0;
                child_processed = 0;
                Intent inx = new Intent(Events_main.this,select_society_main.class);
                inx.putExtra("key","event_tags");
                startActivityForResult(inx, Event_Tags);
            }
        });
    }

    public void set_recy_view()
    {
        DatabaseReference ref;
        Intent i1 = getIntent();
        if(i1!=null && i1.getStringExtra("from")!=null && i1.getStringExtra("from").equals("my_events_nav_menu"))
        {
            ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://nitj-companion-24386.firebaseio.com/Users");
            FirebaseUser fu = FirebaseAuth.getInstance().getCurrentUser();
            ref = ref.child(fu.getUid()).child("MyEvents");
            setTitle("My Events");
            filter.setVisibility(View.GONE);
        }

        else
            ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://nitj-companion-24386.firebaseio.com/Events");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pd.dismiss();
                 total_child = dataSnapshot.getChildrenCount();
                if (total_child==0)
                    Toast.makeText(getApplicationContext(), "No favourite events", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FirebaseRecyclerAdapter  fra = new FirebaseRecyclerAdapter<Events_list, RecyclerView.ViewHolder>(Events_list.class,R.layout.event_card,RecyclerView.ViewHolder.class,ref) {
            @Override
            protected void populateViewHolder(RecyclerView.ViewHolder viewHolder, Events_list model, int position) {

                switch((Integer.parseInt(model.getType())))
                {
                    case 0 :
                        break;
                    default:
                        populate((Events_holder)viewHolder,model);
                        break;
                }

            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                switch (viewType)
                {
                    case 0:
                        LayoutInflater i = LayoutInflater.from(parent.getContext());
                        View layoutView = i. inflate(R.layout.scrollable_holder,parent,false);
                        //above line same as View layoutView = i. inflate(R.layout.card_view_list,parent,false); but a lil change in display check it out
                        Events_holder rcv = new Events_holder(layoutView);
                        return rcv;
                    default:
                        LayoutInflater i1 = LayoutInflater.from(parent.getContext());
                        View layoutView1 = i1.inflate(R.layout.event_card, parent, false);
                        //above line same as View layoutView = i. inflate(R.layout.card_view_list,parent,false); but a lil change in display check it out
                        Events_holder rcv1 = new Events_holder(layoutView1);
                        return rcv1;
                }
            }

            @Override
            public int getItemViewType(int position) {
                Events_list el = getItem(position);
                int a = Integer.parseInt(el.getType());
                switch(a)
                {
                    case 0:
                        return 0;

                    default:
                        return 1;
                }
            }

        };



        rView.setAdapter(fra);
    }
    
    public void populate(final Events_holder holder,final  Events_list model) {
            child_processed++;
        if (filter_list.isEmpty() || (!filter_list.isEmpty() && filter_list.contains(model.getsociety()))) {

            GridLayoutManager.LayoutParams param = (GridLayoutManager.LayoutParams)holder.event_card.getLayoutParams();
            param.height = GridLayoutManager.LayoutParams.WRAP_CONTENT;
            param.width = GridLayoutManager.LayoutParams.MATCH_PARENT;
            param.bottomMargin =16;
            holder.event_card.setLayoutParams(param);
            holder.event.setText(model.getevent());
            holder.event_name.setText(model.getevent_name());
            holder.soceity.setText(model.getsociety());
            holder.special_event.setText(model.getspecial_event());
            holder.venue.setText(model.getvenue());
            holder.time.setText(model.gettime());
            holder.date.setText(model.getdate());

            DatabaseReference dr = FirebaseDatabase.getInstance().getReferenceFromUrl("https://nitj-companion-24386.firebaseio.com/Users");
            FirebaseUser us = FirebaseAuth.getInstance().getCurrentUser();
            dr = dr.child(us.getUid());

            dr.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("MyEvents").child(model.getId()).exists()) {
                        holder.navi.setImageDrawable(holder.navi.getContext().getResources().getDrawable(R.drawable.star_selected));
                    } else {
                        holder.navi.setImageDrawable(holder.navi.getContext().getResources().getDrawable(R.drawable.star_unselected));
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            holder.navi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //int x = 0;


                    FirebaseUser us1 = FirebaseAuth.getInstance().getCurrentUser();
                    final DatabaseReference dr1 = FirebaseDatabase.getInstance().getReferenceFromUrl("https://nitj-companion-24386.firebaseio.com/Users").child(us1.getUid());

                    dr1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child("MyEvents").child(model.getId()).exists()) {
                                dr1.child("MyEvents").child(model.getId()).removeValue();
                                Toast.makeText(holder.navi.getContext(), "Removed from favourites", Toast.LENGTH_SHORT).show();
                            } else {
                                dr1.child("MyEvents").child(model.getId()).setValue(model);
                                Toast.makeText(holder.navi.getContext(), "Added to favourites", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });




                }
            });
        }

        else
        {
            child_count++;
            GridLayoutManager.LayoutParams param = (GridLayoutManager.LayoutParams)holder.event_card.getLayoutParams();
            param.height = 0;
            param.width = 0;
            param.setMargins(0,0,0,0);
            holder.event_card.setLayoutParams(param);
        }
        System.out.println("child_count = "+child_count+" child_processed = "+child_processed+" total_child = "+total_child);
       // if(child_processed-child_count!=0 &&(child_processed - child_count)%((int) total_child -1)==0)
        if(child_count==total_child-1 && child_count == child_processed)
        {
            Toast.makeText(getApplicationContext(),"No result found",Toast.LENGTH_SHORT).show();
            child_processed = 0;
            child_count = 0;
        }

    }





    private void hideViews() {
        filter.animate().translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));


    }

    private void showViews() {
       filter.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK)
        {
            if(requestCode == Event_Tags)
            {
                filter_list = data.getStringArrayListExtra("list_of_tags");
                set_recy_view();
               // Toast.makeText(getApplicationContext(), "First element of the list got = "+filter_list.get(0), Toast.LENGTH_SHORT).show();

            }
        }
    }

    public void onBackPressed() {
        Intent ix = new Intent(getApplicationContext(),Navigation_drawer.class);
        startActivity(ix);
        finish();
    }
}
