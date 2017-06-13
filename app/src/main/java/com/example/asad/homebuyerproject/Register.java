package com.example.asad.homebuyerproject;

/**
 * Created by Asad on 10/9/2016.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import static java.security.AccessController.getContext;

import com.hbb20.CountryCodePicker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


//Calling when register button is clicked xml:activity_register
public class Register extends AppCompatActivity {


    private Button mRegister, mlogin;
        private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    private TextView mName, mEmail, mPassword, mPhone, email_id, Name;
    private Boolean mgetValue;
    private TextView mTerms;
    private static final String TAG = "MainActivity";
    private String auth_failed = "Error";
    private TextView mOwner, mAgent;
    private String SelectedType;
    private AutoCompleteTextView mcity;
    //private RelativeLayout mDealIn;
    private EditText mCompanyname, mCompanydetail, mCompanyAddress;
    private Button mLogoButton;
    private ImageView mImageLayout;
    private static int LOAD_IMAGE_RESULTS = 1;
    private ProgressDialog loginprogress;

    private CoordinatorLayout coordinatorLayout;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    private CountryCodePicker ccp;
    private String ccp1;
    boolean msetvalue = false;


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        loginprogress = new ProgressDialog(Register.this);
        //Calling Methods
        TypeCasting();
        TypeSelectedEvent();
        RegisterButtonClick();
        LoginButtonClick();
        CountryCode();
        ArrayAdapterMethod();  //autocomplete cities
        invisibleelements();
        LogoButtonEvent();
        //Dtabase reference and storage
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        //Datebase reference
        mAuth = FirebaseAuth.getInstance();


        //For current users state
        //For user state weather user is logged in or not
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }

        };


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void LogoButtonEvent() {

        mLogoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mImageLayout.setVisibility(View.VISIBLE);
                // Create the Intent for Image Gallery.
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start new activity with the LOAD_IMAGE_RESULTS to handle back the results when image is picked from the Image Gallery.
                startActivityForResult(i, LOAD_IMAGE_RESULTS);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOAD_IMAGE_RESULTS && resultCode == RESULT_OK && data != null) {
            Uri pickedImage = data.getData();
            PicassoClient.downloadImage(getApplicationContext(),pickedImage.toString(),mImageLayout,R.drawable.placeholder,0.5f);
        }
    }
    private void visibleelements() {


        mCompanydetail.setVisibility(View.VISIBLE);
        mcity.setVisibility(View.VISIBLE);
        mCompanyname.setVisibility(View.VISIBLE);
        mCompanyAddress.setVisibility(View.VISIBLE);
        mLogoButton.setVisibility(View.VISIBLE);
       // mDealIn.setVisibility(View.VISIBLE);
      //  mImageLayout.setVisibility(View.VISIBLE);

    }

    private void invisibleelements() {

        mCompanydetail.setVisibility(View.GONE);
        mcity.setVisibility(View.GONE);
        mCompanyname.setVisibility(View.GONE);
        mCompanyAddress.setVisibility(View.GONE);
        mLogoButton.setVisibility(View.GONE);
        mImageLayout.setVisibility(View.GONE);
       // mDealIn.setVisibility(View.INVISIBLE);


    }

    private void TypeSelectedEvent() {


        mAgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAgent.setBackgroundColor(Color.parseColor("#01579B"));
                mOwner.setBackgroundColor(Color.parseColor("#FFFFFF"));
                //mBuilder.setBackgroundColor(Color.parseColor("#FFFFFF"));

                mAgent.setTextColor(Color.parseColor("#FFFFFF"));
                mOwner.setTextColor(Color.parseColor("#616161"));
               // mBuilder.setTextColor(Color.parseColor("#616161"));
                visibleelements();

                SelectedType = "Agent";

            }
        });
        mOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAgent.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mOwner.setBackgroundColor(Color.parseColor("#01579B"));
               // mBuilder.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mOwner.setTextColor(Color.parseColor("#FFFFFF"));
                mAgent.setTextColor(Color.parseColor("#616161"));
               // mBuilder.setTextColor(Color.parseColor("#616161"));
                invisibleelements();
                SelectedType = "User";


            }
        });
      /*  mBuilder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAgent.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mOwner.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mBuilder.setBackgroundColor(Color.parseColor("#01579B"));
                mBuilder.setTextColor(Color.parseColor("#FFFFFF"));
                mOwner.setTextColor(Color.parseColor("#616161"));
                mAgent.setTextColor(Color.parseColor("#616161"));
                invisibleelements();
                SelectedType = "Builder";

            }
        });*/
    }
    private void ArrayAdapterMethod() {
        ArrayAdapter adapter = new ArrayAdapter(Register.this, android.R.layout.simple_list_item_1, languages);
        mcity.setAdapter(adapter);
        mcity.setThreshold(1);
        mcity.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                InputMethodManager in = (InputMethodManager) Register.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(Register.this.getCurrentFocus().getWindowToken(), 0);

            }

        });
    }
    private void CountryCode() {
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        try {
            ccp1 = ccp.getDefaultCountryCodeWithPlus();
            ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
                @Override
                public void onCountrySelected() {

                    ccp1 = ccp.getSelectedCountryCodeWithPlus();
                }
            });
        } catch (Exception ex) {

        }
    }
    @Override
    public void onStart() {
        super.onStart();
        client.connect();
        mAuth.addAuthStateListener(mAuthListener);
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
        client.disconnect();
    }

    //TypeCastin is done here
    public void TypeCasting() {


        mCompanyname = (EditText) findViewById(R.id.Company_name);
        mCompanydetail = (EditText) findViewById(R.id.Company_detail);
        mcity = (AutoCompleteTextView) findViewById(R.id.city);
        mCompanyAddress = (EditText) findViewById(R.id.Company_address);
        mLogoButton = (Button) findViewById(R.id.LogoButton);
        mImageLayout=(ImageView)findViewById(R.id.Logo);
        //mDealIn=(RelativeLayout)findViewById(R.id.DealIn);

        //come in use when user is signed in
        mAgent = (TextView) findViewById(R.id.radio1);
        mOwner = (TextView) findViewById(R.id.radio0);
        //mBuilder = (TextView) findViewById(R.id.radio1);

        mName = (TextView) findViewById(R.id.nameRegister);
        mPhone = (TextView) findViewById(R.id.mobile);
        mEmail = (TextView) findViewById(R.id.email);
        mPassword = (TextView) findViewById(R.id.password);
        mRegister = (Button) findViewById(R.id.btnRegister);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);

        // String code = ccp.getSelectedCountryCodeWithPlus();
        //   Toast.makeText(Register.this,code,Toast.LENGTH_SHORT).show();
    }


    //Validate the data
    public boolean ValidateRegisterData() {

        try {
            boolean valid = Validate();
            //successful Validation
            if (valid) {
                msetvalue = true;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return msetvalue;
    }

    public boolean Validate() {


        msetvalue = true;

        if (!isValidMail(mEmail.getText().toString())) {
            mEmail.setError("Invalid Email Address");
            msetvalue = false;
        }

        else if (mName.getText().toString().equals("")) {
            System.out.println(mName.getText().toString());
            mName.setError("Name Should not be Empty");
            msetvalue = false;
        }
        else if (mPhone.getText().toString().length() != 11) {

            mPhone.setError("Enter Valid Number");
            msetvalue = false;
        }
        else if (!isValidPassword(mPassword.getText().toString())) {
            mPassword.setError("Try Upper/Lower Case and Numbers");
            return msetvalue = false;
        }


            if (SelectedType != null) {

                //  Toast.makeText(getApplicationContext(), "run", Toast.LENGTH_SHORT).show();

            } else {

                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Please Select Type", Snackbar.LENGTH_LONG)
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

                msetvalue = false;
            }
            if (SelectedType !=null || SelectedType.isEmpty() && SelectedType.equals("Agent")){
                if (mcity.getText().toString().equals("")) {
                    mcity.setError("City Should not be Empty");
                    msetvalue = false;
                }
            }




        return msetvalue;
    }

    private boolean isValidName(String name) {

        Pattern pattern;
        Matcher matcher;
        final String NAME_PATTERN = "(?=.*[a-z])(?=.*[A-Z]).+$";
        pattern = Pattern.compile(NAME_PATTERN);
        matcher = pattern.matcher(name);

        return name.equals("");
    }


    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }


    //Register button click event
    public void RegisterButtonClick() {
        mRegister.setOnClickListener((new View.OnClickListener() {
            public void onClick(View v) {
                //Calling Validate Function
                mgetValue = ValidateRegisterData();
                try {
                    if (mgetValue) {
                        loginprogress.setMessage("Registering Please Wait ..");
                        loginprogress.show();
                        loginprogress.setCancelable(false);
                        loginprogress.setCanceledOnTouchOutside(false);
                        //if validation is successful login user
                        //Calling method to save registration details
                        FirebaseSaveRegistrationData();
                    }
                } catch (Exception ex) {
                    Toast.makeText(Register.this, "Error Occured Validating Data", Toast.LENGTH_SHORT).show();
                }
            }
        }));
    }


    //Firebase save registration detail of users

    public void FirebaseSaveRegistrationData() {

        final String mName1, mEmail1, mPassword1, mPhone1, mPhoneFinal;

        mName1 = mName.getText().toString();
        mEmail1 = mEmail.getText().toString();
        mPassword1 = mPassword.getText().toString();
        mPhone1 = mPhone.getText().toString().charAt(0) == 0 ? mPhone.getText().toString() :mPhone.getText().toString().substring(1);
        mPhoneFinal = ccp1.concat("-").concat(mPhone1);
        mAuth.createUserWithEmailAndPassword(mEmail1, mPassword1)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        try {

                            String user_id = mAuth.getCurrentUser().getUid();
                            //save user data along with its id
                            DatabaseReference current_user_db = mDatabase.child(user_id);
                            current_user_db.child("Name").setValue(mName1);
                            current_user_db.child("Phone").setValue(mPhoneFinal);
                            current_user_db.child("UserType").setValue(SelectedType);
                            if (SelectedType.equals("Agent") ){
                                current_user_db.child("AgentCity").setValue(mcity.getText().toString());

                                loginprogress.dismiss();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(Register.this, "Error Saving Data Please Try Again", Toast.LENGTH_SHORT).show();
                            ex.printStackTrace();
                        }
                        //After signup the user is pushed to signed out
                        mAuth.signOut();
                        Intent intent = new Intent(getApplication(), LoginRegister.class);
                        startActivity(intent);

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            loginprogress.dismiss();
                            Toast.makeText(Register.this, "Authentication Failed Please Retry", Toast.LENGTH_SHORT).show();
                        }


                    }
                });


    }


    //Login Button Click Event
    public void LoginButtonClick() {


        mlogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
        mlogin.setOnClickListener((new View.OnClickListener() {
            public void onClick(View v) {

                //Calling Login Activity
                Intent intent = new Intent(getApplication(), LoginRegister.class);
                startActivity(intent);
            }
        }));
    }

    //Validation on Email
    private boolean isValidMail(String email) {

        try {
            Patterns.EMAIL_ADDRESS.matcher(email).matches();
        } catch (Exception ex) {

        }
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       /* getMenuInflater().inflate(R.menu.menu_register, menu);
        */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Register Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }
}
