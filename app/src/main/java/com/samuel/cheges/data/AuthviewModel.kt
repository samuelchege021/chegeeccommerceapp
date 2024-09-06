package com.samuel.cheges.data

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

import com.samuel.cheges.model.User
import com.samuel.cheges.navigation.Route_Register
import com.samuel.cheges.navigation.Route_home
import com.samuel.cheges.navigation.Route_login


class AuthViewModel(var navController: NavHostController, var context: Context) {


    var mAuth:FirebaseAuth

    init{
        mAuth=FirebaseAuth.getInstance()


    }


    fun signup(name:String,email:String,pass:String,confirmpass:String){


        if (name.isBlank() || email.isBlank()||pass.isBlank()||confirmpass.isBlank()){

            Toast.makeText(context,"please input all fields",Toast.LENGTH_LONG).show()


        }
        else if (pass!=confirmpass){
            Toast.makeText(context,"password does not match",Toast.LENGTH_LONG).show()
        }

        else{
            mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener {


                if(it.isSuccessful){
                    val userdata= User(name,email,pass,confirmpass,mAuth.currentUser!!.uid)


                    val regRef= FirebaseDatabase.getInstance().getReference()
                        .child("Users/"+mAuth.currentUser!!.uid)
                    regRef.setValue(userdata).addOnCompleteListener {

                        if (it.isSuccessful){
                            Toast.makeText(context,"Registered Successfully",Toast.LENGTH_LONG).show()
                            navController.navigate(     Route_login)

                        }else{
                            Toast.makeText(context,"${it.exception!!.message}",Toast.LENGTH_LONG).show()
                            navController.navigate(Route_login)
                        }
                    }
                }else{
                    navController.navigate(Route_Register)
                }

            } }

    }
    fun login(email: String,pass: String){

        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {

            if (it.isSuccessful){
                Toast.makeText(context,"Succeffully Logged in",Toast.LENGTH_LONG).show()
                navController.navigate(Route_home)
//                navController.navigate(ROUTE_REGISTER)TO TAKE YOU TO A DIIFFERNT PAGE
            }else{
                Toast.makeText(context,"${it.exception!!.message}",Toast.LENGTH_LONG).show()
                navController.navigate(Route_login)
            }
        }

    }
    fun logout(){
        mAuth.signOut()
        navController.navigate(Route_login)
    }
    fun isloggedin():Boolean{
        return mAuth.currentUser !=null
    }

}