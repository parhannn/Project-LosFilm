package com.example.pamuts.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pamuts.data.model.Movie
import com.example.pamuts.data.repository.MovieRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val _isFavorited = MutableLiveData<Boolean>()
    val isFavorited: LiveData<Boolean> get() = _isFavorited

    fun getMovieDetail(movieId: Int) = repository.getMovieDetail(movieId)

    fun getMovieInFavorite(id: Int) {
        viewModelScope.launch {
            repository.getMovieInFavorite(id)
                .catch {
                    it.printStackTrace()
                }
                .collect {
                    _isFavorited.value = it.isNotEmpty()
                }
        }
    }

    fun removeFromFavorite(movie: Movie) {
        viewModelScope.launch {
            repository.removeFromFavorite(movie)
            _isFavorited.value = false
        }
    }

    fun addToFavorite(movie: Movie) {
        viewModelScope.launch {
            repository.addToFavorite(movie)
            _isFavorited.value = true
        }
    }
}