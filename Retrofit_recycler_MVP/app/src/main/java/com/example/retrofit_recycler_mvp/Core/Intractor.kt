package com.example.retrofit_recycler_mvp.Core

import android.content.Context
import android.util.Log
import com.example.retrofit_recycler_mvp.Model.AllCountryResponse
import com.example.retrofit_recycler_mvp.Model.Country
import com.example.retrofit_recycler_mvp.Model.CountryRes
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

/**
 * Created by Ashish on 28-09-2017.
 */
class Intractor(mOnGetDatalistener: GetDataContract.onGetDataListener) : GetDataContract.Interactor {
    private val mOnGetDatalistener: GetDataContract.onGetDataListener
    var allcountry: List<CountryRes> = ArrayList<CountryRes>()
    var allCountriesData: MutableList<String> = ArrayList()
    override fun initRetrofitCall(context: Context?, url: String) {
        val gson: Gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://uaevisa-online.org")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val request: AllCountryResponse = retrofit.create(AllCountryResponse::class.java)
        val call: Call<Country?>? = request.country
        call?.enqueue(object : Callback<Country?> {
           override fun onResponse(call: retrofit2.Call<Country?>?, response: Response<Country?>) {
                val jsonResponse: Country? = response.body()
                allcountry = jsonResponse?.country!!
                for (i in allcountry.indices) {
                    allcountry[i].name?.let { allCountriesData.add(it) }
                }
                Log.d("Data", "Refreshed")
                mOnGetDatalistener.onSuccess("List Size: " + allCountriesData.size, allcountry)
            }

           override fun onFailure(call: retrofit2.Call<Country?>?, t: Throwable) {
                Log.v("Error", t.message!!)
                mOnGetDatalistener.onFailure(t.message)
            }
        })
    }

    init {
        this.mOnGetDatalistener = mOnGetDatalistener
    }
}