package com.samuel.cheges.data
// model/ProductViewModel.kt


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuel.cheges.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavHostController

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.samuel.cheges.model.Productx
import com.samuel.cheges.navigation.Route_login


open class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    open val products: StateFlow<List<Product>> = _products

    init {
        viewModelScope.launch {
            repository.getProducts().collect { productList ->
                _products.value = productList
            }
        }
    }
}



class productxviewmodels(
    private val navController: NavHostController,
    private val context: Context
) {
    private val authRepository: AuthViewModel = AuthViewModel(navController, context)
    private val progress: ProgressDialog = ProgressDialog(context).apply {
        setTitle("Loading")
        setMessage("Please wait...")
    }

    init {
        if (!authRepository.isloggedin()) {
            navController.navigate(Route_login)
        }
    }

    fun viewProducts(
        product: MutableState<Productx>,
        products: SnapshotStateList<Productx>
    ): SnapshotStateList<Productx> {
        val ref = FirebaseDatabase.getInstance().getReference("Productx")

        progress.show()
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                progress.dismiss()
                products.clear()
                for (snap in snapshot.children) {
                    val value = snap.getValue(Productx::class.java)
                    if (value != null) {
                        product.value = value
                        products.add(value)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                progress.dismiss()
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        })
        return products
    }
}

