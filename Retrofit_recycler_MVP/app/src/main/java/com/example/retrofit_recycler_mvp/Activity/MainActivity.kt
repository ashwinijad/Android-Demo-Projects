package com.example.retrofit_recycler_mvp.Activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit_recycler_mvp.Adapter.CountryAdapter
import com.example.retrofit_recycler_mvp.Core.GetDataContract
import com.example.retrofit_recycler_mvp.Core.Presenter
import com.example.retrofit_recycler_mvp.Model.CountryRes
import com.example.retrofit_recycler_mvp.R


class MainActivity : AppCompatActivity(), GetDataContract.View {
    private var mPresenter: Presenter? = null
    var recyclerView: RecyclerView? = null
    var linearLayoutManager: LinearLayoutManager? = null
    var countryAdapter: CountryAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPresenter = Presenter(this)
        mPresenter?.getDataFromURL(applicationContext, "")
        recyclerView = findViewById<View>(R.id.recycler) as RecyclerView
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = linearLayoutManager
    }

   override fun onGetDataSuccess(message: String, allCountriesData: List<CountryRes>) {
        countryAdapter = CountryAdapter(applicationContext, allCountriesData)
        recyclerView!!.adapter = countryAdapter
    }

   override fun onGetDataFailure(message: String) {
        Log.d("Status", message)
    }
}
