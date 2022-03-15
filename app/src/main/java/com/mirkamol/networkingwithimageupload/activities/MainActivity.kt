package com.mirkamol.networkingwithimageupload.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mirkamol.networkingwithimageupload.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navigation = findViewById<FragmentContainerView>(R.id.nav_host_fragment)
        val fab = findViewById<FloatingActionButton>(R.id.fab)

        bottomNavigationView.background = null

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        bottomNavigationView.setupWithNavController(
            navHostFragment.navController
        )

        fab.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }




    }
}