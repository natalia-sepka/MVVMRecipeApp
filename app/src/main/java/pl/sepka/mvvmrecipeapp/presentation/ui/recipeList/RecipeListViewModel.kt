package pl.sepka.mvvmrecipeapp.presentation.ui.recipeList

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

const val PAGE_SIZE = 30

@HiltViewModel
class RecipeListViewModel
@Inject
constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val query = mutableStateOf("")
    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)
    val loading = mutableStateOf(false)
    val page = mutableStateOf(1)
    private var recipeListScrollPosition = 0

    init {
        newSearch()
    }

    fun onQueryChanged(query: String) {
        this.query.value = query
    }

    fun newSearch() {
        viewModelScope.launch {
            loading.value = true
            resetSearchState()
            val result = repository.search(page = 1, query = query.value, token = BuildConfig.TOKEN)
            recipes.value = result
            loading.value = false
        }
    }

    fun nextPage() {
        viewModelScope.launch {
            // prevent duplicate events due to recompose happening to quickly
            if ((recipeListScrollPosition + 1) >= (page.value * PAGE_SIZE)) {
                loading.value = true
                incrementPage()
                if (page.value > 1) {
                    val result = repository.search(
                        token = BuildConfig.TOKEN,
                        page = page.value,
                        query = query.value
                    )
                }
                loading.value = false
            }
        }
    }

    /**
     * Append new recipes to the current list of recipes
     */
    private fun appendRecipes(recipe: List<Recipe>) {
        val current = ArrayList(this.recipes.value)
        this.recipes.value = current
    }

    private fun incrementPage() {
        page.value = page.value + 1
    }

    fun onChangeRecipeScrollPosition(position: Int) {
        recipeListScrollPosition = position
    }

    private fun resetSearchState() {
        recipes.value = listOf()
        page.value = 1
        onChangeRecipeScrollPosition(0)
        if (selectedCategory.value?.value != query.value) {
            clearSelectedCategory()
        }
    }

    private fun clearSelectedCategory() {
        selectedCategory.value = null
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getFoodCategory(category)
        selectedCategory.value = newCategory
        onQueryChanged(category)
    }
}
