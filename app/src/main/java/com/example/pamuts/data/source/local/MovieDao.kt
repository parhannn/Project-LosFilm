package com.example.pamuts.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pamuts.data.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getAllFavoriteMovies(): Flow<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorite(movie: Movie)

    @Delete
    suspend fun removeFromFavorite(movie: Movie)

    @Query("SELECT * FROM movie WHERE title LIKE '%' || :query || '%'")
    fun searchMovie(query: String): Flow<List<Movie>>

    @Query("SELECT * FROM movie WHERE id = :id")
    fun getMovieInFavorite(id: Int): Flow<List<Movie>>
}