package com.example.rakshitsharma.edutiate.Home.my_batches;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rakshitsharma.edutiate.Home.my_batches.batches_cards.my_batches_in_home;
import com.example.rakshitsharma.edutiate.Home.my_batches.batches_cards.my_batches_in_home_adapter;
import com.example.rakshitsharma.edutiate.Home.my_batches.details_of_the_batch.MysubjectsRecyclerViewAdapter;
import com.example.rakshitsharma.edutiate.Home.my_batches.details_of_the_batch.batch_details_pager;
import com.example.rakshitsharma.edutiate.Home.my_batches.details_of_the_batch.dummy.DummyContent;
import com.example.rakshitsharma.edutiate.Home.my_batches.details_of_the_batch.subjectsFragment;
import com.example.rakshitsharma.edutiate.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.rakshitsharma.edutiate.Home.my_batches.batches_cards.my_batches_in_home.index;

public class batchdetailsactivity extends AppCompatActivity implements subjectsFragment.OnListFragmentInteractionListener{
FragmentManager mFragmentManager;
    public static FloatingActionButton fab;
    final Context c = this;
    String new_subject;

    public static ArrayList sub_names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batchdetailsactivity);
        getSupportActionBar().setTitle(my_batches_in_home.names.get(my_batches_in_home_adapter.DataObjectHolder.title).toString());
        sub_names = new ArrayList<String>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sub_names.add("UTA-002");
        sub_names.add("UCS-405");
        sub_names.add("UMA-003");
        sub_names.add("UES-011");
        fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
        xfragmentTransaction.replace(R.id.frameLayout,new batch_details_pager()).commit();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                final View mView = layoutInflaterAndroid.inflate(R.layout.dialog_add_subject, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);

                final Spinner spinner = (Spinner)mView.findViewById(R.id.spinner);

                List<String> list = new ArrayList<String>();
                list.add("Choose one");
                list.add("UTA-002");
                list.add("Ucs-406");
                list.add("Uma-011");
                list.add("Ucs-222");
                list.add("Ues-010");
                list.add("Ues-011");
                list.add("EDS");
                list.add("UHU-003");
                list.add("UHU-010");
                list.add("Ues-011");
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(v.getContext(),android.R.layout.simple_spinner_item, list);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(dataAdapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        new_subject = spinner.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                // ToDo get user input here
                                sub_names.add(new_subject);
                                DummyContent.COUNT++;

                                DummyContent.createDummyItem(DummyContent.COUNT);
                                subjectsFragment.adapter.notifyItemInserted(1);
                                /*mFragmentManager = getSupportFragmentManager();
                                FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                                xfragmentTransaction.replace(R.id.frameLayout,new batch_details_pager()).commit();
*/


                                // DummyContent.createDummyItem(DummyContent.index+1);
//                                DummyContent.ITEMS.add(new DummyContent.DummyItem(String.valueOf(DummyContent.index), "" + batchdetailsactivity.sub_names.get(DummyContent.index).toString(), DummyContent.makeDetails(DummyContent.index)));

                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });
                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
            }
        });/*
        if(subjectsFragment.c=='r')
        {
            super.onBackPressed();
            subjectsFragment.c = 'x';
        }*/
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
}
