package com.example.mysimpleapp.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mysimpleapp.BottomMenuScreen

@Composable
fun BottomMenu(navController: NavController) {
    val menuItems = listOf(BottomMenuScreen.TopNews, BottomMenuScreen.Categories, BottomMenuScreen.Sources)
    NavigationBar(containerColor = Color(0xFF833AB4)) {
        val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
        menuItems.forEach { screen ->
            NavigationBarItem(
                selected = currentDestination == screen.route,
                onClick = { navController.navigate(screen.route) },
                icon = {
                    Icon(imageVector = screen.icon, contentDescription = screen.title, tint = Color.White)
                },
                label = { Text(screen.title, color = Color.White) }
            )
        }
    }
}