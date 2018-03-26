package org.favcode54;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Zfinix on 3/12/18.
 */
public class SignupActivity extends BaseActivity {
    ImageButton signup, withgoogle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
      withgoogle = (ImageButton) findViewById(R.id.google);
      signup = (ImageButton) findViewById(R.id.signup);

        withgoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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