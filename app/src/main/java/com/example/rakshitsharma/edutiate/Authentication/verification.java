package com.example.rakshitsharma.edutiate.Authentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rakshitsharma.edutiate.R;
import com.google.firebase.auth.FirebaseAuth;

public class verification extends AppCompatActivity {
Button loginback;
    public static TextView verification_text;
    public static TextView plz_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        loginback = (Button)findViewById(R.id.button2);
        verification_text = (TextView)findViewById(R.id.textView3);

        plz_login = (TextView)findViewById(R.id.textView5);
        verification_text.setText("A verification mail has been sent to your registered e-mail address");
        plz_login.setText("Please Login after verification");

        loginback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(verification.this,login.class));
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),login.class));
        finish();
    }
}
