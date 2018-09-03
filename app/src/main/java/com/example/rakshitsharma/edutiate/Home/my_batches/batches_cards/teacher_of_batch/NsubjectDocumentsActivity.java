package com.example.rakshitsharma.edutiate.Home.my_batches.batches_cards.teacher_of_batch;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rakshitsharma.edutiate.GetAllData.loadingData1;
import com.example.rakshitsharma.edutiate.Home.my_batches.batches_cards.my_batches_in_home_adapter;
import com.example.rakshitsharma.edutiate.NoItemdocsFragment;
import com.example.rakshitsharma.edutiate.R;
import com.example.rakshitsharma.edutiate.Settings.settingsFragment;
import com.example.rakshitsharma.edutiate.Teacher.teacher_loadingData2;
import com.example.rakshitsharma.edutiate.dummy1.DummyContent;
import com.example.rakshitsharma.edutiate.GetAllData.loadingData2;
import com.example.rakshitsharma.edutiate.subject_details.overview;

public class NsubjectDocumentsActivity extends AppCompatActivity implements NoItemdocsFragment.OnFragmentInteractionListener,ItemdocsFragment.OnListFragmentInteractionListener,overview.OnFragmentInteractionListener,settingsFragment.OnFragmentInteractionListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private SectionsPagerAdapterNoitem mSectionsPagerAdapterNoitem;
    public static int viewNumber;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nsubject_documents);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(my_batches_in_home_adapter.title_subject);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        viewNumber = loadingData2.viewNumber;


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsPagerAdapterNoitem = new SectionsPagerAdapterNoitem(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        if(loadingData2.doc_postText.size()!=0)
            mViewPager.setAdapter(mSectionsPagerAdapter);
        else
            mViewPager.setAdapter(mSectionsPagerAdapterNoitem);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nsubject_documents, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == android.R.id.home){
            //Toast.makeText(getApplicationContext(),""+loadingData2.doc_postDate.size(),Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,loadingData1.class));
            finish();
            super.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }
        int choice=0;
        View rootView;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    overview tab1 = new overview();
                    return tab1;
                case 1:
                    ItemdocsFragment tab3 = new ItemdocsFragment();
                    return tab3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Instructor";
                case 1:
                    return "My Documents";
            }
            return null;
        }
    }


    public class SectionsPagerAdapterNoitem extends FragmentPagerAdapter {

        public SectionsPagerAdapterNoitem(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    overview tab1 = new overview();
                    return tab1;
                case 1:
                    NoItemdocsFragment tab3 = new NoItemdocsFragment();
                    return tab3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Instructor";
                case 1:
                    return "My Documents";
            }
            return null;
        }
    }
}
