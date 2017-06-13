package com.example.asad.homebuyerproject;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import static android.R.attr.onClick;
import static com.example.asad.homebuyerproject.R.id.asad;
import static com.example.asad.homebuyerproject.R.id.spinner;

public class Upload_Property_Activity extends AppCompatActivity {

    private ImageView mUploadPhotos;
    private Spinner coverdarea;
    private EditText mTestingValue;
    private NumberFormat numberformat;
    private String SellRent, ResidentialCommercial, PropertyType;
    private ArrayList<String> propertyData=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload__property_);

        //getting data

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                SellRent = null;
                ResidentialCommercial = null;
                PropertyType = null;
            } else {
                propertyData = extras.getStringArrayList("data");
                ResidentialCommercial=extras.getString("ResidentialCommercialData");
                PropertyType=extras.getString("Type");
            }
        } else {
            ResidentialCommercial = (String) savedInstanceState.getSerializable("ResidentialCommercialData");
            PropertyType = (String) savedInstanceState.getSerializable("Type");
        }
        SellRent=propertyData.get(propertyData.indexOf("SellType")+1);
        Typecasting();
        OpenFragments();
    }
   private void opensellsameflatfragment() {
      //sameFlat
       Fragment_Sell_Residential_Flat frag = new Fragment_Sell_Residential_Flat();
       FragmentManager manager = getFragmentManager();
       FragmentTransaction transaction = manager.beginTransaction();
       transaction.replace(R.id.parent, frag, "Residential Flat");
       Bundle bundle = new Bundle();
       propertyData.add("propertyType");
       propertyData.add(ResidentialCommercial);
       propertyData.add("propertycatagory");
       propertyData.add(PropertyType);
       bundle.putStringArrayList("data", propertyData);
       frag.setArguments(bundle);
       transaction.commit();
   }
    private void opensellsamehousefragment(){
        //SameHouse
        Fragment_Sell_Residential_House frag = new Fragment_Sell_Residential_House();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.parent, frag, "House");
        Bundle bundle = new Bundle();
        propertyData.add("propertyType");
        propertyData.add(ResidentialCommercial);
        propertyData.add("propertycatagory");
        propertyData.add(PropertyType);
        bundle.putStringArrayList("data", propertyData);
        frag.setArguments(bundle);
        transaction.commit();
    }
    private void openrentflatfragment(){
        //SameFlat
        Fragment_Rent_Residential_Flat frag = new Fragment_Rent_Residential_Flat();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.parent, frag, "Residential Flat");
        Bundle bundle = new Bundle();
        propertyData.add("propertyType");
        propertyData.add(ResidentialCommercial);
        propertyData.add("propertycatagory");
        propertyData.add(PropertyType);
        bundle.putStringArrayList("data", propertyData);
        frag.setArguments(bundle);
        transaction.commit();
    }
    private void OpenFragments() {
        if (SellRent.equals("Sell") && ResidentialCommercial.equals("Residential") && PropertyType.equals("Flat")) {
            opensellsameflatfragment();
        } else if (SellRent.equals("Sell") && ResidentialCommercial.equals("Residential") && PropertyType.equals("Plot")) {
            Fragment_Sell_Residential_Plot frag = new Fragment_Sell_Residential_Plot();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "Plot");
            Bundle bundle = new Bundle();
            propertyData.add("propertyType");
            propertyData.add(ResidentialCommercial);
            propertyData.add("propertycatagory");
            propertyData.add(PropertyType);
            bundle.putStringArrayList("data",propertyData);
            frag.setArguments(bundle);
            transaction.commit();

        } else if (SellRent.equals("Sell") && ResidentialCommercial.equals("Residential") && PropertyType.equals("Builder Floor")) {
            opensellsameflatfragment();

        } else if (SellRent.equals("Sell") && ResidentialCommercial.equals("Residential") && PropertyType.equals("House")) {
            opensellsamehousefragment();

        } else if (SellRent.equals("Sell") && ResidentialCommercial.equals("Residential") && PropertyType.equals("Pent House")) {
            opensellsameflatfragment();

        } else if (SellRent.equals("Sell") && ResidentialCommercial.equals("Residential") && PropertyType.equals("Studio Apartment")) {
            opensellsameflatfragment();

        } else if (SellRent.equals("Sell") && ResidentialCommercial.equals("Residential") && PropertyType.equals("Villa")) {
            opensellsamehousefragment();

        } else if (SellRent.equals("Sell") && ResidentialCommercial.equals("Commercial") && PropertyType.equals("Office Space")) {
            Fragment_Sell_Commercial_Officespace frag = new Fragment_Sell_Commercial_Officespace();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "Office Space");
            Bundle bundle = new Bundle();
            propertyData.add("propertyType");
            propertyData.add(ResidentialCommercial);
            propertyData.add("propertycatagory");
            propertyData.add(PropertyType);
            bundle.putStringArrayList("data", propertyData);
            frag.setArguments(bundle);
            transaction.commit();

        } else if (SellRent.equals("Sell") && ResidentialCommercial.equals("Commercial") && PropertyType.equals("Showroom")) {
            Fragment_Sell_Commercial_Showroom frag = new Fragment_Sell_Commercial_Showroom();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "Showroom");
            Bundle bundle = new Bundle();
            propertyData.add("propertyType");
            propertyData.add(ResidentialCommercial);
            propertyData.add("propertycatagory");
            propertyData.add(PropertyType);
            bundle.putStringArrayList("data", propertyData);
            frag.setArguments(bundle);
            transaction.commit();

        } else if (SellRent.equals("Sell") && ResidentialCommercial.equals("Commercial") && PropertyType.equals("Shop")) {
            Fragment_Sell_Commercial_Shop frag = new Fragment_Sell_Commercial_Shop();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "Shop");
            Bundle bundle = new Bundle();
            propertyData.add("propertyType");
            propertyData.add(ResidentialCommercial);
            propertyData.add("propertycatagory");
            propertyData.add(PropertyType);
            bundle.putStringArrayList("data", propertyData);
            frag.setArguments(bundle);
            transaction.commit();

        } else if (SellRent.equals("Sell") && ResidentialCommercial.equals("Commercial") && PropertyType.equals("Commercial Land")) {
            Fragment_Sell_Commercial_CommercialLand frag = new Fragment_Sell_Commercial_CommercialLand();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "Commercial Land");
            Bundle bundle = new Bundle();
            propertyData.add("propertyType");
            propertyData.add(ResidentialCommercial);
            propertyData.add("propertycatagory");
            propertyData.add(PropertyType);
            bundle.putStringArrayList("data", propertyData);
            frag.setArguments(bundle);
            transaction.commit();

        } else if (SellRent.equals("Sell") && ResidentialCommercial.equals("Commercial") && PropertyType.equals("Industrial Land")) {
            Fragment_Sell_Commercial_IndustrialLand frag = new Fragment_Sell_Commercial_IndustrialLand();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "Shop");
            Bundle bundle = new Bundle();
            propertyData.add("propertyType");
            propertyData.add(ResidentialCommercial);
            propertyData.add("propertycatagory");
            propertyData.add(PropertyType);
            bundle.putStringArrayList("data", propertyData);
            frag.setArguments(bundle);
            transaction.commit();

        }


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        if (SellRent.equals("Rent") && ResidentialCommercial.equals("Residential") && PropertyType.equals("Flat")) {
            opensellsameflatfragment();
        } else if (SellRent.equals("Rent") && ResidentialCommercial.equals("Residential") && PropertyType.equals("PG Hostel")) {
            Fragment_Rent_Residential_Pghostel frag = new Fragment_Rent_Residential_Pghostel();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "Pg Hostel");
            Bundle bundle = new Bundle();
            propertyData.add("propertyType");
            propertyData.add(ResidentialCommercial);
            propertyData.add("propertycatagory");
            propertyData.add(PropertyType);
            bundle.putStringArrayList("data", propertyData);
            frag.setArguments(bundle);
            transaction.commit();

        } else if (SellRent.equals("Rent") && ResidentialCommercial.equals("Residential") && PropertyType.equals("Builder Floor")) {
            opensellsameflatfragment();

        } else if (SellRent.equals("Rent") && ResidentialCommercial.equals("Residential") && PropertyType.equals("House")) {
            Fragment_Rent_Residential_House frag = new Fragment_Rent_Residential_House();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "House");
            Bundle bundle = new Bundle();
            propertyData.add("propertyType");
            propertyData.add(ResidentialCommercial);
            propertyData.add("propertycatagory");
            propertyData.add(PropertyType);
            bundle.putStringArrayList("data", propertyData);
            frag.setArguments(bundle);
            transaction.commit();

        } else if (SellRent.equals("Rent") && ResidentialCommercial.equals("Residential") && PropertyType.equals("Pent House")) {
            opensellsameflatfragment();

        } else if (SellRent.equals("Rent") && ResidentialCommercial.equals("Residential") && PropertyType.equals("Studio Apartment")) {
            opensellsameflatfragment();

        } else if (SellRent.equals("Rent") && ResidentialCommercial.equals("Residential") && PropertyType.equals("Villa")) {
            Fragment_Rent_Residential_Villa frag = new Fragment_Rent_Residential_Villa();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "Villa");
            Bundle bundle = new Bundle();
            propertyData.add("propertyType");
            propertyData.add(ResidentialCommercial);
            propertyData.add("propertycatagory");
            propertyData.add(PropertyType);
            bundle.putStringArrayList("data", propertyData);
            frag.setArguments(bundle);
            transaction.commit();

        } else if (SellRent.equals("Rent") && ResidentialCommercial.equals("Commercial") && PropertyType.equals("Office Space")) {
            Fragment_Rent_Commercial_Officespace frag = new Fragment_Rent_Commercial_Officespace();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "Office Space");
            Bundle bundle = new Bundle();
            propertyData.add("propertyType");
            propertyData.add(ResidentialCommercial);
            propertyData.add("propertycatagory");
            propertyData.add(PropertyType);
            bundle.putStringArrayList("data", propertyData);
            frag.setArguments(bundle);
            transaction.commit();

        } else if (SellRent.equals("Rent") && ResidentialCommercial.equals("Commercial") && PropertyType.equals("Showroom")) {
            Fragment_Rent_Commercial_Showroom frag = new Fragment_Rent_Commercial_Showroom();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "Showroom");
            Bundle bundle = new Bundle();
            propertyData.add("propertyType");
            propertyData.add(ResidentialCommercial);
            propertyData.add("propertycatagory");
            propertyData.add(PropertyType);
            bundle.putStringArrayList("data", propertyData);
            frag.setArguments(bundle);
            transaction.commit();

        } else if (SellRent.equals("Rent") && ResidentialCommercial.equals("Commercial") && PropertyType.equals("Shop")) {
            Fragment_Rent_Commercial_Shop frag = new Fragment_Rent_Commercial_Shop();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "Shop");
            Bundle bundle = new Bundle();
            propertyData.add("propertyType");
            propertyData.add(ResidentialCommercial);
            propertyData.add("propertycatagory");
            propertyData.add(PropertyType);
            bundle.putStringArrayList("data", propertyData);
            frag.setArguments(bundle);
            transaction.commit();

        } else if (SellRent.equals("Rent") && ResidentialCommercial.equals("Commercial") && PropertyType.equals("Commercial Land")) {
            Fragment_Rent_Commercial_CommercialLand frag = new Fragment_Rent_Commercial_CommercialLand();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "Commercial Land");
            Bundle bundle = new Bundle();
            propertyData.add("propertyType");
            propertyData.add(ResidentialCommercial);
            propertyData.add("propertycatagory");
            propertyData.add(PropertyType);
            bundle.putStringArrayList("data", propertyData);
            frag.setArguments(bundle);
            transaction.commit();

        } else if (SellRent.equals("Rent") && ResidentialCommercial.equals("Commercial") && PropertyType.equals("Industrial Land")) {
           Fragment_Rent_Commercial_IndustrialLand frag = new Fragment_Rent_Commercial_IndustrialLand();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "Shop");
            Bundle bundle = new Bundle();
            propertyData.add("propertyType");
            propertyData.add(ResidentialCommercial);
            propertyData.add("propertycatagory");
            propertyData.add(PropertyType);
            bundle.putStringArrayList("data", propertyData);
            frag.setArguments(bundle);
            transaction.commit();
        }
    }
    public void onStart() {
        super.onStart();
    }
    private void Typecasting() {
        mTestingValue = (EditText) findViewById(R.id.TestingValue);
    }

}
