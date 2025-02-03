package com.example.mysimpleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mysimpleapp.ui.theme.MainViewModel
import com.example.mysimpleapp.ui.theme.MySimpleAppTheme
import com.example.mysimpleapp.ui.theme.NewsApp

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewModel.getTopArticles()
        setContent {
            MySimpleAppTheme {
                NewsApp(viewModel)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MySimpleAppTheme {
        NewsApp(viewModel())
    }
}