package com.warkahot.nitjcompanion;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by warkahot on 18-Sep-16.
 */
public class article_holder extends RecyclerView.ViewHolder {

    ImageView article_img;
    TextView article_heading,writer_name;
    LinearLayout article_card;

    public article_holder(View itemView) {
        super(itemView);

        article_img = (ImageView)itemView.findViewById(R.id.article_image);
        article_heading = (TextView)itemView.findViewById(R.id.article_headline);
        writer_name = (TextView)itemView.findViewById(R.id.writer_name);
        article_card = (LinearLayout)itemView.findViewById(R.id.article_card);
    }
}
