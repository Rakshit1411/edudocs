package com.example.rakshitsharma.edutiate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.rakshitsharma.edutiate.Authentication.login;
import com.example.rakshitsharma.edutiate.GetAllData.SaveSettings;
import com.example.rakshitsharma.edutiate.Teacher.teacher_loadingData1;
import com.example.rakshitsharma.edutiate.Teacher.teacher_login;
import com.google.firebase.auth.FirebaseAuth;

public class checker extends AppCompatActivity {

    String c1,c2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checker);
        final SaveSettings saveSettings = new SaveSettings(getApplicationContext());
        saveSettings.loadSettings();
        if(saveSettings.userID!="0")
        {
            startActivity(new Intent(checker.this,teacher_loadingData1.class));
            finish();
        }

        if (FirebaseAuth.getInstance().getCurrentUser() != null && FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
            Intent i = new Intent(checker.this,login.class);
            i.putExtra("email",FirebaseAuth.getInstance().getCurrentUser().getEmail().toString().trim());
            i.putExtra("password","i dont know");
            startActivity(i);
            finish();
        }

    }
    public void student(View v){
        startActivity(new Intent(this,login.class));
        finish();
    }
    public void teacher(View v){
        startActivity(new Intent(this,teacher_login.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

    }

}
