package com.example.mvvmdatabindingdemo

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MyClient private constructor() {
    private val retrofit: Retrofit
    val myApi: MyApi
        get() = retrofit.create(MyApi::class.java)

    companion object {
        private const val BASE_URL = "https://uniqueandrocode.000webhostapp.com/hiren/"
        private var myClient: MyClient? = null

        @get:Synchronized
        val instance: MyClient?
            get() {
                if (myClient == null) {
                    myClient = MyClient()
                }
                return myClient
            }
    }

    init {
        retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}