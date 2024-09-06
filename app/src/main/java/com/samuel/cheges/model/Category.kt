package com.samuel.cheges.model

data class Category(
    val name: String,
    val imageRes: Int,
    val description: String,
    val id: String,
    val products: List<Product>
)
