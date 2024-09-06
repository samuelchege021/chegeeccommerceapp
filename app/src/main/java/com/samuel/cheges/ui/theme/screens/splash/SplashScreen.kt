package com.samuel.cheges.ui.theme.screens.splash

import android.window.SplashScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.samuel.cheges.R
import com.samuel.cheges.navigation.Route_Register
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavController) {



    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate(Route_Register)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.chege),
            contentDescription = "Splash Login",
            modifier = Modifier.size(200.dp)
        )
    }
}

