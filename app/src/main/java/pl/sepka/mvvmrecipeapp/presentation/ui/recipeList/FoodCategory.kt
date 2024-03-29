package pl.sepka.mvvmrecipeapp.presentation.ui.recipeList

enum class FoodCategory(val value: String) {
    CHICKEN("Chicken"),
    ERROR("error"),
    BEEF("Beef"),
    SOUP("Soup"),
    DESSERT("Dessert"),
    VEGETARIAN("Vegetarian"),
    MILK("Milk"),
    VEGAN("Vegan"),
    PIZZA("Pizza"),
    DONUT("Donut"),
}

fun getAllFoodCategories(): List<FoodCategory> = listOf(
    FoodCategory.ERROR, FoodCategory.CHICKEN, FoodCategory.BEEF,
    FoodCategory.SOUP, FoodCategory.DESSERT, FoodCategory.VEGETARIAN, FoodCategory.MILK,
    FoodCategory.VEGAN, FoodCategory.PIZZA, FoodCategory.DONUT
)

fun getFoodCategory(value: String): FoodCategory? {
    val map = FoodCategory.values().associateBy(FoodCategory::value)
    return map[value]
}
