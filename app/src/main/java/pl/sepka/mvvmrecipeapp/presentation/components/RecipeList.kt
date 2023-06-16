package pl.sepka.mvvmrecipeapp.presentation.components

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pl.sepka.mvvmrecipeapp.R
import pl.sepka.mvvmrecipeapp.domain.model.Recipe
import pl.sepka.mvvmrecipeapp.presentation.ui.recipeList.PAGE_SIZE
import pl.sepka.mvvmrecipeapp.presentation.ui.recipeList.RecipeListEvent
import pl.sepka.mvvmrecipeapp.presentation.ui.recipeList.SHIMMERING_RECIPE_LIST_SIZE

@Composable
fun RecipeList(
    loading: Boolean,
    recipes: List<Recipe>,
    onChangeRecipeScrollPosition: (Int) -> Unit,
    page: Int,
    onNextPage: (RecipeListEvent) -> Unit,
    navController: NavController
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
                        onClick = {
                            val bundle = Bundle()
                            recipe.id?.let { bundle.putInt("recipeId", it) }
                            navController.navigate(
                                R.id.action_recipeListFragment_to_recipeFragment,
                                bundle
                            )
                        }
                    )
                }
            }
        }
        CircularIndeterminateProgressBar(isDisplayed = loading)
    }
}
