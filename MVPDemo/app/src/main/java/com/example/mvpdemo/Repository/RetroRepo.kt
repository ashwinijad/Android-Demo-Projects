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
    override fun initRetrofitCall() {
    val service = retrofitInstance!!.create(GetDataService::class.java)
    val call: Call<List<RetroPhoto?>?>? = service.allPhotos
    call?.enqueue(object : Callback<List<RetroPhoto?>?> {
        override fun onResponse(
            call: Call<List<RetroPhoto?>?>?,
            response: Response<List<RetroPhoto?>?>
        ) {
            if (response?.isSuccessful!!) {
                 jsonResponse= response.body()
          /*     // allcountry = jsonResponse?.country!!
                for (i in jsonResponse!!.indices) {
                    jsonResponse!![i]?.title?.let { allCountriesData.add(it) }
                }
                Log.d("Data", "Refreshed")*/

                mOnGetDatalistener?.onSuccess("List Size: " + allCountriesData.size, response.body())

            }    }

        override fun onFailure(call: Call<List<RetroPhoto?>?>, t: Throwable) {
mOnGetDatalistener?.onFailure(t.toString())
        }
    })
}

    init {
        this.mOnGetDatalistener = mOnGetDatalistener
    }
}
