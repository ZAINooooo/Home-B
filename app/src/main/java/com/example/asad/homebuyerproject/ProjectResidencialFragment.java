package com.example.asad.homebuyerproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;



public class ProjectResidencialFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView mseekvaluemin,mseekvaluemax;



    public ProjectResidencialFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProjectResidencialFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProjectResidencialFragment newInstance(String param1, String param2) {
        ProjectResidencialFragment fragment = new ProjectResidencialFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }private String minprice,maxprice;
    private Button mSearchButton;
    private LinearLayout constructionlayout,bedroomlayout;

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
        View layout= inflater.inflate(R.layout.fragment_project_residencial, container, false);
        typecasting(layout);
        setvaluerange(layout);
        searchbuttompressed();
        //  Toast.makeText(getActivity(),"Min" + r.getSelectedMinValue().toString() +"Max"+ r.getSelectedMaxValue().toString(),Toast.LENGTH_SHORT).show();

        return layout;
    }

    // TODO: Rename method, update argument and hook method into UI event

    public void setvaluerange(View layout) {


        RangeSeekBar r= (RangeSeekBar)layout.findViewById(R.id.rangeSeekBar);

        r.setRangeValues(0,50);
        mseekvaluemax=(TextView)layout.findViewById(R.id.seekValuemax);
        mseekvaluemin=(TextView)layout.findViewById(R.id.seekValuemin);


        r.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {

            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {

                mseekvaluemin.setText( minValue.toString());
                mseekvaluemax.setText(maxValue.toString()+"+Crores" );
                minprice=minValue.toString();
                maxprice=maxValue.toString();
                bar.getAbsoluteMaxValue();
                bar.getAbsoluteMinValue();

                Toast.makeText(getActivity(),"Max" + bar.getAbsoluteMaxValue().toString() +"Min"+  bar.getAbsoluteMinValue().toString(),Toast.LENGTH_SHORT).show();
                //  r.resetSelectedValues();


            }
        });


    }
    ArrayList<String> typese=new ArrayList<String>();
    String bedroomcount;
    private void selectedtype(){
        int count = constructionlayout.getChildCount();
        View checkboxview = null;
        for(int i=0; i<count; i=i+2) {
            checkboxview = constructionlayout.getChildAt(i);
            boolean checked = ((CheckBox) checkboxview).isChecked();
            if (checked){
                typese.add(((CheckBox) checkboxview).getTag().toString());
            }
        }
    }
    private void selectedbedroom(){
        int count = bedroomlayout.getChildCount();
        View checkboxview = null;
        for(int i=0; i<count; i=i+2) {
            checkboxview = bedroomlayout.getChildAt(i);
            boolean checked = ((CheckBox) checkboxview).isChecked();
            if (checked){
                bedroomcount = ((CheckBox) checkboxview).getTag().toString();
            }
        }
    }
    private void searchbuttompressed(){
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedtype();
                selectedbedroom();
                Toast.makeText(getActivity(), "PropertyTypes "+typese, Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), "Bedroom Count"+bedroomcount, Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), "Price "+minprice+" MAX "+maxprice, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void typecasting(View viewfragment){

        mSearchButton=(Button) viewfragment.findViewById(R.id.OwnerButton);
        constructionlayout=(LinearLayout) viewfragment.findViewById(R.id.constructionType);
        bedroomlayout=(LinearLayout)viewfragment.findViewById(R.id.bedroomcounts);
    }

}
