package org.favcode54;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Zfinix on 3/12/18.
 */
public class LoginActivity extends Activity {
    Button recover, create;
    ImageButton signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        recover = (Button) findViewById(R.id.recover);
        create = (Button) findViewById(R.id.create);
        signin = (ImageButton) findViewById(R.id.signin);

        recover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cal = new Intent(getApplicationContext(), RecoverActivity.class);
                startActivity(cal);
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cal = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(cal);
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               SignIn();
            }
        });

    }

    private void SignIn() {
        startActivity(new Intent(this, MainActivity.class));
    }
}