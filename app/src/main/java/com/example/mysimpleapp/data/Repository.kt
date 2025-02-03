package com.example.mysimpleapp.data

import com.example.mysimpleapp.network.NewsManager

class Repository(val manager: NewsManager) {
    suspend fun getArticles() = manager.getArticle("us")
    suspend fun getArticlesByCategory(category: String) = manager.getArticlesByCategory(category)
    suspend fun getArticlesBySource(source: String) = manager.getArticlesBySource(source)
    suspend fun getSearchedArticles(query: String) = manager.getSearchedArticles(query)
}