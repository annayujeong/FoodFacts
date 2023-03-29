package com.example.foodfacts


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.activity.viewModels

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView_main) as NavHostFragment
        navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        findViewById<BottomNavigationView>(R.id.bottomNav_main).setupWithNavController(navController)
        
        val authViewModel:AuthViewModel by viewModels()

        //val cn = ComponentName(this, "org.tensorflow.lite.examples.imageclassification.MainActivity")
        //val intent = Intent().setComponent(cn)
        //startActivity(intent)

        //val intent = Intent(this, org.tensorflow.lite.examples.imageclassification.MainActivity::class.java)
        //startActivity(intent)
    }

}
