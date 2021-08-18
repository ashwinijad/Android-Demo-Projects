package com.example.mvpdemo.Repository

import android.content.Context
import android.util.Log
import com.example.mvpdemo.Contract.RetroContract
import com.example.mvpdemo.Model.RetroPhoto
import com.example.mvpdemo.api.GetDataService
import com.example.mvpdemo.api.RetrofitClientInstance.retrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList


class RetroRepo(mOnGetDatalistener: RetroContract.onGetDataListener) : RetroContract.Interactor{
    private var mOnGetDatalistener: RetroContract.onGetDataListener?=null
    var jsonResponse: List<RetroPhoto?>? = ArrayList<RetroPhoto?>()
    var allCountriesData: MutableList<String> = ArrayList()
    override fun initRetrofitCall(context: Context?, url: String) {
    val service = retrofitInstance!!.create(
        GetDataService::class.java
    )
    val call: Call<List<RetroPhoto?>?>? = service.allPhotos
    call?.enqueue(object : Callback<List<RetroPhoto?>?> {
        override fun onResponse(
            call: Call<List<RetroPhoto?>?>?,
            response: Response<List<RetroPhoto?>?>
        ) {
            //  progressDoalog.dismiss()
            // generateDataList(response.body())
            if (response?.isSuccessful!!) {
                 jsonResponse= response.body()
               // allcountry = jsonResponse?.country!!
                for (i in jsonResponse!!.indices) {
                    jsonResponse!![i]?.title?.let { allCountriesData.add(it) }
                }
                Log.d("Data", "Refreshed")
                mOnGetDatalistener?.onSuccess("List Size: " + allCountriesData.size, response.body())

            }    }

        override fun onFailure(call: Call<List<RetroPhoto?>?>, t: Throwable) {
            TODO("Not yet implemented")
        }
    })
}

    init {
        this.mOnGetDatalistener = mOnGetDatalistener
    }
}

/*    //private var apiclient: GetDataService? = null
    override fun getCountyNameByCapital(*//*city: String,*//* presenter: RetroContract.countryPresenter) {
        val service = retrofitInstance!!.create(
            GetDataService::class.java
        )
        val call: Call<List<RetroPhoto?>?>? = service.allPhotos
        call?.enqueue(object : Callback<List<RetroPhoto?>?> {
           override fun onResponse(
                call: Call<List<RetroPhoto?>?>?,
                response: Response<List<RetroPhoto?>?>
            ) {
              //  progressDoalog.dismiss()
               // generateDataList(response.body())
               if (response?.isSuccessful!!) {
                   var results = response?.body()?.get(0)?.title
                   results?.let { Log.d("success", it) }
                   //    countryTV?.setText(results)
                   country = results.toString()
                   presenter.uiAutoUpdate()

               }
            }

           override fun onFailure(call: Call<List<RetroPhoto?>?>?, t: Throwable?) {
               // progressDoalog.dismiss()
               *//* Toast.makeText(
                    this@MainActivity,
                    "Something went wrong...Please try later!",
                    Toast.LENGTH_SHORT
                ).show()*//*
            }
        })    }

    override fun getCountry(): String= country!!*/