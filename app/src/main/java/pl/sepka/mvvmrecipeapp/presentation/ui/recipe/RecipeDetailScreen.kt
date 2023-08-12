package pl.sepka.mvvmrecipeapp.presentation.ui.recipe

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.sepka.mvvmrecipeapp.presentation.components.RecipeView
import pl.sepka.mvvmrecipeapp.presentation.components.ShimmerRecipeDetails
import pl.sepka.mvvmrecipeapp.presentation.ui.theme.AppTheme

const val IMAGE_HEIGHT = 260

@Composable
fun RecipeDetailScreen(
    isDarkTheme: Boolean,
    recipeId: Int?,
    viewModel: RecipeDetailViewModel
) {
    val loading = viewModel.loading.value
    val recipe = viewModel.recipe.value
    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberScrollState()
    val dialogQueue = viewModel.dialogQueue

    recipeId?.let { id ->
        val onLoad = viewModel.onLoad.value
        if (!onLoad) {
            viewModel.onLoad.value = true
            viewModel.onTriggerEvent(RecipeDetailEvent.GetRecipeDetailEvent(id))
        }
    }

    AppTheme(
        darkTheme = isDarkTheme,
        displayProgressBar = loading,
        dialogQueue = dialogQueue.queue.value
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            snackbarHost = {
                scaffoldState.snackbarHostState
            }
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                if (loading && recipe == null) {
                    ShimmerRecipeDetails(imageHeight = IMAGE_HEIGHT.dp)
                } else {
                    recipe?.let {
                        RecipeView(
                            recipe = it,
                            scrollState = scrollState,
                            imageHeight = IMAGE_HEIGHT
                        )
                    }
                }
            }
        }
    }
}
