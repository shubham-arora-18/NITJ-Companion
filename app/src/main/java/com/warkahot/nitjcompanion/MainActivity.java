package com.warkahot.nitjcompanion;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.devspark.appmsg.AppMsg;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener{

    TextView tv1,tv2,tv3;
    Typeface aharon;
    Button fb_button;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    ProgressDialog pd;
    // [END declare_auth]

    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "GoogleActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if( FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            Intent in = new Intent(MainActivity.this,Navigation_drawer.class);
            in.putExtra("Activity", "MainActivity");
            startActivity(in);
            finish();
            System.out.println("yes i got it twice");
        }



        setContentView(R.layout.activity_main);
        tv1 = (TextView)findViewById(R.id.get_it_all_text);
        tv2 = (TextView)findViewById(R.id.nj_text);
        tv3 = (TextView)findViewById(R.id.welcome_text);
        aharon = Typeface.createFromAsset(getAssets(), "fonts/ahronbd.ttf");


        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait...");
        tv2.setTypeface(aharon);
        tv3.setTypeface(aharon);
        fb_button = (Button)findViewById(R.id.sign_up_first_page);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();



        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();



        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        // [START auth_state_listener]
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Toast.makeText(MainActivity.this, user.getDisplayName()+" signed in", Toast.LENGTH_SHORT).show();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    //Toast.makeText(MainActivity.this, " signed out", Toast.LENGTH_SHORT).show();

                }
                // [START_EXCLUDE]

                // [END_EXCLUDE]
            }
        };


        fb_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(new Network_available().isNetworkAvailable(getApplicationContext())) {
                    pd.show();
                    google_signOut();
                    google_signIn();

                }
                else
                {
                    AppMsg appMsg = AppMsg.makeText(MainActivity.this,"Please check your Internet Connection",AppMsg.STYLE_ALERT);
                    appMsg.show();
                    pd.dismiss();
                }

            }
        });

    }

    private void google_signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void google_signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        //  Toast.makeText(MainActivity.this, " signed out now", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
                System.out.println("Instance id = "+FirebaseInstanceId.getInstance().getToken());

            } else {
                System.out.println("Error is in the activity result");
                Toast.makeText(getApplicationContext(),"text = "+result.toString()+" data = "+data.toString(),Toast.LENGTH_SHORT).show();
                if (pd.isShowing()) {
                    AppMsg amk = AppMsg.makeText(MainActivity.this, "An error occured. Please try again in some time...", AppMsg.STYLE_ALERT);
                    amk.show();
                    pd.dismiss();
                }
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }



    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                         //   Toast.makeText(MainActivity.this, "Authentication failed.",
                           //         Toast.LENGTH_SHORT).show();

                            if (pd.isShowing()) {
                                System.out.println("fire base mein gadbad");
                                AppMsg amk = AppMsg.makeText(MainActivity.this, "An error occured. Please try again in some time...", AppMsg.STYLE_ALERT);
                                amk.show();
                                pd.dismiss();
                            }

                        } else {
                            pd.dismiss();
                            finish();
                            Intent in = new Intent(MainActivity.this, Navigation_drawer.class);
                            in.putExtra("Activity", "MainActivity");
                            startActivity(in);
                        }
                        // ...
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onBackPressed() {


        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
        finish();


    }

}
