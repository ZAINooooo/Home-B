package com.example.asad.homebuyerproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.ArrayList;

public class Feedback extends AppCompatActivity {


    private Toolbar toolbar;
    private TextView Toolbartext;
    private CheckBox mcrash,msearnotworking,mpropertysearchnotworking,mloginnotworking,mslow,mothers;
    private CoordinatorLayout coordinatorLayout;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    // private String termsandcondition;
    private DatabaseReference mDatabase;
    private EditText missuetext;
    private Button mFeedback;
    private ArrayList<String> mIssueType=new ArrayList<>();
    private int i=0;
    private ProgressDialog mPleaseWait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        TypeCasting();
        SetToolbar();
        Validation();


        //Dtabase reference and storage
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Feedback");
        //Datebase reference
        mAuth = FirebaseAuth.getInstance();
        //For current users state
        //For user state weather user is logged in or not
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                  // Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                } else {
                    // User is signed out
                 //   Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }

        };



        //FOR BACK BUTTON ENABLED
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



    private void Validation() {
        mFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPleaseWait.setMessage(" Please Wait ..");
                mPleaseWait.show();
                mPleaseWait.setCancelable(false);
                mPleaseWait.setCanceledOnTouchOutside(false);

                final String[]  child =new String[100];


                if(mcrash.isChecked()  || msearnotworking.isChecked()  ||
                        mpropertysearchnotworking.isChecked() || mloginnotworking.isChecked() ||
                        mslow.isChecked() || mothers.isChecked())
                {
                    if(mcrash.isChecked())
                    {
                        mIssueType.add("Crash");
                    }
                    if(msearnotworking.isChecked())
                    {
                        mIssueType.add("Search Not Working");
                    }
                    if(mpropertysearchnotworking.isChecked())
                    {
                        mIssueType.add("Property Search Not Working");

                    }
                    if(mloginnotworking.isChecked())
                    {
                        mIssueType.add("Login Not Working");
                    }
                    if(mslow.isChecked())
                    {
                        mIssueType.add("Slow");
                    }

                    if(mothers.isChecked())
                    {
                        mIssueType.add("Others");
                    }

                    if(mAuth.getCurrentUser() != null)
                    {


                        String user_id = mAuth.getCurrentUser().getUid();

                        DatabaseReference User_Id = mDatabase.child(user_id);
                        User_Id.child("Date Time").setValue(ServerValue.TIMESTAMP);



                        for(int j=0;j<=i; j++) {
                            User_Id.child("Issue Type").setValue(mIssueType);
                        }

                        if(missuetext.length() > 0) {
                          User_Id.child("Issue").setValue(missuetext.getText().toString());
                            Toast.makeText(getApplicationContext(),"Thanks for your feedback",Toast.LENGTH_SHORT).show();

                           mPleaseWait.dismiss();
                        }
                    }
                    else
                    {
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, "Please LogIn Yourself", Snackbar.LENGTH_LONG)
                                .setAction("Ok", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                });
                        // Changing message text color
                        snackbar.setActionTextColor(Color.RED);
                        // Changing action button text color
                        View sbView = snackbar.getView();
                        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.YELLOW);

                        snackbar.show();
                    }

                }
                else
                {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Please Select Type", Snackbar.LENGTH_LONG)
                            .setAction("Ok", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            });
                    // Changing message text color
                    snackbar.setActionTextColor(Color.RED);
                    // Changing action button text color
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);

                    snackbar.show();
                }



                mPleaseWait.dismiss();

            }
        });

    }


    public void TypeCasting() {
        Toolbartext = (TextView) findViewById(R.id.toolbar_title);
        missuetext=(EditText)findViewById(R.id.issuetext);
        mcrash=(CheckBox)findViewById(R.id.crash);
        msearnotworking=(CheckBox)findViewById(R.id.searchnotworking);
        mloginnotworking=(CheckBox)findViewById(R.id.loginnotworking);
        mpropertysearchnotworking=(CheckBox)findViewById(R.id.postpropertynotworking);
        mslow=(CheckBox)findViewById(R.id.slow);
        mothers=(CheckBox)findViewById(R.id.other);
        mFeedback=(Button)findViewById(R.id.feedback);
        mPleaseWait=new ProgressDialog(Feedback.this);


        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);

        //  mRadioGroup = (RadioGroup) findViewById(R.id.RadioButton);

        //   mNextActivity=(Button)findViewById(R.id.NextActivity);


    }


    public void SetToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar_search);
        setSupportActionBar(toolbar);
        Toolbartext.setText("Feedback");
        //   Toolbartext.setText("Test Screen");

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        //FOR BACK BUTTON ALSO INCLUDE META DATA IN MANIFEST
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);


    }
}
