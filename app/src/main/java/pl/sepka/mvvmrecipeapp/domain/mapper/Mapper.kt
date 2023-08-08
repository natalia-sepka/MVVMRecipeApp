package pl.sepka.mvvmrecipeapp.domain.mapper

import pl.sepka.mvvmrecipeapp.cache.model.RecipeEntity
import pl.sepka.mvvmrecipeapp.domain.model.Recipe
import pl.sepka.mvvmrecipeapp.network.model.RecipeDTO
import pl.sepka.mvvmrecipeapp.util.DateUtil
import java.util.*

fun RecipeDTO.toDomain() = Recipe(
    id = this.pk,
    title = this.title,
    publisher = this.publisher,
    featuredImage = this.featuredImage,
    rating = this.rating,
    sourceUrl = this.sourceUrl,
    ingredients = this.ingredients.map { it },
    dateAdded = DateUtil.longToDate(this.longDateAdded),
    dateUpdated = DateUtil.longToDate(this.longDateUpdated)
)

fun RecipeEntity.toDomain() = Recipe(
    id = this.id,
    title = this.title,
    publisher = this.publisher,
    featuredImage = this.featuredImage,
    rating = this.rating,
    sourceUrl = this.sourceUrl,
    ingredients = this.ingredients,
    dateAdded = this.dateAdded,
    dateUpdated = this.dateUpdated
)

fun Recipe.toRecipeEntity() = RecipeEntity(
    id = this.id,
    title = this.title,
    publisher = this.publisher,
    featuredImage = this.featuredImage,
    rating = this.rating,
    sourceUrl = this.sourceUrl,
    ingredients = this.ingredients,
    dateAdded = this.dateAdded,
    dateUpdated = this.dateUpdated,
    dateRefreshed = DateUtil.createTimestamp()
)
