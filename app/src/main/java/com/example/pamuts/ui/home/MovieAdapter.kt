package com.example.pamuts.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pamuts.R
import com.example.pamuts.data.source.remote.response.MovieResponse
import com.example.pamuts.util.Constants

class MovieAdapter() :
    PagingDataAdapter<MovieResponse, MovieAdapter.MyViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((MovieResponse) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
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

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivMovieImage: AppCompatImageView = itemView.findViewById(R.id.movieImage)
        val tvMovieName: TextView = itemView.findViewById(R.id.movieTitle)

        fun bind(movieResponse: MovieResponse) {
            Glide.with(itemView)
                .load(Constants.IMAGE_URL + movieResponse.posterPath)
                .centerCrop()
                .into(ivMovieImage)

            tvMovieName.text = movieResponse.title
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieResponse>() {
            override fun areContentsTheSame(
                oldItem: MovieResponse,
                newItem: MovieResponse,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(
                oldItem: MovieResponse,
                newItem: MovieResponse,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
