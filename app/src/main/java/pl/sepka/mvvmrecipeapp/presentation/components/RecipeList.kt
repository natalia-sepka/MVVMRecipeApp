package pl.sepka.mvvmrecipeapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.sepka.mvvmrecipeapp.domain.model.Recipe
import pl.sepka.mvvmrecipeapp.presentation.ui.recipeList.PAGE_SIZE
import pl.sepka.mvvmrecipeapp.presentation.ui.recipeList.RecipeListEvent

const val SHIMMERING_RECIPE_LIST_SIZE = 10

@Composable
fun RecipeList(
    loading: Boolean,
    recipes: List<Recipe>,
    onChangeRecipeScrollPosition: (Int) -> Unit,
    page: Int,
    onNextPage: (RecipeListEvent) -> Unit,
    onNavigateToRecipeDetailScreen: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {
        if (loading && recipes.isEmpty()) {
            LazyColumn {
                items(SHIMMERING_RECIPE_LIST_SIZE) {
                    ShimmerRecipeCardItem(imageHeight = 250.dp)
                }
            }
        } else {
            LazyColumn {
                itemsIndexed(
                    items = recipes
                ) { index, recipe ->
                    onChangeRecipeScrollPosition(index)
                    if ((index + 1) >= (page * PAGE_SIZE) && !loading) {
                        onNextPage(RecipeListEvent.NextPageEvent)
                    }
                    RecipeCard(
                        recipe = recipe,
                        onClick = { recipe.id?.let { onNavigateToRecipeDetailScreen(it) } }
                    )
                }
            }
        }
    }
}
