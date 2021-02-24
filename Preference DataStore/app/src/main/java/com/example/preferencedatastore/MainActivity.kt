package com.example.preferencedatastore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

     lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userPreferences = UserPreferences(this)

        button.setOnClickListener {
            val bookmark = edit.text.toString().trim()
            lifecycleScope.launch {
                userPreferences.saveBookmark(bookmark)
            }
        }

        userPreferences.bookmark.asLiveData().observe(this, Observer {
            helo.text = it
        })
    }
}