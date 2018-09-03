package com.example.rakshitsharma.edutiate.Teacher;

import android.Manifest;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.rakshitsharma.edutiate.BottomSheet;
import com.example.rakshitsharma.edutiate.GetAllData.SaveSettings;
import com.example.rakshitsharma.edutiate.R;
import com.example.rakshitsharma.edutiate.dummy.DummyContent;
import com.example.rakshitsharma.edutiate.teacherViewDetailsofBatchDocsitemFragment;
import com.github.rubensousa.bottomsheetbuilder.BottomSheetBuilder;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class teacherView_Details_of_Batch_noAttendance extends AppCompatActivity implements teacherViewDetailsofBatchDocsitemFragment.OnListFragmentInteractionListener {

    private ProgressDialog pDialog;
    String s,tt;
    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    public static final int PERMISSIONS_REQUEST_CODE = 0;
    DownloadManager dm;
    private static final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";
    public static final int FILE_PICKER_REQUEST_CODE = 1;
    LinearLayout content;
    FragmentManager mFragmentManager;
    EditText message;
    public static Button b,download;
    public static ConstraintLayout cl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_view__details_of__batch_no_attendance);
        getSupportActionBar().setTitle("Batch");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());

        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
        content = (LinearLayout)findViewById(R.id.frame);
        //b = (Button)findViewById(R.id.button3);
        //download = (Button)findViewById(R.id.button4);
        message = (EditText)findViewById(R.id.message);
        xfragmentTransaction.replace(R.id.frame,new teacherViewDetailsofBatchDocsitemFragment()).commit();
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);


    }





    public void select(View v){
        checkPermissionsAndOpenFilePicker();

    }

    public void checkPermissionsAndOpenFilePicker() {
        String permission = android.Manifest.permission.READ_EXTERNAL_STORAGE;

        if (ContextCompat.checkSelfPermission(getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                showError();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{permission}, PERMISSIONS_REQUEST_CODE);
            }
        } else {
            openFilePicker();
        }
    }

    private void showError() {
        Toast.makeText(getApplicationContext(), "Allow external storage reading", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openFilePicker();
                } else {
                    showError();
                }
            }
        }
    }

    private void openFilePicker() {
        new MaterialFilePicker()
                .withActivity(this)
                .withRequestCode(FILE_PICKER_REQUEST_CODE)
                .start();


    }

    ProgressDialog progress;

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if(requestCode == FILE_PICKER_REQUEST_CODE && resultCode == RESULT_OK){

            progress = new ProgressDialog(this);
            progress.setTitle("Uploading");
            progress.setMessage("Please wait...");
            progress.show();

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {

                    File f  = new File( data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));

                    String content_type  = getMimeType(f.getPath());

                    String file_path = f.getAbsolutePath();
                    OkHttpClient client = new OkHttpClient();
                    String mimeType= URLConnection.guessContentTypeFromName(f.getName());
                    RequestBody file_body = RequestBody.create(MediaType.parse(mimeType),f);
//                  RequestBody file_body = RequestBody.create(MediaType.parse(content_type),f);

                    RequestBody request_body = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("type",mimeType)
                            .addFormDataPart("uploaded_file",file_path.substring(file_path.lastIndexOf("/")+1), file_body)
                            .build();

                    Request request = new Request.Builder()
                            .url("http://192.168.137.1/fileUpload.php?tokenID="+ SaveSettings.userCode+"&branch_group="+ teacher_loadingData1.teacher_branchGroup.get(MyteacherhomeitemRecyclerViewAdapter.cardNumber).toString()+"&postText="+message.getText().toString()+"&subCode="+teacher_loadingData1.teacher_subCode.get(MyteacherhomeitemRecyclerViewAdapter.cardNumber).toString())
                            .post(request_body)
                            .build();

                    try {
                        Response response = client.newCall(request).execute();

                        if(!response.isSuccessful()){
                            throw new IOException("Error : "+response);
                        }

                        progress.dismiss();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            });

            t.start();


        startActivity(new Intent(teacherView_Details_of_Batch_noAttendance.this,teacher_main.class));
        finish();

        }
    }

    private String getMimeType(String path) {

        String extension = MimeTypeMap.getFileExtensionFromUrl(path);

        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }









    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}
