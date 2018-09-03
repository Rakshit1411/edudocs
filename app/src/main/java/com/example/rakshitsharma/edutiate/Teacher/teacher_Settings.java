package com.example.rakshitsharma.edutiate.Teacher;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rakshitsharma.edutiate.Authentication.login;
import com.example.rakshitsharma.edutiate.GetAllData.SaveSettings;
import com.example.rakshitsharma.edutiate.R;
import com.example.rakshitsharma.edutiate.Settings.website;
import com.example.rakshitsharma.edutiate.checker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class teacher_Settings extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    CardView profile,about_the_team,SignOut;

    private OnFragmentInteractionListener mListener;

    public teacher_Settings() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static teacher_Settings newInstance(String param1, String param2) {
        teacher_Settings fragment = new teacher_Settings();
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
        View v = inflater.inflate(R.layout.fragment_teacher__settings, container, false);
        profile = (CardView)v.findViewById(R.id.profile);
        about_the_team = (CardView)v.findViewById(R.id.about_app);
        SignOut = (CardView)v.findViewById(R.id.signOut);

        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SaveSettings saveSettings = new SaveSettings(getContext());
                saveSettings.erase();
                Intent i = new Intent(getActivity(),checker.class);
                startActivity(i);
                getActivity().finish();

            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),about_teacher_me.class);
                startActivity(i);
                getActivity().finish();
            }
        });


        about_the_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View viewStart = v.findViewById(R.id.about_app);
                v.setTransitionName("trans");

                Intent intent = new Intent(getContext(),website.class);
                getContext().startActivity(intent);
                getActivity().finish();
            }
        });

        return v;
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
