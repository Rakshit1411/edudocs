package com.example.rakshitsharma.edutiate.Teacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rakshitsharma.edutiate.GetAllData.SaveSettings;
import com.example.rakshitsharma.edutiate.R;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.BlurTransformation;

public class about_teacher_me extends AppCompatActivity {

    ImageView header, pro;
    TextView name,room,phone,email,code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_teacher_me);
        name = (TextView)findViewById(R.id.teacher_profile_name);
        room = (TextView)findViewById(R.id.room);
        code = (TextView)findViewById(R.id.teacher_code);
        phone = (TextView)findViewById(R.id.phone);
        email = (TextView)findViewById(R.id.email);
        header = (ImageView)findViewById(R.id.header_cover_image) ;
        pro = (ImageView)findViewById(R.id.pro);
        name.setText(teacher_loadingData1.Teacher_name);
        room.setText(teacher_loadingData1.Teacher_room);
        phone.setText(teacher_loadingData1.Teacher_phone);
        email.setText(teacher_loadingData1.Teacher_email);
        code.setText(SaveSettings.userCode);
        if (!teacher_loadingData1.Teacher_image.isEmpty()) {
            Picasso.with(getApplicationContext()).load(teacher_loadingData1.Teacher_image).into(pro);
            Picasso.with(getApplicationContext()).load(teacher_loadingData1.Teacher_image)
                    .transform(new BlurTransformation(getApplicationContext())).into(header);
        }
       // Toast.makeText(getApplicationContext(),teacher_loadingData1.Teacher_name,Toast.LENGTH_LONG).show();
    }
    public void back(View v){
        startActivity(new Intent(this,teacher_loadingData1.class));
        finish();
    }

    public void signOut(View v){
        final SaveSettings saveSettings = new SaveSettings(getApplicationContext());
        saveSettings.erase();/*
                Intent i = new Intent(getActivity(),checker.class);
                startActivity(i);*/
        Intent i = getApplicationContext().getPackageManager()
                .getLaunchIntentForPackage( getApplicationContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

        finish();
    }
}
