package com.example.foodfacts

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.foodfacts.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView_main) as NavHostFragment
        navController = navHostFragment.navController

        val toolbar: Toolbar = binding.materialToolbar
        setSupportActionBar(toolbar)

        val appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.hide()

        val authViewModel:AuthViewModel by viewModels()
        authViewModel.initializeRepository()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_actionbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favouriteFoodsFragment -> {
                navController.navigate(R.id.favouriteFoodsFragment)
            }
            android.R.id.home,
            R.id.homeFragment -> {
                navController.navigate(R.id.homeFragment)
            }
        }
        return true
    }
}
