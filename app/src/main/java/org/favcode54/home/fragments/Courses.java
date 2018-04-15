package org.favcode54.home.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.favcode54.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Courses.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class Courses extends Fragment {

    private OnFragmentInteractionListener mListener;

    public Courses() {
        // Required empty public constructor
    }

    private Context context;
    private View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_courses, container, false);

        context = getActivity();
        if(context == null){ return null; }

        //connect XML components with code and do other initializations
        initialize();

        loadCachedCourses();

        refreshCourses();

        return rootView;
    }

    private void refreshCourses() {
        //TODO: refresh courses and cache to device
    }

    private void loadCachedCourses() {
        //TODO: load cached courses
    }

    private void initialize() {
        //TODO: connect XML components here
    }

    // TODO: implement explicit switching to dashboard fragment from Courses fragment
    public void externallySwitchFragment(String fragmentTag) {
        if (mListener != null) {
            mListener.onFragmentInteraction(fragmentTag);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Dashboard.OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String fragmentTag);
    }
}
