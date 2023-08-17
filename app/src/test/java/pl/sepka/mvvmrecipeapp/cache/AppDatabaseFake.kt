package pl.sepka.mvvmrecipeapp.cache

import pl.sepka.mvvmrecipeapp.cache.model.RecipeEntity

class AppDatabaseFake {

    val recipes = mutableListOf<RecipeEntity>()
}
