package pl.sepka.mvvmrecipeapp.presentation.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
    val route: String,
    val arguments: List<NamedNavArgument>
) {
    object RecipeList : Screen(route = "recipeList", arguments = emptyList())
    object RecipeDetail : Screen(
        route = "recipeDetail",
        arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
    )
}
