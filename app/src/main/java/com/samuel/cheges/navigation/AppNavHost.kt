package com.samuel.cheges.navigation


import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.samuel.cheges.R
import com.samuel.cheges.data.ProductRepository
import com.samuel.cheges.data.ProductViewModel
import com.samuel.cheges.data.ProductViewModelFactory
import com.samuel.cheges.model.Category
import com.samuel.cheges.ui.theme.screens.home.CategoryProductsScreen
import com.samuel.cheges.ui.theme.screens.home.HomeScreen
import com.samuel.cheges.ui.theme.screens.login.LoginScreen
import com.samuel.cheges.ui.theme.screens.register.RegisterScreen
import com.samuel.cheges.ui.theme.screens.splash.SplashScreen
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Route_splash
) {
    val repository = ProductRepository()
    val factory = ProductViewModelFactory(repository)
    val viewModel: ProductViewModel = viewModel(factory = factory)

    // Example list of categories (replace with your real data)
    val categories = listOf(
        Category(id = "1", name = "Plumbing", description = "Plumbing products",imageRes = R.drawable.topsolar, products = listOf()),
        Category(id = "2", name = "Solar", description = "Solar products", imageRes = R.drawable.bathwaste,products = listOf())
    )

    NavHost(navController = navController, modifier = modifier, startDestination = startDestination) {

        composable(Route_home) {
            HomeScreen(navController = navController, categories = categories)
        }

        composable(Route_login) {
            LoginScreen(navController)
        }

        composable(Route_Register) {
            RegisterScreen(navController)
        }

        composable(Route_splash) {
            SplashScreen(navController)
        }

        composable("CategoryProductsScreen/{categoryId}") { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId")

            // Find the category by its ID
            val category = categoryId?.let { id -> categories.find { it.id == id } }

            if (category != null) {
                CategoryProductsScreen(products = category.products) { product ->
                    // Handle add to cart action here
                }
            } else {
                // Handle the case where the category was not found
                Text("Category not found")
            }
        }
    }
}
