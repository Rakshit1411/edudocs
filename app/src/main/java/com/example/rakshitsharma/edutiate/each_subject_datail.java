package com.example.rakshitsharma.edutiate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rakshitsharma.edutiate.Home.my_batches.batchdetailsactivity;

import in.championswimmer.sfg.lib.SimpleFingerGestures;


public class each_subject_datail extends AppCompatActivity implements overview.OnFragmentInteractionListener {

    private TextView mTextMessage;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    private SimpleFingerGestures mysfg;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.overview:
                {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.framie,new overview()).commit();
                    return true;
                }
                case R.id.content:
                {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.framie,new overview()).commit();
                    return true;
                }
                case R.id.announcements:
                {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.framie,new overview()).commit();
                    return true;
                }
                case R.id.discussion:
                {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.framie,new overview()).commit();
                    return true;
                }

            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_subject_datail);
        mFragmentManager = getSupportFragmentManager();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mysfg = new SimpleFingerGestures();
        View view = View.inflate(getApplicationContext(), R.layout.activity_each_subject_datail,null);
        mysfg.setOnFingerGestureListener(new SimpleFingerGestures.OnFingerGestureListener() {
            @Override
            public boolean onSwipeUp(int i, long l, double v) {
                return false;
            }

            @Override
            public boolean onSwipeDown(int i, long l, double v) {
                return false;
            }

            @Override
            public boolean onSwipeLeft(int i, long l, double v) {
                return false;
            }

            @Override
            public boolean onSwipeRight(int i, long l, double v) {

                startActivity(new Intent(getApplicationContext(),batchdetailsactivity.class));
                finish();
                return true;
            }

            @Override
            public boolean onPinch(int i, long l, double v) {
                return false;
            }

            @Override
            public boolean onUnpinch(int i, long l, double v) {
                return false;
            }

            @Override
            public boolean onDoubleTap(int i) {
                return false;
            }
        });
        mFragmentTransaction = mFragmentManager.beginTransaction();
        FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
        view.setOnTouchListener(mysfg);

        xfragmentTransaction.replace(R.id.framie,new overview()).commit();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        //getCurrentFocus().setTransitionName("trans");
        super.onBackPressed();
    }
}
