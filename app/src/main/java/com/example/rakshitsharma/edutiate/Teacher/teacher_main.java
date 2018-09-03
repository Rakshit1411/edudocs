package com.example.rakshitsharma.edutiate.Teacher;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rakshitsharma.edutiate.R;
import com.example.rakshitsharma.edutiate.Settings.settingsFragment;
import com.example.rakshitsharma.edutiate.Teacher.dummy2.teacher_noti_dummy;
import com.example.rakshitsharma.edutiate.notifications.DummyContent;
import com.example.rakshitsharma.edutiate.notifications.notification;
import com.squareup.picasso.Picasso;

public class teacher_main extends AppCompatActivity implements teacher_Settings.OnFragmentInteractionListener,teachernotificationitemFragment.OnListFragmentInteractionListener,teacherhomeitemFragment.OnListFragmentInteractionListener,settingsFragment.OnFragmentInteractionListener,notification.OnListFragmentInteractionListener {
    FrameLayout content1,content2,content3;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    //content1 = (FrameLayout)findViewById(R.id.content);
    private TextView mTextMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);


        mTextMessage = (TextView) findViewById(R.id.message);

        //menu.getItem(0).setIcon(ContextCompat.getDrawable(this, ));

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        //Toast.makeText(getApplicationContext(),""+ teacher_loadingData1.Teacher_name,Toast.LENGTH_LONG).show();
        FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
        xfragmentTransaction.replace(R.id.content,new teacherhomeitemFragment()).commit();
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.logo);
    }

    @Override
    public void onListFragmentInteraction(teacher_dummyContent.DummyItem item) {

    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(teacher_noti_dummy.DummyItem item) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.teacher_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.teacher_profile:
                startActivity(new Intent(this,about_teacher_me.class));
                finish();
                return true;
            default: return super.onOptionsItemSelected(item);

        }

    }
}
