package pl.sepka.mvvmrecipeapp.interactors.recipe_list

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.sepka.mvvmrecipeapp.cache.RecipeDao
import pl.sepka.mvvmrecipeapp.domain.data.DataState
import pl.sepka.mvvmrecipeapp.domain.mapper.toDomain
import pl.sepka.mvvmrecipeapp.domain.mapper.toRecipeEntity
import pl.sepka.mvvmrecipeapp.domain.model.Recipe
import pl.sepka.mvvmrecipeapp.interactors.BaseUseCase
import pl.sepka.mvvmrecipeapp.repository.RecipeRepository
import pl.sepka.mvvmrecipeapp.util.RECIPE_PAGINATION_PAGE_SIZE

class SearchRecipeUseCase(
    private val recipeDao: RecipeDao,
    private val recipeRepository: RecipeRepository
) : BaseUseCase<SearchRecipeUseCase.Params, List<Recipe>>() {

    override fun action(params: Params): Flow<DataState<List<Recipe>>> = flow {
        emit(DataState.loading())

        // TODO ("Check if there is an internet connection")
        val recipes = recipeRepository.search(
            token = params.token,
            page = params.page,
            query = params.query
        )

        // insert into the cache
        recipeDao.insertRecipes(recipes.map { it.toRecipeEntity() })

        // query the cache
        val cacheResult = if (params.query.isBlank()) {
            recipeDao.getAllRecipes(
                pageSize = RECIPE_PAGINATION_PAGE_SIZE,
                page = params.page
            )
        } else {
            recipeDao.searchRecipes(
                query = params.query,
                pageSize = RECIPE_PAGINATION_PAGE_SIZE,
                page = params.page
            )
        }
        emit(DataState.success(cacheResult.map { it.toDomain() }))
    }

    data class Params(val page: Int, val query: String, val token: String)
}
