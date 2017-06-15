package com.example.rakshitsharma.edutiate.Settings.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.example.rakshitsharma.edutiate.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import static android.R.attr.bitmap;

public class about_me extends AppCompatActivity {

    //Cards-------------
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "CardViewActivity";
    private static int RESULT_LOAD_IMAGE = 1;
    ImageView pro;
    CardView cv;
    private FirebaseUser user;
    private StorageReference mStorageRef;
    private FirebaseAuth auth;
    Bitmap bitmap;
    String picturePath;
    int flag=0;
    SharedPreferences preferences,preferences1;
    SharedPreferences.Editor editor,editor1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        pro=(ImageView)findViewById(R.id.pro);
        cv = (CardView)findViewById(R.id.cv);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        cv.bringToFront();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new profile_card_adapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);
        //opening images chooser
        pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

    }

    private ArrayList<profile_object> getDataSet() {
        ArrayList results = new ArrayList<profile_object>();
        for (int index = 0; index < 1; index++) {
            profile_object obj = new profile_object("Full Name From Database corresponding to the email ID ",
                    "e-mail : "+ user.getEmail());
            results.add(index, obj);
        }
        for (int index = 1; index < 2; index++) {
            profile_object obj = new profile_object("Address From database",
                    "Phone no. from database");
            results.add(index, obj);
        }
        return results;
    }



    //retrieving image on to the image view
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getApplication().getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();

            StorageReference riversRef = mStorageRef.child("images/profile.jpg");

            riversRef.putFile(selectedImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                           // Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                        }
                    });
/*            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] bytes = baos.toByteArray();
            String base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);

            // we finally have our base64 string version of the image, save it.
            DatabaseReference ref = FirebaseDatabase.getInstance()
                    .getReference(SyncStateContract.Constants.FIREBASE_CHILD_RESTAURANTS)
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child(mRestaurant.getPushId())
                    .child("imageUrl");
  */          pro.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((profile_card_adapter) mAdapter).setOnItemClickListener(new profile_card_adapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(LOG_TAG, " Clicked on Item " + position);
            }
        });
    }


}
