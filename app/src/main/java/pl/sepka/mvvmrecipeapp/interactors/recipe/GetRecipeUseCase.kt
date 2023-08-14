package pl.sepka.mvvmrecipeapp.interactors.recipe

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.sepka.mvvmrecipeapp.cache.RecipeDao
import pl.sepka.mvvmrecipeapp.domain.data.DataState
import pl.sepka.mvvmrecipeapp.domain.mapper.toDomain
import pl.sepka.mvvmrecipeapp.domain.mapper.toRecipeEntity
import pl.sepka.mvvmrecipeapp.domain.model.Recipe
import pl.sepka.mvvmrecipeapp.interactors.BaseUseCase
import pl.sepka.mvvmrecipeapp.repository.RecipeRepository

class GetRecipeUseCase(
    private val recipeDao: RecipeDao,
    private val recipeRepository: RecipeRepository
) : BaseUseCase<GetRecipeUseCase.Params, Recipe>() {

    override fun action(params: Params): Flow<DataState<Recipe>> = flow {
        emit(DataState.loading())

        var recipe = recipeDao.getRecipeByID(params.recipeId)?.toDomain()

        if (recipe != null) {
            emit(DataState(recipe))
        } else {
            if (params.isNetworkAvailable) {
                val networkRecipe = recipeRepository.get(params.token, params.recipeId)
                recipeDao.insertRecipe(networkRecipe.toRecipeEntity())
            }

            recipe = recipeDao.getRecipeByID(id = params.recipeId)?.toDomain()
            recipe?.let { emit(DataState(it)) } ?: throw Exception("Unable to get recipe from the cache.")
        }
    }

    data class Params(val recipeId: Int, val token: String, val isNetworkAvailable: Boolean)
}
