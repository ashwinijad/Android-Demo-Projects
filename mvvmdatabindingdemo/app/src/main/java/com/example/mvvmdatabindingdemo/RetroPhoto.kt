package com.example.mvvmdatabindingdemo

import com.google.gson.annotations.SerializedName


class RetroPhoto {
    @SerializedName("id")
    var id = ""

    @SerializedName("name")
    var artistname = ""

    @SerializedName("imageurl")
    var artistimage = ""

    @SerializedName("moviename")
    var moviename = ""

    constructor(id: String, artistname: String, artistimage: String, moviename: String) {
        this.id = id
        this.artistname = artistname
        this.artistimage = artistimage
        this.moviename = moviename
    }

    constructor() {}
}