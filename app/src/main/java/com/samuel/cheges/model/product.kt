package com.samuel.cheges.model

data class Product(
    val name: String,
    val price: Double,
    val imageRes: Int,  // Use Int to store drawable resource IDs
    val description: String,
    val id: String,
    val categoryId: String
)

data class Productx(
    var name: String = "",
    var quantity: String = "",
    var price: String = "",
    var id: String = ""
)
