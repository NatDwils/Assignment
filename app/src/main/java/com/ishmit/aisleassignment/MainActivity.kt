package com.ishmit.aisleassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find the NavHostFragment from the layout
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        // Get the NavController associated with the NavHostFragment
        navController = navHostFragment.navController

        // Find the BottomNavigationView from the layout
        val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        // Set up the BottomNavigationView with the NavController
        navView.setupWithNavController(navController)

        // Set up a listener for item selection in the BottomNavigationView
        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                // Handle navigation to the "notesFragment" destination
                R.id.notesFragment -> {
                    true // Return true to indicate that the item is selected
                }
                else -> false // Return false for other items
            }
        }

        // Set up a listener to detect destination changes in the NavController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            // Show or hide the BottomNavigationView based on the current destination
            if (destination.id == R.id.notesFragment) {
                navView.visibility = View.VISIBLE // Show the BottomNavigationView
            } else {
                navView.visibility = View.GONE // Hide the BottomNavigationView
            }
        }
    }
}
