package com.example.rakshitsharma.edutiate.Authentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.rakshitsharma.edutiate.GetAllData.MyAsyncTask;
import com.example.rakshitsharma.edutiate.R;
import com.example.rakshitsharma.edutiate.Settings.profile.about_me;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import es.dmoral.toasty.Toasty;

public class signUp extends AppCompatActivity {
    private EditText inputEmail, inputPassword,inputName,inputRollNO;
    private Button btnSignIn;
    private CircularProgressButton btnSignUp;
    //private ProgressBar progressBar;
    private FirebaseAuth auth;
    private static ScrollView sc;
    public static FirebaseUser user;
    int count=0;
    public static String userType_signUp;
    static String message="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        auth = FirebaseAuth.getInstance();
        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (CircularProgressButton) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputName = (EditText) findViewById(R.id.name);
        inputPassword = (EditText) findViewById(R.id.password);
        inputRollNO = (EditText) findViewById(R.id.roll_no);
       // progressBar = (ProgressBar) findViewById(R.id.progressBar);

       /* String[] sex = new String[] {
                "Male", "Female", "Others"
        };
        AutoCompleteTextView sexInput = (AutoCompleteTextView) findViewById(R.id.sex);
        ArrayAdapter<String> sex_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, sex);
        sexInput.setAdapter(sex_adapter);

        String[] allBranches = new String[] {
                "Computer Engineering", "Electronics and Communication Engineering", "Mechanical Engineering",
                "Civil Engineering", "Electrical Engineering", "Electronics and Instrumentation Engineering",
                "Computer Engineering (MBA)", "Civil Engineering (MBA)", "Mechanical Engineering (MBA)",
                "Electrical Engineering (MBA)"
        };
        AutoCompleteTextView Branch = (AutoCompleteTextView) findViewById(R.id.Branch);
        ArrayAdapter<String> branch_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, allBranches);
        Branch.setAdapter(branch_adapter);
*/


        /**
         * CRUD Operations
         * */
        // Inserting Contacts
        Log.d("Insert: ", "Inserting ..");
        Log.d("Reading: ", "Reading all contacts..");
        //List<User> users = db.getAllUsers();

  /*      for (User cn : users) {
            String log = "Id: " + cn.getToken() + " ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber();
            // Writing Contacts to log
            Log.d("Name: ", log);
            Toast.makeText(getApplicationContext(),""+cn.get_email_id()+""+cn.get_password(),Toast.LENGTH_SHORT).show();
        }
*/


            btnSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(),login.class));

                    finish();
                }
            });

            btnSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final String email = inputEmail.getText().toString().trim();
                    final String password = inputPassword.getText().toString().trim();
                    final String name = inputName.getText().toString();

                    if (TextUtils.isEmpty(email)) {
                        Snackbar.make(getCurrentFocus(), "Enter e-mail address!", Snackbar.LENGTH_LONG).show();
                        return;
                    }

                    if (TextUtils.isEmpty(password)) {
                        Snackbar.make(getCurrentFocus(), "Enter password!", Snackbar.LENGTH_LONG).show();
                        return;
                    }

                    if (password.length() < 8) {
                        Snackbar.make(getCurrentFocus(), "Password too short, enter minimum 6 characters!", Snackbar.LENGTH_LONG).show();

                        return;
                    }
                    btnSignUp.startAnimation();
                    //startActivity(new Intent(signUp.this, verification.class));
                    //finish();

                    //create user
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(signUp.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    // Toast.makeText(signUp.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                    btnSignUp.revertAnimation();
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        Toasty.error(getApplicationContext(), "Failed to create user:", Toast.LENGTH_SHORT, true).show();
                                        }
                                    else {
                                        user = auth.getCurrentUser();
                                        user.sendEmailVerification().addOnCompleteListener(signUp.this, new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    String url = "http://192.168.137.1/Register.php?type_=0&tokenID="+inputRollNO.getText().toString()+"&email="+email.toString()+"&password="+password.toString()+"&image="+ about_me.picture;
                                                    MyAsyncTask mat = new MyAsyncTask();
                                                    mat.execute(url);

                                                    //if(message=="user is added".trim()){
                                                    startActivity(new Intent(signUp.this, verification.class));
                                                    finish();



                                                } else {
                                                    Toasty.error(getApplicationContext(), "Invalid e-mail address", Toast.LENGTH_SHORT, true).show();
                                                }
                                            }
                                        });

                                    }
                                }
                            });


                }
            });


        }

  /*  @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }*/

  //----------------------------HTTP CALL----------------------

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),login.class));
        finish();
    }

}
