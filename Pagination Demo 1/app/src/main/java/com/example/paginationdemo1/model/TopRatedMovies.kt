package com.example.paginationdemo1.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

class TopRatedMovies {
    /**
     *
     * @return
     * The page
     */
    /**
     *
     * @param page
     * The page
     */
    @SerializedName("page")
    @Expose
    var page: Int? = null
    /**
     *
     * @return
     * The results
     */
    /**
     *
     * @param results
     * The results
     */
    @SerializedName("results")
    @Expose
    var results: List<Result> = ArrayList()
    /**
     *
     * @return
     * The totalResults
     */
    /**
     *
     * @param totalResults
     * The total_results
     */
    @SerializedName("total_results")
    @Expose
    var totalResults: Int? = null
    /**
     *
     * @return
     * The totalPages
     */
    /**
     *
     * @param totalPages
     * The total_pages
     */
    @SerializedName("total_pages")
    @Expose
    var totalPages: Int? = null
}