package org.favcode54.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.bumptech.glide.Glide;

import org.favcode54.AboutUs;
import org.favcode54.R;
import org.favcode54.home.fragments.Courses;
import org.favcode54.home.fragments.Dashboard;
import org.favcode54.utils.PersistentStorageUtils;
import org.favcode54.views.NormalTextView;
import org.favcode54.views.SemiBoldTextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton menuFab;
    private Toolbar toolbar;
    private DrawerLayout menuDrawer;
    private CircleImageView profile_picture_image_view;
    private PersistentStorageUtils persistentStorageUtils;

    //ID for fragments container in XML
    private int container = R.id.container;
    private FragmentTransaction fragmentTransaction;

    //fragment variables
    private Dashboard my_dashboard;
    private Courses courses;

    //tag fragments during fragment transactions
    private static final String myDashboardTag = "my_dashboard", coursesTag = "courses";

    //On orientation change use tag to restore current fragment
    private String current_frag = "my_dashboard";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //initialize and prepare UI components for use
        initialize();
        registerClickEvents();

        if(savedInstanceState == null) {
            // initially switch to Dashboard as default fragment
            switchFragment("");
        }

    }

    private void registerClickEvents() {
        findViewById(R.id.menu).setOnClickListener(this);
        findViewById(R.id.my_dashboard).setOnClickListener(this);
        findViewById(R.id.courses).setOnClickListener(this);
        findViewById(R.id.about_us).setOnClickListener(this);
        findViewById(R.id.contact_us).setOnClickListener(this);
    }

    private void initialize() {

        persistentStorageUtils = new PersistentStorageUtils(this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        my_dashboard = new Dashboard();
        courses =new Courses();

        menuFab = findViewById(R.id.menu);
        profile_picture_image_view = findViewById(R.id.avatar);

        menuDrawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, menuDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        menuDrawer.addDrawerListener(toggle);
        toggle.syncState();

        //update user's name and picture on the UI
        updateUserUI();

    }

    private void updateUserUI() {
        //load user's profile picture if available
        String profile_picture_url = persistentStorageUtils.getProfilePicture();
        if(!profile_picture_url.isEmpty()) {
            Glide.with(this).load(profile_picture_url).placeholder(R.drawable.avatar).into(profile_picture_image_view);
        }

        //set name
        SemiBoldTextView name = findViewById(R.id.name);
        name.setText(persistentStorageUtils.getFullName());

        //set location
        NormalTextView location = findViewById(R.id.location);
        location.setText(persistentStorageUtils.getUserAddress());
    }

    private void switchFragment(String fragmantTag){
        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        switch (fragmantTag){
            case myDashboardTag:
                setTitle("My dashboard");
                current_frag = myDashboardTag;
                fragmentTransaction.replace(container, my_dashboard, fragmantTag).commit();
                break;
            case coursesTag:
                current_frag = coursesTag;
                setTitle("Courses");
                fragmentTransaction.replace(container, courses, fragmantTag).commit();
                break;
            default:
                setTitle("My dashboard");
                current_frag = myDashboardTag;
                fragmentTransaction.replace(container, my_dashboard, myDashboardTag).commit();
                break;
        }
    }

    public void toggleDrawer(boolean toggle){
        if(toggle){
            menuDrawer.openDrawer(GravityCompat.START);
        }else{
            menuDrawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onBackPressed() {
        if (menuDrawer.isDrawerOpen(GravityCompat.START)) {
            toggleDrawer(false);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.my_dashboard:

                toggleDrawer(false);

                //delay switching of fragment to prevent tiny lags while drawer closes
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        switchFragment(myDashboardTag);
                    }
                }, 320);

                break;
            case R.id.courses:

                toggleDrawer(false);

                //delay switching of fragment to prevent tiny lags while drawer closes
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        switchFragment(coursesTag);
                    }
                }, 320);

                break;
            case R.id.about_us:

                startActivity(new Intent(this, AboutUs.class));
                overridePendingTransition(R.anim.slide_in_right,  R.anim.slide_out_left);

                break;
            case R.id.contact_us:

                //TODO: start contact us activity

                break;
            case R.id.menu:
                
                //Floating action button opens drawer
                toggleDrawer(true);
                break;
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        switchFragment(current_frag);
    }
}
