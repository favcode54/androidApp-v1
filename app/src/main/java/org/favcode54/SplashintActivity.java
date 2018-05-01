package org.favcode54;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import org.favcode54.home.HomeActivity;
import org.favcode54.utils.PersistentStorageUtils;

/**
 * Created by Zfinix on 3/12/18.
 */
@SuppressWarnings("DefaultFileTemplate")
public class SplashintActivity extends BaseActivity {
    private Button signin;
    private Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Firstly check if user is logged in. If user is logged in, skip auth process
        PersistentStorageUtils psu = new PersistentStorageUtils(this);
        if(psu.isLoggedIn()){

            //start Home/Portal activity and terminate all fucking processes in this context
            startActivity(new Intent(this, HomeActivity.class));
            finish();
            return;

        }


        setContentView(R.layout.activity_splashint);
        applyFont(findViewById(R.id.rootView));
        signin = findViewById(R.id.signin);
        signup = findViewById(R.id.signup);


        signin.setOnClickListener(v -> {
            Intent cal = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(cal);
        });

        signup.setOnClickListener(v -> {
            Intent cal = new Intent(getApplicationContext(), SignupActivity.class);
            startActivity(cal);
        });


    }

}