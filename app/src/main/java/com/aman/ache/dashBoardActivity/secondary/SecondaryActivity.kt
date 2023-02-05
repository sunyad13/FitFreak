package com.aman.ache.dashBoardActivity.secondary


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.aman.ache.R
import com.aman.ache.dashBoardActivity.secondary.fragment.DetailFragment
import com.aman.ache.databinding.ActivitySecondaryBinding


class SecondaryActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySecondaryBinding
    private lateinit var navController: NavController

    companion object{
        var type = 0   //0 ->beginner ,1->intermediate ,2->advance.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = ActivitySecondaryBinding.inflate(layoutInflater)
        binding = view
        setContentView(view.root)

        val intent = intent
        type = intent.getIntExtra("sender",-1)

        //setting FragmentContainerView as a nav host
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_Host_dash) as NavHostFragment

        //finding navController from navHost
        navController = navHostFragment.navController

    }

   // handling backPress on actionbar with navController
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()||super.onSupportNavigateUp()
    }



}