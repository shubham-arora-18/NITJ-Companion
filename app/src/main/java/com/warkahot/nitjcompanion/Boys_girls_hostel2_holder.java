package com.warkahot.nitjcompanion;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by warkahot on 20-Sep-16.
 */
public class Boys_girls_hostel2_holder extends RecyclerView.ViewHolder {

    TextView hostel_name;
    LinearLayout ll_hostel;
    public Boys_girls_hostel2_holder(View itemView) {
        super(itemView);
        hostel_name = (TextView)itemView.findViewById(R.id.hostel_name);
        ll_hostel = (LinearLayout)itemView.findViewById(R.id.ll_hostel);

    }
}
