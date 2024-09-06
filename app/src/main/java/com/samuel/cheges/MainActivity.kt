package com.samuel.cheges

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import com.google.firebase.FirebaseApp
import com.samuel.cheges.navigation.AppNavHost
import com.samuel.cheges.ui.theme.ChegesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)?.let {
            Log.d("MainActivity", "Firebase initialized successfully")
        } ?: run {
            Log.e("MainActivity", "Failed to initialize Firebase")
        }
        enableEdgeToEdge()
        setContent {
            ChegesTheme {

                    AppNavHost()
                }
            }
        }
    }



