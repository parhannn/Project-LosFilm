package com.example.pamuts.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.pamuts.data.Result
import com.example.pamuts.data.model.Movie
import com.example.pamuts.data.source.local.MovieDao
import com.example.pamuts.data.source.paging.MoviePagingSource
import com.example.pamuts.data.source.paging.MovieSearchPagingSource
import com.example.pamuts.data.source.remote.response.MovieDetailResponse
import com.example.pamuts.data.source.remote.response.MovieResponse
import com.example.pamuts.data.source.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow

class MovieRepository private constructor(
    private val apiService: ApiService,
    private val movieDao: MovieDao,
) : BaseRepository(), IMovieRepository {

    override fun getMovieNowPLaying(): LiveData<PagingData<MovieResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
            ),
            pagingSourceFactory = {
                MoviePagingSource(apiService)
            }
        ).liveData
    }

    override fun searchMovie(query: String): LiveData<PagingData<MovieResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
            ),
            pagingSourceFactory = {
                MovieSearchPagingSource(apiService, query)
            }
        ).liveData
    }

    override fun getMovieDetail(movieId: Int): LiveData<Result<MovieDetailResponse>> = liveData {
        emit(Result.Loading)
        emit(safeApiCall { apiService.getMovieDetails(movieId) })
    }

    override suspend fun addToFavorite(movie: Movie) {
        movieDao.addToFavorite(movie)
    }

    override suspend fun removeFromFavorite(movie: Movie) {
        movieDao.removeFromFavorite(movie)
    }

    override fun searchFavoriteMovie(query: String): Flow<List<Movie>> = movieDao.searchMovie(query)

    override fun getAllFavoriteMovie(): Flow<List<Movie>> = movieDao.getAllFavoriteMovies()

    override fun getMovieInFavorite(id: Int): Flow<List<Movie>> = movieDao.getMovieInFavorite(id)

    companion object {
        @Volatile
        private var instance: MovieRepository? = null
        fun getInstance(
            apiService: ApiService,
            movieDao: MovieDao,
        ): MovieRepository =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(apiService, movieDao)
            }.also { instance = it }
    }
}