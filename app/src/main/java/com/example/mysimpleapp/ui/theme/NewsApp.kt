package com.example.mysimpleapp.ui.theme

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mysimpleapp.BottomMenuScreen
import com.example.mysimpleapp.components.BottomMenu
import com.example.mysimpleapp.ui.theme.screen.DetailScreen
import com.example.mysimpleapp.ui.theme.screen.Sources
import com.example.mysimpleapp.ui.theme.screen.Categories
import com.example.mysimpleapp.ui.theme.screen.TopNews

@Composable
fun NewsApp(mainViewModel: MainViewModel) {
    val navController = rememberNavController()
    MainScreen(navController = navController, mainViewModel = mainViewModel)
}

@Composable
fun MainScreen(navController: NavHostController, mainViewModel: MainViewModel) {
    val query = remember { mutableStateOf("") }
    Scaffold(bottomBar = {BottomMenu(navController = navController)}) {
            paddingValues ->
        Navigation(navController = navController, modifier = Modifier.padding(paddingValues), query = query, viewModel = mainViewModel)
    }
}


@Composable
fun Navigation(navController: NavHostController, modifier: Modifier = Modifier, query: MutableState<String>,
               viewModel: MainViewModel) {
    val topArticles = viewModel.newsResponse.collectAsState().value.articles ?: listOf()
    val searchedArticles = viewModel.searchedNewsResponse.collectAsState().value.articles ?: listOf()
    val articles = if (query.value.isNotEmpty()) searchedArticles else topArticles
    val loading by viewModel.isLoading.collectAsState()
    val error by viewModel.isError.collectAsState()
    NavHost(navController = navController, startDestination = BottomMenuScreen.TopNews.route, modifier = modifier) {
        val isLoading = mutableStateOf(loading)
        val isError = mutableStateOf(error)
        composable(BottomMenuScreen.TopNews.route) {
            TopNews(navController = navController, articles, query = query, viewModel = viewModel, isError = isError, isLoading = isLoading)
        }
        composable(BottomMenuScreen.Categories.route) {
            viewModel.onSelectedCategoryChanged("sports")
            viewModel.getArticlesByCategory("sports")
            Categories(viewModel = viewModel, onFetchCategory = {
                viewModel.onSelectedCategoryChanged(it)
                viewModel.getArticlesByCategory(it)
            }, isLoading = isLoading, isError = isError)
        }
        composable(BottomMenuScreen.Sources.route) {
            Sources(viewModel, isLoading, isError)
        }
        composable("Detail/{index}", arguments = listOf(navArgument("index") { type = NavType.IntType })) {
            navBackStackEntry ->
            val index = navBackStackEntry.arguments?.getInt("index")
            index?.let {
                if (it in articles.indices) {
                    val article = articles[it]
                    DetailScreen(article, navController)
                }
            }
        }
    }
}
