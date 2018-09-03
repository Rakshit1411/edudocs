package com.example.rakshitsharma.edutiate;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rakshitsharma.edutiate.GetAllData.SaveSettings;
import com.example.rakshitsharma.edutiate.Teacher.teacherView_Details_of_Batch_noAttendance;
import com.example.rakshitsharma.edutiate.Teacher.teacher_loadingData1;
import com.example.rakshitsharma.edutiate.Teacher.teacher_loadingData2;
import com.example.rakshitsharma.edutiate.teacherViewDetailsofBatchDocsitemFragment.OnListFragmentInteractionListener;
import com.example.rakshitsharma.edutiate.dummy.DummyContent.DummyItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyteacherViewDetailsofBatchDocsitemRecyclerViewAdapter extends RecyclerView.Adapter<MyteacherViewDetailsofBatchDocsitemRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyteacherViewDetailsofBatchDocsitemRecyclerViewAdapter(List<DummyItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_teacherviewdetailsofbatchdocsitem, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
       // holder.mIdView.setText(mValues.get(position).id);
       // holder.mContentView.setText(mValues.get(position).content);

        holder.post_date.setText(teacher_loadingData2.doc_postDate.get(position).toString());
        holder.post_text.setText(teacher_loadingData2.doc_postText.get(position).toString());
        holder.t_name.setText(SaveSettings.userCode);
        if (!teacher_loadingData1.Teacher_image.isEmpty()) {
            Picasso.with(holder.mView.getContext()).load(teacher_loadingData1.Teacher_image).into(holder.pro);
        }
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
        return teacher_loadingData2.doc_postText.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView t_name;
        public final TextView post_text;
        public final TextView post_date;
        public final ImageView pro;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
           t_name = (TextView)view.findViewById(R.id.postName);
           post_text = (TextView)view.findViewById(R.id.postText);
           post_date = (TextView)view.findViewById(R.id.date);
           pro = (ImageView) view.findViewById(R.id.teacherView1);
        }

        /*@Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    */}
}
