package com.aman.ache.dashBoardActivity


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.aman.ache.R
import com.aman.ache.dashBoardActivity.main.training.ConnectionLiveData
import com.aman.ache.databinding.ActivityDashBoardBinding
import com.google.android.material.snackbar.Snackbar

class DashBoardActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDashBoardBinding
    private lateinit var navController: NavController
    private lateinit var cld : ConnectionLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = ActivityDashBoardBinding.inflate(layoutInflater)
        binding = view
        setContentView(view.root)

        checkConnection()

        window.navigationBarColor = resources.getColor(R.color.black)

        //setting Dashboard activity as a NavHost for those three fragment.
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_Host_dash) as NavHostFragment
        navController = navHost.findNavController()

        //setting up the bottom navigation with nav controller
        setUpBottomNav()

    }


    private fun checkConnection() {

        cld = ConnectionLiveData(application)

        cld.observe(this, Observer{ isConnected ->

            if (!isConnected){
                Snackbar.make(
                    binding.root,
                    "Check your internet connection",
                    Snackbar.LENGTH_SHORT
                )
                    .setBackgroundTint(resources.getColor(R.color.colorGreen))
                    .setTextColor(resources.getColor(R.color.white))
                    .show()
            }
        })

    }


    //setting up the bottom nav with the navigation component called nav controller.
    private fun setUpBottomNav() {
        binding.bottomNavView.setupWithNavController(navController)
    }
}