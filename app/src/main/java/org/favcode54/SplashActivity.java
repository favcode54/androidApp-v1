package org.favcode54;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.wang.avi.AVLoadingIndicatorView;

import org.favcode54.utils.PersistentStorageUtils;

public class SplashActivity extends BaseActivity {
    private AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Check if this is first opening of app. Splash is not neccessary after first run
        PersistentStorageUtils psu = new PersistentStorageUtils(this);
        if(!psu.isFirstRun()){
            startActivity(new Intent(SplashActivity.this, SplashintActivity.class));
            finish();
            return;
        }


        // making notification bar transparent
        changeStatusBarColor();
        String indicator = getIntent().getStringExtra("BallClipRotatePulseIndicator");
        avi = findViewById(R.id.avi);
        avi.setIndicator("BallClipRotatePulseIndicator");

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
                    sleep(7000);
                    startActivity(new Intent(SplashActivity.this, SplashintActivity.class));

                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
        startAnim();
    }

    private void startAnim() {
        avi.show();
        // or avi.smoothToShow();
    }


}
