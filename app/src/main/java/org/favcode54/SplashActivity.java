package org.favcode54;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.wang.avi.AVLoadingIndicatorView;

public class SplashActivity extends Activity {
    private AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        // making notification bar transparent
        changeStatusBarColor();
        String indicator = getIntent().getStringExtra("BallClipRotatePulseIndicator");
        avi = findViewById(R.id.avi);
        avi.setIndicator(indicator);

        start();

    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.WHITE);
        }
    }

    private void start() {


        Thread myThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(8000);
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
        startAnim();
    }

    void startAnim() {
        avi.show();
        // or avi.smoothToShow();
    }


}
