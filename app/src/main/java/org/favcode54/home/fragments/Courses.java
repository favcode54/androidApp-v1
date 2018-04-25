package org.favcode54.home.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.favcode54.R;
import org.favcode54.home.adapters.AllCoursesAdapter;
import org.favcode54.utils.PersistentStorageUtils;
import org.favcode54.utils.QuickNetUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

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

    private Activity context;
    private PersistentStorageUtils persistentStorageUtils;
    private View rootView;
    private RecyclerView my_courses_recycler_view, all_courses_recycler_view;
    private AllCoursesAdapter my_courses_adapter, all_courses_adapter;
    private LinearLayoutManager all_c_mobile_manager, my_c;
    private GridLayoutManager all_c_tab_manager;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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

        if(QuickNetUtils.isConnected(context)){
            loading();

            //consecutively get my courses and all courses asynchronously
            try {
                QuickNetUtils.makeGetRequest(context.getResources().getString(R.string.endpoint_root) + "courses.php?action=get_all_user_courses&user_id=" + persistentStorageUtils.getUserID()).enqueue(
                        new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                context.runOnUiThread(() -> unableToLoadCourses());
                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                                if(!response.isSuccessful()){
                                    context.runOnUiThread(() -> unableToLoadCourses());
                                    return;
                                }

                                ResponseBody raw_response = response.body();

                                if(raw_response == null){
                                    context.runOnUiThread(() -> unableToLoadCourses());
                                    return;
                                }

                                JsonObject response_json = new JsonParser().parse(raw_response.string()).getAsJsonObject();

                                /** TODO: This is a temporary implementation. From current Favcode API, only one course
                                    TODO: can be assigned to a user. To support multiple courses a new version of this app
                                    TODO: must be released after the implementation have been completed
                                 **/
                                persistentStorageUtils.cacheUserCourses(response_json.get("0").getAsJsonObject());

                                //done fetching user courses. Now get all Favcode courses
                                context.runOnUiThread(() -> {
                                    try {
                                        QuickNetUtils.makeGetRequest(context.getResources().getString(R.string.endpoint_root)+"courses.php?action=get_all_courses").enqueue(
                                                new Callback() {
                                                    @Override
                                                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                                        context.runOnUiThread(Courses.this::unableToLoadCourses);
                                                    }

                                                    @Override
                                                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                                                        if(!response.isSuccessful()){
                                                            context.runOnUiThread(Courses.this::unableToLoadCourses);
                                                            return;
                                                        }

                                                        ResponseBody raw_response = response.body();

                                                        if(raw_response == null){
                                                            context.runOnUiThread(Courses.this::unableToLoadCourses);
                                                            return;
                                                        }

                                                        JsonObject jsonObject = new JsonParser().parse(raw_response.string()).getAsJsonObject();

                                                        //now cache courses
                                                        persistentStorageUtils.cacheAllCourses(jsonObject.get("courses").getAsJsonArray());

                                                        context.runOnUiThread(() ->
                                                        {
                                                            stopLoading();
                                                            loadCachedCourses();
                                                        });
                                                    }
                                                }
                                        );
                                    } catch (IOException e) {
                                        unableToLoadCourses();
                                        e.printStackTrace();
                                    }
                                });

                            }
                        }
                );
            } catch (IOException e) {
                Snackbar.make(rootView, "Something went wrong. Please retry later", BaseTransientBottomBar.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }else{
            Snackbar.make(rootView, "No internet connection", Snackbar.LENGTH_LONG).show();
        }
    }

    private void stopLoading() {
        rootView.findViewById(R.id.progress).setVisibility(View.GONE);
    }

    private void unableToLoadCourses() {
        Snackbar.make(rootView, "Unable to load courses. Please retryu later", BaseTransientBottomBar.LENGTH_LONG).show();
    }

    private void loading() {
        rootView.findViewById(R.id.progress).setVisibility(View.VISIBLE);
    }

    private void loadCachedCourses() {

        //temporary implementation. Since only one course can be assigned
        //to a user for now
        JsonArray temp_impl_array = new JsonArray();
        temp_impl_array.add(persistentStorageUtils.getUserCourses());

        my_courses_adapter.updateAdapter(temp_impl_array);
        all_courses_adapter.updateAdapter(persistentStorageUtils.getAllCourses());

    }

    private void initialize() {
        persistentStorageUtils = new PersistentStorageUtils(context);
        my_courses_recycler_view = rootView.findViewById(R.id.my_courses_recycler_view);
        all_courses_recycler_view = rootView.findViewById(R.id.all_courses_recycler_view);
        all_courses_recycler_view.setNestedScrollingEnabled(false);

        my_courses_adapter = new AllCoursesAdapter(new JsonArray());
        all_courses_adapter = new AllCoursesAdapter(new JsonArray());

        //set layout managers and adapters for 'my courses' and 'all courses'
        setLayoutManagersAndAdapters();
    }

    private void setLayoutManagersAndAdapters() {
        my_c = new LinearLayoutManager(context);
        my_c.setOrientation(LinearLayoutManager.HORIZONTAL);

        all_c_tab_manager = new GridLayoutManager(context, 2);

        all_c_mobile_manager = new LinearLayoutManager(context);
        all_c_mobile_manager.setOrientation(LinearLayoutManager.VERTICAL);

        my_courses_recycler_view.setLayoutManager(my_c);
        my_courses_recycler_view.setAdapter(my_courses_adapter);

        all_courses_recycler_view.setLayoutManager(isMobile() ? all_c_mobile_manager : all_c_tab_manager);

        all_courses_recycler_view.setAdapter(all_courses_adapter);

    }

    private boolean isMobile() {
        return rootView.findViewById(R.id.tab_indicator_view) == null;
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("my_courses_recycler_state", my_c.onSaveInstanceState());
        outState.putParcelable("all_courses_recycler_state_mobile", all_c_mobile_manager.onSaveInstanceState());
        outState.putParcelable("all_courses_recycler_state_tab", all_c_tab_manager.onSaveInstanceState());

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if(savedInstanceState == null){
            return;
        }

        my_c.onRestoreInstanceState(savedInstanceState.getParcelable("my_courses_recycler_state"));
        all_c_mobile_manager.onRestoreInstanceState(savedInstanceState.getParcelable("all_courses_recycler_state_mobile"));
        all_c_tab_manager.onRestoreInstanceState(savedInstanceState.getParcelable("all_courses_recycler_state_tab"));

    }
}
