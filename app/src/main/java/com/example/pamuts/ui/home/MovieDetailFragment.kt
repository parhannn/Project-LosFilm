package com.example.pamuts.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.pamuts.MainActivity
import com.example.pamuts.R
import com.example.pamuts.data.Result
import com.example.pamuts.data.model.Movie
import com.example.pamuts.databinding.MovieDetailBinding
import com.example.pamuts.util.Constants
import com.example.pamuts.util.ViewModelFactory

class MovieDetailFragment : Fragment() {

    private var _binding: MovieDetailBinding? = null
    private val viewModel: MovieDetailViewModel by viewModels { ViewModelFactory(requireContext()) }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = MovieDetailBinding.inflate(inflater, container, false)
        binding.goBack.setOnClickListener {
            activity?.let {
                val intent = Intent(it, MainActivity::class.java)
                it.startActivity(intent)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt(EXTRA_ID)
        if (id != null) {
            viewModel.getMovieInFavorite(id)

            viewModel.getMovieDetail(id).observe(viewLifecycleOwner) { result ->
                when(result) {
                    Result.Loading -> {
                    }
                    is Result.Error -> {
                        Toast.makeText(context, result.error, Toast.LENGTH_SHORT).show()
                    }
                    is Result.Success -> {
                        val movie = result.data
                        binding.apply {
                            Glide.with(requireContext())
                                .load(Constants.IMAGE_URL + movie.backdropPath)
                                .into(ivMovieBackdrop)

                            Glide.with(requireContext())
                                .load(Constants.IMAGE_URL + movie.posterPath)
                                .into(ivMoviePoster)

                            tvDate.text = movie.releaseDate ?: ""
                            tvMovieTitle.text = movie.title ?: ""
                            tvOverview.text = movie.overview ?: ""
                            tvStar.text = movie.voteAverage.toString()
                            tvTagline.text = movie.tagline ?: ""
                            tvTime.text = movie.runtime.toString().plus(" min")
                        }

                        binding.favorite.setOnClickListener {
                            val movieData = Movie(
                                id = id,
                                title = movie.title,
                                posterPath = movie.posterPath,
                            )

                            if (viewModel.isFavorited.value == true) {
                                viewModel.removeFromFavorite(movieData)
                            } else {
                                viewModel.addToFavorite(movieData)
                            }
                        }
                    }
                }
            }

            viewModel.isFavorited.observe(viewLifecycleOwner) { isFavorited ->
                if (isFavorited) {
                    binding.favorite.setImageResource(R.drawable.baseline_favorite_24)
                } else {
                    binding.favorite.setImageResource(R.drawable.baseline_favorite_border_24)
                }
            }

        }
    }



    companion object {
        var EXTRA_ID = "extra_id"
    }

}