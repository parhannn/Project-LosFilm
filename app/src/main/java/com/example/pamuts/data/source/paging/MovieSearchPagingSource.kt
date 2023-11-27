package com.example.pamuts.data.source.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pamuts.data.source.remote.response.MovieResponse
import com.example.pamuts.data.source.remote.retrofit.ApiService

class MovieSearchPagingSource(private val apiService: ApiService, private val query: String) : PagingSource<Int, MovieResponse>() {
    override fun getRefreshKey(state: PagingState<Int, MovieResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.searchMovie(position, query).body()?.results
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