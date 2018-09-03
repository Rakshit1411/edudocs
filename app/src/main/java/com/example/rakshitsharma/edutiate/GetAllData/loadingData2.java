package com.example.rakshitsharma.edutiate.GetAllData;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.rakshitsharma.edutiate.Home.my_batches.batches_cards.my_batches_in_home_adapter;
import com.example.rakshitsharma.edutiate.Home.my_batches.batches_cards.teacher_of_batch.NsubjectDocumentsActivity;
import com.example.rakshitsharma.edutiate.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class loadingData2 extends AppCompatActivity {

    public static ArrayList doc_postText;
    public static ArrayList doc_postURL;
    public static ArrayList doc_postDate;
    public static int viewNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_data2);
        doc_postDate = new ArrayList<String>();
        doc_postText = new ArrayList<String>();
        doc_postURL = new ArrayList<String>();
        viewNumber = getIntent().getExtras().getInt("viewNumber");
        Doc_data_AsyncTask ddat = new Doc_data_AsyncTask();
        ddat.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,"http://192.168.137.1/doc_details.php?subCode="+loadingData1.subCode.get(my_batches_in_home_adapter.cardNumber)+"&branch_group="+loadingData1.myGroup+"&tokenID="+loadingData1.Teacher_code.get(my_batches_in_home_adapter.cardNumber));
    }


    public class Doc_data_AsyncTask extends AsyncTask<String, String, String> {

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
                String inString = loadingData1.ConvertStreamToString(urlConnect.getInputStream());
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
                    doc_postDate.add(i,prettyTimeString);
                    doc_postURL.add(i,userInfo.getJSONObject(i).getString("postDoc"));
                    doc_postText.add(i,userInfo.getJSONObject(i).getString("postText"));
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
          //  Toast.makeText(getApplicationContext(),""+doc_postText.size(),Toast.LENGTH_SHORT).show();

           /* for(int i=0;i<doc_postText.size();i++)
                Toast.makeText(getApplicationContext(),doc_postURL.get(i).toString(),Toast.LENGTH_SHORT).show();
            */startActivity(new Intent(getApplicationContext(),NsubjectDocumentsActivity.class));
            finish();
        }

    }




}
