package com.example.mvpdemo.Contract

import android.content.Context
import com.example.mvpdemo.Model.RetroPhoto

interface RetroContract {
/*
    interface countryModel {
        fun getCountyNameByCapital(*//*city: String,*//*
                                   presenter: countryPresenter)
        fun getCountry(): String
    }

    interface countryView {
        fun updateViewData()

    }

    interface countryPresenter {
        fun networkcall(*//*city: String*//*)
        fun showCountry() : String
        fun uiAutoUpdate()
    }*/
interface View {
    fun onGetDataSuccess(message: String, list: List<RetroPhoto?>?)
    fun onGetDataFailure(message: String)
}

    interface Presenter {
        fun getDataFromURL(context: Context?, url: String)
    }

    interface Interactor {
        fun initRetrofitCall(context: Context?, url: String)
    }

    interface onGetDataListener {
        fun onSuccess(message: String?, list: List<RetroPhoto?>?)
        fun onFailure(message: String?)
    }
}
