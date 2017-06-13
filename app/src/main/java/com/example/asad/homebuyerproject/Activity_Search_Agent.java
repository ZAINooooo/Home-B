package com.example.asad.homebuyerproject;

import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class Activity_Search_Agent extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView Toolbartext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__search__agent);

        TypeCasting();
        SetToolbar();

        //FOR BACK BUTTON ENABLED
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    //Typecasting is done here
    public void TypeCasting() {

        Toolbartext = (TextView) findViewById(R.id.toolbar_title);
        // Toolbartext = (TextView) findViewById(R.id.toolbar_title);

    }

    public void SetToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar_search);
        setSupportActionBar(toolbar);
        Toolbartext.setText("Search Agent");

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
