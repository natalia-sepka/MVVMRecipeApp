package pl.sepka.mvvmrecipeapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.sepka.mvvmrecipeapp.network.RecipeService
import pl.sepka.mvvmrecipeapp.repository.RecipeRepository
import pl.sepka.mvvmrecipeapp.repository.RecipeRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRecipeRepository(recipeService: RecipeService) = RecipeRepositoryImpl(recipeService) as RecipeRepository
}
