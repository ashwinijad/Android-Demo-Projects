package com.example.mvvmdemo1.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmdemo1.Model.RetroPhoto
import com.example.mvvmdemo1.api.GetDataService
import com.example.mvvmdemo1.api.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class ViewModel : ViewModel() {

     var heroListLivedata: MutableLiveData<List<RetroPhoto>> ?=
        MutableLiveData()



     fun loadHeroes() {
        val service = RetrofitClientInstance.retrofitInstance!!.create(GetDataService::class.java)
        val call: Call<List<RetroPhoto?>?>? = service.allPhotos
        call?.enqueue(object : Callback<List<RetroPhoto?>?>{
           override fun onResponse(call: Call<List<RetroPhoto?>?>?, response: Response<List<RetroPhoto?>?>) {

               heroListLivedata?.setValue(response.body() as List<RetroPhoto>?)            }

          override  fun onFailure(call: Call<List<RetroPhoto?>?>?, t: Throwable?) {

          }
        })
    }
}