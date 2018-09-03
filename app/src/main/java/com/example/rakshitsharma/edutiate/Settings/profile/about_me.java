package com.example.rakshitsharma.edutiate.Settings.profile;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rakshitsharma.edutiate.Authentication.login;
import com.example.rakshitsharma.edutiate.GetAllData.loadingData1;
import com.example.rakshitsharma.edutiate.MainActivity;
import com.example.rakshitsharma.edutiate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;


import es.dmoral.toasty.Toasty;
import jp.wasabeef.picasso.transformations.BlurTransformation;

import static com.example.rakshitsharma.edutiate.Authentication.login.ConvertStreamToString;

public class about_me extends AppCompatActivity {

    //Cards-------------

    private static int RESULT_LOAD_IMAGE = 1;
    ImageView pro;
    private FirebaseUser user;
    private StorageReference mStorageRef;
    private FirebaseAuth auth;
    Bitmap bitmap;
    EditText currentPassword, newPassword;
    String picturePath;
    int flag = 0;
    public static String picture = "";
    ProgressDialog dialog;
    ImageView back;
    ImageView header;
    TextView Proname, branch, rollNo, groupno, univ, phoneNo, email, changePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        pro = (ImageView) findViewById(R.id.pro);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        header = (ImageView) findViewById(R.id.header_cover_image);
        Proname = (TextView) findViewById(R.id.teacher_profile_name);
        branch = (TextView) findViewById(R.id.branch);
        groupno = (TextView) findViewById(R.id.group);
        rollNo = (TextView) findViewById(R.id.rollnumber);
        univ = (TextView) findViewById(R.id.university);
        phoneNo = (TextView) findViewById(R.id.phonenumber);
        changePassword = (TextView) findViewById(R.id.changePassword);
        email = (TextView) findViewById(R.id.email);
        email.setText(user.getEmail().toString());


        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...\nPlease Wait");
        dialog.setCancelable(false);
        //Toast.makeText(getApplicationContext(),""+login.subCode.get(1).toString(),Toast.LENGTH_SHORT).show();
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog d = new Dialog(about_me.this);
                d.setContentView(R.layout.change_password_dialog);
                Button confirm, cancel;
                currentPassword = (EditText) d.findViewById(R.id.current);
                newPassword = (EditText) d.findViewById(R.id.naya);
                confirm = (Button) d.findViewById(R.id.Confirm);
                cancel = (Button) d.findViewById(R.id.Cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();
                    }
                });
                d.setCancelable(false);
                d.show();
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(newPassword.getText().length()<8 || currentPassword.getText().length()<8)
                        {
                            Toasty.error(getApplicationContext(), "Password length should be more than 8", Toast.LENGTH_SHORT, true).show();
                            return;
                        }
                        AuthCredential credential = EmailAuthProvider
                                .getCredential(user.getEmail().toString(), currentPassword.getText().toString());
                        user.reauthenticate(credential)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            user.updatePassword(newPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        d.dismiss();
                                                        Toasty.success(getApplicationContext(), "Password updated", Toast.LENGTH_SHORT, true).show();
                                                        String url1 = "http://192.168.137.1/updatePassword.php?password="+newPassword.getText().toString()+"&email=" + user.getEmail().toString();
                                                        updatePassword mat = new updatePassword();
                                                        mat.execute(url1);
                                                    } else {
                                                        Toasty.error(getApplicationContext(), "Error password not updated", Toast.LENGTH_SHORT, true).show();
                                                        d.dismiss();
                                                    }
                                                }
                                            });
                                        } else {
                                            Toasty.error(getApplicationContext(), "Your current password was not correct", Toast.LENGTH_SHORT, true).show();
                                            d.dismiss();
                                        }
                                    }
                                });
                        d.dismiss();
                    }

                });

            }
        });


        String url1 = "http://192.168.137.1/ProfileDetails.php?tokenID=" + login.token;


     //   Toast.makeText(getApplicationContext(), login.token, Toast.LENGTH_LONG).show();


        pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });
        String url = "http://192.168.137.1/checkImage.php?email=" + user.getEmail().toString();
        //.makeText(getApplicationContext(), user.getEmail().toString(), Toast.LENGTH_LONG).show();

        MyAsyncTaskLocal matl = new MyAsyncTaskLocal();
        MyAsyncTaskLocal1 mat2 = new MyAsyncTaskLocal1();
        matl.execute(url1);
        mat2.execute(url);

        back = (ImageView) findViewById(R.id.goBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

    }

    int READIMAGE = 253;

    void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, READIMAGE);
                return;
            }
        }
        loadImage();
    }

    int PICK_IMAGE_CODE = 123;

    void loadImage() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }


    //retrieving image onto the image view
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getApplication().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();
            dialog.show();
            pro.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            SaveImageInFirebase();

        }
    }

    void SaveImageInFirebase() {
        FirebaseUser currentUser = auth.getCurrentUser();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://login-to-edutiate.appspot.com");
        final String email = currentUser.getEmail().toString();
        Date currentTime = Calendar.getInstance().getTime();
        String df = currentTime.toString();
        String imagePath = email.substring(1, 4) + "." + df + ".jpg";
        StorageReference imageRef = storageRef.child("images/" + imagePath);
        pro.isDrawingCacheEnabled();
        pro.buildDrawingCache();
        BitmapDrawable drawable = (BitmapDrawable) pro.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toasty.error(about_me.this, "Failed to upload image", Toast.LENGTH_SHORT, true).show();

            }
        });
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests") String DownloadURL = taskSnapshot.getDownloadUrl().toString();
                picture = DownloadURL;
                try {
                    DownloadURL = URLEncoder.encode(DownloadURL, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                String url = "http://192.168.137.1/updateRegister.php?image=" + DownloadURL + "&email=" + user.getEmail().toString();
                MyAsyncTask mat = new MyAsyncTask();
                mat.execute(url);
                Log.d("Download URL", DownloadURL);
                Toasty.success(getApplicationContext(), "Updated successfully", Toast.LENGTH_SHORT, true).show();

            }

        });


    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    public class MyAsyncTaskLocal extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            dialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();
                urlConnect.setConnectTimeout(7000);
                String inString = ConvertStreamToString(urlConnect.getInputStream());
                //Cannot use UI over here because this is background method i.e. it works on the background and not on UI
                publishProgress(inString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "";

        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            try {
                Proname.setText("name2");
               // Toast.makeText(getApplicationContext(), "Data setting", Toast.LENGTH_LONG).show();

                JSONObject json = new JSONObject(values[0]);
                JSONArray userInfo = new JSONArray(json.getString("info"));
                JSONObject userCredentials = userInfo.getJSONObject(0);

                if (!userCredentials.getString("name").isEmpty())
                    Proname.setText(userCredentials.getString("name"));
                if (!userCredentials.getString("branch_group").isEmpty())
                    groupno.setText(userCredentials.getString("branch_group"));
                if (!userCredentials.getString("roll_no").isEmpty())
                    rollNo.setText(userCredentials.getString("roll_no"));
                if (!userCredentials.getString("branch").isEmpty())
                    branch.setText(userCredentials.getString("branch"));
                if (!userCredentials.getString("phone_no").isEmpty())
                    phoneNo.setText(userCredentials.getString("phone_no"));
                if (!userCredentials.getString("university").isEmpty())
                    univ.setText(userCredentials.getString("university"));
             //   Toast.makeText(getApplicationContext(), "" + userCredentials.getString("image"), Toast.LENGTH_SHORT).show();
                //
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }

    }

    //------------------------------------------------
    public class MyAsyncTaskLocal1 extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();
                urlConnect.setConnectTimeout(7000);
                String inString = ConvertStreamToString(urlConnect.getInputStream());
                //Cannot use UI over here because this is background method i.e. it works on the background and not on UI
                publishProgress(inString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "";

        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            try {
             //   Toast.makeText(getApplicationContext(), "Image setting", Toast.LENGTH_LONG).show();

                JSONObject json = new JSONObject(values[0]);
                JSONArray userInfo = new JSONArray(json.getString("info"));
                JSONObject userCredentials = userInfo.getJSONObject(0);
                if (!userCredentials.getString("image").isEmpty()) {
                    Picasso.with(getApplicationContext()).load(userCredentials.getString("image")).into(pro);
                    Picasso.with(getApplicationContext()).load(userCredentials.getString("image"))
                            .transform(new BlurTransformation(getApplicationContext())).into(header);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }

    }


    public class MyAsyncTask extends AsyncTask<String, String, String> {
        public String message;
        public JSONObject json;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();
                urlConnect.setConnectTimeout(7000);
                String inString = ConvertStreamToString(urlConnect.getInputStream());
                //Cannot use UI over here because this is background method i.e. it works on the background and not on UI
                publishProgress(inString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "";

        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            try{

                json = new JSONObject(values[0]);
                message =json.getString("msg");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(dialog.isShowing())
                dialog.dismiss();
        }
        String ConvertStreamToString(InputStream inputStream){
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            String AllString="";
            try{
                do{
                    line = bf.readLine();
                    if(line!=null)
                        AllString+=line;
                }while(line!=null);
                inputStream.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
            return AllString;
        }
    }


    public class updatePassword extends AsyncTask<String, String, String> {
        public String message;
        public JSONObject json;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();
                urlConnect.setConnectTimeout(7000);
                String inString = ConvertStreamToString(urlConnect.getInputStream());
                //Cannot use UI over here because this is background method i.e. it works on the background and not on UI
                publishProgress(inString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "";

        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            try {

                json = new JSONObject(values[0]);
                message = json.getString("msg");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }


        String ConvertStreamToString(InputStream inputStream) {
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            String AllString = "";
            try {
                do {
                    line = bf.readLine();
                    if (line != null)
                        AllString += line;
                } while (line != null);
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return AllString;
        }

    }
}
