package com.example.rakshitsharma.edutiate.Home.my_batches.details_of_the_batch.batch_subjects;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rakshitsharma.edutiate.Home.my_batches.details_of_the_batch.batch_subjects.dummy.DummyContent.DummyItem;
import com.example.rakshitsharma.edutiate.R;
import com.example.rakshitsharma.edutiate.subject_details.each_subject_details;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link subjectsFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MysubjectsRecyclerViewAdapter extends RecyclerView.Adapter<MysubjectsRecyclerViewAdapter.ViewHolder> {

    private List<DummyItem> mValues;
    private final subjectsFragment.OnListFragmentInteractionListener mListener;

    public MysubjectsRecyclerViewAdapter(List<DummyItem> items, subjectsFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_subjects, parent, false);

        return new ViewHolder(view);
    }
public static String list_sub_name;
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.name.setText(mValues.get(position).content);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    View viewStart = holder.mView;
                    mListener.onListFragmentInteraction(holder.mItem);
                    list_sub_name=holder.name.getText().toString();

                    v.setTransitionName("trans");
                    ActivityOptionsCompat options =
                            ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) v.getContext(), viewStart,v.getTransitionName());
                    Intent intent = new Intent(v.getContext(), each_subject_details.class);
                    v.getContext().startActivity(intent,options.toBundle());

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
        public final TextView name;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            name = (TextView) view.findViewById(R.id.sub_name);



        }

        @Override
        public String toString() {
            return super.toString() + " '" + name.getText() + "'";
        }
    }
}
