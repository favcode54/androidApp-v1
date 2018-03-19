package org.favcode54

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import org.favcode54.views.BareWebActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.nav_about -> {
                val intent = Intent(this, BareWebActivity::class.java)
                intent.putExtra(BareWebActivity.URL, "https://favcode54.org/mob-about")
                intent.putExtra(BareWebActivity.TITLE, item.title)
                startActivity(intent)
            }

            R.id.nav_team -> {
                val intent = Intent(this, BareWebActivity::class.java)
                intent.putExtra(BareWebActivity.URL, "https://favcode54.org/mob-our-people")
                intent.putExtra(BareWebActivity.TITLE, item.title)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
