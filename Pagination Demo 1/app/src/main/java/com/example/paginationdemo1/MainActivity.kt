package com.example.paginationdemo1

import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.paginationdemo1.api.MovieApi
import com.example.paginationdemo1.api.MovieService
import com.example.paginationdemo1.model.Result
import com.example.paginationdemo1.model.TopRatedMovies
import com.example.paginationdemo1.utils.PaginationAdapterCallback
import com.example.paginationdemo1.utils.PaginationScrollListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeoutException


class MainActivity : AppCompatActivity(), PaginationAdapterCallback {
    var adapter: PaginationAdapter? = null
    var linearLayoutManager: LinearLayoutManager? = null
    var rv: RecyclerView? = null
    var progressBar: ProgressBar? = null
    var errorLayout: LinearLayout? = null
    var btnRetry: Button? = null
    var txtError: TextView? = null
    var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var isLastPage = false
    private val PAGE_START = 1
    private val TAG = "MainActivity"

    private var isLoading = false

    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    private val TOTAL_PAGES = 5
    private var currentPage = PAGE_START
    private var movieService: MovieService? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rv = findViewById(R.id.main_recycler)
        progressBar = findViewById(R.id.main_progress)
        errorLayout = findViewById(R.id.error_layout)
        btnRetry = findViewById(R.id.error_btn_retry)
        txtError = findViewById(R.id.error_txt_cause)
        swipeRefreshLayout = findViewById(R.id.main_swiperefresh)
        adapter = PaginationAdapter(this)
        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv?.setLayoutManager(linearLayoutManager)
        rv?.setItemAnimator(DefaultItemAnimator())
        rv?.setAdapter(adapter)
        rv?.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager!!) {
            protected override fun loadMoreItems() {
                isLoading = true
                currentPage += 1
                loadNextPage()
            }

            override fun totalPageCount(): Int {
                return TOTAL_PAGES
            }

            override fun isLastPage(): Boolean {
return isLastPage          }

            override fun isLoading(): Boolean {
return isLoading          }


        })

        //init service and load data
        movieService = MovieApi.getClient(this)!!.create(MovieService::class.java)
        loadFirstPage()
        btnRetry?.setOnClickListener(View.OnClickListener { view: View? -> loadFirstPage() })
        swipeRefreshLayout?.setOnRefreshListener { doRefresh() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_refresh -> {
                // Signal SwipeRefreshLayout to start the progress indicator
                swipeRefreshLayout?.setRefreshing(true)
                doRefresh()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Triggers the actual background refresh via the [SwipeRefreshLayout]
     */
    private fun doRefresh() {
        progressBar!!.visibility = View.VISIBLE
        if (callTopRatedMoviesApi().isExecuted()) callTopRatedMoviesApi().cancel()

        // TODO: Check if data is stale.
        //  Execute network request if cache is expired; otherwise do not update data.
        adapter?.getMnovies()?.clear()
        adapter!!.notifyDataSetChanged()
        isLoading = false
        currentPage += 1
        isLastPage = false
        loadFirstPage()
        swipeRefreshLayout?.setRefreshing(false)
    }

    private fun loadFirstPage() {
        Log.d(TAG, "loadFirstPage: ")

        // To ensure list is visible when retry button in error view is clicked
        hideErrorView()
        currentPage = PAGE_START
        callTopRatedMoviesApi().enqueue(object : Callback<TopRatedMovies> {
            override fun onResponse(
                call: Call<TopRatedMovies>,
                response: Response<TopRatedMovies>
            ) {
                hideErrorView()

//                Log.i(TAG, "onResponse: " + (response.raw().cacheResponse() != null ? "Cache" : "Network"));

                // Got data. Send it to adapter
                val results = fetchResults(response)
                progressBar!!.visibility = View.GONE
                results?.let { adapter!!.addAll(it) }
                if (currentPage <= TOTAL_PAGES) adapter!!.addLoadingFooter() else isLastPage =
                    true
            }

            override fun onFailure(call: Call<TopRatedMovies>, t: Throwable) {
                t.printStackTrace()
                showErrorView(t)
            }
        })
    }

    /**
     * @param response extracts List<[&gt;][Result] from response
     * @return
     */
    private fun fetchResults(response: Response<TopRatedMovies>): List<Result>? {
        val topRatedMovies: TopRatedMovies? = response.body()
        return topRatedMovies?.results
    }

    private fun loadNextPage() {
        Log.d(TAG, "loadNextPage: $currentPage")
        callTopRatedMoviesApi().enqueue(object : Callback<TopRatedMovies> {
            override fun onResponse(
                call: Call<TopRatedMovies>,
                response: Response<TopRatedMovies>
            ) {
//                Log.i(TAG, "onResponse: " + currentPage
//                        + (response.raw().cacheResponse() != null ? "Cache" : "Network"));
                adapter!!.removeLoadingFooter()
                isLoading = false
                val results = fetchResults(response)
                results?.let { adapter!!.addAll(it) }
                if (currentPage != TOTAL_PAGES) adapter!!.addLoadingFooter() else isLastPage =
                    true
            }

            override fun onFailure(call: Call<TopRatedMovies>, t: Throwable) {
                t.printStackTrace()
                adapter?.showRetry(true, fetchErrorMessage(t))
            }
        })
    }

    /**
     * Performs a Retrofit call to the top rated movies API.
     * Same API call for Pagination.
     * As [.currentPage] will be incremented automatically
     * by @[PaginationScrollListener] to load next page.
     */
    private fun callTopRatedMoviesApi(): Call<TopRatedMovies> {
        return movieService!!.getTopRatedMovies(
            getString(R.string.my_api_key),
            "en_US",
            currentPage
        )
    }

    override fun retryPageLoad() {
        loadNextPage()
    }

    /**
     * @param throwable required for [.fetchErrorMessage]
     * @return
     */
    private fun showErrorView(throwable: Throwable) {
        if (errorLayout!!.visibility == View.GONE) {
            errorLayout!!.visibility = View.VISIBLE
            progressBar!!.visibility = View.GONE
            txtError!!.text = fetchErrorMessage(throwable)
        }
    }

    /**
     * @param throwable to identify the type of error
     * @return appropriate error message
     */
    private fun fetchErrorMessage(throwable: Throwable): String {
        var errorMsg = resources.getString(R.string.error_msg_unknown)
        if (!isNetworkConnected) {
            errorMsg = resources.getString(R.string.error_msg_no_internet)
        } else if (throwable is TimeoutException) {
            errorMsg = resources.getString(R.string.error_msg_timeout)
        }
        return errorMsg
    }

    // Helpers -------------------------------------------------------------------------------------
    private fun hideErrorView() {
        if (errorLayout!!.visibility == View.VISIBLE) {
            errorLayout!!.visibility = View.GONE
            progressBar!!.visibility = View.VISIBLE
        }
    }

    /**
     * Remember to add android.permission.ACCESS_NETWORK_STATE permission.
     *
     * @return
     */
    private val isNetworkConnected: Boolean
        private get() {
            val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.activeNetworkInfo != null
        }

  /*  companion object {
        private const val TAG = "MainActivity"
        private const val PAGE_START = 1

        // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
        val totalPageCount = 5
            get() = Companion.field
    }*/
}
