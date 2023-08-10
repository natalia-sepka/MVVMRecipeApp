package pl.sepka.mvvmrecipeapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import pl.sepka.mvvmrecipeapp.cache.database.AppDatabase
import pl.sepka.mvvmrecipeapp.interactors.recipe.GetRecipeUseCase
import pl.sepka.mvvmrecipeapp.interactors.recipe_list.RestoreRecipesUseCase
import pl.sepka.mvvmrecipeapp.interactors.recipe_list.SearchRecipeUseCase
import pl.sepka.mvvmrecipeapp.repository.RecipeRepository

@Module
@InstallIn(ViewModelComponent::class)
object InteractorModule {

    @ViewModelScoped
    @Provides
    fun provideSearchRecipeUseCase(db: AppDatabase, recipeRepository: RecipeRepository) =
        SearchRecipeUseCase(recipeDao = db.recipeDao(), recipeRepository = recipeRepository)

    @ViewModelScoped
    @Provides
    fun provideRestoreRecipeUseCase(db: AppDatabase) = RestoreRecipesUseCase(recipeDao = db.recipeDao())

    @ViewModelScoped
    @Provides
    fun provideGetRecipeUseCase(db: AppDatabase, recipeRepository: RecipeRepository) =
        GetRecipeUseCase(recipeDao = db.recipeDao(), recipeRepository = recipeRepository)
}
