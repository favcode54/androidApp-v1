package org.favcode54;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Zfinix on 3/12/18.
 */
public class SplashintActivity extends BaseActivity {
    Button signin, signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashint);
        applyFont(findViewById(R.id.rootView));
        signin = (Button) findViewById(R.id.signin);
        signup = (Button) findViewById(R.id.signup);


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cal = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(cal);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cal = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(cal);
            }
        });


    }

}