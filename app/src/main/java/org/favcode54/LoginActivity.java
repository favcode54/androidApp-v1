package org.favcode54;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static android.view.View.OnClickListener;

/**
 * Created by Zfinix on 3/12/18.
 */
public class LoginActivity extends BaseActivity {
    private Button recover, create, signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        applyFont(findViewById(R.id.rootView));

        recover = findViewById(R.id.recoverpass);
        create = findViewById(R.id.signup);
        signin = findViewById(R.id.signin);

        recover.setOnClickListener(v -> {
            Intent cal = new Intent(getApplicationContext(), RecoverActivity.class);
            startActivity(cal);
        });

        create.setOnClickListener(v -> {
            Intent cal = new Intent(getApplicationContext(), SignupActivity.class);
            startActivity(cal);
        });

        signin.setOnClickListener(v -> SignIn());

    }

    private void SignIn() {
        Intent main = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(main);
    }
}