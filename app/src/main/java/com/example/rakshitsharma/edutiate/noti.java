package com.example.rakshitsharma.edutiate;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import com.example.rakshitsharma.edutiate.GetAllData.loadingData1;

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
import java.util.Timer;
import java.util.TimerTask;

import static com.example.rakshitsharma.edutiate.GetAllData.loadingData1.myGroup;
import static com.example.rakshitsharma.edutiate.Teacher.teacher_loadingData1.ConvertStreamToString;

public class noti extends Service {
    public static ArrayList _all_doc_postText;
    public static ArrayList _all_doc_postURL;
    public static ArrayList _all_doc_postDate,_all_doc_postTeacherCode,_all_doc_postSubCode;

    public static int items;
    public static final int notify = 12000;  //interval between two services(Here Service run every 2 Minute)
    private Handler mHandler = new Handler();   //run on another Thread to avoid crash
    private Timer mTimer = null;    //timer handling

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        if (mTimer != null) // Cancel if already existed
            mTimer.cancel();
        else
            mTimer = new Timer();   //recreate new
        mTimer.scheduleAtFixedRate(new TimeDisplay(), 0, notify);   //Schedule task


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();    //For Cancel Timer
        Toast.makeText(this, "Service is Destroyed", Toast.LENGTH_SHORT).show();
    }

    //class TimeDisplay for handling task
    class TimeDisplay extends TimerTask {
        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    // display toast
                    //Toast.makeText(noti.this, "Service is running", Toast.LENGTH_SHORT).show();
                    All_News_Feeds_Doc_data_AsyncTask anfddat = new All_News_Feeds_Doc_data_AsyncTask();
                    anfddat.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,"http://192.168.137.1/news_feed.php?branch_group="+myGroup);

                }
            });
        }
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
                items = userInfo.length();
                for(int i=0;i<userInfo.length();i++){
                    String strDate = userInfo.getJSONObject(i).getString("postDate");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = dateFormat.parse(strDate);
                    String prettyTimeString = new PrettyTime(new Locale("")).format(date);
                   /* _all_doc_postDate.add(i,prettyTimeString);
                    _all_doc_postURL.add(i,userInfo.getJSONObject(i).getString("postDoc"));
                    _all_doc_postText.add(i,userInfo.getJSONObject(i).getString("postText"));
                    _all_doc_postTeacherCode.add(i,userInfo.getJSONObject(i).getString("tokenID"));
                    _all_doc_postSubCode.add(i,userInfo.getJSONObject(i).getString("subCode"));
                */}
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
            if(items> loadingData1.all_doc_postDate.size()){
                Intent intent = new Intent(getApplicationContext(), loadingData1.class);
                PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                Notification n  = new Notification.Builder(getApplicationContext())
                        .setContentTitle("Updates")
                        .setContentText("New Document has been uploaded")
                        .setSmallIcon(R.drawable.arrow)
                        .setAutoCancel(true).build();


                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                notificationManager.notify(0, n);
            }
        }

    }


}


