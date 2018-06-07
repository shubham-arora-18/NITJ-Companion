package com.warkahot.nitjcompanion;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by sarthi on 13-Jul-16.
 */
public class Events_holder extends RecyclerView.ViewHolder{


    public TextView event;
    public TextView soceity,special_event,venue,time,event_name,date;
    CardView event_card;
    ImageView navi;
    RelativeLayout rl;

    public Events_holder(View itemView) {
        super(itemView);
        event = (TextView)itemView.findViewById(R.id.event_info);
        soceity = (TextView)itemView.findViewById(R.id.society_name);
        special_event = (TextView)itemView.findViewById(R.id.special_event);
        venue = (TextView)itemView.findViewById(R.id.venue_put);
        time = (TextView)itemView.findViewById(R.id.time_put);
        event_name = (TextView)itemView.findViewById(R.id.event_name);
        date = (TextView)itemView.findViewById(R.id.date_put);
        event_card = (CardView)itemView.findViewById(R.id.Event_card);
        navi = (ImageView)itemView.findViewById(R.id.favourite);
        rl = (RelativeLayout)itemView.findViewById(R.id.event_card_rl);



    }

}
