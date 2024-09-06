package com.samuel.cheges.ui.theme.screens.home
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.samuel.cheges.R
import com.samuel.cheges.data.ProductRepository
import com.samuel.cheges.data.ProductViewModel
import com.samuel.cheges.data.ProductViewModelFactory
import com.samuel.cheges.data.productxviewmodels
import com.samuel.cheges.model.Category
import com.samuel.cheges.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable

fun HomeScreen(
    navController: NavHostController,
    categories: List<Category>,
    viewModel: ProductViewModel = viewModel(factory = ProductViewModelFactory(ProductRepository()))
) {
    val products by viewModel.products.collectAsState() // Products from Firebase
    val featuredProducts = listOf(
        // Define your featured products
        Product(id = "1", name = "bath waste", price = 19.99, imageRes = R.drawable.bathwaste, description = "excellent", categoryId = "1"),
        Product(id = "2", name = "bring solar", price = 29.99, imageRes = R.drawable.bringethsolar, description = "awesome", categoryId = "2"),
        Product(id = "3", name = "ecoworth 3", price = 39.99, imageRes = R.drawable.ecoworth, description = "great 3", categoryId = "3"),
        Product(id = "4", name = "floodlight solar 4", price = 49.99, imageRes = R.drawable.floodlightsolar, description = "great", categoryId = "4"),
        Product(id = "5", name = "topsolar", price = 59.99, imageRes = R.drawable.topsolar, description = "good", categoryId = "5")
    )

    val categories = listOf(
        // Define your categories and their products
        Category(
            name = "Plumbing",
            imageRes = R.drawable.bathwaste,
            description = "Pipes, fittings, and bathroom essentials.",
            id = "1",
            products = listOf(
                Product(id = "1", name = "Pipe Set", price = 19.99, imageRes = R.drawable.ecoworth, description = "High-quality pipes.", categoryId = "1"),
                Product(id = "2", name = "Shower Head", price = 29.99, imageRes = R.drawable.maciresolar, description = "Luxury shower experience.", categoryId = "1"),
                // Add more products
            )
        ),
        Category(
            name = "Solar Installation",
            imageRes = R.drawable.topsolar,
            description = "Solar panels, inverters, and accessories.",
            id = "2",
            products = listOf(
                Product(id = "6", name = "Solar Panel 100W", price = 199.99, imageRes = R.drawable.floodlightsolar, description = "High-efficiency solar panel.", categoryId = "2"),
                Product(id = "7", name = "Inverter 1500W", price = 399.99, imageRes = R.drawable.bathwaste, description = "Reliable inverter for home use.", categoryId = "2"),
                // Add more products
            )
        )
    )

    var cart by remember { mutableStateOf(listOf<Product>()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Welcome to Chege", color = Color.White, fontSize = 25.sp) },
                backgroundColor = Color.Blue
            )
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Banner Carousel
                item { BannerCarousel() }

                // Featured Products Section
                item { SectionTitle("Featured Products") }
                items(featuredProducts) { product ->
                    ProductRow(listOf(product)) { selectedProduct ->
                        cart = cart + selectedProduct
                        // Additional handling, e.g., show a snackbar or update a cart view
                    }
                }

                // Categories Grid - Scrollable Horizontally
                item { SectionTitle("Shop by Category") }
                item {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(categories) { category ->
                            CategoryCard(category) { productsInCategory ->
                                navController.navigate("CategoryProductsScreen/${category.id}")
                            }
                        }
                    }
                }

                // All Products List - Scrollable Vertically
                item { SectionTitle("All Products") }
                items(products) { product ->
                    ProductCard(product) {
                        cart = cart + product
                        // Additional handling, e.g., show a snackbar or update a cart view
                    }
                }
            }
        },
        bottomBar = { BottomBar(navController) }
    )
}


@Composable
fun ProductItem(name:String, quantity:String, price:String, id:String,
                navController:NavHostController, productRepository:productxviewmodels) {

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = name)
        Text(text = quantity)
        Text(text = price)

        Button(onClick = {
            navController.navigate("/$id")
        }) {
            Text(text = "addtocart")
        }
    }

}
@Composable
fun BannerCarousel() {
    // Example Banner Carousel (Placeholder)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.Gray)
    ) {
        // Placeholder text, replace with carousel implementation
        Text(
            text = "Banner Carousel (Replace with images)",
            color = Color.White,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.h6,
        modifier = Modifier
            .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
    )
}

@Composable
fun ProductRow(products: List<Product>, onAddToCart: (Product) -> Unit) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(products) { product ->
            ProductCard(product, onAddToCart)
        }
    }
}
@Composable
fun CategoryGrid(categories: List<Category>, onViewProducts: (List<Product>) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            CategoryCard(
                category = category,
                onViewProducts = onViewProducts
            )
        }
    }
}

