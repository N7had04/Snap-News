package com.example.mysimpleapp.ui.theme

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysimpleapp.MainApp
import com.example.mysimpleapp.data.models.ArticleCategory
import com.example.mysimpleapp.data.models.TopNewsResponse
import com.example.mysimpleapp.data.models.getArticleCategory
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val repository = getApplication<MainApp>().repository
    private val _newsResponse = MutableStateFlow(TopNewsResponse())
    val newsResponse: StateFlow<TopNewsResponse>
        get() = _newsResponse

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean>
        get() = _isError

    private val errorHandler = CoroutineExceptionHandler {
        _, error ->
        if (error is Exception) {
            _isError.value = true
        }
    }

    fun getTopArticles() {

        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            _isLoading.value = true
            _newsResponse.value = repository.getArticles()
            _isLoading.value = false
        }

    }

    private val _getArticleByCategory = MutableStateFlow(TopNewsResponse())
    val getArticleByCategory: StateFlow<TopNewsResponse>
        get() = _getArticleByCategory

    private val _selectedCategory: MutableStateFlow<ArticleCategory?> = MutableStateFlow(null)
    val selectedCategory: StateFlow<ArticleCategory?>
        get() = _selectedCategory

    fun getArticlesByCategory(category: String) {

        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            _isLoading.value = true
            _getArticleByCategory.value = repository.getArticlesByCategory(category)
            _isLoading.value = false
        }

    }

    val sourceName = MutableStateFlow("techcrunch")
    private val _getArticleBySource = MutableStateFlow(TopNewsResponse())
    val getArticleBySource: StateFlow<TopNewsResponse>
        get() = _getArticleBySource

    val query = MutableStateFlow("")
    private val _searchedNewsResponse = MutableStateFlow(TopNewsResponse())
    val searchedNewsResponse: StateFlow<TopNewsResponse>
        get() = _searchedNewsResponse

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getArticleCategory(category)
        _selectedCategory.value = newCategory
    }

    fun getArticleBySource() {

        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            _isLoading.value = true
            _getArticleBySource.value = repository.getArticlesBySource(sourceName.value)
            _isLoading.value = false
        }

    }

    fun getSearchedArticles(query: String) {

        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            _searchedNewsResponse.value = repository.getSearchedArticles(query)
            _isLoading.value = false
        }

    }
}