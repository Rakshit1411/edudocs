package com.example.rakshitsharma.edutiate;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.transition.Fade;
import android.support.transition.TransitionManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.rakshitsharma.edutiate.Chatting.ChatDialogsActivity;
import com.example.rakshitsharma.edutiate.Home.my_batches.batches_cards.my_batches_in_home;
import com.example.rakshitsharma.edutiate.Settings.settingsFragment;
import com.example.rakshitsharma.edutiate.notifications.DummyContent;
import com.example.rakshitsharma.edutiate.notifications.notification;


public class MainActivity extends AppCompatActivity implements settingsFragment.OnFragmentInteractionListener,notification.OnListFragmentInteractionListener,my_batches_in_home.OnFragmentInteractionListener {

FrameLayout content1,content2,content3;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    public static ViewGroup v2;
    int count=1;
    String user,password;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                {
                    Fade fade = new Fade();
                    fade.setDuration(300);
                    //TransitionManager.beginDelayedTransition(v2,fade);

                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    content1 = (FrameLayout)findViewById(R.id.content);
                    xfragmentTransaction.replace(R.id.content,new my_batches_in_home()).commit();

                    if(Build.VERSION.SDK_INT >=21) {
                        // previously invisible view

// get the center for the clipping circle
                        int cx = content1.getWidth() / 8;
                        int cy = content1.getHeight();

// get the final radius for the clipping circle
                        float finalRadius = (float) Math.hypot(cx, cy);

// create the animator for this view (the start radius is zero)
                        Animator anim =
                                ViewAnimationUtils.createCircularReveal(content1, cx, cy, 0, finalRadius);

// make the view visible and start the animation
                        content1.setVisibility(View.VISIBLE);
                        anim.start();


                    }



                    count=1;

                    return true;
                }

                case R.id.navigation_dashboard:
                { Fade fade = new Fade();
                    fade.setDuration(500);
                  //  TransitionManager.beginDelayedTransition(v2,fade);
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    content3 = (FrameLayout)findViewById(R.id.content);
                    xfragmentTransaction.replace(R.id.content,new settingsFragment()).commit();

                    if(Build.VERSION.SDK_INT >=21) {
                        // previously invisible view

// get the center for the clipping circle
                        int cx = content3.getWidth() / 2;
                        int cy = content3.getHeight();

// get the final radius for the clipping circle
                        float finalRadius = (float) Math.hypot(cx, cy);

// create the animator for this view (the start radius is zero)
                        Animator anim =
                                ViewAnimationUtils.createCircularReveal(content3, cx, cy, 0, finalRadius);

// make the view visible and start the animation
                        content3.setVisibility(View.VISIBLE);
                        anim.start();


                    }


                    count=3;

                    return true;
                }
                case R.id.navigation_notifications:
                { Fade fade = new Fade();
                    fade.setDuration(500);
//                    TransitionManager.beginDelayedTransition(v2,fade);
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();

                    content2 = (FrameLayout)findViewById(R.id.content);
                    xfragmentTransaction.replace(R.id.content,new notification()).commit();

                    if(Build.VERSION.SDK_INT >=21) {
                        // previously invisible view

// get the center for the clipping circle
                        int cx = content2.getWidth();
                        int cy = content2.getHeight();

// get the final radius for the clipping circle
                        float finalRadius = (float) Math.hypot(cx, cy);

// create the animator for this view (the start radius is zero)
                        Animator anim =
                                ViewAnimationUtils.createCircularReveal(content2, cx, cy, 0, finalRadius);

// make the view visible and start the animation
                        content2.setVisibility(View.VISIBLE);
                        anim.start();


                    }


                    count=2;

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
        v2 = (ViewGroup)findViewById(R.id.content);
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
