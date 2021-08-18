package com.example.mvpdemo.api

import com.example.mvpdemo.Model.RetroPhoto
import retrofit2.Call
import retrofit2.http.GET


interface GetDataService {
    @get:GET("/photos")
    val allPhotos: Call<List<RetroPhoto?>?>?
}