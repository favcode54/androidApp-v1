package org.favcode54;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
@SuppressWarnings("ALL")
public class MainActivity extends BaseActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
   applyFont(findViewById(R.id.rootView));
        /** Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        Activity activity = (Activity) this.getApplicationContext();
        if (toolbar != null) {
            activity.setTitle("Favcode");
        }**/
        this.setSupportActionBar(findViewById(R.id.toolbar));
        //noinspection ConstantConditions
        getSupportActionBar().setTitle(getIntent().getStringExtra(""));


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_about:
                Intent intent1 = new Intent(this, BareWebActivity.class);
                intent1.putExtra(BareWebActivity.URL, "https://favcode54.org/mob-about");
                intent1.putExtra(BareWebActivity.TITLE, item.getTitle());
                this.startActivity(intent1);
                break;
            case R.id.nav_team:
                Intent intent2 = new Intent(this, BareWebActivity.class);
                intent2.putExtra(BareWebActivity.URL, "https://favcode54.org/mob-our-people");
                intent2.putExtra(BareWebActivity.TITLE, item.getTitle());
                this.startActivity(intent2);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}