package com.aman.ache.dashBoardActivity.category

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.aman.ache.R
import com.aman.ache.databinding.ActivityCategoryBinding


class CategoryActivity : AppCompatActivity() {

    private lateinit var binding:ActivityCategoryBinding
    private lateinit var navController: NavController


    companion object{
        var type = -1
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view  = ActivityCategoryBinding.inflate(layoutInflater)
        binding = view
        setContentView(view.root)

        val intent = intent
        type = intent.getIntExtra("type",-1)


        //setting FragmentContainerView as a nav host
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_Host_dash) as NavHostFragment
        navController = navHostFragment.navController
    }

    // handling backPress on actionbar with navController
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()||super.onSupportNavigateUp()
    }

}