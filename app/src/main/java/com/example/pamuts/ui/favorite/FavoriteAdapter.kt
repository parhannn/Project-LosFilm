package com.example.pamuts.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pamuts.R
import com.example.pamuts.data.model.Movie
import com.example.pamuts.util.Constants

class FavoriteAdapter() : ListAdapter<Movie, FavoriteAdapter.MyViewHolder>(
    DIFF_CALLBACK) {

    var onItemClick : ((Movie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item , parent , false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let { movie ->
            holder.bind(movie)
            holder.itemView.setOnClickListener {
                onItemClick?.invoke(movie)
            }
        }
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val ivMovieImage: AppCompatImageView = itemView.findViewById(R.id.movieImage)
        val tvMovieName: TextView = itemView.findViewById(R.id.movieTitle)

        fun bind(movie: Movie) {
            Glide.with(itemView)
                .load(Constants.IMAGE_URL + movie.posterPath)
                .centerCrop()
                .into(ivMovieImage)

            tvMovieName.text = movie.title
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }

}