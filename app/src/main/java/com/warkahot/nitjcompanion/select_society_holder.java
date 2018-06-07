package com.warkahot.nitjcompanion;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by sarthi on 14-Jul-16.
 */
public class select_society_holder extends RecyclerView.ViewHolder {
    TextView society_name;
    public select_society_holder(View itemView) {
        super(itemView);
        society_name = (TextView)itemView.findViewById(R.id.society_tag);
    }
}
