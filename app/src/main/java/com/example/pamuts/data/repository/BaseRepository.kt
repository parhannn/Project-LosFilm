package com.example.pamuts.data.repository

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import com.example.pamuts.data.Result
import com.example.pamuts.data.source.remote.response.ErrorResponse

abstract class BaseRepository() {
    suspend fun <T> safeApiCall(apiToBeCalled: suspend() -> Response<T>): Result<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response: Response<T> = apiToBeCalled()

                if (response.isSuccessful) {
                    Result.Success(data = response.body()!!)
                } else {
                    val gson = Gson().getAdapter(ErrorResponse::class.java)
                    Result.Error(error = gson.fromJson(response.errorBody()?.string()).statusMessage ?: "")
                }
            } catch (e: HttpException) {
                Result.Error(error = e.message ?: "Something went wrong")
            } catch (e: IOException) {
                Result.Error(error = "Please check your network connection")
            } catch (e: Exception) {
                Result.Error(error = "Something went wrong")
            }
        }
    }
}