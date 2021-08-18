package com.example.retrofit_recycler_mvp.Core

import android.content.Context
import com.example.retrofit_recycler_mvp.Model.CountryRes

/**
 * Created by Ashish on 28-09-2017.
 */
class Presenter(private val mGetDataView: GetDataContract.View) : GetDataContract.Presenter,
    GetDataContract.onGetDataListener {
    private val mIntractor: Intractor
    override fun getDataFromURL(context: Context?, url: String) {
        mIntractor.initRetrofitCall(context, url)
    }

   override fun onSuccess(message: String?, allCountriesData: List<CountryRes>) {
       if (message != null) {
           mGetDataView.onGetDataSuccess(message, allCountriesData)
       }
    }

    override fun onFailure(message: String?) {
        if (message != null) {
            mGetDataView.onGetDataFailure(message)
        }
    }

    init {
        mIntractor = Intractor(this)
    }
}