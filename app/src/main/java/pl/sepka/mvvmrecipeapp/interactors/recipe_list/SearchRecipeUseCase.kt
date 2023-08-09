package pl.sepka.mvvmrecipeapp.interactors.recipe_list

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.sepka.mvvmrecipeapp.cache.RecipeDao
import pl.sepka.mvvmrecipeapp.domain.data.DataState
import pl.sepka.mvvmrecipeapp.domain.mapper.toDomain
import pl.sepka.mvvmrecipeapp.domain.mapper.toRecipeEntity
import pl.sepka.mvvmrecipeapp.domain.model.Recipe
import pl.sepka.mvvmrecipeapp.repository.RecipeRepository
import pl.sepka.mvvmrecipeapp.util.RECIPE_PAGINATION_PAGE_SIZE
import java.lang.Exception

class SearchRecipeUseCase(
    private val recipeDao: RecipeDao,
    private val recipeRepository: RecipeRepository
) {
    fun execute(token: String, page: Int, query: String): Flow<DataState<List<Recipe>>> = flow {
        try {
            emit(DataState.loading())

            // TODO ("Check if there is an internet connection")
            val recipes = recipeRepository.search(token = token, page = page, query = query)

            // insert into the cache
            recipeDao.insertRecipes(recipes.map { it.toRecipeEntity() })

            // query the cache
            val cacheResult = if (query.isBlank()) {
                recipeDao.getAllRecipes(
                    pageSize = RECIPE_PAGINATION_PAGE_SIZE,
                    page = page
                )
            } else {
                recipeDao.searchRecipes(
                    query = query,
                    pageSize = RECIPE_PAGINATION_PAGE_SIZE,
                    page = page
                )
            }
            emit(DataState.success(cacheResult.map { it.toDomain() }))
        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "Unknown error"))
        }
    }
}
