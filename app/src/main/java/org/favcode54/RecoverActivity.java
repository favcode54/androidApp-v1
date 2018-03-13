package org.favcode54;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Zfinix on 3/12/18.
 */
public class RecoverActivity extends Activity {
    Button recover, back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover);
       // back = (Button) findViewById(R.id.back);
        recover = (Button) findViewById(R.id.recover);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cal = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(cal);
            }
        });

        recover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cal = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(cal);
            }
        });


    }

    private void SignIn() {
    }
}