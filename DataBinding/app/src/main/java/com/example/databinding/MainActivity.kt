package com.example.databinding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.databinding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var Activitymain: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Activitymain = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Activitymain?.person=getData()//
        Activitymain?.callback=this//referencing the current context

    }
    fun onSubmit() {
        if (!Activitymain?.editTextTextPersonName?.getText().toString().isEmpty()) {
            val text: String = Activitymain?.editTextTextPersonName?.getText().toString()
            Activitymain?.name?.setText(text)
        }
    }
    fun getData(): Person? {
        val p = Person()
        p.name = "Name"
        p.age = 50
        return p
    }
}