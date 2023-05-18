package pl.sepka.mvvmrecipeapp.network

import pl.sepka.mvvmrecipeapp.network.model.RecipeDTO
import pl.sepka.mvvmrecipeapp.network.model.response.RecipeSearchDTO
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RecipeService {

    @GET("search")
    suspend fun search(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("query") query: String
    ): RecipeSearchDTO

    @GET("get")
    suspend fun get(
        @Header("Authorization") token: String,
        @Query("id") id: Int
    ): RecipeDTO
}
