package com.example.asad.homebuyerproject;



import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.NavigationView;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import android.widget.Toast;

import com.firebase.client.Firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private TextView Toolbartext;
    private NavigationView navigationView;
    private View mLoginButton;
    private View new1;
    private TextView mEmailLogin,mNameLogin;
    private Firebase mRef;
    private Spinner mLogOut;
    private Button SearchButton;
    private DrawerLayout mDrawerLayout;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        Firebase.setAndroidContext(this);
        TypeCasting(); // TypeCast of Attributes
        SearchButtonClickEvent();
        NavigationDrawerClickEvent(); //Attach header at run time
        SetToolbar();    //setting Toolbar
        NavigationDrawerMethod(); //Show navigation drawer
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    //Set Drawer
    //Calling Method on class "NavigationBar.java"
    public void NavigationDrawerMethod() {
        NavigationBar drawerfragment = (NavigationBar)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation1);
        drawerfragment.setup(R.id.fragment_navigation1, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

    }

    //Setting toolbar and toolbar text
    public void SetToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        Toolbartext.setText("Home Screen");

    }


    public void SearchButtonClickEvent ()
    {
        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(i);
            }
        });
    }
    public void TypeCasting() {
        Toolbartext = (TextView) findViewById(R.id.toolbar_title);
        SearchButton = (Button) findViewById(R.id.buttonsearch);
    }

    /*Inflate view nad Login/Register click event on navigation drawer
    * Inflate header at runtime because its changes when user is online
    */
    public void NavigationDrawerClickEvent() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            navigationView = (NavigationView) findViewById(R.id.nav_view);
        try {
            new1 = navigationView.inflateHeaderView(R.layout.nav_header);
        }catch (Exception ex)
        {
            Toast.makeText(getApplication(),"Unable to Add Header",Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }
            new1.findViewById(R.id.Login);

            new1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Calling activity LoginRegister
                    Intent intent = new Intent(HomeActivity.this, LoginRegister.class);
                    startActivity(intent);
                }
            });


        //Account checking weather logged in if login then change header
        if (user != null) {

            //Remove previous header
            new1.setVisibility(View.GONE);
            navigationView = (NavigationView) findViewById(R.id.nav_view);
            // Name, email address, and profile photo Url
            final String copy;
            FirebaseAuth mAuth = FirebaseAuth.getInstance();

            try {
                mLoginButton = navigationView.inflateHeaderView(R.layout.nav_header_login);
                mLogOut=(Spinner) mLoginButton.findViewById(R.id.spinner);
                mEmailLogin = (TextView) mLoginButton.findViewById(R.id.EmailLogin);
                mNameLogin = (TextView) mLoginButton.findViewById(R.id.NameLogin);
                mEmailLogin.setText(mAuth.getCurrentUser().getEmail());


                final DatabaseReference mdatabase = FirebaseDatabase.getInstance().getReference();
                final DatabaseReference mRef = mdatabase.child("Users").child(mAuth.getCurrentUser().getUid());

                mRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("Name") != null)
                            name = dataSnapshot.child("Name").getValue().toString();


                        mNameLogin.setText(name);
                        mdatabase.removeEventListener(this);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), "Can not Perform this operation", Toast.LENGTH_SHORT).show();
                ex.printStackTrace();
            }

            mLogOut.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {
                    String selectedItem = parent.getItemAtPosition(position).toString();
                    if(selectedItem.equals("Log Out"))
                    {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent= new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(intent);
                    }else if(selectedItem.equals("Profile Info"))
                    {  FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        Fragment newFragment=new Accountinfo();
                        transaction.replace(R.id.containerView,newFragment);
                        transaction.addToBackStack(null);
                        mDrawerLayout=  (DrawerLayout) findViewById(R.id.drawer_layout);
                        mDrawerLayout.closeDrawers();
                        transaction.commit();

                    }
                    else if(selectedItem.equals("Change Password"))
                    {
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        Fragment newFragment=new Update_ProfileFragment();
                        transaction.replace(R.id.containerView,newFragment);
                        transaction.addToBackStack(null);
                        mDrawerLayout=  (DrawerLayout) findViewById(R.id.drawer_layout);
                        mDrawerLayout.closeDrawers();
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                } // to close the onItemSelected
                public void onNothingSelected(AdapterView<?> parent)
                {

                }
            });


        }


    }



}



