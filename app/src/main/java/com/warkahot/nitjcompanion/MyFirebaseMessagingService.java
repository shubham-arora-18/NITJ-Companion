package com.warkahot.nitjcompanion;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    int flag;
public MyFirebaseMessagingService() {
}
@Override
public void onMessageReceived(final RemoteMessage remoteMessage) {
super.onMessageReceived(remoteMessage);
Log.d("firebase", "From: " + remoteMessage.getFrom());
//Log.d("firebase", "Notification Message Body: " +
//remoteMessage.getNotification().getBody());



    flag = 0;

    //page sends for page to open(optional) - choose from articles , placements, society,mess,events
    //title is for title(optional)
    //content for content(compulsory)
    //subscriptions (compulsory) note - add / after each subscription name(optional) see subscriptions to select from in your user / id in your database

    String page = "";
    Intent intent = new Intent(this, MainActivity.class);


    if(remoteMessage.getData().get("page")!=null)
        page = remoteMessage.getData().get("page");

    if(page.equals("articles"))
        intent = new Intent(this, article_main.class);
    else if(page.equals("placements"))
        intent = new Intent(this, Placements.class);
    else if(page.equals("society"))
        intent = new Intent(this, society_main.class);
    else if(page.equals("mess"))
        intent = new Intent(this, Boys_girls_hostel1.class);
    else if(page.equals("events"))
        intent = new Intent(this, Events_main.class);

    String title = "NitJ Companion";
    if(remoteMessage.getData().get("title")!=null)
    title = remoteMessage.getData().get("title");

    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT);

    Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.text_icon)
            .setColor(Color.parseColor("#044f95"))
            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon))
            .setContentTitle(title)
            .setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage.getData().get("content")))
            .setContentText(remoteMessage.getData().get("content"))
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent);


   final  ArrayList<String> subs = new ArrayList<>();
    String topics = remoteMessage.getData().get("subscriptions");
    String temp = "";
    for (int i = 0 ;i<topics.length();i++)
    {

        if(topics.charAt(i)=='/')
        {
            subs.add(temp);
            temp = "";
        }
        else
        temp += topics.charAt(i);
    }
    temp = "";

    for (int i = 0 ;i<subs.size();i++)
    {
        System.out.println("Element in sub at "+ i +" = "+subs.get(i));
    }

    final DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://nitj-companion-24386.firebaseio.com/Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Subscriptions");
    ref.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for(int i = 0;i<subs.size();i++)
            {
                if(dataSnapshot.child(subs.get(i)).exists())
                {
                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(Integer.parseInt(remoteMessage.getData().get("id")), notificationBuilder.build());
                    break;
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });


}
}