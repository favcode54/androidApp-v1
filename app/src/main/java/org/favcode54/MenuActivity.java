package org.favcode54;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Zfinix on 3/12/18.
 */
public class MenuActivity extends BaseActivity {
    private Button recover;
    private Button create;
    private ImageButton signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        applyFont(findViewById(R.id.rootView));
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
    }
}