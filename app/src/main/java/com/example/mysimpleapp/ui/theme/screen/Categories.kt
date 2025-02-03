package com.example.mysimpleapp.ui.theme.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mysimpleapp.R
import com.example.mysimpleapp.components.ErrorUI
import com.example.mysimpleapp.components.LoadingUI
import com.example.mysimpleapp.data.models.TopNewsArticle
import com.example.mysimpleapp.data.models.getAllArticleCategory
import com.example.mysimpleapp.network.NewsManager
import com.example.mysimpleapp.ui.theme.MainViewModel
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun Categories(onFetchCategory: (String) -> Unit = {}, viewModel: MainViewModel,
               isLoading: MutableState<Boolean>, isError: MutableState<Boolean>) {
    val tabsItems = getAllArticleCategory()
    Column {
        when {
            isLoading.value -> {
                LoadingUI()
            }
            isError.value -> {
                ErrorUI()
            }
            else -> {
                LazyRow {
                    items(tabsItems.size){
                        val category = tabsItems[it]
                        CategoryTab(category = category.categoryName, onFetchCategory = onFetchCategory,
                            isSelected = viewModel.selectedCategory.collectAsState().value == category)
                    }
                }
            }
        }
        ArticleContent(articles = viewModel.getArticleByCategory.collectAsState().value.articles ?: listOf())
    }
}

@Composable
fun CategoryTab(category: String, isSelected: Boolean = false, onFetchCategory: (String) -> Unit) {
    val background = if (isSelected) Color(0xFF833AB4)
    else Color(0xFF405DE6)
    Surface(modifier = Modifier
        .padding(horizontal = 4.dp, vertical = 16.dp)
        .clickable { onFetchCategory(category) },
        shape = MaterialTheme.shapes.small,
        color = background) {
        Text(text = category, style = MaterialTheme.typography.bodyLarge, color = Color.White,
            modifier = Modifier.padding(8.dp))
    }
}

@Composable
fun ArticleContent(articles: List<TopNewsArticle>, modifier: Modifier = Modifier){
    LazyColumn {
        items(articles) {
                article ->
            Card(modifier.padding(8.dp),
                border = BorderStroke(2.dp, color = colorResource(id = R.color.purple_500))
            ) {
                Row(
                    modifier
                        .fillMaxWidth()
                        .padding(8.dp)) {
                    CoilImage(imageModel = article.urlToImage,
                        modifier = Modifier.size(125.dp),
                        placeHolder = painterResource(id = R.drawable.ic_breaking_news),
                        error = painterResource(id = R.drawable.ic_breaking_news)
                    )
                    Column(modifier.padding(8.dp)) {
                        Text(text = article.title ?: "News title is not available", fontWeight = FontWeight.Bold,
                            maxLines = 3, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(bottom = 8.dp))
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = "")
                            Text(text = article.author ?: "Author is not available", modifier = Modifier.padding(start = 16.dp))
                        }
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)) {
                            Icon(imageVector = Icons.Default.DateRange, contentDescription = "")
                            Text(text = article.publishedAt ?: "Publish time is not available", modifier = Modifier.padding(start = 16.dp))
                        }
                    }
                }
            }

        }
    }
}

@Preview
@Composable
fun ArticleContentPreview() {
    ArticleContent(articles = listOf(
        TopNewsArticle(
        author = "Namita Sting",
        title = "Cleo Smith news - live: Kidnap suspect in hospital again as 'hard police grind' credited for breakthrough...",
        description = "The suspected kidnapper of four-year-old Cleo Smith has been treated",
        publishedAt = "2021-11-04T04:42:40Z"
    )
    ))
}