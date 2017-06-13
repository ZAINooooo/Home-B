package com.example.asad.homebuyerproject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager ePager;
    private SlidingTabLayout eTabs;
    private TextView Toolbartext;

    private TextView mBuy, mProject, mRent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        TypeCasting();
        TabsSetter();
        SetToolbar();
        // text.setText(spin.getSelectedItem().toString());


        //FOR BACK BUTTON ENABLED
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    //Typecasting is done here
    public void TypeCasting() {
        ePager = (ViewPager) findViewById(R.id.Pager);
        Toolbartext = (TextView) findViewById(R.id.toolbar_title);


        // Toolbartext = (TextView) findViewById(R.id.toolbar_title);

    }

    public void SetToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar_search);
        setSupportActionBar(toolbar);
        Toolbartext.setText("Search Property");
    }


    public void TabsSetter() {
        ePager.setAdapter(new SearchActivity.MyPagerAdapter(getSupportFragmentManager()));
        ePager.setOffscreenPageLimit(2);
        eTabs = (SlidingTabLayout) findViewById(R.id.TabLayout);
        eTabs.setViewPager(ePager);
    }


    //Sliding tabs class for residential search
    //Buy,Rent,Project
    class MyPagerAdapter extends FragmentStatePagerAdapter {

        String[] tabs;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabs = getResources().getStringArray(R.array.tabs2);
        }

        @Override
        public Fragment getItem(int position) {
            // BuyFragment myFragment = BuyFragment.getInstance(position);
            // return myFragment;
            switch (position) {
                case 0:
                    // Top Rated fragment activity
                    return new Fragment_Residencial_Search_Activity();
                case 1:
                    // Games fragment activity
                    return new Fragment_Commercial_Search_Activity();

            }

            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return tabs[position];
        }

        @Override
        public int getCount() {

            return 2;
        }

    }

    @Override
    protected void onPause()
    {
        super.onPause();
        System.gc();
    }
    @Override
    protected void onStop()
    {
        super.onStop();
        System.gc();
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        System.gc();
        ePager = null;
        eTabs = null;
    }

}
