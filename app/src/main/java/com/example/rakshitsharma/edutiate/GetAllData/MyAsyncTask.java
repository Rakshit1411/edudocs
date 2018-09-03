package com.example.rakshitsharma.edutiate.GetAllData;

import android.os.AsyncTask;
import android.widget.Toast;

import com.example.rakshitsharma.edutiate.Settings.profile.about_me;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import de.mrapp.android.dialog.ProgressDialog;

/**
 * Created by Rakshit on 10/7/2017.
 */

public class MyAsyncTask extends AsyncTask<String, String, String> {
    public static String message;
    public static JSONObject json;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
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