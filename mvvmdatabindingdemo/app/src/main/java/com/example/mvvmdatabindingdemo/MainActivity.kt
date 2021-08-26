package com.example.mvvmdatabindingdemo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    private var recyclerview: RecyclerView? = null
    private var myListViewModel: MyListViewModel? = null
    private var adapter: MyAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerview = findViewById<View>(R.id.customRecycler) as RecyclerView
        myListViewModel = ViewModelProviders.of(this).get(MyListViewModel::class.java)
        myListViewModel!!.getMutableLiveData()
            .observe(this, object : Observer<ArrayList<MyListViewModel>> {
              override  fun onChanged(myListViewModels: ArrayList<MyListViewModel>?) {
                    adapter = myListViewModels?.let { MyAdapter(it, this@MainActivity) }
                    recyclerview!!.layoutManager = LinearLayoutManager(applicationContext)
                    recyclerview!!.adapter = adapter
                }

         /*       override fun onChanged(t: ArrayList<MyListViewModel>?) {
                    adapter = MyAdapter(t!!, this@MainActivity)
                    recyclerview!!.layoutManager = LinearLayoutManager(applicationContext)
                    recyclerview!!.adapter = adapter                }*/
            })
    }
}