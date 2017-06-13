package com.example.asad.homebuyerproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import nl.changer.polypicker.Config;
import nl.changer.polypicker.ImagePickerActivity;

public class Additional_Detail_Activity extends AppCompatActivity  {

    private ImageView mUploadPhotos, mUploadPhotos12;
    private ArrayList<String> propertyData=new ArrayList<>();
    private ArrayList<String> Amenities=new ArrayList<>();
    private ArrayList<String> Tenent=new ArrayList<>();
    private ArrayList<String> Videopath=new ArrayList<>();
    private HashMap<String, String> PropertyMap = new HashMap<String, String>();
    private  ArrayList<Bitmap> captureimage=new ArrayList<Bitmap>();
    private ArrayList<Bitmap>imagebitmap=new ArrayList<Bitmap>();
    private ArrayList<String> mDataDownloadURL=new ArrayList<String>();
    private List<String> mAgents = new ArrayList<String>();
    private List<String> mAgentsID = new ArrayList<String>();
    private EditText directionFacing,additionaldetail;

    private Button Addtodatabase;
    private LinearLayout layout1,layout2,layout3,layout4;
    private DatabaseReference mDatabase,mUploadDatabase;
    private FirebaseAuth mAuth;
    private RelativeLayout layotphoto;
    private Button photoselect;
    private Spinner AgentDropDown;
    private int REQUEST_VIDEO = 0, INTENT_REQUEST_GET_IMAGES=2;
    private int uploadcount=0,videoupload=0;
    private String userChoosenTask;
    private boolean addAllimage=false;
    private Boolean photosuploaded = false,videouploaded = false;

