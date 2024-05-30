package com.thienphu.mathgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.thienphu.mathgame.ui.theme.MathGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MathGameTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyNavigation()
                }
            }
        }
    }
}

@Composable
fun MyNavigation() {
    val navHostController: NavHostController = rememberNavController()
    NavHost(navController = navHostController, startDestination = "FirstPage") {
        composable("FirstPage") {
            FirstPage(navController = navHostController)
        }
        composable(
            "SecondPage/{category}",
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) {
            val selectedCategory = it.arguments?.getString("category")
            selectedCategory?.let { category ->
                SecondPage(
                    navController = navHostController,
                    category = category
                )
            }

        }
    }

}


