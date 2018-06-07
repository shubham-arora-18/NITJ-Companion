package com.warkahot.nitjcompanion;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    public MainFragment() {
        // Required empty public constructor
    }


    Button articles, mess,events,placements, society;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.mainpage, container, false);

        articles = (Button)v.findViewById(R.id.news_button);
        mess = (Button)v.findViewById(R.id.doubts_button);
        events = (Button)v.findViewById(R.id.events_button);
        placements = (Button)v.findViewById(R.id.placements_button);
        society = (Button)v.findViewById(R.id.rating_buton);


        articles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext(), article_main.class);
                startActivity(in);
            }
        });

        placements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext(),Placements.class);
                startActivity(in);

            }
        });

        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext(),Events_main.class);
                startActivity(in);

            }
        });

        mess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext(), Boys_girls_hostel1.class);
                startActivity(in);
            }
        });

        society.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext(), society_main.class);
                startActivity(in);
            }
        });


        return v;
    }

}
