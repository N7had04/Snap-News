package com.example.mysimpleapp.ui.theme.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import com.example.mysimpleapp.data.models.TopNewsArticle
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun DetailScreen(article: TopNewsArticle, navController: NavController) {
    Scaffold(topBar = {NewsAppTopBar(onBackPressed = {navController.popBackStack()})}) {
        paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(paddingValues = paddingValues), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            CoilImage(imageModel = article.urlToImage,
                contentScale = ContentScale.Crop,
                error = ImageBitmap.imageResource(R.drawable.ic_breaking_news),
                placeHolder = ImageBitmap.imageResource(R.drawable.ic_breaking_news),
                modifier = Modifier.padding(8.dp)
            )
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Author",
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp), tint = Color(0xFFFD1D1D))
                Text(text = article.author?:"Author is not available")
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "PublishedAt",
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp), tint = Color(0xFFFD1D1D))
                Text(text = article.publishedAt ?: "Publish time is not available")
            }

            Text(text = article.title?:"Not available", fontWeight = FontWeight.Bold, modifier = Modifier.padding(16.dp))
            Text(text = article.description?:"Not available", modifier = Modifier.padding(16.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsAppTopBar(onBackPressed: ()->Unit = {}) {
    TopAppBar(title = { Text(text = "Details", fontWeight = FontWeight.SemiBold, color = Color.White) },
        navigationIcon = { IconButton(onClick = { onBackPressed() }) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "", tint = Color.White)
        }}, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF833AB4)))
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {

}