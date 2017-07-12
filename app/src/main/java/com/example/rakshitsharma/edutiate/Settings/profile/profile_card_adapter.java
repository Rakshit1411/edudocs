package com.example.rakshitsharma.edutiate.Settings.profile;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rakshitsharma.edutiate.R;

import java.util.ArrayList;

/**
 * Created by Rakshit Sharma on 4/2/2017.
 */

public class profile_card_adapter extends RecyclerView
        .Adapter<profile_card_adapter
        .DataObjectHolder> {

    //-----for adapter of cards

        private static String LOG_TAG = "profile_card_adapter";
        private ArrayList<profile_object> mDataset;
        private static MyClickListener myClickListener;

        public static class DataObjectHolder extends RecyclerView.ViewHolder
                implements View
                .OnClickListener {
            TextView label;
            TextView dateTime;

            public DataObjectHolder(View itemView) {
                super(itemView);
                label = (TextView) itemView.findViewById(R.id.textView);
                dateTime = (TextView) itemView.findViewById(R.id.textView2);

                Log.i(LOG_TAG, "Adding Listener");
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                myClickListener.onItemClick(getAdapterPosition(), v);
            }
        }

        public void setOnItemClickListener(MyClickListener myClickListener) {
            this.myClickListener = myClickListener;
        }

        public profile_card_adapter(ArrayList<profile_object> myDataset) {
            mDataset = myDataset;
        }

        @Override
        public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType)
        {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.profile_card, parent, false);

            DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
            return dataObjectHolder;
        }

        @Override
        public void onBindViewHolder(DataObjectHolder holder, int position) {
            holder.label.setText(mDataset.get(position).getmText1());
            holder.dateTime.setText(mDataset.get(position).getmText2());
        }

        public void addItem(profile_object dataObj, int index) {
            mDataset.add(index, dataObj);
            notifyItemInserted(index);
        }

        public void deleteItem(int index) {
            mDataset.remove(index);
            notifyItemRemoved(index);
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }

        public interface MyClickListener {
            public void onItemClick(int position, View v);
        }
    }


