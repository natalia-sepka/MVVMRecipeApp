package pl.sepka.mvvmrecipeapp.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import pl.sepka.mvvmrecipeapp.presentation.navigation.Screen
import pl.sepka.mvvmrecipeapp.presentation.ui.recipe.RecipeDetailScreen
import pl.sepka.mvvmrecipeapp.presentation.ui.recipeList.RecipeListScreen

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                NavHost(
                    navController = navController,
                    startDestination = Screen.RecipeList.route,
                    builder = {
                        addRecipeListScreen()
                        addRecipeDetailScreen()
                    }
                )
            }
        }
    }

    private fun NavGraphBuilder.addRecipeListScreen() {
        composable(
            route = Screen.RecipeList.route
        ) {
            RecipeListScreen(
                isDarkTheme = (application as BaseApplication).isDark.value,
                onToggleTheme = { (application as BaseApplication)::toggleLightTheme },
                viewModel = hiltViewModel()
            )
        }
    }

    private fun NavGraphBuilder.addRecipeDetailScreen() {
        composable(
            route = Screen.RecipeDetail.route
        ) {
            RecipeDetailScreen(
                isDarkTheme = (application as BaseApplication).isDark.value,
                recipeId = 1,
                viewModel = hiltViewModel()
            )
        }
    }
}
