package org.favcode54.home;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.favcode54.R;
import org.favcode54.home.adapters.TopicsAdapter;
import org.favcode54.views.NormalTextView;

public class CourseActivity extends AppCompatActivity {

    private JsonObject course_object;
    private RecyclerView topics_recycler;
    private TopicsAdapter topicsAdapter;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initialize();
        populate();

    }

    private void populate() {
        JsonElement desc_raw = course_object.get("description");
        JsonElement title_raw = course_object.get("course_name");

        ((CollapsingToolbarLayout) findViewById(R.id.toolbar_layout)).setTitle(title_raw instanceof JsonNull ? "" : title_raw.getAsString());
        NormalTextView course_description = findViewById(R.id.description);
        course_description.setText(desc_raw instanceof JsonNull ? "" : desc_raw.getAsString());


    }

    private void initialize() {
        course_object = new JsonParser().parse(getIntent().getStringExtra("course_object")).getAsJsonObject();
        topics_recycler = findViewById(R.id.topics_recycler);
        topics_recycler.setNestedScrollingEnabled(false);

        if(!(course_object.get("subjects") instanceof JsonNull)) {
            topicsAdapter = new TopicsAdapter(course_object.get("subjects").getAsJsonArray());
        }else{
            topicsAdapter = new TopicsAdapter(new JsonArray());
        }

        GridLayoutManager gm = new GridLayoutManager(this, 2);
        topics_recycler.setLayoutManager(gm);
        topics_recycler.setAdapter(topicsAdapter);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //TODO: handle orientation change
    }
}
