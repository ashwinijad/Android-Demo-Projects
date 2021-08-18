package com.example.preferencedatastore

import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.github.arturogutierrez.Badges
import com.github.arturogutierrez.BadgesNotSupportedException
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {
    val regex = "^\\-?(\\d{0," + (5) +  "}|\\d{0," + (5) +  "}\\.\\d{0," + 4  + "})$"

    lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val reg =regex.toRegex()

        try {
            Badges.setBadge(applicationContext, 5)
        } catch (badgesNotSupportedException: BadgesNotSupportedException) {
            Log.d("TAG", badgesNotSupportedException.message.toString())
        }
        userPreferences = UserPreferences(this)

 /*       edit.filters=arrayOf(
                InputFilter { source, start, end, destination, destinationStart, destinationEnd ->
                    if (end > start) {
                        // adding: filter
                        // build the resulting text
                        val destinationString = destination.toString()
                        val resultingTxt = destinationString.substring(0, destinationStart) + source.subSequence(start, end) + destinationString.substring(destinationEnd)
                        // return null to accept the input or empty to reject it
                        return@InputFilter if (resultingTxt.matches(reg)) null else ""
                    }
                    // removing: always accept
                    null
                }
        )*/
        button.setOnClickListener {
            val df =  DecimalFormat("#.####");

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