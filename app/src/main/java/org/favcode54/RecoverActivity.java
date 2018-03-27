package org.favcode54;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

/**
 * Created by Zfinix on 3/12/18.
 */
@SuppressWarnings("DefaultFileTemplate")
public class RecoverActivity extends BaseActivity {
    private Button recover;
    private Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover);
        applyFont(findViewById(R.id.rootView));
        back = findViewById(R.id.back);
        recover = findViewById(R.id.recover);


        back.setOnClickListener(v -> {
            Intent cal = new Intent(getApplicationContext(), SignupActivity.class);
            startActivity(cal);
        });

        recover.setOnClickListener(v -> {
            Intent cal = new Intent(getApplicationContext(), SignupActivity.class);
            startActivity(cal);
        });


    }
}