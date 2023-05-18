package pl.sepka.mvvmrecipeapp.domain.mapper

import pl.sepka.mvvmrecipeapp.domain.model.Recipe
import pl.sepka.mvvmrecipeapp.network.model.RecipeDTO
import java.util.*

fun RecipeDTO.toDomain() = Recipe(
    id = this.pk,
    title = this.title.orEmpty(),
    publisher = this.publisher.orEmpty(),
    featuredImage = this.featuredImage.orEmpty(),
    rating = this.rating ?: 0,
    sourceUrl = this.sourceUrl.orEmpty(),
    description = this.description.orEmpty(),
    cookingInstructions = this.cookingInstructions.orEmpty(),
    ingredients = this.ingredients?.map { it } ?: emptyList(),
    dateAdded = this.dateAdded?.let { Date(it) },
    dateUpdated = this.dateUpdated?.let { Date(it) }
)
