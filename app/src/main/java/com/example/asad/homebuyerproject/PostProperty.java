package com.example.asad.homebuyerproject;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PostProperty extends AppCompatActivity {

    private Toolbar toolbar;
    private RadioGroup mRadioGroup;
    private TextView mOwner, mBuilder;//, mAgent;
    private Spinner spin;
    private TextView text;
    private Button mNextActivity;
    private String Type;
    private FirebaseAuth mAuth;
    private  String cities1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_property);

        loadData();
        TypeCasting();
        SetToolbar();
        //FOR BACK BUTTON ENABLED
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      /*  mAgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agent();
            }
        });*/

        mOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                owner();
            }
        });

        mBuilder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bilder();
            }
        });

    }

    private void agent() {
       // mAgent.setBackgroundColor(Color.parseColor("#E1F5FE"));
        mOwner.setBackgroundColor(Color.parseColor("#FFFFFF"));
        mBuilder.setBackgroundColor(Color.parseColor("#FFFFFF"));
        Intent next = new Intent(PostProperty.this,Activity_Post_property_Click.class);
        Type="Agent";
        next.putExtra("TypeSelected",Type);
        startActivity(next);
    }

    private void owner() {
        mOwner.setBackgroundColor(Color.parseColor("#E1F5FE"));
       // mAgent.setBackgroundColor(Color.parseColor("#FFFFFF"));
        mBuilder.setBackgroundColor(Color.parseColor("#FFFFFF"));
        Intent next = new Intent(PostProperty.this,Activity_Post_property_Click.class);
        Type="Owner";
        next.putExtra("TypeSelected",Type);
        startActivity(next);

    }

    private void bilder() {
        mBuilder.setBackgroundColor(Color.parseColor("#E1F5FE"));
        mOwner.setBackgroundColor(Color.parseColor("#FFFFFF"));
       // mAgent.setBackgroundColor(Color.parseColor("#FFFFFF"));
        Intent next = new Intent(PostProperty.this,Activity_Post_property_Click.class);
        Type="Builder";
        next.putExtra("TypeSelected",Type);
        startActivity(next);
    }

    private void setselected() {

        if(cities1.toLowerCase().contains("owner"))
        {

            mOwner.setBackgroundColor(Color.parseColor("#E1F5FE"));
        }
        else if(cities1.equals("Agent"))
        {
           // mAgent.setBackgroundColor(Color.parseColor("#E1F5FE"));
        }
        else if(cities1.equals("Builder"))
        {
            mBuilder.setBackgroundColor(Color.parseColor("#E1F5FE"));
        }
    }

    private void loadData()
    {
        mAuth = FirebaseAuth.getInstance();
       String copy = mAuth.getCurrentUser().getUid();
        DatabaseReference mdatabase;
        mdatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mRef = mdatabase.child("Users/" + copy );
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot) {
                    try {

                      cities1 = dataSnapshot.child("UserType").getValue(String.class);
                        setselected();
                    } catch (Exception ex) {

                    }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }
    public void SetToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
    }
    public void TypeCasting() {
        //mAgent = (TextView) findViewById(R.id.radio2);
        mOwner = (TextView) findViewById(R.id.radio0);
        mBuilder = (TextView) findViewById(R.id.radio1);
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
