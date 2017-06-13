package com.example.asad.homebuyerproject;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by hassan on 05/01/2017.
 */

public class Recycler_Property_Activity  extends AppCompatActivity {
    private List<Property> PropertyList = new ArrayList<Property>();
    ArrayList<HashMap<String, String>> mPropertyNotes=new ArrayList<HashMap<String, String>>();
    private RecyclerView recyclerView;
    private PropertyAdapter mAdapter;
    private Toolbar toolbar;
    private TextView EmptySpace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerlists);
        SetToolbar();    //setting Toolbar
        NavigationDrawerMethod(); //Show navigation drawer
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        if (getIntent()!=null){
            mPropertyNotes = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("odata");
        }
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        EmptySpace = (TextView) findViewById(R.id.EmptyList);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        if (mPropertyNotes.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            EmptySpace.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            EmptySpace.setVisibility(View.GONE);
        }

        mAdapter = new PropertyAdapter(PropertyList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new RecyclerViewItemDivider(this, LinearLayoutManager.VERTICAL));
        recyclerView.addOnItemTouchListener(
                new RecyclerTouchListener(getApplicationContext(), recyclerView ,new RecyclerTouchListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Property property = PropertyList.get(position);
                      Search_Result_Details_Fragment fragment = new Search_Result_Details_Fragment();
                        FragmentManager manager = getFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();

                        //send data to fragment
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("Object",property);
                        fragment.setArguments(bundle);
                        transaction.replace(R.id.Replaceing, fragment, "listActivity");
                        transaction.addToBackStack(null);
                        transaction.commit();


                    }
                    @Override public void onLongItemClick(View view, int position) {
                      Toast.makeText(Recycler_Property_Activity.this, "LONG CLICK "+String.valueOf(position), Toast.LENGTH_SHORT).show();
                    }
                })
        );

        recyclerView.setAdapter(mAdapter);
        preparePropertyData();
        //FOR BACK BUTTON ENABLED


/*
        SetToolbar();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
    }
    public void NavigationDrawerMethod() {

        NavigationBar drawerfragment = (NavigationBar)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation1);
        drawerfragment.setup(R.id.fragment_navigation1, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

    }

    public void SetToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView Toolbartext;
        Toolbartext = (TextView) findViewById(R.id.toolbar_title);
        Toolbartext.setText("Search Result");

    }

    String ImageURl;
    List<String> images;
    String bedroom = "";
    private void preparePropertyData() {

        for (HashMap<String, String> map : mPropertyNotes) {
            if (!map.isEmpty()) {
                if (map.get("GaleryData") != null) {

                    String str = map.get("GaleryData");
                    if (str.contains("Photos")) {
                        str = str.replace("[", "");
                        str = str.replace("]", "");
                        str = str.replace(" ", "");
                        images = (List<String>) Arrays.asList(str.split(","));
                        ImageURl = images.get(1).toString();
                    }

                }
                if (map.get("Bedrooms") != null) {
                    bedroom = map.get("Bedrooms");
                }
                Property property = new Property(map.get("PropertyPrice"), map.get("propertyshortaddress"),
                        map.get("propertycatagory"), map.get("SellType"), ImageURl,map);
                ImageURl = "null";
                PropertyList.add(property);
            }mAdapter.notifyDataSetChanged();
        }
    }




    protected void onPause()
    {
        super.onPause();
        System.gc();
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