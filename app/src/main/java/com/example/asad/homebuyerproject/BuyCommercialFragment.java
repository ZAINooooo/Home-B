package com.example.asad.homebuyerproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class BuyCommercialFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView mseekvaluemin,mseekvaluemax;
    private TextView mseekvaluemin1,mseekvaluemax1;


    public BuyCommercialFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BuyCommercialFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BuyCommercialFragment newInstance(String param1, String param2) {
        BuyCommercialFragment fragment = new BuyCommercialFragment();
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
    private Button mSearchButton;
    private LinearLayout propertylayout;
    private AutoCompleteTextView citysearch;
    ArrayList<HashMap<String,String>> notes = new ArrayList<HashMap<String,String>>();
    private HashMap<String, String> PropertyMap = new HashMap<String, String>();
    DatabaseReference mdatabase;
    private String mCity="";
    private String minprice = "",maxprice = "",minarea = "",maxarea = "";
    private String[] languages = {
            "Bagh", "Bhimber", "khuiratta", "Kotli", "Mangla",
            "Mirpur", "Muzaffarabad", "Plandri", "Rawalakot", "Punch",
            "Amir Chah", "Bazdar", "Bela", "Bellpat", "Bagh", "Burj",
            "Chagai", "Chah Sandan", "Chakku", "Chaman", "Chhatr",
            "Dalbandin", "Dera Bugti", "Diwana", "Duki", "Dushi",
            "Duzab", "Gajar", "Gandava", "Garhi Khairo", "Garruck",
            "Ghazluna", "Girdan", "Gulistan", "Gwadar", "Hab Chauki",
            "Hameedabad", "Harnai", "Jhal", "Jhal Jhao", "Jhatpat",
            "Jiwani", "Kalat", "Kamararod", "Kanpur", "Kappar", "Katuri",
            "Khuzdar", "Kohan", "Korak", "Lasbela", "Loralai", "Mand",
            "Mashki Chah", "Mastung", "Naseerabad", "Nushki", "Ormara",
            "Palantuk", "Panjgur", "Piharak", "Qamruddin Karez", "Qila Abdullah",
            "Qila Ladgasht", "Qila Safed", "Quetta", "Rakhni", "Robat Thana",
            "Rodkhan", "Saindak", "Sanjawi", "Saruna", "Shingar", "Shorap",
            "Sibi", "Sonmiani", "Spezand", "Sui", "Suntsar", "Surab", "Thalo",
            "Tump", "Turbat", "Umarao", "pirMahal", "Vitakri", "Washap", "Wasjuk",
            "Astor", "Hunza", "Gilgit", "Nagar", "Skardu", "Shangrila", "Shandur",
            "Bajaur", "Hangu", "Malakand", "Miram Shah", "Mohmand", "Khyber", "Kurram",
            "North Waziristan", "South Waziristan", "Abbottabad", "Ayubia", "Adezai",
            "Bannu", "Birote", "Chakdara", "Charsadda", "Darya Khan", "Dera Ismail Khan",
            "Drasan", "Hangu", "Haripur", "Kalam", "Karak", "Khanaspur", "Kohat", "Kohistan",
            "Lakki Marwat", "Lower Dir", "Malakand", "Mansehra", "Mardan", "Mongora", "Nowshera",
            "Peshawar", "Saidu Sharif", "Shangla", "Swabi", "Swat", "Tangi", "Thall", "Tordher",
            "Upper Dir", "Ali Pur", "Arifwala", "Attock", "Bhalwal", "Bahawalnagar", "Bahawalpur",
            "Bhakkar", "Chailianwala", "Chakwal", "Chichawatni", "Chiniot", "Daska", "Darya Khan",
            "Dhaular", "Dinga", "Dipalpur", "Faisalabad", "Gadar", "Ghakhar Mandi", "Gujranwala",
            "Gujrat", "Gujar Khan", "Hafizabad", "Haroonabad", "Jampur", "Jhang", "Jhelum", "Kalabagh",
            "Kasur", "Kamokey", "Khanewal", "Khanpur", "Khushab", "Kot Addu", "Jahania", "Jalla Araain",
            "Laar", "Lahore", "Lalamusa", "Layyah", "Lodhran", "Mamoori", "Mandi Bahauddin", "Makhdoom Aali",
            "Mian Channu", "Minawala", "Mianwali", "Multan", "Murree", "Muridke", "Muzaffargarh", "Narowal",
            "Okara", "Rajan Pur", "Pak Pattan", "Panjgur", "Pattoki", "Raiwind", "Rahim Yar Khan", "Rawalpindi",
            "Rohri", "Sadiqabad", "Safdar Abad", "Sahiwal", "Sangla Hill", "Samberial", "Sargodha", "Sohawa",
            "Talagang", "Islamabad", "Tarbela", "Taxila", "Toba Tek Singh", "Vehari", "Wah Cantonment", "Wazirabad",
            "Dadu", "Diplo", "Ghotki", "Hala", "Hyderabad", "Islamkot", "Jacobabad", "Jamesabad", "Jamshoro",
            "Karachi", "Kashmor", "Keti Bandar", "Khairpur", "Klupro", "Khokhropur", "Kotri", "Larkana", "Mathi",
            "Matiari", "Mirpur Batoro", "Mirpur Khas", "Mirpur Sakro", "Mithi", "Moro", "Nagar Parkar", "Naushara",
            "Noushero Feroz", "Nawabshah", "Pokran", "Qambar", "Ranipur", "Ratodero", "Rohri", "Sanghar", "Shahpur Chakar",
            "Shikarpur", "Sukkur", "Tando Adam", "Tando Allahyar", "Tando Bago", "Thatta", "Umarkot", "Warah",
    };
    private ProgressDialog mUploadProgress;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout= inflater.inflate(R.layout.fragment_buy_commercial, container, false);
        mUploadProgress=new ProgressDialog(getActivity());
        typecasting(layout);
        ArrayAdapterMethod();
        setvaluerange(layout);
        setAreaRange(layout);
        searchbuttompressed();
        //  Toast.makeText(getActivity(),"Min" + r.getSelectedMinValue().toString() +"Max"+ r.getSelectedMaxValue().toString(),Toast.LENGTH_SHORT).show();

        return layout;
    }

    public void ArrayAdapterMethod() {
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, languages);
        citysearch.setAdapter(adapter);
        citysearch.setThreshold(1);
        citysearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);

            }
        });
    }
    ArrayList<String> typese=new ArrayList<String>();

    private void selectedtype(){
        mCity=citysearch.getText().toString();
        int count = propertylayout.getChildCount();
        View checkboxview = null;
        for(int i=0; i<count; i=i+2) {
            checkboxview = propertylayout.getChildAt(i);
            boolean checked = ((CheckBox) checkboxview).isChecked();
            if (checked){
                typese.add(((CheckBox) checkboxview).getTag().toString());
            }
        }
    }

    private String value=null;
    private void searchbuttompressed(){
        mdatabase= FirebaseDatabase.getInstance().getReference();

        mSearchButton.setOnClickListener(new View.OnClickListener()


        {
            @Override
            public void onClick(View view) {
                if (!PropertyMap.isEmpty())
                    PropertyMap.clear();

                if (!notes.isEmpty())
                    notes.clear();


                selectedtype();
                DatabaseReference mRef=mdatabase.child("Property").child("Sell").child("Commercial");
                mUploadProgress.setMessage("Loading Data Please Wait ..");
                mUploadProgress.show();
                mUploadProgress.setCancelable(false);
                mUploadProgress.setCanceledOnTouchOutside(false);
                mRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        String sq = dataSnapshot.getKey();
                        if (!typese.isEmpty()&&typese.contains(sq)) {
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                int appmaxprice=0,appminprice=0,firepirce=0,appminarea=0,appmaxarea=0,firearea=0;

                                try {
                                   /* appmaxprice = Integer.parseInt(maxprice);
                                    appminprice = Integer.parseInt(minprice);
                                    appminarea = Integer.parseInt(maxarea);
                                    appmaxarea = Integer.parseInt(minarea);*/
                                    appmaxprice=3500000;
                                    appminprice=2000;
                                    appmaxarea=500;
                                    appmaxprice=200;
                                    firepirce = Integer.parseInt(child.child("PropertyPrice").getValue().toString());
                                    firearea = Integer.parseInt(child.child("PropertySize").getValue().toString());

                                } catch(NumberFormatException nfe) {
                                    System.out.println("Could not parse " + nfe);
                                }
                                if (!mCity.matches("") && child.child("City").getValue().equals(mCity)&&
                                        !minarea.matches("")&&!maxarea.matches("")&&firearea >=appminarea&&firearea<=appmaxarea&&
                                        !minprice.matches("")&&!maxprice.matches("")&&firepirce >=appminprice&&firepirce<=appmaxprice) {
                                    for (DataSnapshot single : child.getChildren()) {
                                        PropertyMap.put(single.getKey(), single.getValue().toString());
                                    }
                                } else if (!mCity.matches("") && child.child("City").getValue().equals(mCity)&&
                                        !minarea.matches("")&&!maxarea.matches("")&&firearea >=appminarea&&firearea<=appmaxarea&&
                                        minprice.matches("")&&maxprice.matches("")) {
                                    for (DataSnapshot single : child.getChildren()) {
                                        PropertyMap.put(single.getKey(), single.getValue().toString());
                                    }
                                }else if (!mCity.matches("") && child.child("City").getValue().equals(mCity)&&
                                        minarea.matches("")&&maxarea.matches("")&&
                                        !minprice.matches("")&&!maxprice.matches("")&&firepirce >=appminprice&&firepirce<=appmaxprice) {
                                    for (DataSnapshot single : child.getChildren()) {
                                        PropertyMap.put(single.getKey(), single.getValue().toString());
                                    }
                                } else if (!mCity.matches("") && child.child("City").getValue().equals(mCity)&&
                                        minarea.matches("")&&maxarea.matches("")&&
                                        minprice.matches("")&&maxprice.matches("")) {
                                    for (DataSnapshot single : child.getChildren()) {
                                        PropertyMap.put(single.getKey(), single.getValue().toString());
                                    }
                                }  else if (mCity.matches("") &&
                                        !minarea.matches("")&&!maxarea.matches("")&&firearea >=appminarea&&firearea<=appmaxarea&&
                                        !minprice.matches("")&&!maxprice.matches("")&&firepirce >=appminprice&&firepirce<=appmaxprice) {
                                    for (DataSnapshot single : child.getChildren()) {
                                        PropertyMap.put(single.getKey(), single.getValue().toString());
                                    }
                                }else if (mCity.matches("") &&
                                        !minarea.matches("")&&!maxarea.matches("")&&firearea >=appminarea&&firearea<=appmaxarea&&
                                        minprice.matches("")&&maxprice.matches("")) {
                                    for (DataSnapshot single : child.getChildren()) {
                                        PropertyMap.put(single.getKey(), single.getValue().toString());
                                    }
                                }else if (mCity.matches("") &&
                                        minarea.matches("")&&maxarea.matches("")&&
                                        !minprice.matches("")&&!maxprice.matches("")&&firepirce >=appminprice&&firepirce<=appmaxprice) {
                                    for (DataSnapshot single : child.getChildren()) {
                                        PropertyMap.put(single.getKey(), single.getValue().toString());
                                    }
                                }else if (mCity.matches("") &&
                                        minarea.matches("")&&maxarea.matches("")&&
                                        minprice.matches("")&&maxprice.matches("")) {
                                    for (DataSnapshot single : child.getChildren()) {
                                        PropertyMap.put(single.getKey(), single.getValue().toString());
                                    }
                                }
                                if (!PropertyMap.isEmpty()) {
                                    notes.add(PropertyMap);
                                    PropertyMap = new HashMap<>();
                                }
                            }
                        }else if(typese.isEmpty()){
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                int appmaxprice=0,appminprice=0,firepirce=0,appminarea=0,appmaxarea=0,firearea=0;

                                try {
                                   /* appmaxprice = Integer.parseInt(maxprice);
                                    appminprice = Integer.parseInt(minprice);
                                    appminarea = Integer.parseInt(maxarea);
                                    appmaxarea = Integer.parseInt(minarea);*/
                                    appmaxprice=3500000;
                                    appminprice=2000;
                                    appmaxarea=500;
                                    appmaxprice=200;
                                    firepirce = Integer.parseInt(child.child("PropertyPrice").getValue().toString());
                                    firearea = Integer.parseInt(child.child("PropertySize").getValue().toString());
                                } catch(NumberFormatException nfe) {
                                    System.out.println("Could not parse " + nfe);
                                }
                                if (!mCity.matches("") && child.child("City").getValue().equals(mCity)&&
                                        !minarea.matches("")&&!maxarea.matches("")&&firearea >=appminarea&&firearea<=appmaxarea&&
                                        !minprice.matches("")&&!maxprice.matches("")&&firepirce >=appminprice&&firepirce<=appmaxprice) {
                                    for (DataSnapshot single : child.getChildren()) {
                                        PropertyMap.put(single.getKey(), single.getValue().toString());
                                    }
                                }else if (!mCity.matches("") && child.child("City").getValue().equals(mCity)&&
                                        !minarea.matches("")&&!maxarea.matches("")&&firearea >=appminarea&&firearea<=appmaxarea&&
                                        minprice.matches("")&&maxprice.matches("")) {
                                    for (DataSnapshot single : child.getChildren()) {
                                        PropertyMap.put(single.getKey(), single.getValue().toString());
                                    }
                                }
                                else if (!mCity.matches("") && child.child("City").getValue().equals(mCity)&&
                                        minarea.matches("")&&maxarea.matches("")&&
                                        !minprice.matches("")&&!maxprice.matches("")&&firepirce >=appminprice&&firepirce<=appmaxprice) {
                                    for (DataSnapshot single : child.getChildren()) {
                                        PropertyMap.put(single.getKey(), single.getValue().toString());
                                    }
                                }else if (!mCity.matches("") && child.child("City").getValue().equals(mCity)&&
                                        minarea.matches("")&&maxarea.matches("")&&
                                        minprice.matches("")&&maxprice.matches("")) {
                                    for (DataSnapshot single : child.getChildren()) {
                                        PropertyMap.put(single.getKey(), single.getValue().toString());
                                    }
                                }else if (mCity.matches("") &&
                                        !minarea.matches("")&&!maxarea.matches("")&&firearea >=appminarea&&firearea<=appmaxarea&&
                                        !minprice.matches("")&&!maxprice.matches("")&&firepirce >=appminprice&&firepirce<=appmaxprice) {
                                    for (DataSnapshot single : child.getChildren()) {
                                        PropertyMap.put(single.getKey(), single.getValue().toString());
                                    }
                                }else if (mCity.matches("") &&
                                        !minarea.matches("")&&!maxarea.matches("")&&firearea >=appminarea&&firearea<=appmaxarea&&
                                        minprice.matches("")&&maxprice.matches("")) {
                                    for (DataSnapshot single : child.getChildren()) {
                                        PropertyMap.put(single.getKey(), single.getValue().toString());
                                    }
                                }else if (mCity.matches("") &&
                                        minarea.matches("")&&maxarea.matches("")&&
                                        minprice != null && maxprice != null &&!minprice.matches("")&&!maxprice.matches("")&&firepirce >=appminprice&&firepirce<=appmaxprice) {
                                    for (DataSnapshot single : child.getChildren()) {
                                        PropertyMap.put(single.getKey(), single.getValue().toString());
                                    }
                                }else if (mCity.matches("")
                                        &&minarea.matches("")&&maxarea.matches("")
                                        &&minprice.matches("")
                                        &&maxprice.matches("")){
                                    for (DataSnapshot single : child.getChildren()) {
                                        PropertyMap.put(single.getKey(), single.getValue().toString());
                                    }
                                }
                                if (!PropertyMap.isEmpty()) {
                                    notes.add(PropertyMap);
                                    PropertyMap = new HashMap<>();
                                }
                            }
                        }


                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getActivity(), "Some thing went wrong "+databaseError.toException(), Toast.LENGTH_SHORT).show();
                    }
                });
                mRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                MovetoRecyclerActivity(dataSnapshot.getChildrenCount());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
            }
        });
    }
    private void MovetoRecyclerActivity(long a){
        mUploadProgress.dismiss();
        if (getContext() != null) {
            Intent next = new Intent(getActivity(), Recycler_Property_Activity.class);
            next.putExtra("odata", notes);
            startActivity(next);
        }
    }
    private void setAreaRange(View layout) {

        RangeSeekBar r= (RangeSeekBar)layout.findViewById(R.id.rangeSeekBar1);

        r.setRangeValues(0,6000);
        mseekvaluemax1=(TextView)layout.findViewById(R.id.seekValuemax1);
        mseekvaluemin1=(TextView)layout.findViewById(R.id.seekValuemin1);


        r.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {

            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {



                if(Integer.parseInt(maxValue.toString()) == 6000)
                {
                    maxarea=maxValue.toString()+"+";

                }

                if(Integer.parseInt(minValue.toString()) == 6000)
                {
                    minarea=maxValue.toString()+"+";
                }

                mseekvaluemin1.setText( minValue.toString());
                mseekvaluemax1.setText(maxValue.toString());
                minarea=minValue.toString();
                maxarea=maxValue.toString();

                // Toast.makeText(getActivity(),"Max" + bar.getAbsoluteMaxValue().toString() +"Min"+  bar.getAbsoluteMinValue().toString(),Toast.LENGTH_SHORT).show();
                //  r.resetSelectedValues();


            }
        });

    }

    public void setvaluerange(View layout) {


        RangeSeekBar r = (RangeSeekBar) layout.findViewById(R.id.rangeSeekBar);

        r.setRangeValues(0, 20);
        mseekvaluemax = (TextView) layout.findViewById(R.id.seekValuemax);
        mseekvaluemin = (TextView) layout.findViewById(R.id.seekValuemin);


        r.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {

            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {


                //   mseekvaluemin.setText(minValue.toString());
                //  mseekvaluemax.setText(maxValue.toString() + "+Crores");
                {

                    if (Integer.parseInt(maxValue.toString()) == 20) {
                        mseekvaluemax.setText("50+" + "Crores");
                        maxprice="500000000";
                    } else if (Integer.parseInt(maxValue.toString()) == 19) {
                        mseekvaluemax.setText("40" + "Crores");
                        maxprice="400000000";
                    } else if (Integer.parseInt(maxValue.toString())== 18) {
                        mseekvaluemax.setText("30" + "Crores");
                        maxprice="300000000";
                    } else if (Integer.parseInt(maxValue.toString()) == 17) {
                        mseekvaluemax.setText("20" + "Crores");
                        maxprice="200000000";
                    } else if (Integer.parseInt(maxValue.toString()) == 16) {
                        mseekvaluemax.setText("15" + "Crores");
                        maxprice="150000000";
                    } else if (Integer.parseInt(maxValue.toString()) == 15) {
                        mseekvaluemax.setText("10" + "Crores");
                        maxprice="100000000";
                    } else if (Integer.parseInt(maxValue.toString()) == 14) {
                        mseekvaluemax.setText("7" + "Crores");
                        maxprice="70000000";
                    } else if (Integer.parseInt(maxValue.toString()) == 13) {
                        mseekvaluemax.setText("2.5" + "Crores");
                        maxprice="25000000";
                    } else if (Integer.parseInt(maxValue.toString()) == 12) {
                        mseekvaluemax.setText("1" + "Crore");
                        maxprice="10000000";
                    } else if (Integer.parseInt(maxValue.toString()) == 11) {
                        mseekvaluemax.setText("90" + "Lacs");
                        maxprice="9000000";
                    } else if (Integer.parseInt(maxValue.toString())== 10) {
                        mseekvaluemax.setText("80" + "Lacs");
                        maxprice="8000000";
                    } else if (Integer.parseInt(maxValue.toString()) == 9) {
                        mseekvaluemax.setText("70" + "Lacs");
                        maxprice="7000000";
                    } else if (Integer.parseInt(maxValue.toString()) == 8) {
                        mseekvaluemax.setText("60" + "Lacs");
                        maxprice="6000000";
                    } else if (Integer.parseInt(maxValue.toString()) == 7) {
                        mseekvaluemax.setText("50" + "Lacs");
                        maxprice="5000000";
                    } else if (Integer.parseInt(maxValue.toString()) == 6) {
                        mseekvaluemax.setText("40" + "Lacs");
                        maxprice="4000000";
                    } else if (Integer.parseInt(maxValue.toString()) == 5) {
                        mseekvaluemax.setText("35" + "Lacs");
                        maxprice="3500000";
                    } else if (Integer.parseInt(maxValue.toString()) == 4) {
                        mseekvaluemax.setText("30" + "Lacs");
                        maxprice="3000000";
                    } else if (Integer.parseInt(maxValue.toString())== 3) {
                        mseekvaluemax.setText("25" + "Lacs");
                        maxprice="2500000";
                    } else if (Integer.parseInt(maxValue.toString())== 2) {
                        mseekvaluemax.setText("20" + "Lacs");
                        maxprice="2000000";
                    } else if (Integer.parseInt(maxValue.toString()) == 1) {
                        mseekvaluemax.setText("10" + "Lacs");
                        maxprice="1000000";
                    } else if (Integer.parseInt(maxValue.toString()) == 0) {
                        mseekvaluemax.setText("0");
                    }
                }

                {

                    if (Integer.parseInt( minValue.toString()) == 20) {
                        mseekvaluemin.setText("50+" + "Crores");
                        maxprice="500000000";
                    } else if (Integer.parseInt( minValue.toString())  == 19) {
                        mseekvaluemin.setText("45" + "Crores");
                        maxprice="450000000";
                    } else if (Integer.parseInt( minValue.toString())  == 18) {
                        mseekvaluemin.setText("35" + "Crores");
                        maxprice="350000000";

                    } else if (Integer.parseInt( minValue.toString())  == 17) {
                        mseekvaluemin.setText("25" + "Crores");
                        maxprice="250000000";
                    } else if (Integer.parseInt( minValue.toString())  == 16) {
                        mseekvaluemin.setText("12.5" + "Crores");
                        maxprice="125000000";
                    } else if (Integer.parseInt( minValue.toString()) == 15) {
                        mseekvaluemin.setText("6" + "Crores");
                        maxprice="60000000";
                    } else if (Integer.parseInt( minValue.toString()) == 14) {
                        mseekvaluemin.setText("3.5" + "Crores");
                        maxprice="35000000";
                    } else if (Integer.parseInt( minValue.toString()) == 13) {
                        mseekvaluemin.setText("2" + "Crores");
                        maxprice="20000000";
                    } else if (Integer.parseInt( minValue.toString()) == 12) {
                        mseekvaluemin.setText("1.5" + "Crore");
                        maxprice="15000000";
                    } else if (Integer.parseInt( minValue.toString())  == 11) {
                        mseekvaluemin.setText("95" + "Lacs");
                        maxprice="9500000";
                    } else if (Integer.parseInt( minValue.toString())  == 10) {
                        mseekvaluemin.setText("85" + "Lacs");
                        maxprice="8500000";
                    } else if (Integer.parseInt( minValue.toString()) == 9) {
                        mseekvaluemin.setText("75" + "Lacs");
                        maxprice="7500000";
                    } else if (Integer.parseInt( minValue.toString()) == 8) {
                        mseekvaluemin.setText("65" + "Lacs");
                        maxprice="6500000";
                    } else if (Integer.parseInt( minValue.toString()) == 7) {
                        mseekvaluemin.setText("55" + "Lacs");
                        maxprice="5500000";
                    } else if (Integer.parseInt( minValue.toString()) == 6) {
                        mseekvaluemin.setText("45" + "Lacs");
                        maxprice="4500000";
                    } else if (Integer.parseInt( minValue.toString()) == 5) {
                        mseekvaluemin.setText("38" + "Lacs");
                        maxprice="3800000";
                    } else if (Integer.parseInt( minValue.toString()) == 4) {
                        mseekvaluemin.setText("35" + "Lacs");
                        maxprice="3500000";
                    } else if (Integer.parseInt( minValue.toString()) == 3) {
                        mseekvaluemin.setText("23" + "Lacs");
                        maxprice="2300000";
                    } else if (Integer.parseInt( minValue.toString())== 2) {
                        mseekvaluemin.setText("15" + "Lacs");
                        maxprice="1500000";
                    } else if (Integer.parseInt( minValue.toString())  == 1) {
                        mseekvaluemin.setText("5" + "Lacs");
                        maxprice="500000";
                    } else if (Integer.parseInt( minValue.toString()) == 0) {
                        mseekvaluemin.setText("0");
                    }
                }


                // Toast.makeText(getActivity(), "Max" + bar.getAbsoluteMaxValue().toString() + "Min" + bar.getAbsoluteMinValue().toString(), Toast.LENGTH_SHORT).show();
                //  r.resetSelectedValues();


            }
        });
    }
    private void typecasting(View viewfragment){
        citysearch=(AutoCompleteTextView) viewfragment.findViewById(R.id.comercialcity);
        mSearchButton=(Button) viewfragment.findViewById(R.id.OwnerButton);
        propertylayout=(LinearLayout) viewfragment.findViewById(R.id.buycomproperty);

    }

}
