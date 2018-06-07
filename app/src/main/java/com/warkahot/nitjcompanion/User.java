package com.warkahot.nitjcompanion;

/**
 * Created by warkahot on 17-Sep-16.
 */
public class User {

    public String name;
    public String email;
    public String google_uid;
    public String photoUrl;
    public String firebase_uid;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String name, String email, String google_uid, String photoUrl,String firebase_uid) {
        this.name = name;
        this.email = email;
        this.google_uid = google_uid;
        this.photoUrl = photoUrl;
        this.firebase_uid = firebase_uid;
    }


}