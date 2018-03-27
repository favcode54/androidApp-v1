package org.favcode54;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
/**
 * Created by Zfinix on 3/12/18.
 */
public class LoginActivity extends BaseActivity {
    private Button recover;
    private Button create;
    private ImageButton signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //applyFont(findViewById(R.id.rootViewx));
        setContentView(R.layout.activity_login);
        recover = findViewById(R.id.recover);
        create = findViewById(R.id.create);
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