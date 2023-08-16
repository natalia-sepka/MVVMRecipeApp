package pl.sepka.mvvmrecipeapp.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import pl.sepka.mvvmrecipeapp.datastore.SettingsDataStore
import pl.sepka.mvvmrecipeapp.presentation.navigation.Screen
import pl.sepka.mvvmrecipeapp.presentation.ui.recipe.RecipeDetailScreen
import pl.sepka.mvvmrecipeapp.presentation.ui.recipeList.RecipeListScreen
import pl.sepka.mvvmrecipeapp.presentation.ui.util.InternetConnectionManager
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var internetConnectionManager: InternetConnectionManager

    @Inject
    lateinit var settingsDataStore: SettingsDataStore

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
                        addRecipeListScreen(navController = navController)
                        addRecipeDetailsScreen()
                    }
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        internetConnectionManager.registerConnectionObserver(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        internetConnectionManager.unregisterConnectionObserver(this)
    }

    private fun NavGraphBuilder.addRecipeListScreen(
        navController: NavController
    ) {
        composable(
            route = Screen.RecipeList.route
        ) {
            RecipeListScreen(
                isDarkTheme = settingsDataStore.isDark.value,
                isNetworkAvailable = internetConnectionManager.isNetworkAvailable.value,
                onToggleTheme = { settingsDataStore.toggleTheme() },
                onNavigateToRecipeDetailScreen = {
                    val route = Screen.RecipeDetail.route + "/${it.id}"
                    navController.navigate(route)
                },
                viewModel = hiltViewModel()
            )
        }
    }

    private fun NavGraphBuilder.addRecipeDetailsScreen() {
        composable(
            route = Screen.RecipeDetail.route + "/{recipeId}",
            arguments = Screen.RecipeDetail.arguments
        ) {
            RecipeDetailScreen(
                isDarkTheme = settingsDataStore.isDark.value,
                isNetworkAvailable = internetConnectionManager.isNetworkAvailable.value,
                recipeId = it.arguments?.getInt("recipeId"),
                viewModel = hiltViewModel()
            )
        }
    }
}
