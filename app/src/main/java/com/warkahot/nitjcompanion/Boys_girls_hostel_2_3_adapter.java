package com.warkahot.nitjcompanion;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by warkahot on 20-Sep-16.
 */
public class Boys_girls_hostel_2_3_adapter extends RecyclerView.Adapter<Boys_girls_hostel2_holder> {
    Context c;
    List<String> item_list;
    String type;
    String hostel_name;
    int count;
    String hostel;

    public Boys_girls_hostel_2_3_adapter(Context c, List<String> item_list, String type, int count, String hostel) {
        this.c = c;
        this.item_list = item_list;
        this.type = type;
        this.count = count;
        this.hostel = hostel;
    }

    @Override
    public Boys_girls_hostel2_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        
        LayoutInflater i = LayoutInflater.from(c);
        View layoutView = i. inflate(R.layout.hostel_card,parent,false);
        //above line same as View layoutView = i. inflate(R.layout.card_view_list,parent,false); but a lil change in display check it out
        Boys_girls_hostel2_holder rcv = new Boys_girls_hostel2_holder(layoutView);
        return rcv;
        
    }

    @Override
    public void onBindViewHolder(final Boys_girls_hostel2_holder holder, int position) {
            holder.hostel_name.setText(item_list.get(position));
        if(type.equals("boys"))
            holder.ll_hostel.setBackground(c.getResources().getDrawable(R.drawable.boys_mess_button));
        else
            holder.ll_hostel.setBackground(c.getResources().getDrawable(R.drawable.girls_mess_button));

        holder.ll_hostel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count == 0) {
                    hostel_name = holder.hostel_name.getText().toString();
                    Intent i = new Intent(c, Boys_girls_hostel3.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("type", type);
                    i.putExtra("hostel", hostel_name);
                    c.startActivity(i);
                }
                else
                {
                    hostel_name = hostel;
                    String day_clicked = holder.hostel_name.getText().toString();
                    Intent i = new Intent(c, Boys_girls_hostel4.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("type", type);
                    i.putExtra("hostel", hostel_name);
                    i.putExtra("day",day_clicked);
                    c.startActivity(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return item_list.size();
    }

}
