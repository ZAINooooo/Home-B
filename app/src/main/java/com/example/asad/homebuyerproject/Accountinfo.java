package com.example.asad.homebuyerproject;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Accountinfo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Accountinfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Accountinfo extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    private String mParam1;
    private String mParam2;
    private TextView mName, mCity, mEmail, mContact, mType;
    private ValueEventListener mPostListener;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    // private String termsandcondition;

    private DatabaseReference mDatabase;
    private int i;
    String user;
    private String[]name =new String[3];


    private OnFragmentInteractionListener mListener;

    public Accountinfo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Accountinfo.
     */
    // TODO: Rename and change types and number of parameters
    public static Accountinfo newInstance(String param1, String param2) {
        Accountinfo fragment = new Accountinfo();
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

        Firebase.setAndroidContext(getActivity());
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_accountinfo, container, false);
        Typecasting(layout);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
               //    user= user.getUid().toString();
                 //   Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                  //  Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        LoadData();


        return layout;
    }


    private void LoadData() {
        final FirebaseUser user_id;
        final String copy;
        mAuth = FirebaseAuth.getInstance();
        DatabaseReference mdatabase;
        mdatabase = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference mRef = mdatabase.child("Users").child(mAuth.getCurrentUser().getUid());
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
               String name ="",Contact = "" , Type ="" , City = "";
                    try {
                        if (dataSnapshot.child("Name") != null)
                            name = dataSnapshot.child("Name").getValue().toString();
                        if (dataSnapshot.child("Phone") != null)
                            Contact = dataSnapshot.child("Phone").getValue().toString();
                        if (dataSnapshot.child("UserType") != null)
                            Type = dataSnapshot.child("UserType").getValue().toString();
                        if (dataSnapshot.child("AgentCity") != null)
                            City = dataSnapshot.child("AgentCity").getValue().toString();
                        mName.setText(name);
                        mContact.setText(Contact);
                        mType.setText(Type);
                        mEmail.setText(mAuth.getCurrentUser().getEmail());
                        mCity.setText(City);
                        mRef.removeEventListener(this);

                    }catch (Exception ex)
                    {
                        Toast.makeText(getActivity(), "Error Downloading information try Again Later", Toast.LENGTH_SHORT).show();
                        ex.printStackTrace();
                    }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
}

    private void Typecasting(View layout) {
        mName = (TextView) layout.findViewById(R.id.name);
        mEmail = (TextView) layout.findViewById(R.id.email);
        mType = (TextView) layout.findViewById(R.id.password);
        mCity = (TextView) layout.findViewById(R.id.city);
        mContact = (TextView) layout.findViewById(R.id.contact);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
