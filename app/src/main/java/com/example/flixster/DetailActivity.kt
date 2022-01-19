package com.example.flixster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RatingBar
import android.widget.TextView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import okhttp3.Headers


private const val YOUTUBE_API_KEY = "AIzaSyB-eWrxo2y_fTxyLxsy2065Nbz7Qip9yBg"
private const val TRAILERS_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
private const val TAG = "DetailActivity"
class DetailActivity : YouTubeBaseActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var rbVoteAverage: RatingBar
    private lateinit var tvOverview: TextView
    private lateinit var ytPlayerView: YouTubePlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Specify that the UI for this activity is R.layout.activity_detail
        setContentView(R.layout.activity_detail)

        // Get reference to UI elements
        tvTitle = findViewById(R.id.tvTitle)
        rbVoteAverage = findViewById(R.id.rbVoteAverage)
        tvOverview = findViewById(R.id.tvOverview)
        ytPlayerView = findViewById(R.id.player)

        // get the movie out of intent's extra
        // Cast as Movie because by default intent.getParcelableExtra<Movie>(MOVIE_EXTRA) is a
        // nullable object. By the design of our app, we know that it is always going to be a movie.
        // So, we cast it as a non-nullable movie
        val movie = intent.getParcelableExtra<Movie>(MOVIE_EXTRA) as Movie

        tvTitle.text = movie.title
        tvOverview.text = movie.overview
        rbVoteAverage.rating = movie.voteAverage.toFloat()

        val client = AsyncHttpClient()
        client.get(TRAILERS_URL.format(movie.movieID), object: JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "onFailure $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                Log.i(TAG, "onSuccess")
                val results = json.jsonObject.getJSONArray("results")
                if (results.length() == 0) {
                    Log.w(TAG, "No movie trailers found")
                    return
                }
                val movieTrailerJson = results.getJSONObject(0)
                val youtubeKey = movieTrailerJson.getString("key")
                // play youtube video with this trailer
                initializeYoutube(youtubeKey)
            }

        })


    }

    private fun initializeYoutube(youtubeKey: String) {
        ytPlayerView.initialize(YOUTUBE_API_KEY, object: YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider?,
                player: YouTubePlayer?,
                p2: Boolean
            ) {
                Log.i(TAG, "onInitializationSuccess")
                player?.cueVideo(youtubeKey);
            }

            override fun onInitializationFailure(
                provider: YouTubePlayer.Provider?,
                initResult: YouTubeInitializationResult?
            ) {
                Log.e(TAG, "onInitializationFailure")
            }
        })
    }
}