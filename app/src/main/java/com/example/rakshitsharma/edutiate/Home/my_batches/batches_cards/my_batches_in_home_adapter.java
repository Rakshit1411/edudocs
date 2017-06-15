package com.example.rakshitsharma.edutiate.Home.my_batches.batches_cards;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.rakshitsharma.edutiate.R;
import com.example.rakshitsharma.edutiate.Home.my_batches.batchdetailsactivity;

import java.util.ArrayList;


/**
 * Created by Rakshit Sharma on 4/4/2017.
 */

public class my_batches_in_home_adapter extends RecyclerView
        .Adapter<my_batches_in_home_adapter
        .DataObjectHolder>  {

    private static String LOG_TAG = "my_batches_adapter";
    private ArrayList<my_batches_in_home_object> mDataset;
    private static my_batches_in_home_adapter.MyClickListener myClickListener;
    private static CardView cv;
    public static TextView institute;

    public static TextView subjects;
    public static TextView name_inBatch;



    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {

public static int title;
        public DataObjectHolder(final View itemView) {
            super(itemView);
            institute = (TextView) itemView.findViewById(R.id.institute);
            subjects = (TextView) itemView.findViewById(R.id.subjects);
            name_inBatch = (TextView) itemView.findViewById(R.id.name_inBatch);


            Log.i(LOG_TAG, "Adding Listener");
            // cv = (CardView)itemView.findViewById(R.id.card_view);

            final String trans = itemView.getContext().getString(R.string.transition);

//-------------Shared element------------
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //  View viewStart = itemView.findViewById(R.id.card_view);
                    title=getAdapterPosition();

                    //v.setTransitionName("trans");

                    //ActivityOptionsCompat options =
                      //      ActivityOptionsCompat.makeSceneTransitionAnimation((Activity)itemView.getContext(), viewStart,v.getTransitionName());
                    //Intent intent = new Intent(itemView.getContext(),batchdetailsactivity.class);
                   //itemView.getContext().startActivity(intent,options.toBundle());
                    Intent intent = new Intent(itemView.getContext(),batchdetailsactivity.class);
                    itemView.getContext().startActivity(intent);

                    }
            });
        }
//---------------shared element-------------


        @Override
        public void onClick(View v) {

            myClickListener.onItemClick(getAdapterPosition(), v);
            //pos=getAdapterPosition();
        }
    }

    public void setOnItemClickListener(my_batches_in_home_adapter.MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public my_batches_in_home_adapter(ArrayList<my_batches_in_home_object> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_batch_card, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;

    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        institute.setText(mDataset.get(position).getmText1());
        name_inBatch.setText(mDataset.get(position).getmText2());
        subjects.setText(mDataset.get(position).getmText3());



    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void addItem(my_batches_in_home_object dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }


    public interface MyClickListener {
        public void onItemClick(int position, View v);

    }
}
