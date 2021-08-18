package com.example.retrofit_recycler_mvp.Model

import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by Ashish on 06-02-2017.
 */
interface AllCountryResponse {
    @get:GET("/api/getData1.php?secure_id=nAN9qJlcBAR%2Fzs0R%2BZHJmII0W7GFPuRzY%2BfyrT65Fyw%3D&requireData=nationality&requireData=all&gofor=country")
    val country: Call<Country?>?
}