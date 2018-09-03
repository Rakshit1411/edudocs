package com.example.rakshitsharma.edutiate.Settings;

import android.app.ActivityManager;
import android.app.PendingIntent;
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
import android.widget.RemoteViews;

import com.example.rakshitsharma.edutiate.GetAllData.SaveSettings;
import com.example.rakshitsharma.edutiate.MainActivity;
import com.example.rakshitsharma.edutiate.R;
import com.example.rakshitsharma.edutiate.Authentication.login;
import com.example.rakshitsharma.edutiate.Settings.profile.about_me;
import com.example.rakshitsharma.edutiate.checker;
import com.example.rakshitsharma.edutiate.noti;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;

public class settingsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static FirebaseAuth auth;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    CardView profile,about_the_team,SignOut;

    private OnFragmentInteractionListener mListener;

    public settingsFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static settingsFragment newInstance(String param1, String param2) {
        settingsFragment fragment = new settingsFragment();
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
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        auth = FirebaseAuth.getInstance();
        profile = (CardView)v.findViewById(R.id.profile);
        about_the_team = (CardView)v.findViewById(R.id.about_app);
        SignOut = (CardView)v.findViewById(R.id.signOut);
        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                final SaveSettings saveSettings = new SaveSettings(getContext());
                saveSettings.erase();
                Intent intent = new Intent(getActivity(),checker.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);
                getActivity().finish();
                FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user == null) {
                            // user auth state is changed - user is null
                            // launch login activity
                            Intent i = new Intent(getActivity(),checker.class);
                            startActivity(i);
                            getActivity().finish();

                        }
                    }
                };
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
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),about_me.class);
                startActivity(i);
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







    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
}
