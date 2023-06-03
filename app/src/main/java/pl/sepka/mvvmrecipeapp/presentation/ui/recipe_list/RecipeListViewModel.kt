package pl.sepka.mvvmrecipeapp.presentation.ui.recipe_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.sepka.mvvmrecipeapp.BuildConfig
import pl.sepka.mvvmrecipeapp.domain.model.Recipe
import pl.sepka.mvvmrecipeapp.repository.RecipeRepository
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel
@Inject
constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())

    init {
        viewModelScope.launch {
            val result = repository.search(page = 1, query = "chicken", token = BuildConfig.TOKEN)
            recipes.value = result
        }
    }
}
