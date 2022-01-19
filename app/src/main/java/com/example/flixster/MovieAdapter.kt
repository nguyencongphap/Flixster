package com.example.flixster

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

/*
the Adapter is the glue between the Views, the RecyclerView, and the underline
dataset
The logic for the Adapter is part of the MovieAdapter class
 */


const val MOVIE_EXTRA = "MOVIE_EXTRA"
private const val TAG = "MovieAdapter"

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
    // Interfaces added here are: RecyclerView.ViewHolder(itemView), View.OnClickListener
    // View.OnClickListener is an interface that has exactly 1 method to override called onClick()
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        // get references to the individual components in the item view:
        // which are the image view and the 2 text views
        // and populate it with the correct data in the movie
        private val ivPoster = itemView.findViewById<ImageView>(R.id.ivPoster)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvOverview = itemView.findViewById<TextView>(R.id.tvOverview)

        // register the ClickListener
        // Every time we create a new ViewHolder,
        init {
            // "this" refers to the class ViewHodler, and the class is implementing an appropriate
            // interface View.OnClickListener. That's why "this" works.
            itemView.setOnClickListener(this)
        }
        // Bind one particular element in our dataset to a view
        fun bind(movie: Movie) {

            // This is where we write the logic to attach a ClickListener that gets notified when
            // the user taps on the element.

            tvTitle.text = movie.title
            tvOverview.text = movie.overview
            // populate Image using a library called Glide
            Glide.with(context).load(movie.posterImageUrl).into(ivPoster)
        }

        override fun onClick(p0: View?) {
            // 1. Get notified of the particular movie which was clicked
            // access the movie in the dataset at the index of this particular ViewHolder
            // adapterPosition returns the position of this particular ViewHolder
            val movie = movies[adapterPosition]

            // 2. Use the intent system to navigate to the new activity
            // Create a new intent: pass in the context, pass in the activity class where we
            // want to go
            val intent = Intent(context, DetailActivity::class.java)
            // tell the context to begin sending the intent
            // pass movie as a part of the intent
            // since the type of movie cannot be passed directly into putExtra, we have to make
            // movie become a Parcelable object which is the type that putExtra accepts.
            // Serializable and Parcelable are ways that tell the Android system how to deconstruct
            // your complicated object (movie in this case) into component parts and reconstruct
            // that complicated object. Parcelable is more performant and modern way of passing data
            // between activities than Serializable.
            intent.putExtra(MOVIE_EXTRA, movie)


            context.startActivity(intent)
        }
    }
}
