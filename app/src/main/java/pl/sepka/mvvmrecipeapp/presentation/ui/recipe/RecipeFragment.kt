package pl.sepka.mvvmrecipeapp.presentation.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.sepka.mvvmrecipeapp.presentation.BaseApplication
import pl.sepka.mvvmrecipeapp.presentation.components.CircularIndeterminateProgressBar
import pl.sepka.mvvmrecipeapp.presentation.components.RecipeView
import pl.sepka.mvvmrecipeapp.presentation.components.ShimmerRecipeDetails
import pl.sepka.mvvmrecipeapp.presentation.ui.theme.AppTheme
import javax.inject.Inject

const val IMAGE_HEIGHT = 260

@AndroidEntryPoint
class RecipeFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val viewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("recipeId")?.let { recipeId ->
            viewModel.onTriggerEvent(RecipeEvent.GetRecipeEvent(recipeId))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                RecipeFragmentScreen()
            }
        }
    }

    @Composable
    fun RecipeFragmentScreen() {
        val loading = viewModel.loading.value
        val recipe = viewModel.recipe.value
        val scaffoldState = rememberScaffoldState()
        val scrollState = rememberScrollState()

        AppTheme(
            darkTheme = application.isDark.value
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
                    CircularIndeterminateProgressBar(isDisplayed = loading)
                }
            }
        }
    }
}
