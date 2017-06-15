package com.example.rakshitsharma.edutiate.Home.my_batches.details_of_the_batch;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.rakshitsharma.edutiate.Home.my_batches.batchdetailsactivity;
import com.example.rakshitsharma.edutiate.R;

public class batch_details_pager extends Fragment {

    public static TabLayout tabLayout;
  //  public static ViewPager viewPager;
    public static int int_items = 3 ;
    TabItem tab1;
    TabItem tab2;
    TabItem tab3;

    FragmentManager mFragmentManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
        View x =  inflater.inflate(R.layout.activity_batch_details_pager,null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
   //     viewPager = (ViewPager) x.findViewById(R.id.viewpager);

        tab1=(TabItem)x.findViewById(R.id.tabItem);
        tab2=(TabItem)x.findViewById(R.id.tabItem2);
        tab3=(TabItem)x.findViewById(R.id.tabItem3);
        /**
         *
         *Set an Adapater for the View Pager
         */
  //      viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */


  /*      viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position==0)
                    batchdetailsactivity.fab.setVisibility(View.VISIBLE);
                if(position==1 || position==2)
                    batchdetailsactivity.fab.setVisibility(View.INVISIBLE);
                }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

*/
       // viewPager.beginFakeDrag();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0)
                {
                    mFragmentManager = getFragmentManager();
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.framee,new subjectsFragment()).commit();
                }
                else if(tab.getPosition()==1)
                {
                    mFragmentManager = getFragmentManager();
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.framee,new subjectsFragment()).commit();
                }
                else
                {
                    mFragmentManager = getFragmentManager();
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.framee,new subjectsFragment()).commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


      /*  tabLayout.post(new Runnable() {
            @Override
            public void run() {
      //          tabLayout.setupWithViewPager(viewPager);
            }
        });
        */mFragmentManager = getFragmentManager();
        FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
        xfragmentTransaction.replace(R.id.framee,new subjectsFragment()).commit();

        return x;

    }


    class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 : return new subjectsFragment();
                case 1 : return new subjectsFragment();
                case 2 : return new subjectsFragment();
            }
            return null;
        }


        @Override
        public int getCount() {

            return int_items;

        }

        /*
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "Subjects";
                case 1 :
                    return "Discussions";
                case 2 :
                    return "Announcements";
            }

            return null;
        }

    }



}
