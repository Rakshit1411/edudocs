package com.example.rakshitsharma.edutiate.Teacher;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.rakshitsharma.edutiate.R;

import java.util.List;
import java.util.Random;

public class MyteacherhomeitemRecyclerViewAdapter extends RecyclerView.Adapter<MyteacherhomeitemRecyclerViewAdapter.ViewHolder> {

    private final List<teacher_dummyContent.DummyItem> mValues;
    private final teacherhomeitemFragment.OnListFragmentInteractionListener mListener;
    public static int cardNumber;
    CardView cv;
    public static int teacher_card;
    public MyteacherhomeitemRecyclerViewAdapter(List<teacher_dummyContent.DummyItem> items, teacherhomeitemFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_teacherhomeitem, parent, false);

        cv = (CardView)view.findViewById(R.id.card_view);
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int randomColor = generator.getRandomColor();
        Random rand = new Random();
        int x = rand.nextInt(17);
        cv.setCardBackgroundColor(Color.parseColor(teacherhomeitemFragment.col.get(x).toString()));


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
//        holder.mIdView.setText("abc");
  //      holder.mContentView.setText("pqr");
        holder.subCode.setText(teacher_loadingData1.teacher_subCode.get(position).toString());
        holder.branch_group.setText(teacher_loadingData1.teacher_branchGroup.get(position).toString());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    cardNumber = holder.getLayoutPosition();
                   // Toast.makeText(holder.mView.getContext(),""+holder.getAdapterPosition(),Toast.LENGTH_SHORT).show();
                    teacher_card = holder.getAdapterPosition();
                    Intent i = new Intent(holder.mView.getContext(),teacher_loadingData2.class);
                    holder.mView.getContext().startActivity(i);

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
        public final TextView branch_group;
        public final TextView subCode;
        public teacher_dummyContent.DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            branch_group = (TextView) view.findViewById(R.id.batchName);
            subCode = (TextView) view.findViewById(R.id.subCode);
          //  Toast.makeText(view.getContext(),getLayoutPosition(),Toast.LENGTH_SHORT).show();


        }

        /*@Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }*/
    }
}
