package com.example.pamuts.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.pamuts.data.Result
import com.example.pamuts.data.model.Movie
import com.example.pamuts.data.source.remote.response.MovieDetailResponse
import com.example.pamuts.data.source.remote.response.MovieResponse
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getMovieNowPLaying(): LiveData<PagingData<MovieResponse>>
    fun searchMovie(query: String): LiveData<PagingData<MovieResponse>>
    fun getMovieDetail(movieId: Int): LiveData<Result<MovieDetailResponse>>
    suspend fun addToFavorite(movie: Movie)
    suspend fun removeFromFavorite(movie: Movie)
    fun searchFavoriteMovie(query: String): Flow<List<Movie>>

    fun getAllFavoriteMovie(): Flow<List<Movie>>

    fun getMovieInFavorite(id: Int): Flow<List<Movie>>

}