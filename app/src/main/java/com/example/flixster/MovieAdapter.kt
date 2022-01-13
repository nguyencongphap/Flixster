package com.example.flixster

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


private const val TAG = "MovieAdapter"
// TODO: Continue here Flixter episode 3 19:43''

// We'll have MovieAdapter extends RecyclerView.Adapter.
// RecyclerView.Adapter is parametrized by the ViewHolder we define inside the class MovieAdapter
// RecyclerView.Adapter is an abstract class. Thus, we have to define the member functions
// onCreateViewHolder, onBindViewHolder, and getItemCount
class MovieAdapter(private val context: Context, private val movies: List<Movie>)
    : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    // Create a view holder of type ViewHolder that we defined below and return it
    // This is an Expensive operation
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.i(TAG, "onCreateViewHolder")
        // Get an instance of LayoutInflater from "context" and then inflate a layout
        // R.layout.item_movie is the individual row layout we define in item_movie.xml
        // to hold an individual movie info
        // function inflate returns a View item
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)

        // pass view to ViewHolder as a constructor parameter
        return ViewHolder(view)
    }

    // Main idea: given a ViewHolder and a position,we should take the data at that position and
    // bind it to the ViewHolder
    // This is a Cheap operation
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i(TAG, "onBindViewHolder position $position")
        val movie = movies[position]
        holder.bind(movie)
    }

    // Return the number of items in our data set
    override fun getItemCount(): Int {
        return movies.size
    }

    // A ViewHolder is a component which is already defined by Recyclerview
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // get references to the individual components in the item view:
        // which are the image view and the 2 text views
        // and populate it with the correct data in the movie
        private val ivPoster = itemView.findViewById<ImageView>(R.id.ivPoster)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvOverview = itemView.findViewById<TextView>(R.id.tvOverview)
        fun bind(movie: Movie) {
            tvTitle.text = movie.title
            tvOverview.text = movie.overview
            // populate Image using a library called Glide
            Glide.with(context).load(movie.posterImageUrl).into(ivPoster)
        }
    }
}
