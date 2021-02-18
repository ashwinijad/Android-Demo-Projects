package com.example.localizationdemo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.localizationdemo.LocaleManager.LocaleDef
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var drawerLayout: DrawerLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout?.setDrawerListener(toggle);
        toggle.syncState()
        drawerLayout?.addDrawerListener(toggle)
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        drawerLayout!!.addDrawerListener(toggle)
        toggle.isDrawerIndicatorEnabled = true
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        toolbar!!.setNavigationOnClickListener {
            if (drawerLayout!!.isDrawerOpen(navigationView!!)) {
                drawerLayout!!.closeDrawer(navigationView!!)
            } else {
                drawerLayout!!.openDrawer(navigationView!!)

            }
        }
    }

    override fun onBackPressed() {
        if (drawerLayout!!.isDrawerOpen(GravityCompat.START)) {
            drawerLayout!!.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        when (id) {
            R.id.item1 -> {
                setNewLocale(this, LocaleManager.ENGLISH)
                return true
            }
            R.id.item2 -> {
                setNewLocale(this, LocaleManager.HINDI)
                return true
            }
            R.id.item3 -> {
                setNewLocale(this, LocaleManager.SPANISH)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setNewLocale(mContext: AppCompatActivity, @LocaleDef language: String) {
        LocaleManager.setNewLocale(this, language)
        val intent = mContext.intent
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id: Int = item.getItemId()
        when (id) {
            R.id.nav_item_one -> {
            }
            R.id.nav_item_two -> {
            }
            R.id.nav_item_three -> {
            }
            R.id.nav_item_four -> {
            }
            R.id.nav_item_five -> {
            }
            R.id.nav_item_six -> {
            }
            else -> {
            }
        }
        drawerLayout!!.closeDrawer(GravityCompat.START)
        return true
    }
}