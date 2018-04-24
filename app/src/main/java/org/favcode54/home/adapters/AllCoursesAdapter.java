package org.favcode54.home.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import org.favcode54.R;
import org.favcode54.home.CourseActivity;
import org.favcode54.views.NormalTextView;

/**
 * Created by Intija on 4/24/2018.
 */

public class AllCoursesAdapter extends RecyclerView.Adapter<AllCoursesAdapter.CustomViewHolder> {

    private JsonArray allCoursesArray;

    public AllCoursesAdapter(JsonArray courses){
        this.allCoursesArray = courses;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return allCoursesArray.size();
    }

    public void updateAdapter(JsonArray allCourses) {
        this.allCoursesArray = allCourses;
        this.notifyDataSetChanged();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private ImageView thumbnail;
        private NormalTextView course_title;
        private Context context;
        private View clickable_area;
        private static final String THUMB_PREFIX = "https://portal.favcode54.org/account/pictures/";

        public CustomViewHolder(View itemView) {
            super(itemView);

            thumbnail = itemView.findViewById(R.id.thumbnail);
            course_title = itemView.findViewById(R.id.course_title);
            clickable_area = itemView.findViewById(R.id.clickable_area);
            context = itemView.getContext();

        }

        void bind(int position){
            JsonObject course_object = allCoursesArray.get(position).getAsJsonObject();

            JsonElement course_name_object = course_object.get("course_name");

            course_title.setText(course_name_object != null ? course_name_object.getAsString() : "(No course name)");

            JsonElement thumb_path = course_object.get("img_path");

            if(thumb_path != null){
                Glide.with(context).load(THUMB_PREFIX + (thumb_path instanceof JsonNull ? "" : thumb_path.getAsString())).placeholder(R.color.colorPrimary).into(thumbnail);
            }

            clickable_area.setOnClickListener(v -> {
                context.startActivity(
                        new Intent(context, CourseActivity.class)
                        .putExtra("course_object", course_object.toString())
                );
            });

        }
    }
}
