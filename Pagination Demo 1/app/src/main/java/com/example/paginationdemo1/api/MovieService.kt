package com.example.paginationdemo1.api

import com.example.paginationdemo1.model.TopRatedMovies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String?,
        @Query("language") language: String?,
        @Query("page") pageIndex: Int
    ): Call<TopRatedMovies>
}