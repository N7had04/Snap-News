package com.example.mysimpleapp.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mysimpleapp.ui.theme.MainViewModel

@Composable
fun SearchBar(query: MutableState<String>, viewModel: MainViewModel) {
    val localFocusManager = LocalFocusManager.current
    Card(shape = RoundedCornerShape(16.dp), modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        TextField(value = query.value, onValueChange = {query.value = it},
            modifier = Modifier.fillMaxWidth(), label = { Text(text = "Search", color = Color.Black)},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Search),
            leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = "", tint = Color.Black)},
            trailingIcon = {
                if (query.value != "") {
                    IconButton(onClick = { query.value = ""}) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "", tint = Color.Black)
                    }
                }
            },
            textStyle = TextStyle(color = Color.Black, fontSize = 18.sp),
            keyboardActions = KeyboardActions(
                onSearch = {
                    if (query.value != "") {
                        viewModel.getSearchedArticles(query = query.value)
                    }
                    localFocusManager.clearFocus()
                }
            )
        )

    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    SearchBar(query = mutableStateOf(""), viewModel())
}