package com.example.asad.homebuyerproject;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Search_Result_Details_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Search_Result_Details_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Search_Result_Details_Fragment extends Fragment implements
        GoogleMap.OnMyLocationButtonClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleApiClient.OnConnectionFailedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String LOG_TAG = "PlacesAPIActivity";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private GoogleApiClient mGoogleApiClient;
    private static final int PERMISSION_REQUEST_CODE = 100;
    ArrayList<MyPlace> placeArrayList = new ArrayList<MyPlace>();
    private GoogleMap mMap;
    private ProgressDialog progressDialog;
    double latitude; //= 0;//24.92865;
    double longitude; //= 0;//67.06630;
    double newLatitude;// = 0;
    double newLongitude;// = 0;
    boolean notificationOn = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;

    private static final String mapifyAPIKey = "AIzaSyBcO2OcGmmYq3vhPm98J8X5bouZ6DWv74g";
    //https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=24.92865,67.06630&radius=1000&key=AIzaSyDRY3KCI8RZMRgPbwsC8R0ml6PSWkVwtxI
    private static final String PLACES_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
    String urlStr = "";
    private ImageView mCoverView
            ,Railway
            ,Resturent
            ,Grocery
            ,Atm
            ,School
            ,Airport
            ,CallPhone;
    private TextView mImageCount
            ,mPropertyPrice
            ,mBedroom
            ,mPropertyType
            ,mSellerType
            ,mArea
            ,mSeller
            ,mPriceProperty
            ,mBedroomProperty
            ,mBathroom
            ,mPossession
            ,mPropertyDescription
            ,Furnished
            ,CornerProperty
            ,Informationverify
            ,viewContact
            ,SendMessage
            ,UserNameView
            ,UserPhoneView
            ;
    private Button yesButton,noButton;
    private MapView mMapView;
    private LinearLayout Amentities1
            ,Amentities2;
    private FrameLayout Container
            ,UserInfoPanel;
    private FragmentActivity myContext;
    private int PlaceHolder;
    private double lng,lat;
    private View view;
    private String UserName
            ,UserPhone;
    Property property;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;
    private HashMap<String,String> map =new HashMap<String,String>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Search_Result_Details_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Search_Result_Details_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Search_Result_Details_Fragment newInstance(String param1, String param2) {
        Search_Result_Details_Fragment fragment = new Search_Result_Details_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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

        try {
            view = inflater.inflate(R.layout.fragment_search_result_details_, container, false);
            MapsInitializer.initialize(this.getActivity());
            mMapView = (MapView) view.findViewById(R.id.mapplace);
            mMapView.onCreate(savedInstanceState);
            mMapView.getMapAsync(this);
            mUser = FirebaseAuth.getInstance().getCurrentUser();
        property = (Property)getArguments().getSerializable("Object");
        map = property.getPropertyMap();
        typecasting();
        /* SupportMapFragment mapFragment =
                (SupportMapFragment) myContext.getSupportFragmentManager().findFragmentById(R.id.mapplace);
        mapFragment.getMapAsync(this);
*/

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(myContext, GOOGLE_API_CLIENT_ID, this)
                .build();

        populatedata();


        }
        catch (InflateException e){
            Log.e("Gasdas", "Inflate exception");
        }
        return view;
        // Inflate the layout for this fragment

    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }
    @Override
    public void onDestroy() {
        mGoogleApiClient.stopAutoManage(myContext);
        mGoogleApiClient.disconnect();
        mMap.clear();
        super.onDestroy();
        mMapView.onDestroy();
    }
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState); mMapView.onSaveInstanceState(outState);
    }
    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }
    @Override
    public void onDestroyView() {
        mGoogleApiClient.stopAutoManage(myContext);
        mGoogleApiClient.disconnect();
        mMap.clear();
        super.onDestroyView();
    }
    List<String> images = new ArrayList<String>();
    private void populatedata() {
        try {
            PicassoClient.downloadImage(view.getContext(),property.getImage(),mCoverView,R.drawable.placeholder,0.8f);
        }
        catch (OutOfMemoryError e){
            Toast.makeText(getActivity(), "Can not Load Image", Toast.LENGTH_SHORT).show();
        }
        if (!map.isEmpty()) {
            if (map.get("GaleryData") != null) {

                String str = map.get("GaleryData");
                str = str.replace("[", "");
                str = str.replace("]", "");
                str = str.replace(" ", "");
                images = (List<String>) Arrays.asList(str.split(","));
            }
            if (map.get("Bedrooms") != null) {
                mBedroom.setText(map.get("Bedrooms"));
                mBedroomProperty.setText(map.get("Bedrooms"));
            } else {
                mBedroom.setText("--");
                mBedroomProperty.setText("--");
            }

            if (!images.isEmpty()) {
                mImageCount.setText(String.valueOf(images.size()));
            }
            mPropertyPrice.setText("RS: " + property.getPrice());
            mPriceProperty.setText("RS: " + property.getPrice());
            mPropertyType.setText(map.get("propertycatagory"));
            mArea.setText(map.get("PropertySize") + " Sqft");
            mSeller.setText(map.get("SellerType"));
            mSellerType.setText(map.get("SellType"));
            if (map.get("Bathrooms") != null) {
                mBathroom.setText(map.get("Bathrooms"));
            } else {
                mBathroom.setText("--");
            }
            mPossession.setText("Ready To Move");
            if (map.get("PropertyAdditionDetail") != null) {
                mPropertyDescription.setText(map.get("PropertyAdditionDetail"));
            } else {
                mPropertyDescription.setText("--");
            }
            if (map.get("propertylongitude") != null && map.get("propertylontitude") != null) {
                lng = Double.parseDouble(map.get("propertylongitude"));
                lat = Double.parseDouble(map.get("propertylontitude"));

                Railway.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mMap.clear();
                        findPlacesNearMe("train_station");
                        //addMarkersToMap(50);
                        System.out.println("Railway Clicked");
                        addMarkersToMap("train_station");
                    }
                });
                Resturent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mMap.clear();
                        findPlacesNearMe("restaurant");
                        addMarkersToMap("restaurant");
                    }
                });
                Grocery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mMap.clear();
                        findPlacesNearMe("grocery_or_supermarket");
                        addMarkersToMap("grocery_or_supermarket");
                    }
                });
                Airport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mMap.clear();
                        findPlacesNearMe("airport");
                        addMarkersToMap("airport");
                    }
                });
                School.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mMap.clear();
                        findPlacesNearMe("school");
                        addMarkersToMap("school");
                    }
                });
                Atm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mMap.clear();
                        findPlacesNearMe("atm");
                        addMarkersToMap("atm");
                    }
                });
            }
            if (map.get("PropertyAdditionDetail") !=null){
                mPropertyDescription.setText(map.get("PropertyAdditionDetail"));
            }
            else{
                mPropertyDescription.setText("--");
            }
            if (map.get("Furnishing") != null) {
                Furnished.setText(map.get("Furnishing"));
            } else {
                Furnished.setText("None Furnished");
            }
            if (map.get("PropertyCorner") != null) {
                CornerProperty.setText(map.get("PropertyCorner"));
            } else {
                CornerProperty.setText("NO");
            }
            if (map.get("Amentities") != null) {
                List<String> Amentities = new ArrayList<String>();
                String str = map.get("Amentities");
                str = str.replace("[", "");
                str = str.replace("]", "");
                str = str.replace(" ", "");
                Amentities = (List<String>) Arrays.asList(str.split(","));
                int count = Amentities1.getChildCount();
                int in1=0,in2=0;
                for (int i = 0; i < Amentities.size(); i++) {
                    if (i < count) {
                        View Textboxview = null;
                        innerloop:
                        for (int j = in1; j < count; j++) {
                            Textboxview = Amentities1.getChildAt(j);
                            if (j <= i) {
                                in1++;
                                ((TextView) Textboxview).setText(Amentities.get(i));
                                break innerloop;
                            }
                            else ((TextView) Textboxview).setVisibility(View.GONE);
                        }
                    }else  if (i  > count) {
                            int count2 = Amentities2.getChildCount();
                            View Textboxview2 = null;
                        innerloop:
                            for (int k = in2; k < count2; k++) {
                                Textboxview2 = Amentities2.getChildAt(k);

                                if (k <= (i - count)) {
                                    in2++;
                                    ((TextView) Textboxview2).setText(Amentities.get(i));
                                    break innerloop;
                                }
                                else ((TextView) Textboxview2).setVisibility(View.GONE);
                            }
                        }
                    }
                }else {
                Container.setVisibility(View.GONE);
            }
            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Informationverify.setText("Thankyou For Your Response");
                    yesButton.setVisibility(View.GONE);
                    noButton.setVisibility(View.GONE);
                }
            });
            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Informationverify.setText("Thankyou For Your Response");
                    yesButton.setVisibility(View.GONE);
                    noButton.setVisibility(View.GONE);
                }
            });
            if (mUser != null){
                if(map.get("DealingAgent") != null){
                    mDatabase = FirebaseDatabase.getInstance().getReference();
                   mDatabase.child("Users").child(map.get("DealingAgent")).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child("Name").getValue() != null)
                                UserName = dataSnapshot.child("Name").getValue().toString();

                            if (dataSnapshot.child("Phone").getValue() != null)
                                UserPhone = dataSnapshot.child("Phone").getValue().toString();
                                mDatabase.removeEventListener(this);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    viewContact.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            UserNameView.setText(UserName);
                            UserPhoneView.setText(UserPhone);
                        }
                    });
                    CallPhone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String uri = "tel:" + UserPhone.trim() ;
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse(uri));
                            startActivity(intent);
                        }
                    });
                    SendMessage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String uri =UserPhone.trim() ;
                            Uri smsUri = Uri.fromParts("sms", uri, null);
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.putExtra("sms_body", "Hey i like your Property at "+
                                    map.get("propertyshortaddress")+" "+map.get("propertyfulladress")+" "+"I would like to meet.");
                            intent.putExtra("address",UserPhone.trim());
                            intent.setType("vnd.android-dir/mms-sms");
                            startActivity(intent);
                        }
                    });
                }
                else if (map.get("UserID") != null){
                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.child("Users").child(map.get("UserID")).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child("Name").getValue() != null)
                            UserName = dataSnapshot.child("Name").getValue().toString();

                            if (dataSnapshot.child("Phone").getValue() != null)
                            UserPhone = dataSnapshot.child("Phone").getValue().toString();
                            mDatabase.removeEventListener(this);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    viewContact.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                           UserNameView.setText(UserName);
                            UserPhoneView.setText(UserPhone);
                        }
                    });
                    CallPhone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String uri = "tel:" + UserPhone.trim() ;
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse(uri));
                            startActivity(intent);
                        }
                    });
                    SendMessage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String uri =UserPhone.trim() ;
                            Uri smsUri = Uri.fromParts("sms", uri, null);
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.putExtra("sms_body", "Hey i like your Property at "+
                                    map.get("propertyshortaddress propertyfulladress")+" "+map.get("propertyfulladress")+"I would like to meet.");
                            intent.putExtra("address",UserPhone.trim());
                            intent.setType("vnd.android-dir/mms-sms");
                            startActivity(intent);
                        }
                    });
                }
                else {
                    viewContact.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(myContext, "User Not Available", Toast.LENGTH_SHORT).show();
                        }
                    });
                    SendMessage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(myContext, "User Not Available", Toast.LENGTH_SHORT).show();
                        }
                    });
                    CallPhone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(myContext, "User Not Available", Toast.LENGTH_SHORT).show();
                        }
                    });
                    UserInfoPanel.setVisibility(View.GONE);
                }
            }
            else{
                viewContact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(myContext, "Please Login to View Contact Information", Toast.LENGTH_SHORT).show();
                    }
                });
                SendMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(myContext, "Plaese Login to Send Message", Toast.LENGTH_SHORT).show();
                    }
                });
                CallPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(myContext, "Please Login to Call Number", Toast.LENGTH_SHORT).show();
                    }
                });
                UserInfoPanel.setVisibility(View.GONE);
            }
        }
    }

    private void typecasting() {
        mCoverView =(ImageView) view.findViewById(R.id.CoverImage);
        mImageCount = (TextView) view.findViewById(R.id.ImagesLeftCounter);
        mPropertyPrice = (TextView) view.findViewById(R.id.Pamount);
        mBedroom = (TextView) view.findViewById(R.id.mBedroom);
        mPropertyType = (TextView) view.findViewById(R.id.mPropertyType);
        mSellerType = (TextView) view.findViewById(R.id.mSellerType);
        mArea = (TextView) view.findViewById(R.id.mArea);
        mSeller = (TextView) view.findViewById(R.id.mSeller);
        mPriceProperty = (TextView) view.findViewById(R.id.mPriceproperty);
        mBedroomProperty = (TextView) view.findViewById(R.id.mBedroomProperty);
        mBathroom = (TextView) view.findViewById(R.id.mBathroom);
        mPossession = (TextView) view.findViewById(R.id.mPossession);
        mPropertyDescription = (TextView) view.findViewById(R.id.mPropertyDescription);
        Furnished = (TextView) view.findViewById(R.id.Furnished);
        CornerProperty = (TextView) view.findViewById(R.id.CornerProperty);
        Railway=(ImageView) view.findViewById(R.id.Railway);
        Resturent=(ImageView) view.findViewById(R.id.Resturent);
        Grocery=(ImageView) view.findViewById(R.id.Grosory);
        Atm=(ImageView) view.findViewById(R.id.Atm);
        School=(ImageView) view.findViewById(R.id.School);
        Airport=(ImageView) view.findViewById(R.id.Airport);
        Amentities1 = (LinearLayout) view.findViewById(R.id.Amentities1);
        Amentities2 = (LinearLayout) view.findViewById(R.id.Amentities2);
        Container = (FrameLayout) view.findViewById(R.id.FrameContainer);
        UserInfoPanel = (FrameLayout) view.findViewById(R.id.UserInformation);
        Informationverify = (TextView)view.findViewById(R.id.InformationContainer);
        yesButton = (Button)view.findViewById(R.id.YesButton);
        noButton = (Button)view.findViewById(R.id.NoButton);
        viewContact = (TextView)view.findViewById(R.id.viewContact);
        SendMessage = (TextView)view.findViewById(R.id.sendMessage);
        CallPhone = (ImageView) view.findViewById(R.id.callPhone);
        UserNameView = (TextView)view.findViewById(R.id.mUserName);
        UserPhoneView = (TextView) view.findViewById(R.id.mUserPhone);
         PlaceHolder = R.drawable.placeholder1;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        myContext=(FragmentActivity) getActivity();
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        mGoogleApiClient.stopAutoManage(myContext);
        mGoogleApiClient.disconnect();
        mMap.clear();
        mListener = null;
        view = null;

        super.onDetach();




    }

    private void addMarkersToMap(String placeTypeStr) {
        int size = placeArrayList.size();
        if (placeTypeStr.equals("all")) {
            for (int i = 0; i < placeArrayList.size(); i++) {
                if ((placeArrayList.get(i).name.contains("train_station")) || placeArrayList.get(i).type.equals("train_station")) {
                    mMap.addMarker(new MarkerOptions()
                            .position(placeArrayList.get(i).location)
                            .title(placeArrayList.get(i).name)
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.railway_pin)));
                } else if ( (placeArrayList.get(i).name.contains("school")) || (placeArrayList.get(i).type.equals("school")) || (placeArrayList.get(i).type.equals("School")) || (placeArrayList.get(i).type.equals("university")) ) {
                    mMap.addMarker(new MarkerOptions()
                            .position(placeArrayList.get(i).location)
                            .title(placeArrayList.get(i).name)
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.school_pin)));
                }else if ((placeArrayList.get(i).name.contains("grocery_or_supermarket")) || placeArrayList.get(i).type.equals("grocery_or_supermarket")) {
                    mMap.addMarker(new MarkerOptions()
                            .position(placeArrayList.get(i).location)
                            .title(placeArrayList.get(i).name)
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.grocery_pin)));
                } else if ((placeArrayList.get(i).name.contains("airport")) || placeArrayList.get(i).type.equals("airport")) {
                    mMap.addMarker(new MarkerOptions()
                            .position(placeArrayList.get(i).location)
                            .title(placeArrayList.get(i).name)
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.airport_pin)));
                }else if ((placeArrayList.get(i).name.contains("restaurant")) || (placeArrayList.get(i).type.equals("bakery")) || (placeArrayList.get(i).type.equals("restaurant"))) {
                    mMap.addMarker(new MarkerOptions()
                            .position(placeArrayList.get(i).location)
                            .title(placeArrayList.get(i).name)
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.restaurant_pin)));
                } else if ((placeArrayList.get(i).name.contains("bank")) || (placeArrayList.get(i).type.equals("bank")) || (placeArrayList.get(i).name.contains("atm")) || (placeArrayList.get(i).type.equals("atm"))) {
                    mMap.addMarker(new MarkerOptions()
                            .position(placeArrayList.get(i).location)
                            .title(placeArrayList.get(i).name)
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.atm_pin)));
                }
            }// end for
        }else if (placeTypeStr.equals("train_station") && size > 0) { //parks
            int i;
            for (i = 0; i < placeArrayList.size(); i++) {
                if ((placeArrayList.get(i).name.contains("train_station")) || placeArrayList.get(i).type.equals("train_station")) {
                    mMap.addMarker(new MarkerOptions()
                            .position(placeArrayList.get(i).location)
                            .title(placeArrayList.get(i).name)
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.railway_pin)));
                }
            }

            //moveToCurrentLocation(placeArrayList.get(i).location);

        } else if (placeTypeStr.equals("school") && size > 0) { //university
            int i;
            for (i = 0; i < placeArrayList.size(); i++) {
                if ((placeArrayList.get(i).name.contains("University")) || (placeArrayList.get(i).name.contains("school")) || (placeArrayList.get(i).type.equals("school")) || (placeArrayList.get(i).type.equals("university"))) {
                    mMap.addMarker(new MarkerOptions()
                            .position(placeArrayList.get(i).location)
                            .title(placeArrayList.get(i).name)
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.school_pin)));
                }
            }
            //moveToCurrentLocation(placeArrayList.get(i).location);
        } else if (placeTypeStr.equals("grocery_or_supermarket") && size > 0) { //hospital
            int i;
            for (i = 0; i < placeArrayList.size(); i++) {
                if ((placeArrayList.get(i).name.contains("grocery_or_supermarket")) || placeArrayList.get(i).type.equals("grocery_or_supermarket")) {

                    mMap.addMarker(new MarkerOptions()
                            .position(placeArrayList.get(i).location)
                            .title(placeArrayList.get(i).name)
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.grocery_pin)));
                }
            }
            //moveToCurrentLocation(placeArrayList.get(i).location);
        } else if (placeTypeStr.equals("airport") && size > 0 ) { //Mosque
            int i;
            for (i = 0; i < placeArrayList.size(); i++) {
                if ((placeArrayList.get(i).name.contains("airport")) || placeArrayList.get(i).type.equals("airport")) {
                    mMap.addMarker(new MarkerOptions()
                            .position(placeArrayList.get(i).location)
                            .title(placeArrayList.get(i).name)
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.airport_pin)));
                }
            }

        } else if (placeTypeStr.equals("restaurant") && size > 0) { //Restaurant
            int i;
            for (i = 0; i < placeArrayList.size(); i++) {
                if ((placeArrayList.get(i).name.contains("restaurant")) || (placeArrayList.get(i).type.equals("bakery")) || (placeArrayList.get(i).type.equals("restaurant"))) {
                    mMap.addMarker(new MarkerOptions()
                            .position(placeArrayList.get(i).location)
                            .title(placeArrayList.get(i).name)
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.restaurant_pin)));
                }
            }
            //moveToCurrentLocation(placeArrayList.get(i).location);
        } else if (placeTypeStr.equals("atm") && size > 0) { //Bank
            int i;
            for (i = 0; i < placeArrayList.size(); i++) {
                if ((placeArrayList.get(i).name.contains("Bank")) || (placeArrayList.get(i).type.equals("bank")) || (placeArrayList.get(i).type.equals("finance"))) {
                    mMap.addMarker(new MarkerOptions()
                            .position(placeArrayList.get(i).location)
                            .title(placeArrayList.get(i).name)
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.atm_pin)));
                }
            }
            //moveToCurrentLocation(placeArrayList.get(i).location);
        }

        if (size > 0){setCameraToShowAllMarkers();}

    }
    private void setCameraToShowAllMarkers(){

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (int i = 0; i < placeArrayList.size(); i++) {
            builder.include(placeArrayList.get(i).location);
        }
        LatLngBounds bounds = builder.build();

        int padding = 250; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        mMap.animateCamera(cu);
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(LOG_TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(getActivity(),
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMyLocationButtonClickListener(this);


    }
    private boolean checkInternetConenction() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec =(ConnectivityManager)myContext.getSystemService(myContext.getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||

                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
            //Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
            return true;
        }else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
            Toast.makeText(getActivity(), " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }
    private void findPlacesNearMe(String placeType) {

        if (checkInternetConenction()) {



                newLatitude = lat; // returns latitude
                newLongitude = lng; // returns longitude

            System.out.println("mapify: newLat= "+ newLatitude + " lat= " + latitude);

            placeArrayList = new ArrayList<MyPlace>();
            if (placeType.equals(placeType)) {
                urlStr = PLACES_SEARCH_URL + "location=" + String.valueOf(newLatitude) + "," + String.valueOf(newLongitude) + "&type=" + placeType + "&radius=400&key=" + mapifyAPIKey;
            }



            if (placeType.equals(placeType)) {
                new JSONParse().execute();
            }
            latitude = newLatitude;
            longitude = newLongitude;
        }
    }

    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        //private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONParser jParser = new JSONParser();

            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromURL(urlStr);
            return json;
        }
        @Override
        protected void onPostExecute(JSONObject jsonObj) {
            //  pDialog.dismiss();
            try {

                JSONArray timeline = jsonObj.getJSONArray("results");

                for (int i =0; i < timeline.length(); i++) {
                    //String name = timeline.getJSONObject(i).getString("name");
                    MyPlace place = new MyPlace();

                    place.name = timeline.getJSONObject(i).getString("name");

                    Double lat = new Double(timeline.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getString("lat"));
                    Double lng = new Double(timeline.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getString("lng"));

                    place.location = new LatLng(lat, lng);

                    String types =  timeline.getJSONObject(i).getString("types");
                    place.type = types.substring(2,types.indexOf(",") - 1);

                    //System.out.print("Name123= "+ place.name + " lat= " + lat + " lng= "+ lng+ " type= "+ place.type);
                    placeArrayList.add(place);

                }

                addMarkersToMap("all");


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
