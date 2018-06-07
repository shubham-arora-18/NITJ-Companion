package com.warkahot.nitjcompanion;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by warkahot on 21-Sep-16.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    public MyFirebaseInstanceIdService() {
    }
    @Override
    public void onTokenRefresh() {
// Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("firebase", "Refreshed token: " + refreshedToken);

// We will Send this refreshedToken to our app server, so app
// server can save it
// and can later use it for sending notification to app.

// sendRegistrationToServer(refreshedToken);
    }
}