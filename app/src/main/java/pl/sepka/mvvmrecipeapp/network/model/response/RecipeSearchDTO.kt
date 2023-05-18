package pl.sepka.mvvmrecipeapp.network.model.response

import com.google.gson.annotations.SerializedName
import pl.sepka.mvvmrecipeapp.network.model.RecipeDTO

data class RecipeSearchDTO(

    @SerializedName("count")
    var count: Int,

    @SerializedName("results")
    var recipes: List<RecipeDTO>
)
