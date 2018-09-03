package com.example.rakshitsharma.edutiate.notifications;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.rakshitsharma.edutiate.GetAllData.loadingData2;
import com.example.rakshitsharma.edutiate.R;
import com.example.rakshitsharma.edutiate.GetAllData.loadingData1;
import com.example.rakshitsharma.edutiate.notifications.notification.OnListFragmentInteractionListener;
import com.example.rakshitsharma.edutiate.notifications.DummyContent.DummyItem;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.List;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {
    private final List<DummyItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    DownloadManager dm;
    TextDrawable drawable;
    public MyItemRecyclerViewAdapter(List<DummyItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        //holder.mIdView.setText(mValues.get(position).id);
        int p = loadingData1.Teacher_code.lastIndexOf(loadingData1.all_doc_postTeacherCode.get(position));
        holder.postDate.setText(""+ loadingData1.all_doc_postDate.get(position));
        holder.postText.setText(""+loadingData1.all_doc_postText.get(position));
        holder.postName.setText(""+loadingData1.Teacher.get(p));
        holder.postText.setText(""+loadingData1.all_doc_postText.get(position));
        holder.postSubCode.setText(""+loadingData1.all_doc_postSubCode.get(position));
        if(loadingData1.Teacher_image.get(p).toString()!="null")
            Picasso.with(holder.mView.getContext()).load(loadingData1.Teacher_image.get(p).toString()).into(holder.img);

        holder.down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://192.168.137.1/"+ loadingData1.all_doc_postURL.get(position);
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
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView postDate,postText,postName,postSubCode;
        public final ImageView img,down;
        //public final TextView mIdView;
        //public final TextView mContentView;

        public DummyItem mItem;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            postDate = (TextView)view.findViewById(R.id.date);
            postSubCode = (TextView)view.findViewById(R.id.subCode);
            postText = (TextView)view.findViewById(R.id.postText);
            postName = (TextView)view.findViewById(R.id.postName);
            img = (ImageView)view.findViewById(R.id.teacherView1);
            down = (ImageView)view.findViewById(R.id.down);

        }
        //@Override
       /* public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }*/


    }
}

