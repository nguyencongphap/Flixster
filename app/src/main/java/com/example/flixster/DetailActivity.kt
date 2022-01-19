package com.example.flixster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RatingBar
import android.widget.TextView

private const val TAG = "DetailActivity"
class DetailActivity : AppCompatActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var rbVoteAverage: RatingBar
    private lateinit var tvOverview: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Specify that the UI for this activity is R.layout.activity_detail
        setContentView(R.layout.activity_detail)

        // Get reference to UI elements
        tvTitle = findViewById(R.id.tvTitle)
        rbVoteAverage = findViewById(R.id.rbVoteAverage)
        tvOverview = findViewById(R.id.tvOverview)

        // get the movie out of intent's extra
        // Cast as Movie because by default intent.getParcelableExtra<Movie>(MOVIE_EXTRA) is a
        // nullable object. By the design of our app, we know that it is always going to be a movie.
        // So, we cast it as a non-nullable movie
        val movie = intent.getParcelableExtra<Movie>(MOVIE_EXTRA) as Movie

        tvTitle.text = movie.title
        tvOverview.text = movie.overview
        rbVoteAverage.rating = movie.voteAverage.toFloat()
    }
}