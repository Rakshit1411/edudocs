package com.example.rakshitsharma.edutiate.Teacher;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.rakshitsharma.edutiate.GetAllData.SaveSettings;
import com.example.rakshitsharma.edutiate.R;
import com.example.rakshitsharma.edutiate.Teacher.teacher_login;
import com.example.rakshitsharma.edutiate.Teacher.teacher_main;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.rakshitsharma.edutiate.Teacher.teacher_login.teacher_code;

public class teacher_loadingData1 extends AppCompatActivity {

    public static String Teacher_phone,Teacher_room,Teacher_image,Teacher_email,Teacher_name;
    public static ArrayList teacher_subCode,teacher_branchGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_loading_data1);
        teacher_subCode = new ArrayList<String>();
        teacher_branchGroup = new ArrayList<String>();
      //  Toast.makeText(getApplicationContext(), ""+ SaveSettings.userID+"-"+ SaveSettings.userCode,Toast.LENGTH_SHORT).show();
        teacher_home_data thd = new teacher_home_data();
        thd.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,"http://192.168.137.1/teacher_home.php?teacher_code="+SaveSettings.userCode);
    }
    public class teacher_home_data extends AsyncTask<String, String, String> {

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

                JSONObject json = new JSONObject(values[0]);
                JSONArray userInfo = new JSONArray(json.getString("info"));
                // JSONObject userCredentials = userInfo.getJSONObject(0);
                //Toast.makeText(getApplicationContext(),"1"+userInfo.getJSONObject(0).getString("branch_group"),Toast.LENGTH_SHORT).show();

                for(int i=0;i<userInfo.length();i++){
                    teacher_branchGroup.add(i,userInfo.getJSONObject(i).getString("branch_group"));
                    teacher_subCode.add(i,userInfo.getJSONObject(i).getString("subCode"));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Toast.makeText(getApplicationContext(),""+teacher_branchGroup.size(),Toast.LENGTH_SHORT).show();
            teacher_my_profile tmp = new teacher_my_profile();
            tmp.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,"http://192.168.137.1/teacher_details.php?teacher_code="+SaveSettings.userCode);
        }

    }





//------------------------------------------------



    public class teacher_my_profile extends AsyncTask<String, String, String> {

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

                //         Toast.makeText(getApplicationContext(),"+++++",Toast.LENGTH_SHORT).show();
                JSONObject json = new JSONObject(values[0]);
                JSONArray userInfo = new JSONArray(json.getString("info"));
                Teacher_phone = (userInfo.getJSONObject(0).getString("phone_no"));
                Teacher_room = (userInfo.getJSONObject(0).getString("room_no"));
                Teacher_image = (userInfo.getJSONObject(0).getString("teacher_image"));
                Teacher_email = (userInfo.getJSONObject(0).getString("teacher_email"));



                //  JSONObject userCredentials = userInfo.getJSONObject(0);
                //   Toast.makeText(getApplicationContext(),"teachers: "+userInfo.length(),Toast.LENGTH_SHORT).show();
                //  Toast.makeText(getApplicationContext(),"teachers:dsadas ",Toast.LENGTH_SHORT).show();


                //message =json.getString("msg");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            LoadTeacherName ltn = new LoadTeacherName();
            ltn.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,"http://192.168.137.1/teacher_side_nameCode.php?teacher_code="+SaveSettings.userCode);

           // Toast.makeText(getApplicationContext(),SaveSettings.userCode,Toast.LENGTH_SHORT).show();

        }
    }



//---------------------------------------------------



    public class LoadTeacherName extends AsyncTask<String, String, String> {

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

                JSONObject json = new JSONObject(values[0]);
                JSONArray userInfo = new JSONArray(json.getString("info"));
                Teacher_name = (userInfo.getJSONObject(0).getString("teacher_name"));
              //  Toast.makeText(getApplicationContext(),userInfo.getJSONObject(0).getString("teacher_name"),Toast.LENGTH_LONG).show();


                //message =json.getString("msg");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
           // Toast.makeText(getApplicationContext(),teacher_loadingData1.Teacher_name,Toast.LENGTH_LONG).show();

            startActivity(new Intent(getApplicationContext(),teacher_main.class));
            finish();

        }

    }


//---------------------------------------------------



    public static String ConvertStreamToString(InputStream inputStream){
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