@Composable
fun ProductCard(product: Product, onAddToCart: (Product) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onAddToCart(product) }
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = product.imageRes)
                        .apply {
                            placeholder(R.drawable.topsolar)
                            error(R.drawable.error_image)
                        }
                        .build()
                ),
                contentDescription = product.name,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .background(Color.LightGray),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.name,
                style = MaterialTheme.typography.subtitle1
            )
            Text(
                text = "$${product.price}",
                style = MaterialTheme.typography.body2,
                color = Color.Red
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = product.description,
                style = MaterialTheme.typography.body2,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { onAddToCart(product) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Add to Cart")
            }
        }
    }
}


@Composable
fun CategoryCard(category: Category, onViewProducts: (List<Product>) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onViewProducts(category.products) }
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = category.imageRes)
                        .apply {
                            placeholder(R.drawable.topsolar) // Replace with your actual placeholder
                            error(R.drawable.error_image) // Replace with your actual error image
                        }
                        .build()
                ),
                contentDescription = category.name,
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
                    .background(Color.LightGray),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = category.name,
                style = MaterialTheme.typography.subtitle1
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = category.description,
                style = MaterialTheme.typography.body2,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { onViewProducts(category.products) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "View Products")
            }
        }
    }
}

@Composable
fun CategoryProductsScreen(products: List<Product>, onAddToCart: (Product) -> Unit) {
    LazyColumn {
        items(products) { product ->
            ProductCard(product, onAddToCart)
        }
    }
}



@Composable
fun BottomBar(navController: NavHostController) {
    val selectedIndex = remember { mutableStateOf(0) }

    BottomNavigation(elevation = 10.dp,modifier=Modifier.windowInsetsPadding(WindowInsets.systemBars)) {
        val items = listOf(
            "Home" to Icons.Default.Home,
            "Search" to Icons.Default.Search,
            "Profile" to Icons.Default.Person,
            "Cart" to Icons.Default.ShoppingCart
        )

        items.forEachIndexed { index, pair ->
            BottomNavigationItem(
                icon = { Icon(imageVector = pair.second, contentDescription = null) },
                label = { Text(text = pair.first) },
                selected = selectedIndex.value == index,
                onClick = {
                    selectedIndex.value = index
                    when (index) {
                        0 -> navController.navigate("Route_home")
                        1 -> navController.navigate("Route_search")
                        2 -> navController.navigate("Route_profile")
                        3 -> navController.navigate("Route_cart")
                    }
                }
            )
        }
    }
}
//
//@Preview
//@Composable
//private fun HomePreview() {
//    // Define mock products
//    val mockProducts = listOf(
//        Product(id = "1", name = "Pipe Set", price = 19.99, imageRes = R.drawable.ecoworth, description = "High-quality pipes.", categoryId = "1"),
//        Product(id = "2", name = "Shower Head", price = 29.99, imageRes = R.drawable.maciresolar, description = "Luxury shower experience.", categoryId = "1"),
//        Product(id = "3", name = "Faucet", price = 49.99, imageRes = R.drawable.nora, description = "Durable and stylish faucet.", categoryId = "1"),
//        Product(id = "4", name = "Bathroom Sink", price = 79.99, imageRes = R.drawable.fullkit, description = "Modern design sink.", categoryId = "1"),
//        Product(id = "5", name = "Toilet Seat", price = 39.99, imageRes = R.drawable.bringethsolar, description = "Comfortable and easy to clean.", categoryId = "1"),
//        Product(id = "6", name = "Solar Panel 100W", price = 199.99, imageRes = R.drawable.floodlightsolar, description = "High-efficiency solar panel.", categoryId = "2"),
//        Product(id = "7", name = "Inverter 1500W", price = 399.99, imageRes = R.drawable.bathwaste, description = "Reliable inverter for home use.", categoryId = "2"),
//        Product(id = "8", name = "Battery 200Ah", price = 599.99, imageRes = R.drawable.bathwaste, description = "Long-lasting solar battery.", categoryId = "2"),
//        Product(id = "9", name = "Charge Controller", price = 149.99, imageRes = R.drawable.floodlightsolar, description = "Efficient charge controller.", categoryId = "2"),
//        Product(id = "10", name = "Mounting Kit", price = 99.99, imageRes = R.drawable.bringethsolar, description = "Complete mounting kit for solar panels.", categoryId = "2")
//    )
//
//    // Define mock categories
//    val mockCategories = listOf(
//        Category(
//            name = "Plumbing",
//            imageRes = R.drawable.topsolar, // Replace with an appropriate image for plumbing
//            description = "All things plumbing.",
//            id = "1"
//        ),
//        Category(
//            name = "Solar Installation",
//            imageRes = R.drawable.topsolar, // Replace with an appropriate image for solar installation
//            description = "Solar panel solutions.",
//            id = "2"
//        )
//    )
//
//    // Define mock ViewModel
//    val mockViewModel = object : ProductViewModel(ProductRepository()) {
//        override val products: StateFlow<List<Product>> = MutableStateFlow(mockProducts)
//    }
//
//    // Define a function to provide mock categories
//    val mockCategoriesProvider = { mockCategories }
//
//    HomeScreen(
//        navController = rememberNavController(),
//        viewModel = mockViewModel,
//        categoriesProvider = mockCategoriesProvider
//    )
//}
