package org.favcode54;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

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