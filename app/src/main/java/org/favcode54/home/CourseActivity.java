package org.favcode54.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.favcode54.R;

public class CourseActivity extends AppCompatActivity {

    private JsonObject course_object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        //ensure course object to be rendered in activity is in it's extras and is
        // not null before starting this activity
        initialize();
    }

    private void initialize() {

        course_object = new JsonParser().parse(getIntent().getStringExtra("course_object")).getAsJsonObject();

        //connect instances with UI elements below

    }
}
