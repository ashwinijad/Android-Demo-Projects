package com.example.mvvmdemo1.View

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmdemo1.Adapter.CustomAdapter
import com.example.mvvmdemo1.Model.RetroPhoto
import com.example.mvvmdemo1.R
import com.example.mvvmdemo1.ViewModel.ViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var adapter: CustomAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        customRecycler?.setHasFixedSize(true)
        customRecycler?.setLayoutManager(LinearLayoutManager(this))

        val model: ViewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
model.loadHeroes()
        model.heroListLivedata?.observe(this, object : Observer<List<RetroPhoto?>?> {
           override fun onChanged(@Nullable heroList: List<RetroPhoto?>?) {
                adapter = CustomAdapter(this@MainActivity, heroList)
               customRecycler?.adapter = adapter
            }
        })
    }
}