package com.example.flixster

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import org.json.JSONArray

@Parcelize
data class Movie (
    val movieID: Int,
    val voteAverage: Double,
    private val posterPath: String,
    val title: String,
    var overview: String,
) : Parcelable {

    @IgnoredOnParcel
    val posterImageUrl = "https://image.tmdb.org/t/p/w342$posterPath"

    // companion objects allow us to call methods on
    // the movie class without having an instance.
    // So, if we define a function here, then we're able
    // to call that function without making an instance of
    // this Movie class
    companion object {

        // This functions iterates through JSONArray and
        // returns a list of these Movie data classes
        fun fromJsonArray(movieJsonArray: JSONArray): List<Movie> {
            val movies = mutableListOf<Movie>()
            for (i in 0 until movieJsonArray.length()) {
                val movieJson = movieJsonArray.getJSONObject(i)
                movies.add(
                    Movie(
                        movieJson.getInt("id"),
                        movieJson.getDouble("vote_average"),
                        movieJson.getString("poster_path"),
                        movieJson.getString("title"),
                        movieJson.getString("overview")
                    )
                )
            }
            return movies
        }
    }
}
