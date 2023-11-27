package com.example.pamuts.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pamuts.data.repository.MovieRepository

class HomeViewModel(
    private val movieRepository: MovieRepository,
) : ViewModel() {
    private val _query = MutableLiveData<String>()
    val query: LiveData<String> = _query

    init {
        _query.value = ""
    }

    fun getMovieNowPlaying() = movieRepository.getMovieNowPLaying()

    fun searchMovie(movieQuery: String) = movieRepository.searchMovie(movieQuery)

    fun setQuery(newQuery: String) {
        _query.value = newQuery
    }
}