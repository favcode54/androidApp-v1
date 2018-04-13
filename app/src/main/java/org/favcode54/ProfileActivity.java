package org.favcode54;

import android.os.Bundle;

public class ProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        applyFont(findViewById(R.id.rootView));

    }
}
