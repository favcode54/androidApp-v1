package org.favcode54.home.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.favcode54.R;
import org.favcode54.views.NormalTextView;

/**
 * Created by Intija on 4/26/2018.
 */

public class TopicsAdapter extends RecyclerView.Adapter<TopicsAdapter.CustomViewHolder> {

    JsonArray topics_array;

    public TopicsAdapter(JsonArray topics){
        this.topics_array = topics;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return topics_array.size();
    }

    public void updateAdapter(JsonArray topics_array){
        this.topics_array = topics_array;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private Context context;
        private NormalTextView topic_name;
        private ImageView thumb;

        public CustomViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            topic_name = itemView.findViewById(R.id.topic_name);
            thumb = itemView.findViewById(R.id.thumb);
        }

        void bind(int position) {

            JsonObject topic = topics_array.get(position).getAsJsonObject();
            topic_name.setText(topic.get("subject_name").getAsString());
        }
    }
}
