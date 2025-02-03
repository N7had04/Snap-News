package com.example.mysimpleapp.data.models
import com.example.mysimpleapp.data.models.ArticleCategory.*

enum class ArticleCategory(val categoryName: String) {
    BUSINESS("business"),
    ENTERTAINMENT("entertainment"),
    GENERAL("general"),
    HEALTH("health"),
    SCIENCE("science"),
    SPORTS("sports"),
    TECHNOLOGY("technology")
}

fun getAllArticleCategory():List<ArticleCategory>{
    return listOf(SPORTS, HEALTH, SCIENCE, GENERAL, TECHNOLOGY, BUSINESS, ENTERTAINMENT)
}

fun getArticleCategory(category: String): ArticleCategory? {
    val map = entries.associateBy(ArticleCategory::categoryName)
    return map[category]
}