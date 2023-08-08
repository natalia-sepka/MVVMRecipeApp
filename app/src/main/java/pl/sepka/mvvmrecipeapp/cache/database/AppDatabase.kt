package pl.sepka.mvvmrecipeapp.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.sepka.mvvmrecipeapp.cache.RecipeDao
import pl.sepka.mvvmrecipeapp.cache.model.RecipeEntity

@Database(entities = [RecipeEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao

    companion object {
        val DATABASE_NAME = "recipe_db"
    }
}
