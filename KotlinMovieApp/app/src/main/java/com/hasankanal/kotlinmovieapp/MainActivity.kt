package com.hasankanal.kotlinmovieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.hasankanal.kotlinmovieapp.ui.auth.LoginFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var navController = findNavController(R.id.fragment)
        bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener(NavController.OnDestinationChangedListener { controller, destination, arguments ->
           when(destination.id){
               R.id.signUpFragment -> bottomNavigationView.visibility = View.GONE
               R.id.loginFragment -> bottomNavigationView.visibility = View.GONE
               else -> bottomNavigationView.visibility = View.VISIBLE
           }
            })

    }
}