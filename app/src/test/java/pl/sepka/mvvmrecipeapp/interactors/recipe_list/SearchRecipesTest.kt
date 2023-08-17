package pl.sepka.mvvmrecipeapp.interactors.recipe_list

import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pl.sepka.mvvmrecipeapp.cache.AppDatabaseFake
import pl.sepka.mvvmrecipeapp.cache.RecipeDaoFake
import pl.sepka.mvvmrecipeapp.domain.model.Recipe
import pl.sepka.mvvmrecipeapp.network.RecipeService
import pl.sepka.mvvmrecipeapp.network.data.MockWebServerResponses.recipeListResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class SearchRecipesTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl
    private val appDatabase = AppDatabaseFake()
    private val DUMMY_TOKEN = "gg335v5453453" // can be anything
    private val DUMMY_QUERY = "This doesn't matter" // can be anything

    // system in test
    private lateinit var searchRecipesUseCase: SearchRecipeUseCase

    // dependencies
    private lateinit var recipeService: RecipeService
    private lateinit var recipeDao: RecipeDaoFake

    @BeforeEach
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        baseUrl = mockWebServer.url("/api/recipe/")
        recipeService = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .create()
                )
            )
            .build()
            .create(RecipeService::class.java)

        recipeDao = RecipeDaoFake(appDatabase)

        // instantiate the system in test
        searchRecipesUseCase = SearchRecipeUseCase(
            recipeDao = recipeDao,
            recipeService = recipeService
        )
    }

    @Test
    fun getRecipesFromNetwork_emitRecipesFromCache(): Unit = runBlocking {
        // condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(recipeListResponse)
        )

        // confirm the cache is empty to start
        assert(recipeDao.getAllRecipes(1, 30).isEmpty())

        val flowItems = searchRecipesUseCase.invoke(
            SearchRecipeUseCase.Params(
                1,
                DUMMY_QUERY,
                DUMMY_TOKEN,
                true
            )
        ).toList()

        // confirm the cache is no longer empty
        assert(recipeDao.getAllRecipes(1, 30).isNotEmpty())

        // first emission should be `loading`
        assert(flowItems[0].loading)

        // second emission should be the list of recipes
        val recipes = flowItems[1].data
        assert((recipes?.size ?: 0) > 0)

        // confirm they are actually Recipe objects
        assert(recipes?.get(index = 0) is Recipe)

        // ensure loading is false now
        assert(!flowItems[1].loading) // loading should be false now
    }

    // Simulate a bad request

    @Test
    fun getRecipeFromNetwork_emitHttpError(): Unit = runBlocking {
        // condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .setBody("{}")
        )

        val flowItems = searchRecipesUseCase.invoke(
            SearchRecipeUseCase.Params(
                1,
                DUMMY_QUERY,
                DUMMY_TOKEN,
                true
            )
        ).toList()

        // first emission should be `loading`
        assert(flowItems[0].loading)

        // second emission should be the exception
        val error = flowItems[1].error
        assert(error != null)

        assert(!flowItems[1].loading) // loading should be false now
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }
}
