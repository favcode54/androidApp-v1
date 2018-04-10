package org.favcode54;

import android.os.Bundle;

/**
 * Created by Zfinix on 3/12/18.
 */
public class MenuActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        applyFont(findViewById(R.id.rootView));
    }
}