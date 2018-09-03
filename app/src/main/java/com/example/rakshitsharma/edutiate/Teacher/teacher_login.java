package com.example.rakshitsharma.edutiate.Teacher;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rakshitsharma.edutiate.Authentication.forgot_password;
import com.example.rakshitsharma.edutiate.Authentication.login;
import com.example.rakshitsharma.edutiate.GetAllData.SaveSettings;
import com.example.rakshitsharma.edutiate.R;
import com.example.rakshitsharma.edutiate.checker;

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
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import es.dmoral.toasty.Toasty;


public class teacher_login extends AppCompatActivity {

    public static String type;         //0 for student and 1 for teacher
    public static ArrayList subCode,branch;
    public static EditText inputPassword;
    public static AutoCompleteTextView inputEmail;
    public static String teacher_code;

    private Button btnReset;
    private CircularProgressButton btnLogin;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$", Pattern.CASE_INSENSITIVE);

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);
        inputEmail = (AutoCompleteTextView) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);


        Account[] accounts = AccountManager.get(this).getAccounts();
        Set<String> emailSet = new HashSet<String>();
        for (Account account : accounts) {
            if (EMAIL_PATTERN.matcher(account.name).matches()) {
                emailSet.add(account.name);
            }
        }
        inputEmail.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<String>(emailSet)));

        btnLogin = (CircularProgressButton) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);


        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(teacher_login.this, forgot_password.class));
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authentication a = new authentication();
                a.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,"http://192.168.137.1/teacher_login.php?teacher_email="+inputEmail.getText().toString()+"&teacher_password="+inputPassword.getText().toString());

            }
        });
    }




    public class authentication extends AsyncTask<String, String, String> {

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
                //             Toast.makeText(getApplicationContext(),"progress:"+login.token,Toast.LENGTH_SHORT).show();
                teacher_code =userCredentials.getString("teacher_code");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //.makeText(getApplicationContext(),"post:"+teacher_code,Toast.LENGTH_SHORT).show();

            if(teacher_code!=null){
                final SaveSettings saveSettings = new SaveSettings(getApplicationContext());
                saveSettings.saveUserSettings(inputEmail.getText().toString(),teacher_code);
                startActivity(new Intent(teacher_login.this,teacher_loadingData1.class));
                finish();
            }
            else
                Toasty.error(getApplicationContext(), "Wrong Credentials Or Bad Network", Toast.LENGTH_SHORT, true).show();

        }

    }





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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),checker.class));
        finish();
    }
}
