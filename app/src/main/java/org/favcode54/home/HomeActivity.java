package org.favcode54.home;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.favcode54.AboutUs;
import org.favcode54.R;
import org.favcode54.home.fragments.Courses;
import org.favcode54.home.fragments.Dashboard;
import org.favcode54.utils.PersistentStorageUtils;
import org.favcode54.utils.QuickNetUtils;
import org.favcode54.views.NormalTextView;
import org.favcode54.views.SemiBoldTextView;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import timber.log.Timber;

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
                new Handler().postDelayed(() -> switchFragment(myDashboardTag), 320);

                break;
            case R.id.courses:

                toggleDrawer(false);

                //delay switching of fragment to prevent tiny lags while drawer closes
                new Handler().postDelayed(() -> switchFragment(coursesTag), 320);

                break;
            case R.id.about_us:

                startActivity(new Intent(this, AboutUs.class));
                overridePendingTransition(R.anim.slide_in_right,  R.anim.slide_out_left);

                break;
            case R.id.contact_us:
                toggleDrawer(false);

                new Handler().postDelayed(this::contactUs, 320);

                break;
            case R.id.menu:
                
                //Floating action button opens drawer
                toggleDrawer(true);
                break;
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("current_fragment", current_frag);
    }

    public void contactUs() {
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        View v = LayoutInflater.from(this).inflate(R.layout.contact_us, null);

        final EditText name = v.findViewById(R.id.name),
                email = v.findViewById(R.id.email),
                message = v.findViewById(R.id.message);
        View clicker = v.findViewById(R.id.submit);

        ab.setView(v);

        final AlertDialog a = ab.create();
        clicker.setOnClickListener(view -> {
            String n = name.getText().toString(),
                    e = email.getText().toString(),
                    m = message.getText().toString();

            if(e.isEmpty() || m.isEmpty() || n.isEmpty()){
                Snackbar.make(view, "Please fill in all fields", Snackbar.LENGTH_SHORT).show();
                return;
            }
            if(!e.contains("@") || !e.contains(".")){
                Snackbar.make(view, "Invalid email address", Snackbar.LENGTH_SHORT).show();
                return;
            }
            if(m.length() < 10){
                Snackbar.make(view, "Please include more info in your message", BaseTransientBottomBar.LENGTH_LONG).show();
                return;
            }

            a.cancel();
            Toast.makeText(this, "Sending message...", Toast.LENGTH_SHORT).show();

            //Send email to Favcode support email address
            RequestBody requestBody = new FormBody.Builder()
                    .add("sender_name", n)
                    .add("sender_email", e)
                    .add("message", m)
                    .build();
            try {
                QuickNetUtils.makePostRequest(getString(R.string.endpoint_root) + "contact_us.php", requestBody).enqueue(
                        new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                if(response.isSuccessful()){
                                    Timber.i("Contact us message sent");
                                }else{
                                    Timber.i("Contact us message not sent");
                                }
                            }
                        }
                );
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        });
        a.show();
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState == null){
            return;
        }

        switchFragment(savedInstanceState.getString("current_fragment"));
    }
}
