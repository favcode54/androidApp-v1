package org.favcode54;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        this.setSupportActionBar(findViewById(R.id.toolbar));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_about:
                Intent intent = new Intent(this, BareWebActivity.class);
                intent.putExtra(BareWebActivity.URL, "https://favcode54.org/mob-about");
                intent.putExtra(BareWebActivity.TITLE, item.getTitle());
                this.startActivity(intent);
                break;
            case R.id.nav_team:
                Intent intent1 = new Intent(this, BareWebActivity.class);
                intent1.putExtra(BareWebActivity.URL, "https://favcode54.org/mob-our-people");
                intent1.putExtra(BareWebActivity.TITLE, item.getTitle());
                this.startActivity(intent1);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}