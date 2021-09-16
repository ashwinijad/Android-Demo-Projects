package com.example.mvpdemo.Contract

import android.content.Context
import com.example.mvpdemo.Model.RetroPhoto

interface RetroContract {

interface View {//activty or fragment
    fun onGetDataSuccess(message: String, list: List<RetroPhoto?>?)
    fun onGetDataFailure(message: String)
}

    interface Presenter {
        fun getDataFromURL()//context: Context?, url: String
    }

    interface Interactor {//repository
        fun initRetrofitCall()//context: Context?, url: String
    }

    interface onGetDataListener {//this will make interaction betn repo and presenter
        fun onSuccess(message: String?, list: List<RetroPhoto?>?)
        fun onFailure(message: String?)
    }
}
