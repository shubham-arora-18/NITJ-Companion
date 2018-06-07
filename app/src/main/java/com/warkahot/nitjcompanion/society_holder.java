package com.warkahot.nitjcompanion;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by warkahot on 18-Sep-16.
 */
public class society_holder extends RecyclerView.ViewHolder {

    ImageView soc_img;
    TextView soc_name;
    CardView soc_card;

    public society_holder(View itemView) {
        super(itemView);

        soc_img = (ImageView)itemView.findViewById(R.id.image_society);
        soc_name = (TextView)itemView.findViewById(R.id.society_name);
        soc_card = (CardView)itemView.findViewById(R.id.soc_card);
    }
}
