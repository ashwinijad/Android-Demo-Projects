package com.example.mvpdemo

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvpdemo.Adapter.CustomAdapter
import com.example.mvpdemo.Contract.RetroContract
import com.example.mvpdemo.Model.RetroPhoto
import com.example.mvpdemo.Presenter.presenter


class MainActivity : AppCompatActivity() , RetroContract.View {
    private var mPresenter: presenter? = null
    var recyclerView: RecyclerView? = null
    var linearLayoutManager: LinearLayoutManager? = null
    var countryAdapter: CustomAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPresenter = presenter(this)
        mPresenter?.getDataFromURL()
        recyclerView = findViewById<View>(R.id.customRecycler) as RecyclerView
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = linearLayoutManager
    }




    override fun onGetDataSuccess(message: String, allCountriesData: List<RetroPhoto?>?) {
        countryAdapter = CustomAdapter(applicationContext, allCountriesData)
        recyclerView!!.adapter = countryAdapter
    }

    override fun onGetDataFailure(message: String) {
        Log.d("Status", message)
    }
}