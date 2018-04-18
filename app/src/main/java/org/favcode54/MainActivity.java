package org.favcode54;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

//@SuppressWarnings("ALL")
public class MainActivity extends BaseActivity {
    private ImageButton menux;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
   applyFont(findViewById(R.id.rootView));
        this.setSupportActionBar(findViewById(R.id.toolbar));

        menux = findViewById(R.id.menux);
        //menux.setRotation(90);
        onClicklistner();
        menux.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menux.setRotation(menux.getRotation() + 90);
                Intent menu = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(menu);
            }
        });
        //noinspection ConstantConditions
        getSupportActionBar().setTitle(getIntent().getStringExtra(""));


    }

    private void onClicklistner() {

        findViewById(R.id.lesson).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent menu = new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(menu);
                }
            });

        findViewById(R.id.blog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menu = new Intent(getApplicationContext(), org.favcode54.blog.MainActivity.class);
                startActivity(menu);
            }
        });

        findViewById(R.id.project).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menu = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(menu);
            }
        });

        findViewById(R.id.job).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menu = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(menu);
            }
        });

        findViewById(R.id.about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), BareWebActivity.class);
                intent1.putExtra(BareWebActivity.URL, "https://favcode54.org/mob-about");
                intent1.putExtra(BareWebActivity.TITLE, "ABOUT");
                startActivity(intent1);
            }
        });

        findViewById(R.id.user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent user = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(user);
            }
        });
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