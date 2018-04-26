package org.favcode54.home.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.intrusoft.scatter.ChartData;
import com.intrusoft.scatter.PieChart;

import org.favcode54.R;
import org.favcode54.home.adapters.AllCoursesAdapter;
import org.favcode54.utils.PersistentStorageUtils;
import org.favcode54.views.SemiBoldTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    private PersistentStorageUtils persistentStorageUtils;
    private RecyclerView my_courses;
    private AllCoursesAdapter my_courses_adapter;
    private PieChart grade_pie;
    private SemiBoldTextView max_total, my_total;


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
        //temporary implementation. Since only one course can be assigned
        //to a user for now
        JsonArray temp_impl_array = new JsonArray();
        JsonObject course_object =persistentStorageUtils.getUserCourses();
        temp_impl_array.add(course_object);

        if(!course_object.toString().equals("{}")){
            my_courses_adapter.updateAdapter(temp_impl_array);
        }

        int my_total_ = persistentStorageUtils.getUserTotalGrade();
        int max_total_ = persistentStorageUtils.getUserMaxGrade();

        my_total.setText(String.valueOf(my_total_));
        max_total.setText(String.valueOf(max_total_));

        float my_pie_grade = (float)(my_total_ * max_total_) / 100;

        List<ChartData> pie_data = new ArrayList<>();
        pie_data.add(new ChartData("Your grade", my_pie_grade, context.getResources().getColor(R.color.pie_color)));
        pie_data.add(new ChartData("", 100 - my_pie_grade, context.getResources().getColor(R.color.pie_color_faded)));

        grade_pie.setChartData(pie_data);
    }

    private void initialize() {
        persistentStorageUtils = new PersistentStorageUtils(context);
        my_courses_adapter = new AllCoursesAdapter(new JsonArray());

        my_courses = rootView.findViewById(R.id.my_courses_recycler_view);
        my_total = rootView.findViewById(R.id.your_grade);
        max_total = rootView.findViewById(R.id.max_grade);
        grade_pie = rootView.findViewById(R.id.pie);

        //set layout manager for my courses
        LinearLayoutManager lm = new LinearLayoutManager(context);
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        my_courses.setLayoutManager(lm);
        my_courses.setAdapter(my_courses_adapter);
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
