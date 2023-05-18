package pl.sepka.mvvmrecipeapp.network.model

import com.google.gson.annotations.SerializedName

data class RecipeDTO(

    @SerializedName("pk")
    var pk: Int? = null,

    @SerializedName("title")
    var title: String? = null,

    @SerializedName("rating")
    var rating: Int? = null,

    @SerializedName("publisher")
    var publisher: String? = null,

    @SerializedName("featured_image")
    val featuredImage: String?,

    @SerializedName("source_url")
    var sourceUrl: String? = null,

    @SerializedName("description")
    var description: String? = null,

    @SerializedName("cooking_instructions")
    var cookingInstructions: String? = null,

    @SerializedName("ingredients")
    var ingredients: List<String>? = null,

    @SerializedName("date_added")
    var dateAdded: String? = null,

    @SerializedName("date_updated")
    var dateUpdated: String? = null

)
