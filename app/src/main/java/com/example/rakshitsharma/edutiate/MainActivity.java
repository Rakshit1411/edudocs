package com.example.rakshitsharma.edutiate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.rakshitsharma.edutiate.Chatting.ChatDialogsActivity;
import com.example.rakshitsharma.edutiate.Home.my_batches.batches_cards.my_batches_in_home;
import com.example.rakshitsharma.edutiate.Settings.settingsFragment;
import com.example.rakshitsharma.edutiate.notifications.DummyContent;
import com.example.rakshitsharma.edutiate.notifications.notification;

public class MainActivity extends AppCompatActivity implements settingsFragment.OnFragmentInteractionListener,notification.OnListFragmentInteractionListener,my_batches_in_home.OnFragmentInteractionListener {


    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    String user,password;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.content,new my_batches_in_home()).commit();

                    return true;
                }

                case R.id.navigation_dashboard:
                {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.content,new settingsFragment()).commit();

                    return true;
                }
                case R.id.navigation_notifications:
                {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.content,new notification()).commit();
                    return true;

                }

            }
            return false;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        user = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.action_bar_icon);
        xfragmentTransaction.replace(R.id.content,new my_batches_in_home()).commit();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_refresh:
                user = getIntent().getStringExtra("email");
                password = getIntent().getStringExtra("password");
                Intent i = new Intent(this,ChatDialogsActivity.class);
                i.putExtra("email",user);
                i.putExtra("password",password);
                startActivity(i);

                break;

            default:
                break;
        }

        return true;
    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
