package com.example.rakshitsharma.edutiate;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rakshitsharma.edutiate.GetAllData.SaveSettings;
import com.example.rakshitsharma.edutiate.Teacher.MyteacherhomeitemRecyclerViewAdapter;
import com.example.rakshitsharma.edutiate.Teacher.teacherView_Details_of_Batch_noAttendance;
import com.example.rakshitsharma.edutiate.Teacher.teacher_loadingData1;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

public class BottomSheet extends BottomSheetDialogFragment {
    private ProgressDialog pDialog;
    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    public static final int PERMISSIONS_REQUEST_CODE = 0;
    ImageView im,can;
    public static final int FILE_PICKER_REQUEST_CODE = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_dialog_bottom_sheet, container, false);
        im = (ImageView)v.findViewById(R.id.imageView4);
        can = (ImageView)v.findViewById(R.id.imageView10);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermissionsAndOpenFilePicker();
                teacherView_Details_of_Batch_noAttendance.download.callOnClick();
            }
        });

        can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        //        getActivity().getSupportFragmentManager().beginTransaction().remove(new BottomSheet());
            }
        });
        return v;
    }


    public void checkPermissionsAndOpenFilePicker() {
        String permission = android.Manifest.permission.READ_EXTERNAL_STORAGE;
        //Toast.makeText(getActivity(),"fsdasasas43242332fsd",Toast.LENGTH_LONG).show();

        if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
                showError();
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, PERMISSIONS_REQUEST_CODE);
            }
        } else {
            openFilePicker();
        }
    }

    private void showError() {
        Toast.makeText(getActivity(), "Allow external storage reading", Toast.LENGTH_SHORT).show();
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
                .withActivity(getActivity())
                .withRequestCode(FILE_PICKER_REQUEST_CODE)
                .start();
        //Toast.makeText(getActivity(),"fsdasasas43242332fsd",Toast.LENGTH_LONG).show();

    }

    ProgressDialog progress;

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        //Toast.makeText(getActivity(),"fsdfsdfsdfsd",Toast.LENGTH_LONG).show();
        if(requestCode == FILE_PICKER_REQUEST_CODE && resultCode == RESULT_OK){

            progress = new ProgressDialog(getContext());
            progress.setTitle("Uploading");
            progress.setMessage("Please wait...");
            progress.show();

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {

                    File f  = new File(data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));
                    String content_type  = getMimeType(f.getPath());

                    String file_path = f.getAbsolutePath();
                    OkHttpClient client = new OkHttpClient();
                    RequestBody file_body = RequestBody.create(MediaType.parse(content_type),f);

                    RequestBody request_body = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("type",content_type)
                            .addFormDataPart("uploaded_file",file_path.substring(file_path.lastIndexOf("/")+1), file_body)
                            .build();

                    Request request = new Request.Builder()
                            .url("http://192.168.137.1/fileUpload.php?tokenID="+ SaveSettings.userCode+"&branch_group="+ teacher_loadingData1.teacher_branchGroup.get(MyteacherhomeitemRecyclerViewAdapter.cardNumber).toString()+"&postText=blablabla&subCode="+teacher_loadingData1.teacher_subCode.get(MyteacherhomeitemRecyclerViewAdapter.cardNumber).toString())
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




        }
    }

    private String getMimeType(String path) {

        String extension = MimeTypeMap.getFileExtensionFromUrl(path);

        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }



}
