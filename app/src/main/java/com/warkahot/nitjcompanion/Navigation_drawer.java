package com.warkahot.nitjcompanion;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Navigation_drawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,GoogleApiClient.OnConnectionFailedListener {

    int count = 0;

    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawer;
    private GoogleApiClient mGoogleApiClient;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer_2);

        System.out.println("My fcm redistration token = "+ FirebaseInstanceId.getInstance().getToken());


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();



        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        MainFragment frag1 = new MainFragment();
        android.support.v4.app.FragmentTransaction frag_transac = getSupportFragmentManager().beginTransaction();
        frag_transac.replace(R.id.content, frag1);
        frag_transac.commit();




        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

         String uid = "";
        // Name, email address, and profile photo Url
         String name ="";
         String email ="";
         String photoUrl ="";
         String firebase_uid = "";

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);




        Intent ix = getIntent();
        if(ix!=null && ix.getStringExtra("Activity")!=null && ix.getStringExtra("Activity").equals("MainActivity"))
        {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            System.out.println("User id as shown = "+user.getUid());
            if (user != null) {

                uid = user.getUid();
                name = user.getDisplayName();
                email = user.getEmail();
                photoUrl = user.getPhotoUrl().toString();

                for (UserInfo profile : user.getProviderData()) {

                    // Id of the provider (ex: google.com)
                    final String providerId = profile.getProviderId();
                    // UID specific to the provider
                    // Name, email address, and profile photo Url
                    firebase_uid = profile.getUid();

                    System.out.println("Provider Id = "+providerId);
                    System.out.println("Uid = "+ uid);
                    System.out.println("email = "+email);
                    System.out.println("firebase_uid = " + firebase_uid);

                    Log.d("Image and name = ", photoUrl + " " + name);

                };
            }
        }

        ImageView iv = (ImageView)navigationView.getHeaderView(0).findViewById(R.id.profile_image);
        Picasso.with(Navigation_drawer.this).load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).transform(new CircleTransform()).into(iv);
        ((TextView)navigationView.getHeaderView(0).findViewById(R.id.navigation_name)).setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());



        final String uid1 =uid;
        final String name1  = name;
        final String email1 = email;
        final String photoUrl1 = photoUrl;
        final String firebase_uid1 = firebase_uid;



        final DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://nitj-companion-24386.firebaseio.com/Users");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                System.out.println("There are " + dataSnapshot.getChildrenCount() + " item details");
                if(!dataSnapshot.child(uid1).exists() )
                {
                    //Toast.makeText(getApplicationContext(), "User not exists", Toast.LENGTH_SHORT).show();
                    User u = new User(name1,email1,uid1,photoUrl1.toString(),firebase_uid1);
                    ref.child(uid1).setValue(u);
                    FirebaseMessaging.getInstance().subscribeToTopic("nitj_comp_all");
                    ref.child(uid1).child("Subscriptions").child("nitj_comp_all").setValue("yes");
                    ref.child(uid1).child("Subscriptions").child("nitj_comp_events").setValue("yes");
                    System.out.println("yes i got i here here");
                    ref.child(uid1).child("Subscriptions").child("nitj_comp_placements").setValue("yes");
                    ref.child(uid1).child("Subscriptions").child("nitj_comp_articles").setValue("yes");


                }
               // Toast.makeText(getApplicationContext(),"Hello",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());

            }
        });
    }



    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new SweetAlertDialog(Navigation_drawer.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Are you sure?")
                    .setContentText("You want to exit !")
                    .setConfirmText("Yes , I am sure!")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            Intent startMain = new Intent(Intent.ACTION_MAIN);
                            startMain.addCategory(Intent.CATEGORY_HOME);
                            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(startMain);
                            finish();
                        }
                    })
                    .show();
        }
    }





    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



        if (id == R.id.support) {

            drawer.closeDrawer(GravityCompat.START);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent ix = new Intent(Navigation_drawer.this, support.class);
                    ix.putExtra("from", "my_events_nav_menu");
                    startActivity(ix);
                }
            }, 400);

            // Handle the camera action
        }
        else if (id == R.id.my_events)
        {
            drawer.closeDrawer(GravityCompat.START);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent ix = new Intent(Navigation_drawer.this, Events_main.class);
                    ix.putExtra("from", "my_events_nav_menu");
                    startActivity(ix);
                }
            }, 400);

        } else if (id == R.id.my_articles) {
            drawer.closeDrawer(GravityCompat.START);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent ix = new Intent(Navigation_drawer.this, article_main.class);
                    ix.putExtra("from", "my_events_nav_menu");
                    startActivity(ix);
                }
            }, 400);

        } else if (id == R.id.settings) {
            drawer.closeDrawer(GravityCompat.START);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent ix = new Intent(Navigation_drawer.this, settings.class);
                    ix.putExtra("from", "my_events_nav_menu");
                    startActivity(ix);
                }
            }, 400);

        }
        else if(id == R.id.signout)
        {
            drawer.closeDrawer(GravityCompat.START);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    new SweetAlertDialog(Navigation_drawer.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Are you sure?")
                            .setContentText("You want to sign out !")
                            .setConfirmText("Yes, sign out!")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    signOut();
                                    Intent ix = new Intent(getApplicationContext(), MainActivity.class);
                                    ix.putExtra("type", "bye-bye1");
                                    startActivity(ix);
                                    finish();
                                }
                            })
                            .show();
                }
            }, 400);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                          FirebaseAuth.getInstance().signOut();
                          Toast.makeText(Navigation_drawer.this, " signed out now", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }
}