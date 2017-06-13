package com.example.asad.homebuyerproject;



import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class Fragment_Commercial_Search_Activity extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Toolbar toolbar;
    private ViewPager ePager;
    private SlidingTabLayout eTabs;
    private TextView Toolbartext;

    private TextView mBuy, mRent;
    //mProject,
    public Fragment_Commercial_Search_Activity() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout;
        layout = inflater.inflate(R.layout.activity_commercial__search, container, false);

        TypeCasting(layout);
        TypeSelectedEvent();
        openbydefaultfragment();
      //  TabsSetter();

        // text.setText(spin.getSelectedItem().toString());



        return layout;

    }

    private void TypeSelectedEvent() {

        mBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openbydefaultfragment();


            }
        });

       /* mProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBuy.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mProject.setBackgroundColor(Color.parseColor("#E1F5FE"));
                mRent.setBackgroundColor(Color.parseColor("#FFFFFF"));

                ProjectCommercialFragment frag=new ProjectCommercialFragment();
                FragmentManager manager =getFragmentManager();
                FragmentTransaction transaction= manager.beginTransaction();
                transaction.replace(R.id.openFragment1,frag,"Project");
                transaction.commit();

            }
        });*/

        mRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBuy.setBackgroundColor(Color.parseColor("#FFFFFF"));
               // mProject.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mRent.setBackgroundColor(Color.parseColor("#E1F5FE"));

               RentCommercialFragment frag=new RentCommercialFragment();
                FragmentManager manager =getFragmentManager();
                FragmentTransaction transaction= manager.beginTransaction();
                transaction.replace(R.id.openFragment1,frag,"Rent");
                transaction.commit();

            }
        });


    }

    private void openbydefaultfragment() {

        mBuy.setBackgroundColor(Color.parseColor("#E1F5FE"));
       // mProject.setBackgroundColor(Color.parseColor("#FFFFFF"));
        mRent.setBackgroundColor(Color.parseColor("#FFFFFF"));

        BuyCommercialFragment frag=new BuyCommercialFragment();
        FragmentManager manager =getFragmentManager();
        FragmentTransaction transaction= manager.beginTransaction();
        transaction.replace(R.id.openFragment1,frag,"Buy");
        transaction.commit();
    }

    //Typecasting is done hereActivity activity;
    public void TypeCasting(View layout) {

        mRent = (TextView) layout.findViewById(R.id.radio2);
        mBuy = (TextView) layout.findViewById(R.id.radio0);
      //  mProject = (TextView) layout.findViewById(R.id.radio1);

        // Toolbartext = (TextView) findViewById(R.id.toolbar_title);

    }


  /*  public void TabsSetter() {
        ePager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        eTabs = (SlidingTabLayout) findViewById(R.id.TabLayout);
        eTabs.setViewPager(ePager);
    }
    */


 /*   @Override
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

*/

    //Sliding tabs class for residential search
    //Buy,Rent,Project
   /* class MyPagerAdapter extends FragmentPagerAdapter {

        String[] tabs;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabs = getResources().getStringArray(R.array.tabs123);
        }

        @Override
        public Fragment getItem(int position) {
            // BuyFragment myFragment = BuyFragment.getInstance(position);
            // return myFragment;
            switch (position) {
                case 0:

                    return new BuyCommercialFragment();
                case 1:

                    return new ProjectCommercialFragment();
                case 2:
                    return new RentCommercialFragment();

            }

            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return tabs[position];
        }

        @Override
        public int getCount() {

            return 3;
        }
    }



  /*  public static class MyFragment extends Fragment {

        private TextView textview;

        public static MyFragment getInstance(int position) {
            MyFragment myFragment = new MyFragment();
            Bundle args = new Bundle();
            args.putInt("position", position);
            myFragment.setArguments(args);
            return myFragment;
        }

        public View onCreateView(LayoutInflater inflator, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View layout = inflator.inflate(R.layout.fragment_my, container, false);
          //  textview = (TextView) layout.findViewById(R.id.position);
           // Bundle bundle = getArguments();
          //  if (bundle != null) {
          //      textview.setText("The page selected is" + bundle.getInt("position"));
          //  }

            return layout;
        }



    }*/

}

