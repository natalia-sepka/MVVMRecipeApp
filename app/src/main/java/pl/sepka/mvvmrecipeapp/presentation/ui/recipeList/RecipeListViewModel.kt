package pl.sepka.mvvmrecipeapp.presentation.ui.recipeList

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
import pl.sepka.mvvmrecipeapp.interactors.recipe_list.SearchRecipeUseCase
import pl.sepka.mvvmrecipeapp.repository.RecipeRepository
import pl.sepka.mvvmrecipeapp.util.RECIPE_PAGINATION_PAGE_SIZE
import pl.sepka.mvvmrecipeapp.util.TAG
import javax.inject.Inject

const val STATE_KEY_PAGE = "recipe.state.page.key"
const val STATE_KEY_QUERY = "recipe.state.query.key"
const val STATE_KEY_LIST_POSITION = "recipe.state.query.list.position"
const val STATE_KEY_SELECTED_CATEGORY = "recipe.state.query.selected_category"

@HiltViewModel
class RecipeListViewModel
@Inject
constructor(
    private val repository: RecipeRepository,
    private val savedStateHandle: SavedStateHandle,
    private val searchRecipeUseCase: SearchRecipeUseCase
) : ViewModel() {

    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val query = mutableStateOf("")
    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)
    val loading = mutableStateOf(false)
    val page = mutableStateOf(1)
    private var recipeListScrollPosition = 0

    init {
        savedStateHandle.get<Int>(STATE_KEY_PAGE)?.let { page ->
            setPage(page)
        }
        savedStateHandle.get<Int>(STATE_KEY_LIST_POSITION)?.let { position ->
            setListScrollPosition(position)
        }
        savedStateHandle.get<FoodCategory>(STATE_KEY_SELECTED_CATEGORY)?.let { category ->
            setSelectedCategory(category)
        }
        savedStateHandle.get<String>(STATE_KEY_QUERY)?.let { query ->
            setQuery(query)
        }

        if (recipeListScrollPosition != 0) {
            onTriggerEvent(RecipeListEvent.RestoreStateEvent)
        } else {
            onTriggerEvent(RecipeListEvent.NewSearchEvent)
        }
    }

    fun onTriggerEvent(event: RecipeListEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    RecipeListEvent.NewSearchEvent -> newSearch()
                    RecipeListEvent.NextPageEvent -> nextPage()
                    RecipeListEvent.RestoreStateEvent -> restoreState()
                }
            } catch (e: Exception) {
                Log.e(
                    TAG,
                    "onTriggerEvent: Exception: $e," +
                        "${e.cause}"
                )
            }
        }
    }

    private suspend fun restoreState() {
        loading.value = true
        val results: MutableList<Recipe> = mutableListOf()
        for (p in 1..page.value) {
            val result = repository.search(
                token = BuildConfig.TOKEN,
                page = p,
                query = query.value
            )
            results.addAll(result)
            if (p == page.value) {
                recipes.value = results
                loading.value = false
            }
        }
    }

    fun onQueryChanged(query: String) {
        setQuery(query)
    }

    private fun newSearch() {
        resetSearchState()
        searchRecipeUseCase.execute(page = page.value, query = query.value, token = BuildConfig.TOKEN)
            .onEach {
                loading.value = it.loading
                it.data?.let { list ->
                    recipes.value = list
                }
                it.error?.let { error ->
                    Log.e(TAG, "newSearch: $error")
                    // handle error
                }
            }.launchIn(viewModelScope)
    }

    private suspend fun nextPage() {
        // prevent duplicate events due to recompose happening to quickly
        if ((recipeListScrollPosition + 1) >= (page.value * RECIPE_PAGINATION_PAGE_SIZE)) {
            incrementPage()
            if (page.value > 1) {
                searchRecipeUseCase.execute(
                    page = page.value,
                    query = query.value,
                    token = BuildConfig.TOKEN
                ).onEach {
                    loading.value = it.loading
                    it.data?.let { list -> appendRecipes(list) }
                    it.error?.let { error ->
                        Log.e(TAG, "nextPage: $error")
                    }
                }.launchIn(viewModelScope)
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
        setPage(page.value + 1)
    }

    fun onChangeRecipeScrollPosition(position: Int) {
        setListScrollPosition(position = position)
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
        setSelectedCategory(null)
        selectedCategory.value = null
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getFoodCategory(category)
        setSelectedCategory(newCategory)
        onQueryChanged(category)
    }

    private fun setListScrollPosition(position: Int) {
        recipeListScrollPosition = position
        savedStateHandle.set(STATE_KEY_LIST_POSITION, position)
    }

    private fun setPage(page: Int) {
        this.page.value = page
        savedStateHandle.set(STATE_KEY_PAGE, page)
    }

    private fun setSelectedCategory(category: FoodCategory?) {
        selectedCategory.value = category
        savedStateHandle.set(STATE_KEY_SELECTED_CATEGORY, category)
    }

    private fun setQuery(query: String) {
        this.query.value = query
        savedStateHandle.set(STATE_KEY_QUERY, query)
    }
}