    private StorageReference mStorage;
    private ProgressDialog mUploadProgress;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment__additional__detail);
        Bundle extras = getIntent().getExtras();
        if(extras==null){
            Toast.makeText(Additional_Detail_Activity.this, "Previous Data Not Found", Toast.LENGTH_SHORT).show();
        }else {
            propertyData = extras.getStringArrayList("data");

        }Firebase.setAndroidContext(this);
        mStorage = FirebaseStorage.getInstance().getReference();
        mUploadProgress=new ProgressDialog(Additional_Detail_Activity.this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
       mUploadDatabase=mDatabase.child("Property").child(propertyData.get(propertyData.indexOf("SellType")+1)).
                child(propertyData.get(propertyData.indexOf("propertyType")+1)).
                child(propertyData.get(propertyData.indexOf("propertycatagory")+1)).
                push();
        mAuth = FirebaseAuth.getInstance();
        Typecasting();
        PostPropertyClickEvent();
        UploadPhotosEvent();
        getAgents();

    }

    private void getAgents() {
        DatabaseReference mAgentDatabase= FirebaseDatabase.getInstance().getReference();
        mAgentDatabase.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                   if (child.child("UserType").getValue() != null &&  child.child("AgentCity").getValue() != null &&
                            child.child("UserType").getValue().toString().equals("Agent") &&
                            child.child("AgentCity").getValue().toString()
                                    .equals(propertyData.get(propertyData.indexOf("City") + 1))) {
                        mAgents.add(child.child("Name").getValue().toString()+" , "+child.child("AgentCity").getValue().toString());
                        mAgentsID.add(child.getKey().toString());
                    }
                }
                SetSpinnerApapter();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void SetSpinnerApapter(){
       if (mAgents.isEmpty()){
           mAgents.add("No Agent Available");
       }
        ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, mAgents);
        adapter.setDropDownViewResource(R.layout.spinner_style);
        AgentDropDown.setAdapter(adapter);

        AgentDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

               if (!mAgentsID.isEmpty()){
                   if (PropertyMap.containsKey("DealingAgent")){
                      PropertyMap.remove("DealingAgent");
                       PropertyMap.put("DealingAgent",mAgentsID.get(i));
                   }
                   else{
                       PropertyMap.put("DealingAgent",mAgentsID.get(i));
                   }
               }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void UploadPhotosEvent() {

        photoselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch (requestCode)
        {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if(userChoosenTask.equals("Take Photo OR Select From Library"))
                        Imagesintent();
                    else if(userChoosenTask.equals("Take Video"))
                        galleryIntent();
                }
                else
                {
                    //code for deny
                }
                break;
        }
    }


    private void selectImage()
    {
        final CharSequence[] items =
                {
                        "Take Photo OR Select From Library", "Take Video", "Cancel"
                };


        AlertDialog.Builder builder = new AlertDialog.Builder(Additional_Detail_Activity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item)
            {
                boolean result=Utility.checkPermission(Additional_Detail_Activity.this);
                if (items[item].equals("Take Photo OR Select From Library"))
                {
                    userChoosenTask ="Take Photo OR Select From Library";
                    if(result)
                        Imagesintent();
                }
                else if (items[item].equals("Take Video"))
                {
                    userChoosenTask ="Take Video";
                    if(result)
                        galleryIntent();
                }

                else if (items[item].equals("Cancel"))
                {
                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 7);
        intent.putExtra(android.provider.MediaStore.EXTRA_VIDEO_QUALITY, 0);
        startActivityForResult(intent,REQUEST_VIDEO);

    }

    private void Imagesintent()
    {
        Context mContext=getApplicationContext();
        Intent intent = new Intent(mContext, ImagePickerActivity.class);
        Config config = new Config.Builder()
                .setTabBackgroundColor(R.color.white)    // set tab background color. Default white.
                .setTabSelectionIndicatorColor(R.color.blue)
                .setCameraButtonColor(R.color.green)
                .setSelectionLimit(5)    // set photo selection limit. Default unlimited selection.
                .build();
        ImagePickerActivity.setConfig(config);
        startActivityForResult(intent, INTENT_REQUEST_GET_IMAGES);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == INTENT_REQUEST_GET_IMAGES) {
                Parcelable[] parcelableUris = data.getParcelableArrayExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);

                if (parcelableUris == null) {
                    return;
                }
                // Java doesn't allow array casting, this is a little hack
                Uri[] uris = new Uri[parcelableUris.length];
                System.arraycopy(parcelableUris, 0, uris, 0, parcelableUris.length);
                Context mContext=getApplicationContext();
                if (uris != null) {
                    if (addAllimage) {
                        addAllimage = false;
                        captureimage.clear();
                    }for (Uri uri : uris) {
                        try {
                            Bitmap bmp=getThumbnail(Uri.parse("file://"+uri),mContext);
                            imagebitmap.add(bmp);
                            addImagesToThegallery(bmp);
                            //captureimage.add(bmp);
                        }
                        catch (IOException e){
                            Toast.makeText(this, "Uri is" +e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.i("UriData", " uri: " + uri);
                        }

                    }
                  //  addImagesToThegallery(captureimage);

                }
            }
            if (requestCode == REQUEST_VIDEO) {
                if (data !=null)
                    try{
                        Uri vid = data.getData();
                        String path = getRealPathFromURI(getApplicationContext(),vid);
                        Videopath.add(path);
                        LinearLayout imageGallery = (LinearLayout) findViewById(R.id.imageGallery);
                        imageGallery.addView(getImageView(createVideoThumbNail(path)));

                    }
                    catch (Exception e){
                        Toast.makeText(this, "Sorry Something went Wrong" +e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.i("UriData", " uri: " + e.getMessage());
                    }
            }

        }
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public Bitmap createVideoThumbNail(String path){
        return ThumbnailUtils.createVideoThumbnail(path,MediaStore.Video.Thumbnails.MINI_KIND);
    }
    private void addImagesToThegallery(Bitmap image){//(ArrayList<Bitmap> galimg) {
        LinearLayout imageGallery = (LinearLayout) findViewById(R.id.imageGallery);
        addAllimage=true;
        //for (Bitmap image : galimg) {

            imageGallery.addView(getImageView(image));
        //}
    }
    private View getImageView(Bitmap image) {
        ImageView imageView = new ImageView(getApplicationContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(0, 0, 1, 0);
        imageView.setLayoutParams(lp);
        imageView.setImageBitmap(image);
        return imageView;
    }

    public  Bitmap getThumbnail(Uri uri , Context con) throws FileNotFoundException, IOException{
        int maxImageSize=512;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = calculateInSampleSize(bitmapOptions,120,120);
        bitmapOptions.inDither=true;//optional
        bitmapOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
        InputStream input = con.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
               Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
        float ratio1 = Math.min(
                (float) maxImageSize / decoded.getWidth(),
                (float) maxImageSize / decoded.getHeight());
        int width = Math.round((float) ratio1 * decoded.getWidth());
        int height = Math.round((float) ratio1 * decoded.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(decoded, width,
                height, true);
        bitmap = null;
        decoded = null;
        return newBitmap;
    }
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
    private static int getPowerOfTwoForSampleRatio(double ratio){
        int k = Integer.highestOneBit((int)Math.floor(ratio));
        if(k==0) return 1;
        else return k;
    }


    private void PostPropertyClickEvent() {

        Addtodatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUploadProgress.setMessage("Uploading Data Please Wait ..");
                mUploadProgress.show();
                mUploadProgress.setCancelable(false);
                mUploadProgress.setCanceledOnTouchOutside(false);
                if (!imagebitmap.isEmpty()) {
                    for (Bitmap bm : imagebitmap
                            ) {
                        uploadphoto(bm);
                    }
                }else{
                    photosuploaded=true;
                }
                if (!Videopath.isEmpty()){
                    for (String vidpath: Videopath){
                        uploadVideo(vidpath);
                    }
                }else{
                    videouploaded=true;
                }
                if (videouploaded && photosuploaded){
                    UploadStringdata();
                }
    }
        });
    }
        private void uploadphoto(Bitmap bitmap){

            String uuid = UUID.randomUUID().toString();
            Firebase.setAndroidContext(Additional_Detail_Activity.this);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                StorageReference filepath=mStorage.child("Photos").
                        child(uuid+".png");
                filepath.putBytes(baos.toByteArray()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        uploadcount++;
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        mDataDownloadURL.add(downloadUrl.toString());
                        if (uploadcount==imagebitmap.size()){
                            photosuploaded=true;
                        }
                        if (videouploaded && photosuploaded){
                            UploadStringdata();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mUploadProgress.dismiss();
                        Toast.makeText(Additional_Detail_Activity.this, "Something Went Wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        }
        private void uploadVideo(String Path){

                    String uuid1 = UUID.randomUUID().toString();
                    Uri file = Uri.fromFile(new File(Path));
                    StorageReference vidfilepath=mStorage.child("video").
                            child(uuid1+".mp4");
                    vidfilepath.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            videoupload++;
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            mDataDownloadURL.add(downloadUrl.toString());

                            if (videoupload==Videopath.size()){
                              videouploaded=true;
                            }
                            if (videouploaded && photosuploaded){
                                UploadStringdata();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mUploadProgress.dismiss();
                            Toast.makeText(Additional_Detail_Activity.this, "Something Went Wrong"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }


        private void UploadStringdata(){
                if (!directionFacing.getText().toString().matches("")){
                    propertyData.add("PropertyDirection");
                    propertyData.add(directionFacing.getText().toString());
                }
                if (!additionaldetail.getText().toString().matches("")){
                    propertyData.add("PropertyAdditionDetail");
                    propertyData.add(additionaldetail.getText().toString());
                }
                int count = layout1.getChildCount();
                View checkboxview = null;
                for(int i=0; i<count; i++) {
                    checkboxview = layout1.getChildAt(i);
                    boolean checked = ((CheckBox) checkboxview).isChecked();
                    if (checked){
                        Amenities.add(((CheckBox) checkboxview).getText().toString());
                    }
                }
                int count2 = layout2.getChildCount();
                View checkboxview2 = null;
                for(int i=0; i<count2; i++) {
                    checkboxview2 = layout2.getChildAt(i);
                    boolean checked = ((CheckBox) checkboxview2).isChecked();
                    if (checked){
                        Amenities.add(((CheckBox) checkboxview2).getText().toString());
                    }
                }
                int count3 = layout3.getChildCount();
                View checkboxview3 = null;
                for(int i=0; i<count3; i++) {
                    checkboxview3 = layout3.getChildAt(i);
                    boolean checked = ((CheckBox) checkboxview3).isChecked();
                    if (checked){
                        Amenities.add(((CheckBox) checkboxview3).getText().toString());
                    }
                }int count4 = layout4.getChildCount();
                View checkboxview4 = null;
                for(int i=0; i<count4; i++) {
                    checkboxview4 = layout4.getChildAt(i);
                    boolean checked = ((CheckBox) checkboxview4).isChecked();
                    if (checked){
                        Amenities.add(((CheckBox) checkboxview4).getText().toString());
                    }
                }
            String uuid1 = UUID.randomUUID().toString();
            if (!propertyData.isEmpty()){
                for (int i=0;i<propertyData.size();i=i+2){
                    PropertyMap.put(propertyData.get(i),propertyData.get(i+1));
                }
                PropertyMap.put("UserID",mAuth.getCurrentUser().getUid());
                mUploadDatabase.setValue(PropertyMap);
            }
            if (!Amenities.isEmpty()){
                mUploadDatabase.child("Amentities").setValue(Amenities);
            }
            /*if (!Tenent.isEmpty()){
                mUploadDatabase.child("Tenent").setValue(Tenent);
            }*/
            if (!mDataDownloadURL.isEmpty()){
                mUploadDatabase.child("GaleryData").setValue(mDataDownloadURL);
            }
                mUploadProgress.dismiss();
                Intent next = new Intent(Additional_Detail_Activity.this, HomeActivity.class);
                startActivity(next);
}

    private void Typecasting() {

     //   mUploadPhotos = (ImageView) findViewById(R.id.Upload_Photos);
        directionFacing=(EditText)findViewById(R.id.DirectionFacingResult);
        additionaldetail=(EditText)findViewById(R.id.AddMoreDetailsResult);
        Addtodatabase=(Button)findViewById(R.id.Readytogo);
        layout1=(LinearLayout)findViewById(R.id.PanelOne);
        layout2=(LinearLayout)findViewById(R.id.Panel2);
        layout3=(LinearLayout)findViewById(R.id.panelthree);
        layout4=(LinearLayout)findViewById(R.id.panelfour);
        layotphoto=(RelativeLayout)findViewById(R.id.top1);
        photoselect=(Button) findViewById(R.id.photobutton);
        AgentDropDown = (Spinner)findViewById(R.id.agentSpiner);
    }


}
