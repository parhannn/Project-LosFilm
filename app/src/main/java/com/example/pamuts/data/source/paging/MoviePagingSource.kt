package com.example.pamuts.data.source.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pamuts.data.source.remote.response.MovieResponse
import com.example.pamuts.data.source.remote.retrofit.ApiService

class MoviePagingSource(private val apiService: ApiService) : PagingSource<Int, MovieResponse>() {
    override fun getRefreshKey(state: PagingState<Int, MovieResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getMovieNowPlaying(position).body()?.results
            LoadResult.Page(
                data = responseData as List<MovieResponse>? ?: listOf(),
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.isNullOrEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}