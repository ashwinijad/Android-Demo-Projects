package com.example.multispinner

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    //MultipleSelection spinner object
    var mSpinner: MultipleSelectionSpinner? = null

    //List which hold multiple selection spinner values
    var list: MutableList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //casting of spinner
        mSpinner = findViewById(R.id.mSpinner)

        //adding items to list
        list.add("Violet")
        list.add("Indigo")
        list.add("Brown")
        list.add("Green")
        list.add("Yellow")
        list.add("Orange")
        list.add("Red")

        //set items to spinner from list
        mSpinner?.setItems(list)

        //onClick listener of button for showing multiple selection spinner values
        findViewById<View>(R.id.btn).setOnClickListener { Toast.makeText(this@MainActivity, "Selected : " + mSpinner?.selectedItemsAsString, Toast.LENGTH_SHORT).show() }
    }
}