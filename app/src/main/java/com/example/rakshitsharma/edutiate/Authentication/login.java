package com.example.rakshitsharma.edutiate.Authentication;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.rakshitsharma.edutiate.R;
import com.example.rakshitsharma.edutiate.GetAllData.loadingData1;
import com.example.rakshitsharma.edutiate.checker;
import com.example.rakshitsharma.edutiate.noti;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import es.dmoral.toasty.Toasty;


public class login extends AppCompatActivity {

    public static String type;         //0 for student and 1 for teacher
    public static ArrayList subCode,branch;
    public static EditText inputPassword;
    public static AutoCompleteTextView inputEmail;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseAuth auth;
    public static String token;
    public static FirebaseUser user;
    public static int listsize=0;
    private ProgressBar progressBar;
    private Button btnSignup, btnReset;
    private CircularProgressButton btnLogin;
    RadioGroup user_type;
    static final String APP_ID = "58696";
    static final String AUTH_KEY= "bRLpxHfCm3hULSt";
    static final String AUTH_SECRET = "mSaSV4KzQnzgZh9";
    static final String ACCOUNT_KEY = "BpJLmNq9dj9XjL3JaWAY";
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$", Pattern.CASE_INSENSITIVE);

    public static String userType_login;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        String userID;
        branch = new ArrayList<String>();
        subCode = new ArrayList<String>();
        user=auth.getCurrentUser();
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



        if (auth.getCurrentUser() != null && user.isEmailVerified()) {
            Intent i = new Intent(login.this,loadingData1.class);
            i.putExtra("email",user.getEmail().toString().trim());
            i.putExtra("password","i dont know");
            startActivity(i);
            finish();
        }

            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            btnSignup = (Button) findViewById(R.id.btn_signup);
            btnLogin = (CircularProgressButton) findViewById(R.id.btn_login);
            btnReset = (Button) findViewById(R.id.btn_reset_password);


        /*final SaveSettings saveSettings = new SaveSettings(this);
        saveSettings.loadSettings();
        if(saveSettings.userID!="0")
            startActivity(new Intent(this,MainActivity.class));
*/
            btnSignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(login.this, signUp.class));
                    finish();
                }
            });

            btnReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(login.this, forgot_password.class));
                    finish();
                }
            });

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String email1 = inputEmail.getText().toString().trim();
                    final String password1 = inputPassword.getText().toString().trim();
                    //Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_SHORT).show();
                    if (TextUtils.isEmpty(email1)) {
                        Toasty.info(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT, true).show();
                        return;
                    }

                    if (TextUtils.isEmpty(password1)) {
                        Toasty.info(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT, true).show();
                        return;
                    }

                    btnLogin.startAnimation();

                    //Toast.makeText(getApplicationContext(),"3 "+email1+" "+password1,Toast.LENGTH_SHORT).show();
                    auth.signInWithEmailAndPassword(email1, password1)
                            .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    //progressBar.setVisibility(View.GONE);

                                    user = auth.getCurrentUser();
                                    if (!task.isSuccessful()) {
                                        // there was an error

                                       // Toast.makeText(getApplicationContext(),"4",Toast.LENGTH_SHORT).show();
                                        if (password1.length() < 6) {
                                            inputPassword.setError("Minimum length of password should be 6");
                                        } else {
                                            Toasty.error(getApplicationContext(), "Wrong Credentials Or Bad Network", Toast.LENGTH_SHORT, true).show();
                                        }
                                        //btnLogin.setVisibility(View.VISIBLE);
                                        btnLogin.revertAnimation();
                                    }
                                    else if(!user.isEmailVerified())
                                    {
                                        inputEmail.setError("Your Account has not been verified");
                                        btnLogin.revertAnimation();
                                        //btnLogin.setVisibility(View.VISIBLE);
                                    }
                                    else {
                                        btnLogin.doneLoadingAnimation(Color.parseColor("#F1C40F"), BitmapFactory.decodeResource(getResources(),R.mipmap.done));
                                        String url = "http://192.168.137.1/login.php?email="+user.getEmail().toString()+"&password="+password1;
                                        MyAsyncTaskLocal matl = new MyAsyncTaskLocal();
                                        matl.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,url);
                                        //if(mat.message=="pass login"){
                                        //saveSettings.saveUserSettings(user.getEmail().toString());

                                      //  Toast.makeText(getApplicationContext(),"10",Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(login.this, loadingData1.class);
                                        intent.putExtra("email",email1);
                                        intent.putExtra("password",password1);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });


                }
            });
        }

    public static class MyAsyncTaskLocal extends AsyncTask<String, String, String> {

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
                token = userCredentials.getString("tokenID");
                //Toast.makeText(getApplicationContext(),""+userCredentials.getString("tokenID"),Toast.LENGTH_SHORT).show();
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

    public boolean hostAvailable(String host, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 2000);
            return true;
        } catch (IOException e) {
            // Either we have a timeout or unreachable host or failed DNS lookup
            System.out.println(e);
            return false;
        }
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),checker.class));
        finish();
    }
}
