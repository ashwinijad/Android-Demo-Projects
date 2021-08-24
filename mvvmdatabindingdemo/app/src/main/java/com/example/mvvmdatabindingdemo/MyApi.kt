package com.example.mvvmdatabindingdemo

import retrofit2.Call
import retrofit2.http.GET


interface MyApi {
    @GET("androidwebmvvm.php")
    fun getartistdata(): Call<ArrayList<RetroPhoto>?>?
}