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
 * {@link Dashboard.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class Dashboard extends Fragment {

    private OnFragmentInteractionListener mListener;

    public Dashboard() {
        // Required empty public constructor
    }

    private Context context;
    private View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        context = getActivity();
        if(context == null){ return null; }

        //connect XML components with code and do other initializations
        initialize();

        loadCachedDashboard();

        refreshDashboard();

        return rootView;
    }

    private void refreshDashboard() {

        //TODO: refresh dashboard and cache to device

    }

    private void loadCachedDashboard() {
        //TODO: load cached dashboard
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
