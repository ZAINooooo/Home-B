package com.example.asad.homebuyerproject;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Update_ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Update_ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Update_ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText mnewPassword, moldPassword;
    private Button mupdateButton;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FirebaseAuth mAuth;
    private ProgressDialog progressBar;
    private int progressBarStatus = 0;

    private CoordinatorLayout coordinatorLayout;
    private Handler progressBarbHandler = new Handler();

    private OnFragmentInteractionListener mListener;

    public Update_ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Update_ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Update_ProfileFragment newInstance(String param1, String param2) {
        Update_ProfileFragment fragment = new Update_ProfileFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update__profile, container, false);

        TypeCasting(view);


        mupdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                final String password = mnewPassword.getText().toString();
                final String oldPassword = moldPassword.getText().toString();

                if (isValidPassword(password) && isValidPassword(oldPassword)) {
                    assert user != null;
                    String email = user.getEmail().toString();
                    AuthCredential credential = EmailAuthProvider.getCredential(email, oldPassword);
                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            assert user != null;
                            user.updatePassword(password)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                FirebaseAuth.getInstance().signOut();
                                                Intent intent = new Intent(getActivity(), HomeActivity.class);
                                                startActivity(intent);
                                            } else {
                                                Snackbar snackbar = Snackbar
                                                        .make(coordinatorLayout, "Please fill valid password", Snackbar.LENGTH_LONG)
                                                        .setAction("Ok", new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View view) {


                                                            }
                                                        });
                                                // Changing message text color
                                                snackbar.setActionTextColor(Color.RED);
                                                // Changing action button text color
                                                View sbView = snackbar.getView();
                                                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                                                textView.setTextColor(Color.YELLOW);

                                                snackbar.show();
                                            }
                                        }
                                    });
                        }


                    });
                }
            }
        });


        return view;
    }


    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
    private void TypeCasting(View view) {
        mnewPassword = (EditText) view.findViewById(R.id.newPassword);
        mupdateButton = (Button) view.findViewById(R.id.updateButton);
        moldPassword = (EditText) view.findViewById(R.id.oldPassword);
        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id
                .coordinatorLayout);
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
