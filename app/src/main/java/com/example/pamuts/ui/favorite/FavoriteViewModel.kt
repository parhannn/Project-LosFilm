package com.example.pamuts.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pamuts.data.model.Movie
import com.example.pamuts.data.repository.MovieRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is favorite fragment with recylcer view and search view"
    }
    val text: LiveData<String> = _text

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    private val _query = MutableLiveData<String>()
    val query: LiveData<String> = _query

    fun getAllFavoriteMovie() {
        viewModelScope.launch {
            repository.getAllFavoriteMovie()
                .catch {
                    it.printStackTrace()
                }
                .collect {
                    _movies.value = it
                }
        }
    }

    fun searchFavoriteMovie(query: String) {
        viewModelScope.launch {
            repository.searchFavoriteMovie(query)
                .catch {
                    it.printStackTrace()
                }
                .collect {
                    _movies.value = it
                }
        }
    }

    fun setQuery(newQuery: String) {
        _query.value = newQuery
    }
}