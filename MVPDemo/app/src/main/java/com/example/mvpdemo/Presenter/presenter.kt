package com.example.mvpdemo.Presenter

import android.content.Context
import com.example.mvpdemo.Contract.RetroContract
import com.example.mvpdemo.Model.RetroPhoto
import com.example.mvpdemo.Repository.RetroRepo

class presenter(private val mGetDataView: RetroContract.View) : RetroContract.Presenter, RetroContract.onGetDataListener {

    private val mIntractor: RetroRepo
    override fun getDataFromURL() {
        mIntractor.initRetrofitCall()
    }

    override fun onSuccess(message: String?, allCountriesData: List<RetroPhoto?>?) {
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
        mIntractor = RetroRepo(this)
    }
}
