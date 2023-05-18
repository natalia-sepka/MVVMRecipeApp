package pl.sepka.mvvmrecipeapp.network.model.response

import com.google.gson.annotations.SerializedName
import pl.sepka.mvvmrecipeapp.network.model.RecipeResponse

class RecipeSearchResponse(

    @SerializedName("count")
    var count: Int,

    @SerializedName("results")
    var recipes: List<RecipeResponse>
)
