package com.example.mvvmdemo1.api

import com.example.mvvmdemo1.Model.RetroPhoto
import retrofit2.Call
import retrofit2.http.GET


interface GetDataService {
    @get:GET("/photos")
    val allPhotos: Call<List<RetroPhoto?>?>?
}