package com.example.mysimpleapp.ui.theme.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.mysimpleapp.R
import com.example.mysimpleapp.components.ErrorUI
import com.example.mysimpleapp.components.LoadingUI
import com.example.mysimpleapp.data.models.TopNewsArticle
import com.example.mysimpleapp.ui.theme.Green235
import com.example.mysimpleapp.ui.theme.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Sources(viewModel: MainViewModel, isLoading: MutableState<Boolean>, isError: MutableState<Boolean>) {
    val items = listOf(
        "TechCrunch" to "techcrunch",
        "ABC News" to "abc-news",
        "Business Insider" to "business-insider",
        "BBC News" to "bbc-news",
        "Politico" to "politico",
        "TheVerge" to "the-verge"
    )
    Scaffold(topBar = {TopAppBar(title = { Text(text = "Source: ${viewModel.sourceName.collectAsState().value}",
        color = Color.White)},
        actions = {
            var menuExpanded by remember { mutableStateOf(false) }
            IconButton(onClick = { menuExpanded = true}) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = null, tint = Color.White)
            }
            MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(16.dp))) {
                DropdownMenu(expanded = menuExpanded, 
                    onDismissRequest = { menuExpanded = false }) {
                    items.forEach { 
                        DropdownMenuItem(text = { Text(text = it.first) }, onClick = {
                            viewModel.sourceName.value = it.second
                            viewModel.getArticleBySource()
                            menuExpanded = false
                        })
                    }
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF833AB4))
    )}) {
        paddingValues ->
        when {
            isLoading.value -> {
                LoadingUI()
            }
            isError.value -> {
                ErrorUI()
            }
            else -> {
                viewModel.getArticleBySource()
                val articles = viewModel.getArticleBySource.collectAsState().value
                SourceContent(articles = articles.articles?: listOf(), paddingValues)
            }
        }
    }
}

@Composable
fun SourceContent(articles: List<TopNewsArticle>, paddingValues: PaddingValues) {
    val uriHandler = LocalUriHandler.current

    LazyColumn(modifier = Modifier.padding(paddingValues)) {
        items(articles) {
            article ->
            val annotatedString = buildAnnotatedString {
                pushStringAnnotation(
                    tag = "URL",
                    annotation = article.url ?: "newsapi.org"
                )
                withStyle(style = SpanStyle(color = colorResource(id = R.color.purple_700),
                    textDecoration = TextDecoration.Underline)) {
                    append("Read Full Article Here")
                }
            }
            Card(modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()) {
                Column(modifier = Modifier
                    .height(200.dp)
                    .padding(end = 8.dp, start = 8.dp),
                    verticalArrangement = Arrangement.SpaceEvenly) {
                    Text(text = article.title ?: "Breaking News",
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(text = article.description ?: "Something went wrong! News is not available!",
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                    Card(colors = CardDefaults.cardColors(containerColor = Green235)) {
                        Text(
                            text = annotatedString,
                            modifier = Modifier
                                .padding(9.dp)
                                .clickable {
                                    annotatedString.getStringAnnotations(
                                        tag = "URL",
                                        start = 0,
                                        end = annotatedString.length
                                    ).firstOrNull()?.let { result ->
                                        uriHandler.openUri(result.item)
                                    }
                                },
                            style = LocalTextStyle.current.copy(textDecoration = TextDecoration.Underline)
                        )
                    }
                }
            }
        }
    }
}