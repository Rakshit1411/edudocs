package com.example.rakshitsharma.edutiate;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.transition.Fade;
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
import android.widget.Toast;

import com.example.rakshitsharma.edutiate.Authentication.login;
import com.example.rakshitsharma.edutiate.GetAllData.loadingData1;
import com.example.rakshitsharma.edutiate.Home.my_batches.batches_cards.my_batches_in_home;
import com.example.rakshitsharma.edutiate.Settings.settingsFragment;
import com.example.rakshitsharma.edutiate.Teacher.teacher_dummyContent;
import com.example.rakshitsharma.edutiate.Teacher.teacherhomeitemFragment;
import com.example.rakshitsharma.edutiate.notifications.DummyContent;
import com.example.rakshitsharma.edutiate.notifications.notification;

import java.io.File;


public class MainActivity extends AppCompatActivity implements teacherhomeitemFragment.OnListFragmentInteractionListener,my_batches_in_home.OnFragmentInteractionListener,settingsFragment.OnFragmentInteractionListener,notification.OnListFragmentInteractionListener {

FrameLayout content1,content2,content3;
    FragmentManager mFragmentManager;

    FragmentTransaction mFragmentTransaction;
    public static ViewGroup v2;
    int counta=0,countb=0,countc=0;
    BottomNavigationView navigation;
    public static String token;
    String user,password;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {



            switch (item.getItemId()) {
                case R.id.navigation_home:
                {
                    counta++;
                    Fade fade = new Fade();
                    fade.setDuration(300);
                    //TransitionManager.beginDelayedTransition(v2,fade);

                   // Toast.makeText(getApplicationContext(),loadingData1.typo,Toast.LENGTH_LONG).show();

                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    content1 = (FrameLayout)findViewById(R.id.content);
                    xfragmentTransaction.replace(R.id.content,new my_batches_in_home()).commit();
                    if(Build.VERSION.SDK_INT >=21 && counta!=1) {
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





                    return true;
                }

                case R.id.navigation_dashboard:
                { Fade fade = new Fade();
                    fade.setDuration(500);
                  //  TransitionManager.beginDelayedTransition(v2,fade);
                    countb++;
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    content3 = (FrameLayout)findViewById(R.id.content);
                    xfragmentTransaction.replace(R.id.content,new settingsFragment()).commit();
                    if(Build.VERSION.SDK_INT >=21 && countb!=1) {
                        // previously invisible view

// get the center for the clipping circle
                        int cx = content3.getWidth();
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


                    return true;
                }
                case R.id.navigation_notifications:
                { Fade fade = new Fade();
                    fade.setDuration(500);
//                    TransitionManager.beginDelayedTransition(v2,fade);
                    countc++;
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();

                    content2 = (FrameLayout)findViewById(R.id.content);
                    xfragmentTransaction.replace(R.id.content,new notification()).commit();
                    if(Build.VERSION.SDK_INT >=21 && countc!=1) {
                        // previously invisible view

// get the center for the clipping circle
                        int cx = content2.getWidth()/2;
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
        getSupportActionBar().setIcon(R.drawable.logo);
        xfragmentTransaction.replace(R.id.content,new my_batches_in_home()).commit();
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //Toast.makeText(getApplicationContext(),loadingData1.typo,Toast.LENGTH_LONG).show();

        startService(new Intent(this,noti.class));



        //  List<batch> batchs = db1.getAllBatches();

       /* for (batch cn : batchs) {
            Toast.makeText(getApplicationContext(),""+cn.get_name()+""+cn.get_institute(), Toast.LENGTH_SHORT).show();
        }
        for(int i=0;i<5;i++)
        Toast.makeText(getApplicationContext()," "+db1.getBatchesCount(),Toast.LENGTH_LONG).show();
   */
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        }
        else if(dir!= null && dir.isFile())
            return dir.delete();
        else {
            return false;
        }
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(teacher_dummyContent.DummyItem item) {

    }
}
