package com.example.mysimpleapp.ui.theme.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mysimpleapp.R
import com.example.mysimpleapp.components.ErrorUI
import com.example.mysimpleapp.components.LoadingUI
import com.example.mysimpleapp.components.SearchBar
import com.example.mysimpleapp.data.models.TopNewsArticle
import com.example.mysimpleapp.ui.theme.MainViewModel
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun TopNews(navController: NavController, articles: List<TopNewsArticle>, query: MutableState<String>,
            viewModel: MainViewModel, isLoading: MutableState<Boolean>, isError: MutableState<Boolean>) {
    Log.d("TopNews", "Articles loaded: $articles")

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF833AB4)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        SearchBar(query = query, viewModel = viewModel)
        val searchedText = query.value
        val resultList = mutableListOf<TopNewsArticle>()
        if (searchedText != "") {
            resultList.addAll(viewModel.searchedNewsResponse.collectAsState().value.articles ?: articles)
        } else {
            resultList.addAll(articles)
        }
        when {
            isLoading.value -> {
                LoadingUI()
            }
            isError.value -> {
                ErrorUI()
            }
            else -> {
                LazyColumn {
                    items(resultList.size) {
                            index -> TopNewsItem(article = resultList[index], onNewsClick = {
                        if (resultList.isNotEmpty() && index in resultList.indices) {
                            navController.navigate("Detail/$index")
                        } else {
                            Log.e("TopNews", "Invalid article index or empty list.")
                        }
                    })
                    }
                }
            }
        }
    }
}

@Composable
fun TopNewsItem(article: TopNewsArticle, onNewsClick: () -> Unit = {}) {
    Box(modifier = Modifier
        .height(250.dp)
        .padding(top = 8.dp, bottom = 8.dp)
        .clickable { onNewsClick() }) {
        CoilImage(imageModel = article.urlToImage, contentScale = ContentScale.Crop,
            error = ImageBitmap.imageResource(R.drawable.ic_breaking_news),
            placeHolder = ImageBitmap.imageResource(R.drawable.ic_breaking_news)
        )
        Column(modifier = Modifier
            .wrapContentHeight()
            .padding(top = 16.dp, start = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween) {
            Text(text = article.publishedAt?:"Not available", color = Color.White, fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 32.dp))
            Spacer(modifier = Modifier.height(120.dp))
            Text(text = article.title ?: "Breaking News", color = Color.White, fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 32.dp, end = 32.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopNewsPreview() {

}
