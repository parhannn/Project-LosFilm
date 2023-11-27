package com.example.pamuts.di

import android.content.Context
import com.example.pamuts.data.repository.MovieRepository
import com.example.pamuts.data.source.local.MovieDatabase
import com.example.pamuts.data.source.remote.retrofit.ApiConfig
import com.example.pamuts.util.Constants

object Injection {

    fun provideMovieRepository(context: Context) : MovieRepository {
        val apiService = ApiConfig.getApiService(Constants.BEARER)
        val db = MovieDatabase.getDatabase(context)
        return MovieRepository.getInstance(apiService, db.movieDao())
    }
}