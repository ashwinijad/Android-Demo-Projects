package com.example.mvpdemo.Presenter

import android.content.Context
import com.example.mvpdemo.Contract.RetroContract
import com.example.mvpdemo.Model.RetroPhoto
import com.example.mvpdemo.Repository.RetroRepo

/*
class presenter (countryView: RetroContract.countryView) : RetroContract.countryPresenter {


    private var view: RetroContract.countryView = countryView
    private var model: RetroContract.countryModel = RetroRepo()


    override fun uiAutoUpdate() {
        view.updateViewData()
    }

    override fun networkcall(*/
/*city: String*//*
) {
        model?.getCountyNameByCapital( this)
    }

    override fun showCountry() = model.getCountry()

}*/
class presenter(private val mGetDataView: RetroContract.View) : RetroContract.Presenter,
    RetroContract.onGetDataListener {
    private val mIntractor: RetroRepo
    override fun getDataFromURL(context: Context?, url: String) {
        mIntractor.initRetrofitCall(context, url)
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
