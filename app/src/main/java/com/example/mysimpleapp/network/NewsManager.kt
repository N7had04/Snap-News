package com.example.mysimpleapp.network

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.mysimpleapp.data.models.ArticleCategory
import com.example.mysimpleapp.data.models.TopNewsResponse
import com.example.mysimpleapp.data.models.getArticleCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsManager(private val service: NewsService) {
    private val _newsResponse = mutableStateOf(TopNewsResponse())
    val newsResponse: State<TopNewsResponse>
    @Composable get() = remember {
        _newsResponse
    }

    val sourceName = mutableStateOf("abc-news")
    private val _getArticleBySource = mutableStateOf(TopNewsResponse())
    val getArticleBySource: MutableState<TopNewsResponse>
        @Composable get() = remember {
            _getArticleBySource
        }

    val query = mutableStateOf("")
    private val _searchedNewsResponse = mutableStateOf(TopNewsResponse())
    val searchedNewsResponse: MutableState<TopNewsResponse>
        @Composable get() = remember {
            _searchedNewsResponse
        }

    val selectedCategory: MutableState<ArticleCategory?> = mutableStateOf(null)

    suspend fun getArticle(country: String): TopNewsResponse = withContext(Dispatchers.IO) {
        service.getTopArticles(country = country)
//        val service = Api.retrofitService.getTopArticles("us")
//        service.enqueue(object : Callback<TopNewsResponse> {
//            override fun onResponse(
//                call: Call<TopNewsResponse>,
//                response: Response<TopNewsResponse>
//            ) {
//                try {
//                    val articles = response.body()?.articles ?: emptyList()
//                } catch (e: Exception) {
//                    Log.e("NewsManager", "Error parsing response: ${e.localizedMessage}")
//                }
//
//                if (response.isSuccessful) {
//                    _newsResponse.value = response.body()!!
//                    Log.d("NewsManager", "Articles: ${response.body()?.articles}")
//                } else {
//                    Log.e("NewsManager", "Error: ${response.errorBody()?.string()}")
//                }
//            }
//
//            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
//                Log.e("NewsManager", "API Call Failed: ${t.localizedMessage}")
//            }
//
//        })
    }

    suspend fun getArticlesByCategory(category: String): TopNewsResponse = withContext(Dispatchers.IO) {
        service.getArticlesByCategory(category = category)

//        val service = Api.retrofitService.getArticlesByCategory(category)
//        service.enqueue(object : Callback<TopNewsResponse> {
//            override fun onResponse(
//                call: Call<TopNewsResponse>,
//                response: Response<TopNewsResponse>
//            ) {
//                try {
//                    val articles = response.body()?.articles ?: emptyList()
//                } catch (e: Exception) {
//                    Log.e("NewsManager", "Error parsing response: ${e.localizedMessage}")
//                }
//
//                if (response.isSuccessful) {
//                    _getArticleByCategory.value = response.body()!!
//                    Log.d("NewsManager", "Articles by category: ${response.body()?.articles}")
//                } else {
//                    Log.e("NewsManager", "Error: ${response.errorBody()?.string()}")
//                }
//            }
//
//            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
//                Log.e("NewsManager", "API Call Failed: ${t.localizedMessage}")
//            }
//
//        })
    }

    suspend fun getArticlesBySource(source: String): TopNewsResponse = withContext(Dispatchers.IO) {
        service.getArticlesBySources(source)
//        val service = Api.retrofitService.getArticlesBySources(source = sourceName.value)
//        service.enqueue(object : Callback<TopNewsResponse> {
//            override fun onResponse(
//                call: Call<TopNewsResponse>,
//                response: Response<TopNewsResponse>
//            ) {
//                try {
//                    val articles = response.body()?.articles ?: emptyList()
//                } catch (e: Exception) {
//                    Log.e("NewsManager", "Error parsing response: ${e.localizedMessage}")
//                }
//
//                if (response.isSuccessful) {
//                    _getArticleBySource.value = response.body()!!
//                    Log.d("NewsManager", "Articles by source: ${response.body()?.articles}")
//                } else {
//                    Log.e("NewsManager", "Error: ${response.errorBody()?.string()}")
//                }
//            }
//
//            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
//                Log.e("NewsManager", "API Call Failed: ${t.localizedMessage}")
//            }
//
//        })
    }

    suspend fun getSearchedArticles(query: String) : TopNewsResponse = withContext(Dispatchers.IO) {
        service.getArticles(query)
//        val service = Api.retrofitService.getArticles(query)
//        service.enqueue(object : Callback<TopNewsResponse> {
//            override fun onResponse(
//                call: Call<TopNewsResponse>,
//                response: Response<TopNewsResponse>
//            ) {
//                try {
//                    val articles = response.body()?.articles ?: emptyList()
//                } catch (e: Exception) {
//                    Log.e("NewsManager", "Error parsing response: ${e.localizedMessage}")
//                }
//
//                if (response.isSuccessful) {
//                    _searchedNewsResponse.value = response.body()!!
//                    Log.d("NewsManager", "Articles by query: ${response.body()?.articles}")
//                } else {
//                    Log.e("NewsManager", "Error: ${response.errorBody()?.string()}")
//                }
//            }
//
//            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
//                Log.e("NewsManager search error:", "API Call Failed: ${t.localizedMessage}")
//            }
//
//        })
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getArticleCategory(category = category)
        selectedCategory.value = newCategory
    }
}