package com.warkahot.nitjcompanion;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by warkahot on 18-Sep-16.
 */
public class Placements_holder extends RecyclerView.ViewHolder {

    TextView company_name,lpa_val,branch,post,studennts;

    public Placements_holder(View itemView) {
        super(itemView);

        company_name = (TextView)itemView.findViewById(R.id.company_name);
        lpa_val = (TextView)itemView.findViewById(R.id.lpavalue);
        branch = (TextView)itemView.findViewById(R.id.branch_name);
        post = (TextView)itemView.findViewById(R.id.post_name);
        studennts = (TextView)itemView.findViewById(R.id.list_of_students);
    }
}
