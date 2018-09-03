package com.example.rakshitsharma.edutiate.GetAllData;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.rakshitsharma.edutiate.Authentication.login;
import com.example.rakshitsharma.edutiate.MainActivity;
import com.example.rakshitsharma.edutiate.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class loadingData1 extends AppCompatActivity {
    public static String typo;
    public static ArrayList subName;
    public static ArrayList subCode;
    public static ArrayList Teacher;
    public static ArrayList Teacher_code;
    public static ArrayList Teacher_room;
    public static ArrayList Teacher_phone;
    public static ArrayList Teacher_image;
    public static ArrayList Teacher_email;
    public static ArrayList all_doc_postText;
    public static ArrayList all_doc_postURL;
    public static ArrayList all_doc_postDate,all_doc_postTeacherCode,all_doc_postSubCode;

    public static String myName,myBranch,myGroup,myphone,myRollno,myUniv;
    String url1;
    int x=0,y=0,z=0;
    int getTokenFlag=0,loadsubjectsFlag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_data);

        subName = new ArrayList<String>();
        subCode = new ArrayList<String>();
        Teacher = new ArrayList<String>();
        Teacher_email = new ArrayList<String>();
        Teacher_image = new ArrayList<String>();
        Teacher_phone = new ArrayList<String>();
        Teacher_room = new ArrayList<String>();
        Teacher_code = new ArrayList<String>();
        all_doc_postDate = new ArrayList<String>();
        all_doc_postText = new ArrayList<String>();
        all_doc_postURL = new ArrayList<String>();
        all_doc_postTeacherCode = new ArrayList<String>();
        all_doc_postSubCode = new ArrayList<String>();
       // Toast.makeText(getApplication(),login.user.getEmail().toString(), Toast.LENGTH_SHORT).show();
        String url = "http://192.168.137.1/getToken.php?email="+ login.user.getEmail().toString();
        getToken matl = new getToken();
        matl.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,url);



    }

    public class LoadSubjectsData extends AsyncTask<String, String, String> {

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
                for(int i=0;i<userInfo.length();i++){
                    subCode.add(i,userInfo.getJSONObject(i).getString("subCode"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            helper_LoadSubjectsNames();
        }

    }


    private void helper_LoadSubjectsNames(){

        for(int i=0;i<subCode.size();i++) {
            LoadSubjectsNames s1 = new LoadSubjectsNames();
            s1.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,"http://192.168.137.1/getSubjectName.php?subCode="+subCode.get(i));
        }
        helper_LoadTeachersNames();

    }


    public class LoadSubjectsNames extends AsyncTask<String, String, String> {

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

                subName.add(x, userInfo.getJSONObject(0).getString("subName"));
                x++;
                //message =json.getString("msg");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

    }

    private void helper_LoadTeachersNames() {
        for(int i=0;i<subCode.size();i++) {
            LoadTeachersData s2 = new LoadTeachersData();
            s2.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,"http://192.168.137.1/teachers.php?subCode="+subCode.get(i) + "&branch_group="+myGroup);
        }
       // Toast.makeText(getApplicationContext(),Teacher_code.get(0)+""+Teacher_email.get(0)+""+Teacher_room.get(0),Toast.LENGTH_SHORT).show();

        All_News_Feeds_Doc_data_AsyncTask anfddat = new All_News_Feeds_Doc_data_AsyncTask();
        anfddat.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,"http://192.168.137.1/news_feed.php?branch_group="+myGroup);



        finishAsyncTask fat = new finishAsyncTask();
        fat.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);






    }








    public class All_News_Feeds_Doc_data_AsyncTask extends AsyncTask<String, String, String> {

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
                for(int i=0;i<userInfo.length();i++){
                    String strDate = userInfo.getJSONObject(i).getString("postDate");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = dateFormat.parse(strDate);
                    String prettyTimeString = new PrettyTime(new Locale("")).format(date);
                    all_doc_postDate.add(i,prettyTimeString);
                    all_doc_postURL.add(i,userInfo.getJSONObject(i).getString("postDoc"));
                    all_doc_postText.add(i,userInfo.getJSONObject(i).getString("postText"));
                    all_doc_postTeacherCode.add(i,userInfo.getJSONObject(i).getString("tokenID"));
                    all_doc_postSubCode.add(i,userInfo.getJSONObject(i).getString("subCode"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Toast.makeText(getApplicationContext(),""+doc_postText.size(),Toast.LENGTH_SHORT).show();

            //for(int i=0;i<all_doc_postText.size();i++)
              //
            //  Toast.makeText(getApplicationContext(),all_doc_postText.get(i).toString(),Toast.LENGTH_SHORT).show();

        }

    }







    public class LoadTeachersData extends AsyncTask<String, String, String> {

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
                    Teacher.add(y, userInfo.getJSONObject(0).getString("teacher_name"));
                    Teacher_code.add(y, userInfo.getJSONObject(0).getString("teacher_code"));
                    y++;

       //             Toast.makeText(getApplicationContext(),"5555",Toast.LENGTH_SHORT).show();

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
                //for(int i=0;i<subCode.size();i++)
                //Toast.makeText(getApplicationContext(),""+subCode.get(i), Toast.LENGTH_SHORT).show();
                loadsubjectsFlag=1;
                load_teachers_all_data_AsyncTask ltadat = new load_teachers_all_data_AsyncTask();
                ltadat.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, "http://192.168.137.1/teacher_details.php?teacher_code="+Teacher_code.get(y-1));
                //Toast.makeText(getApplicationContext(),"teachers   "+Teacher_code.get(y-1),Toast.LENGTH_SHORT).show();

            }

        }






    public class load_teachers_all_data_AsyncTask extends AsyncTask<String, String, String> {

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
                Teacher_phone.add(z, userInfo.getJSONObject(0).getString("phone_no"));
                Teacher_room.add(z, userInfo.getJSONObject(0).getString("room_no"));
                Teacher_image.add(z, userInfo.getJSONObject(0).getString("teacher_image"));
                Teacher_email.add(z, userInfo.getJSONObject(0).getString("teacher_email"));
                z++;


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
            //for(int i=0;i<subCode.size();i++)
            //Toast.makeText(getApplicationContext(),""+subCode.get(i), Toast.LENGTH_SHORT).show();
            loadsubjectsFlag=1;
            //Toast.makeText(getApplicationContext(),"teachers:dsadas ",Toast.LENGTH_SHORT).show();





        }

    }











    public class getToken extends AsyncTask<String, String, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
     //           Toast.makeText(getApplicationContext(),""+login.token,Toast.LENGTH_SHORT).show();

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
                    JSONObject userCredentials = userInfo.getJSONObject(0);
                    login.token = userCredentials.getString("tokenID");
                    typo = userCredentials.getString("type_");
       //             Toast.makeText(getApplicationContext(),"progress:"+login.token,Toast.LENGTH_SHORT).show();
                    //message =json.getString("msg");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                getTokenFlag = 1;
       //         Toast.makeText(getApplicationContext(),"post:"+login.token,Toast.LENGTH_SHORT).show();
                /*
            */
                String url1 = "http://192.168.137.1/ProfileDetails.php?tokenID=" + login.token;
                aboutMeAsyncTask amat = new aboutMeAsyncTask();
                amat.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,url1);

            }

        }


    public class aboutMeAsyncTask extends AsyncTask<String, String, String> {

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
                JSONObject userCredentials = userInfo.getJSONObject(0);

                if (!userCredentials.getString("name").isEmpty())
                    myName = (userCredentials.getString("name"));
                if (!userCredentials.getString("branch_group").isEmpty())
                    myGroup = (userCredentials.getString("branch_group"));
                if (!userCredentials.getString("roll_no").isEmpty())
                    myRollno = (userCredentials.getString("roll_no"));
                if (!userCredentials.getString("branch").isEmpty())
                    myBranch = (userCredentials.getString("branch"));
                if (!userCredentials.getString("phone_no").isEmpty())
                    myphone = (userCredentials.getString("phone_no"));
                if (!userCredentials.getString("university").isEmpty())
                    myUniv = (userCredentials.getString("university"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            LoadSubjectsData lsd = new LoadSubjectsData();
            url1 = "http://192.168.137.1/mySubjects.php?tokenID="+login.token;

            lsd.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,url1);
        }

    }





    public class finishAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
       //     Toast.makeText(getApplicationContext(),""+login.token,Toast.LENGTH_SHORT).show();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            y=0;
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }


    }












    static String ConvertStreamToString(InputStream inputStream){
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
