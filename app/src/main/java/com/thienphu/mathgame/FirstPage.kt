package com.thienphu.mathgame

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstPage() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Math Game", fontSize = 20.sp) },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.Blue
                )
            )
        },
        content = {paddingValues ->
            Card(modifier = Modifier.padding(paddingValues)) {
                Text(text = "Hello Test")
            }
        }
    )

}