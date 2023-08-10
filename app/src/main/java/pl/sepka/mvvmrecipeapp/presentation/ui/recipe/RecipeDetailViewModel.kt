package pl.sepka.mvvmrecipeapp.presentation.ui.recipe

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import pl.sepka.mvvmrecipeapp.BuildConfig
import pl.sepka.mvvmrecipeapp.domain.model.Recipe
import pl.sepka.mvvmrecipeapp.interactors.recipe.GetRecipeUseCase
import pl.sepka.mvvmrecipeapp.util.TAG
import javax.inject.Inject

const val STATE_KEY_RECIPE = "recipe.state.key.selected_recipeId"

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val state: SavedStateHandle,
    private val getRecipeUseCase: GetRecipeUseCase
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

    private fun getRecipe(id: Int) {
        getRecipeUseCase.invoke(GetRecipeUseCase.Params(recipeId = id, token = BuildConfig.TOKEN))
            .onEach {
                loading.value = it.loading
                it.data?.let { recipe ->
                    this.recipe.value = recipe
                    state[STATE_KEY_RECIPE] = recipe.id
                }
                it.error?.let { error ->
                    Log.e(TAG, "getRecipe: $error")
                    // handle error
                }
            }.launchIn(viewModelScope)
    }
}
