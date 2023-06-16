package pl.sepka.mvvmrecipeapp.presentation.ui.recipeList

sealed class RecipeListEvent {
    object NewSearchEvent : RecipeListEvent()
    object NextPageEvent : RecipeListEvent()

    // restore after process death
    object RestoreStateEvent : RecipeListEvent()
}
