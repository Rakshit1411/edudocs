package com.example.rakshitsharma.edutiate.Home.my_batches.batches_cards.teacher_of_batch;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rakshitsharma.edutiate.Home.my_batches.batches_cards.my_batches_in_home_adapter;
import com.example.rakshitsharma.edutiate.Home.my_batches.batches_cards.teacher_of_batch.ItemdocsFragment.OnListFragmentInteractionListener;
import com.example.rakshitsharma.edutiate.R;
import com.example.rakshitsharma.edutiate.dummy1.DummyContent.DummyItem;
import com.example.rakshitsharma.edutiate.GetAllData.loadingData1;
import com.example.rakshitsharma.edutiate.GetAllData.loadingData2;
import com.squareup.picasso.Picasso;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemdocsRecyclerViewAdapter extends RecyclerView.Adapter<MyItemdocsRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    DownloadManager dm;
    public MyItemdocsRecyclerViewAdapter(List<DummyItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_itemdocs, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        final int pos = position;
        holder.postDate.setText(""+ loadingData2.doc_postDate.get(position));
        holder.postText.setText(""+loadingData2.doc_postText.get(position));
        holder.postName.setText(""+ loadingData1.Teacher.get(my_batches_in_home_adapter.cardNumber).toString());
        if(loadingData1.Teacher_image.get(my_batches_in_home_adapter.cardNumber).toString()!="null")
            Picasso.with(holder.mView.getContext()).load(loadingData1.Teacher_image.get(my_batches_in_home_adapter.cardNumber).toString()).into(holder.img);
        holder.down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }

        });
        holder.down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://192.168.137.1/"+ loadingData2.doc_postURL.get(pos);
                dm = (DownloadManager)holder.mView.getContext().getSystemService(DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(url.trim());
                DownloadManager.Request request = new DownloadManager.Request(uri);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                }
                Long reference = dm.enqueue(request);
            }
        });
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return loadingData2.doc_postText.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView postDate,postText,postName;
        public final ImageView img,down;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            postDate = (TextView)view.findViewById(R.id.date);
            postText = (TextView)view.findViewById(R.id.postText);
            postName = (TextView)view.findViewById(R.id.postName);
            img = (ImageView)view.findViewById(R.id.teacherView1);
            down = (ImageView)view.findViewById(R.id.down);

        }


    }
}
