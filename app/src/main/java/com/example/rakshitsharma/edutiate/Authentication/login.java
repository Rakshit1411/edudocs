package com.example.rakshitsharma.edutiate.Authentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.rakshitsharma.edutiate.MainActivity;
import com.example.rakshitsharma.edutiate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.quickblox.auth.QBAuth;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

public class login extends AppCompatActivity {
    public static EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;
    RadioGroup user_type;
    static final String APP_ID = "58696";
    static final String AUTH_KEY= "bRLpxHfCm3hULSt";
    static final String AUTH_SECRET = "mSaSV4KzQnzgZh9";
    static final String ACCOUNT_KEY = "BpJLmNq9dj9XjL3JaWAY";
    RadioButton typer;
    String email;
    public static String userType_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        initializeFramework();
        user=auth.getCurrentUser();

        if (auth.getCurrentUser() != null && user.isEmailVerified()) {
            startActivity(new Intent(login.this, MainActivity.class));
            finish();
        }

            inputEmail = (EditText) findViewById(R.id.email);
            inputPassword = (EditText) findViewById(R.id.password);
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            btnSignup = (Button) findViewById(R.id.btn_signup);
            btnLogin = (Button) findViewById(R.id.btn_login);
            btnReset = (Button) findViewById(R.id.btn_reset_password);




            btnSignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(login.this, signUp.class));
                }
            });

            btnReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(login.this, forgot_password.class));
                }
            });

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    email = inputEmail.getText().toString();
                    final String password = inputPassword.getText().toString();

                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(password)) {
                        Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    progressBar.setVisibility(View.VISIBLE);

                    //authenticate user

                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    progressBar.setVisibility(View.GONE);
                                    user = auth.getCurrentUser();
                                    if (!task.isSuccessful()) {
                                        // there was an error
                                        if (password.length() < 6) {
                                            inputPassword.setError("Minimum length of password should be 6");
                                        } else {
                                            Toast.makeText(login.this, "Wrong Credentials", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    else if(!user.isEmailVerified())
                                    {
                                        inputEmail.setError("Your Account has not been verified");
                                    }
                                    else {
                                        QBUser qbUser = new QBUser(email,password);
                                        QBUsers.signIn(qbUser).performAsync(new QBEntityCallback<QBUser>() {
                                            @Override
                                            public void onSuccess(QBUser qbUser, Bundle bundle) {

                                            }

                                            @Override
                                            public void onError(QBResponseException e) {

                                            }
                                        });

                                        Intent intent = new Intent(login.this, MainActivity.class);
                                        intent.putExtra("email",email);
                                        intent.putExtra("password",password);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }
            });
        }
        private void initializeFramework() {
            QBSettings.getInstance().init(getApplicationContext(),APP_ID,AUTH_KEY,AUTH_SECRET);
            QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);
        }

}
