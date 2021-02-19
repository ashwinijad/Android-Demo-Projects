package com.example.paginationdemo1.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

class Result {
    /**
     *
     * @return
     * The posterPath
     */
    /**
     *
     * @param posterPath
     * The poster_path
     */
    @SerializedName("poster_path")
    @Expose
    var posterPath: String? = null
    /**
     *
     * @return
     * The adult
     */
    /**
     *
     * @param adult
     * The adult
     */
    @SerializedName("adult")
    @Expose
    var adult: Boolean? = null
    /**
     *
     * @return
     * The overview
     */
    /**
     *
     * @param overview
     * The overview
     */
    @SerializedName("overview")
    @Expose
    var overview: String? = null
    /**
     *
     * @return
     * The releaseDate
     */
    /**
     *
     * @param releaseDate
     * The release_date
     */
    @SerializedName("release_date")
    @Expose
    var releaseDate: String? = null
    /**
     *
     * @return
     * The genreIds
     */
    /**
     *
     * @param genreIds
     * The genre_ids
     */
    @SerializedName("genre_ids")
    @Expose
    var genreIds: List<Int> = ArrayList()
    /**
     *
     * @return
     * The id
     */
    /**
     *
     * @param id
     * The id
     */
    @SerializedName("id")
    @Expose
    var id: Int? = null
    /**
     *
     * @return
     * The originalTitle
     */
    /**
     *
     * @param originalTitle
     * The original_title
     */
    @SerializedName("original_title")
    @Expose
    var originalTitle: String? = null
    /**
     *
     * @return
     * The originalLanguage
     */
    /**
     *
     * @param originalLanguage
     * The original_language
     */
    @SerializedName("original_language")
    @Expose
    var originalLanguage: String? = null
    /**
     *
     * @return
     * The title
     */
    /**
     *
     * @param title
     * The title
     */
    @SerializedName("title")
    @Expose
    var title: String? = null
    /**
     *
     * @return
     * The backdropPath
     */
    /**
     *
     * @param backdropPath
     * The backdrop_path
     */
    @SerializedName("backdrop_path")
    @Expose
    var backdropPath: String? = null
    /**
     *
     * @return
     * The popularity
     */
    /**
     *
     * @param popularity
     * The popularity
     */
    @SerializedName("popularity")
    @Expose
    var popularity: Double? = null
    /**
     *
     * @return
     * The voteCount
     */
    /**
     *
     * @param voteCount
     * The vote_count
     */
    @SerializedName("vote_count")
    @Expose
    var voteCount: Int? = null
    /**
     *
     * @return
     * The video
     */
    /**
     *
     * @param video
     * The video
     */
    @SerializedName("video")
    @Expose
    var video: Boolean? = null
    /**
     *
     * @return
     * The voteAverage
     */
    /**
     *
     * @param voteAverage
     * The vote_average
     */
    @SerializedName("vote_average")
    @Expose
    var voteAverage: Double? = null
}
