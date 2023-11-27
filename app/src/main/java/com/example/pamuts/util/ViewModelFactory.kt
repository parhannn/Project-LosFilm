package com.example.pamuts.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pamuts.di.Injection
import com.example.pamuts.ui.favorite.FavoriteViewModel
import com.example.pamuts.ui.home.HomeViewModel
import com.example.pamuts.ui.home.MovieDetailViewModel

class ViewModelFactory(val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when(modelClass){
        HomeViewModel::class.java -> HomeViewModel(Injection.provideMovieRepository(context))
        MovieDetailViewModel::class.java -> MovieDetailViewModel(Injection.provideMovieRepository(context))
        FavoriteViewModel::class.java -> FavoriteViewModel(Injection.provideMovieRepository(context))
        else -> throw IllegalArgumentException("Unknown ViewModel class")
    } as T
}