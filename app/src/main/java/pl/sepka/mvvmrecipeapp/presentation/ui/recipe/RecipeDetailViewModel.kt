package pl.sepka.mvvmrecipeapp.presentation.ui.recipe

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.sepka.mvvmrecipeapp.BuildConfig
import pl.sepka.mvvmrecipeapp.domain.model.Recipe
import pl.sepka.mvvmrecipeapp.repository.RecipeRepository
import pl.sepka.mvvmrecipeapp.util.TAG
import javax.inject.Inject

const val STATE_KEY_RECIPE = "recipe.state.key.selected_recipeId"

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val state: SavedStateHandle
) : ViewModel() {
    val recipe: MutableState<Recipe?> = mutableStateOf(null)
    val loading = mutableStateOf(false)
    val onLoad = mutableStateOf(false)

    init {
        state.get<Int>(STATE_KEY_RECIPE)?.let {
            onTriggerEvent(RecipeDetailEvent.GetRecipeDetailEvent(it))
        }
    }

    fun onTriggerEvent(event: RecipeDetailEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is RecipeDetailEvent.GetRecipeDetailEvent -> {
                        if (recipe.value == null) {
                            getRecipe(event.id)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "onTriggerEvent: Exception: $e, ${e.cause}")
            }
        }
    }

    private suspend fun getRecipe(id: Int) {
        loading.value = true
        val recipe = recipeRepository.get(token = BuildConfig.TOKEN, id = id)
        this.recipe.value = recipe
        state[STATE_KEY_RECIPE] = recipe.id
        loading.value = false
    }
}
