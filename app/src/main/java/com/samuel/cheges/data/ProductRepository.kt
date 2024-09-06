package com.samuel.cheges.data

import com.google.firebase.firestore.FirebaseFirestore
import com.samuel.cheges.model.Product
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ProductRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val productsCollection = firestore.collection("products")

    fun getProducts(): Flow<List<Product>> = callbackFlow {
        val listenerRegistration = productsCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
            } else {
                val products = snapshot?.documents?.mapNotNull { document ->
                    document.toObject(Product::class.java)
                } ?: emptyList()
                trySend(products)
            }
        }
        awaitClose { listenerRegistration.remove() }
    }
}
