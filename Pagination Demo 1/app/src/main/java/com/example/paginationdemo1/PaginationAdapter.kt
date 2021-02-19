package com.example.paginationdemo1

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.paginationdemo1.model.Result
import com.example.paginationdemo1.utils.GlideApp
import com.example.paginationdemo1.utils.GlideRequest
import com.example.paginationdemo1.utils.PaginationAdapterCallback
import java.util.*


class PaginationAdapter internal constructor(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder?>() {
    private var movieResults: MutableList<Result>?
    private var isLoadingAdded = false
    private var retryPageLoad = false
    private val mCallback: PaginationAdapterCallback
    private var errorMsg: String? = null
   /* val movies: List<Result>?
        get() = movieResults*/

    fun setMovies(movieResults: MutableList<Result>?) {
        this.movieResults = movieResults
    }
    fun getMnovies(): MutableList<Result>? {
        return movieResults
    }
    override fun onCreateViewHolder(
        @NonNull parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            ITEM -> {
                val viewItem = inflater.inflate(R.layout.item_list, parent, false)
                viewHolder = MovieVH(viewItem)
            }
            LOADING -> {
                val viewLoading = inflater.inflate(R.layout.item_progress, parent, false)
                viewHolder = LoadingVH(viewLoading)
            }
            HERO -> {
                val viewHero = inflater.inflate(R.layout.item_hero, parent, false)
                viewHolder = HeroVH(viewHero)
            }
        }
        return viewHolder!!
    }

    override fun onBindViewHolder(@NonNull holder: RecyclerView.ViewHolder, position: Int) {
        val result = movieResults!![position] // Movie
        when (getItemViewType(position)) {
            HERO -> {
                val heroVh = holder as HeroVH
                heroVh.mMovieTitle.setText(result.title)
                heroVh.mYear.text = formatYearLabel(result)
                heroVh.mMovieDesc.setText(result.overview)
                result.backdropPath?.let {
                    loadImage(it)
                        .into(heroVh.mPosterImg)
                }
            }
            ITEM -> {
                val movieVH = holder as MovieVH
                movieVH.mMovieTitle.setText(result.title)
                movieVH.mYear.text = formatYearLabel(result)
                movieVH.mMovieDesc.setText(result.overview)

                // load movie thumbnail
                result.posterPath?.let {
                    loadImage(it)
                        .listener(object : RequestListener<Drawable?> {
                            override fun onLoadFailed(
                                @Nullable e: GlideException?,
                                model: Any,
                                target: Target<Drawable?>,
                                isFirstResource: Boolean
                            ): Boolean {
                                // TODO: 2/16/19 Handle failure
                                movieVH.mProgress.visibility = View.GONE
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any,
                                target: Target<Drawable?>,
                                dataSource: DataSource,
                                isFirstResource: Boolean
                            ): Boolean {
                                // image ready, hide progress now
                                movieVH.mProgress.visibility = View.GONE
                                return false // return false if you want Glide to handle everything else.
                            }
                        })
                        .into(movieVH.mPosterImg)
                }
            }
            LOADING -> {
                val loadingVH = holder as LoadingVH
                if (retryPageLoad) {
                    loadingVH.mErrorLayout.visibility = View.VISIBLE
                    loadingVH.mProgressBar.visibility = View.GONE
                    loadingVH.mErrorTxt.text =
                        if (errorMsg != null) errorMsg else context.getString(R.string.error_msg_unknown)
                } else {
                    loadingVH.mErrorLayout.visibility = View.GONE
                    loadingVH.mProgressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return if (movieResults == null) 0 else movieResults!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            HERO
        } else {
            if (position == movieResults!!.size - 1 && isLoadingAdded) LOADING else ITEM
        }
    }
    /*
        Helpers - bind Views
   _________________________________________________________________________________________________
    */
    /**
     * @param result
     * @return [releasedate] | [2letterlangcode]
     */
    private fun formatYearLabel(result: Result): String {
        return (result.releaseDate?.substring(0, 4) // we want the year only
            .toString() + " | "
                + result.originalLanguage?.toUpperCase())
    }

    /**
     * Using Glide to handle image loading.
     * Learn more about Glide here:
     * [](http://blog.grafixartist.com/image-gallery-app-android-studio-1-4-glide/)
     *
     *
     * //     * @param posterPath from [Result.getPosterPath]
     *
     * @return Glide builder
     */
    //    private DrawableRequestBuilder<String> loadImage(@NonNull String posterPath) {
    //        return Glide
    //                .with(context)
    //                .load(BASE_URL_IMG + posterPath)
    //                .diskCacheStrategy(DiskCacheStrategy.ALL)   // cache both original & resized image
    //                .centerCrop()
    //                .crossFade();
    //    }
    private fun loadImage(@NonNull posterPath: String): GlideRequest<Drawable> {
        return GlideApp
            .with(context)
            .load(BASE_URL_IMG + posterPath)
            .centerCrop()
    }

    /*
        Helpers - Pagination
   _________________________________________________________________________________________________
    */
    fun add(r: Result) {
        movieResults!!.add(r)
        notifyItemInserted(movieResults!!.size - 1)
    }

    fun addAll(moveResults: List<Result>) {
        for (result in moveResults) {
            add(result)
        }
    }

    fun remove(r: Result) {
        val position = movieResults!!.indexOf(r)
        if (position > -1) {
            movieResults!!.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clear() {
        isLoadingAdded = false
        while (itemCount > 0) {
            remove(getItem(0))
        }
    }

    val isEmpty: Boolean
        get() = itemCount == 0

    fun addLoadingFooter() {
        isLoadingAdded = true
        add(Result())
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position = movieResults!!.size - 1
        val result = getItem(position)
        if (result != null) {
            movieResults!!.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun getItem(position: Int): Result {
        return movieResults!![position]
    }

    /**
     * Displays Pagination retry footer view along with appropriate errorMsg
     *
     * @param show
     * @param errorMsg to display if page load fails
     */
    fun showRetry(show: Boolean, @Nullable errorMsg: String?) {
        retryPageLoad = show
        notifyItemChanged(movieResults!!.size - 1)
        if (errorMsg != null) this.errorMsg = errorMsg
    }

    /*
   View Holders
   _________________________________________________________________________________________________
    */
    /**
     * Header ViewHolder
     */
    protected inner class HeroVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mMovieTitle: TextView
        val mMovieDesc: TextView
        val mYear // displays "year | language"
                : TextView
        val mPosterImg: ImageView

        init {
            mMovieTitle = itemView.findViewById(R.id.movie_title)
            mMovieDesc = itemView.findViewById(R.id.movie_desc)
            mYear = itemView.findViewById(R.id.movie_year)
            mPosterImg = itemView.findViewById(R.id.movie_poster)
        }
    }

    /**
     * Main list's content ViewHolder
     */
    protected inner class MovieVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mMovieTitle: TextView
        val mMovieDesc: TextView
        val mYear // displays "year | language"
                : TextView
        val mPosterImg: ImageView
        val mProgress: ProgressBar

        init {
            mMovieTitle = itemView.findViewById(R.id.movie_title)
            mMovieDesc = itemView.findViewById(R.id.movie_desc)
            mYear = itemView.findViewById(R.id.movie_year)
            mPosterImg = itemView.findViewById(R.id.movie_poster)
            mProgress = itemView.findViewById(R.id.movie_progress)
        }
    }

    protected inner class LoadingVH(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val mProgressBar: ProgressBar
        private val mRetryBtn: ImageButton
        val mErrorTxt: TextView
        val mErrorLayout: LinearLayout
        override fun onClick(view: View) {
            when (view.id) {
                R.id.loadmore_retry, R.id.loadmore_errorlayout -> {
                    showRetry(false, null)
                    mCallback.retryPageLoad()
                }
            }
        }

        init {
            mProgressBar = itemView.findViewById(R.id.loadmore_progress)
            mRetryBtn = itemView.findViewById(R.id.loadmore_retry)
            mErrorTxt = itemView.findViewById(R.id.loadmore_errortxt)
            mErrorLayout = itemView.findViewById(R.id.loadmore_errorlayout)
            mRetryBtn.setOnClickListener(this)
            mErrorLayout.setOnClickListener(this)
        }
    }

    companion object {
        // View Types
        private const val ITEM = 0
        private const val LOADING = 1
        private const val HERO = 2
        private const val BASE_URL_IMG = "https://image.tmdb.org/t/p/w200"
    }

    init {
        mCallback = context as PaginationAdapterCallback
        movieResults = ArrayList()
    }
}