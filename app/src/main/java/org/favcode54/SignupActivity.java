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
public class SignupActivity extends Activity {
    Button signin;
    ImageButton signup, withgoogle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
      //signin = (Button) findViewById(R.id.signin);
      withgoogle = (ImageButton) findViewById(R.id.google);
      signup = (ImageButton) findViewById(R.id.signup);

        withgoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cal = new Intent(getApplicationContext(), RecoverActivity.class);
                startActivity(cal);
            }
        });

        /** signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cal = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(cal);
            }
        }); **/

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp();
            }
        });

    }

    private void SignUp() {
    }
}