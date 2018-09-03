package com.example.rakshitsharma.edutiate.Home.my_batches.batches_cards;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rakshitsharma.edutiate.R;
import com.example.rakshitsharma.edutiate.GetAllData.loadingData1;

import java.util.ArrayList;

public class my_batches_in_home extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    public static ViewGroup v1;
    public static CardView cv1;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "RecyclerViewActivity";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static my_batches_in_home_object obj;
    private OnFragmentInteractionListener mListener;

    public my_batches_in_home() {
        // Required empty public constructorfinal
    }

    // TODO: Rename and change types and number of parameters
    public static my_batches_in_home newInstance(String param1, String param2) {
        my_batches_in_home fragment = new my_batches_in_home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_my_batches_in_home, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        cv1 = (CardView)v.findViewById(R.id.card_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new my_batches_in_home_adapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);
        v1 = (ViewGroup)v.findViewById(R.id.recycler_view);
        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        ((my_batches_in_home_adapter) mAdapter).setOnItemClickListener(new my_batches_in_home_adapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(LOG_TAG, " Clicked on Item " + position);

            }
        });
    }
public static int index;
    public static String col[] = new String[17];
    private ArrayList<my_batches_in_home_object> getDataSet() {
        ArrayList results = new ArrayList<my_batches_in_home_object>();
        col[0]="#955170";
        col[1]="#43698f";
        col[2]="#48A360";
        col[3]="#77917a";
        col[4]="#b07a7f";
        col[5]="#5277A8";
        col[6]="#60605C";
        col[7]="#2D7D8F";
        col[8]="#60605C";
        col[9]="#B08C54";
        col[10]="#BE2D61";
        col[11]="#7E7D42";
        col[12]="#228A92";
        col[13]="#A53D3D";
        col[14]="#575D80";
        col[15]="#5A6ABA";
        col[16]="#A68572";
        //Toast.makeText(getActivity(),"Total size final : "+ loadingData1.Teacher.size(),Toast.LENGTH_SHORT).show();
        for (index = 0; index < loadingData1.Teacher.size(); index++) {
            my_batches_in_home_object obj = new my_batches_in_home_object(loadingData1.subName.get(index).toString(), loadingData1.subCode.get(index).toString(), loadingData1.Teacher.get(index).toString(),"dsadsadsadsad");
            results.add(index, obj);
        }
        index=-1;

        return results;
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
