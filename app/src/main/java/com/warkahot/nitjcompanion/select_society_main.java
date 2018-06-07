package com.warkahot.nitjcompanion;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.devspark.appmsg.AppMsg;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by sarthi on 14-Jul-16.
 */
public class select_society_main extends Activity {
    RecyclerView rv;
    TextView done,cancel,heading;
    ArrayList<String> list=  new ArrayList<>();
    ProgressDialog pd;
    public void onCreate(Bundle b)
    {

        super.onCreate(b);
        setContentView(R.layout.select_society_main);
        final Intent ix = getIntent();

        rv= (RecyclerView)findViewById(R.id.select_society_rv);
        done = (TextView)findViewById(R.id.done_select_society);
        cancel = (TextView)findViewById(R.id.cancel_select_society);
        heading = (TextView)findViewById(R.id.heading_society);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        /*
        if(ix.getExtras()!=null && ix.getExtras().get("key").equals("doubt_tags"))
        {
            System.out.println("Yes intent caught");
            heading.setText("Add Tag / Tags ...");
            rv.setAdapter(new select_society_adapter(this,get_doubt_tags()));
        }
        else
            rv.setAdapter(new select_society_adapter(this,get_society_name()));*/
        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait...");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://nitj-companion-24386.firebaseio.com/Societies");

        if(new Network_available().isNetworkAvailable(getApplicationContext())) {
            pd.show();
            FirebaseRecyclerAdapter<select_society_list, select_society_holder> adapter = new FirebaseRecyclerAdapter<select_society_list, select_society_holder>(select_society_list.class, R.layout.select_socity_holder, select_society_holder.class, ref) {
                @Override
                protected void populateViewHolder(final select_society_holder viewHolder, final select_society_list model, int position) {
                    pd.dismiss();
                    viewHolder.society_name.setText(model.getSociety_name());
                    viewHolder.society_name.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (list.contains(model.getSociety_name())) {
                                list.remove(model.getSociety_name());
                                viewHolder.society_name.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.select_society_1));
                                viewHolder.society_name.setTextColor(Color.parseColor("#00901b"));
                            } else {
                                list.add(model.getSociety_name());
                                viewHolder.society_name.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.select_society_2));
                                viewHolder.society_name.setTextColor(Color.parseColor("#ffffff"));
                            }
                        }
                    });
                }
            };

            rv.setAdapter(adapter);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (pd.isShowing()) {
                        AppMsg amk = AppMsg.makeText(select_society_main.this, "An error occured. Please try again in some time...", AppMsg.STYLE_ALERT);
                        amk.show();
                        pd.dismiss();
                    }

                }
            }, 20000);

        }
        else
        {
            AppMsg appMsg = AppMsg.makeText(select_society_main.this,"Please check your Internet Connection",AppMsg.STYLE_ALERT);
            appMsg.show();
            pd.dismiss();
        }


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Done clicked");

                System.out.println("done intent caught");
                Intent return_intent = new Intent();

                if(getIntent()!=null && getIntent().getStringExtra("key").equals("event_tags"))
                return_intent = new Intent(select_society_main.this,Events_main.class);

                return_intent.putStringArrayListExtra("list_of_tags", list);
                setResult(Activity.RESULT_OK, return_intent);
                finish();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /*public ArrayList<select_society_list> get_society_name()
    {
        ArrayList<select_society_list> list = new ArrayList<>();
        for(int i = 0;i<10;i++)
        {
            list.add(new select_society_list("Society No. "+i));
        }
        return list;
    }

    public ArrayList<select_society_list> get_doubt_tags()
    {
        ArrayList<select_society_list> list = new ArrayList<>();
        for(int i = 0;i<10;i++)
        {
            list.add(new select_society_list("Doubt Tag No. "+i));
        }
        return list;
    }*/
}
