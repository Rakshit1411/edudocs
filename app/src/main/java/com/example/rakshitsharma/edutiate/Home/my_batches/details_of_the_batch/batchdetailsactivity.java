package com.example.rakshitsharma.edutiate.Home.my_batches.details_of_the_batch;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.rakshitsharma.edutiate.Home.my_batches.batches_cards.my_batches_in_home;
import com.example.rakshitsharma.edutiate.Home.my_batches.batches_cards.my_batches_in_home_adapter;
import com.example.rakshitsharma.edutiate.Home.my_batches.details_of_the_batch.batch_announcement.batch_announcementFragment;
import com.example.rakshitsharma.edutiate.Home.my_batches.details_of_the_batch.batch_details_pager;
import com.example.rakshitsharma.edutiate.Home.my_batches.details_of_the_batch.batch_subjects.dummy.DummyContent;
import com.example.rakshitsharma.edutiate.Home.my_batches.details_of_the_batch.batch_subjects.subjectsFragment;
import com.example.rakshitsharma.edutiate.R;

import java.util.ArrayList;

public class batchdetailsactivity extends AppCompatActivity implements subjectsFragment.OnListFragmentInteractionListener, batch_announcementFragment.OnListFragmentInteractionListener {
FragmentManager mFragmentManager;
    final Context c = this;
    String new_subject;
    public static FrameLayout framee;
    public static int w,h;
    public static ArrayList sub_names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batchdetailsactivity);
        getSupportActionBar().setTitle(my_batches_in_home.names.get(my_batches_in_home_adapter.DataObjectHolder.title).toString());
        sub_names = new ArrayList<String>();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        sub_names.add("UTA-002");
        sub_names.add("UCS-405");
        sub_names.add("UMA-003");
        sub_names.add("UES-011");
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
        xfragmentTransaction.replace(R.id.frameLayout,new batch_details_pager()).commit();


    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }


    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    @Override
    public void onListFragmentInteraction(com.example.rakshitsharma.edutiate.Home.my_batches.details_of_the_batch.batch_announcement.dummy.DummyContent.DummyItem item) {

    }
}
