package com.example.pamuts.data.source.remote.retrofit

import com.example.pamuts.data.source.remote.response.MovieDetailResponse
import com.example.pamuts.data.source.remote.response.MovieListResponse
import com.example.pamuts.data.source.remote.response.MovieSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/now_playing")
    suspend fun getMovieNowPlaying(
        @Query("page") page: Int = 1
    ) : Response<MovieListResponse>

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("page") page: Int = 1,
        @Query("query") query: String
    ) : Response<MovieSearchResponse>

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Int
    ) : Response<MovieDetailResponse>
}