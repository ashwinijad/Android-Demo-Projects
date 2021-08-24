package com.example.mvvmdatabindingdemo

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyListViewModel : ViewModel {
    var id = ""
    var artistname = ""
    var imageurl = ""
    var moviename = ""
    var mutableLiveData1 = MutableLiveData<ArrayList<MyListViewModel>>()
    private var arrayList: ArrayList<MyListViewModel>? = null
   // private val userReposin: UserReposin? = null
    private var myList: ArrayList<RetroPhoto>? = null

    constructor(myList: RetroPhoto) {
        id = myList.id.toString()
        artistname = myList.id.toString()
        imageurl = myList.artistimage
        moviename = myList.moviename
    }

    fun getMutableLiveData(): MutableLiveData<ArrayList<MyListViewModel>> {
        arrayList = ArrayList()
        val api: MyApi? = MyClient.instance?.myApi
        val call: Call<ArrayList<RetroPhoto>?>? = api?.getartistdata()
        call?.enqueue(object : Callback<ArrayList<RetroPhoto>?> {
         override   fun onResponse(
                call: Call<ArrayList<RetroPhoto>?>?,
                response: Response<ArrayList<RetroPhoto>?>
            ) {
                myList = ArrayList()
                myList = response.body()
                for (i in 0 until myList!!.size) {
                    val myk: RetroPhoto = myList!![i]
                    val myListViewModel = MyListViewModel(myk)
                    arrayList!!.add(myListViewModel)
                    mutableLiveData1.setValue(arrayList)
                }
            }

         override   fun onFailure(call: Call<ArrayList<RetroPhoto>?>?, t: Throwable?) {}
        })
        return mutableLiveData1
    }

    companion object {
        @BindingAdapter("imageUrl")
        @JvmStatic

        fun loadimage(imageView: ImageView, imageUrl: String?) {
/*            Glide.with(imageView.getContext()).load(imageUrl)
                .apply(RequestOptions.circleCropTransform()).into<Target<Drawable>>(imageView)*/
            Picasso.with(imageView.getContext()).load(imageUrl).into(imageView);
        }
    }
}