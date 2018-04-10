package org.favcode54;

import android.os.Bundle;
import android.widget.ImageButton;

/**
 * Created by Zfinix on 3/12/18.
 */
public class SignupActivity extends BaseActivity {
    private ImageButton signup;
    private ImageButton withgoogle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        applyFont(findViewById(R.id.rootView));
      withgoogle = findViewById(R.id.google);
      signup = findViewById(R.id.signup);

        withgoogle.setOnClickListener(v -> {

        });

        signup.setOnClickListener(v -> SignUp());

    }

    private void SignUp() {
    }
}