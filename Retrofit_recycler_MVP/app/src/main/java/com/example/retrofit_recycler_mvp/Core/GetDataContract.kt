package com.example.retrofit_recycler_mvp.Core

import android.content.Context
import com.example.retrofit_recycler_mvp.Model.CountryRes

/**
 * Created by Ashish on 28-09-2017.
 */
interface GetDataContract {
    interface View {
        fun onGetDataSuccess(message: String, list: List<CountryRes>)
        fun onGetDataFailure(message: String)
    }

    interface Presenter {
        fun getDataFromURL(context: Context?, url: String)
    }

    interface Interactor {
        fun initRetrofitCall(context: Context?, url: String)
    }

    interface onGetDataListener {
        fun onSuccess(message: String?, list: List<CountryRes>)
        fun onFailure(message: String?)
    }
}