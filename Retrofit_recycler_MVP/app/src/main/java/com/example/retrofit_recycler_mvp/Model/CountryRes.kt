package com.example.retrofit_recycler_mvp.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CountryRes {
    private var _0: String? = null
    var id = 0
    private var _1: String? = null
    var name: String? = null
    var phoneCode: String? = null
    var currencyId = 0

    @SerializedName("countryid")
    @Expose
    var countryid = 0

    @SerializedName("countryName")
    @Expose
    var countryName: String? = null
    fun get0(): String? {
        return _0
    }

    fun set0(_0: String?) {
        this._0 = _0
    }

    fun get1(): String? {
        return _1
    }

    fun set1(_1: String?) {
        this._1 = _1
    }
}