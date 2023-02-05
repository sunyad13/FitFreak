package com.aman.ache.dashBoardActivity.search

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import com.aman.ache.dashBoardActivity.DashBoardActivity
import com.aman.ache.dashBoardActivity.search.viewmodel.SearchViewModel
import com.aman.ache.dashBoardActivity.secondary.model.DetailDataClass
import com.aman.ache.databinding.FragmentSearchBinding

class SearchActivity : AppCompatActivity() {
    private val viewModel:SearchViewModel by viewModels()
    private lateinit var binding:FragmentSearchBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = FragmentSearchBinding.inflate(layoutInflater)
        binding = view
        setContentView(view.root)

        binding.searchShimmerLay.startShimmer()

        if(viewModel.list.isEmpty()){
            viewModel.getAllExercises(this,binding)
        }else{
            viewModel.loadRec(this,binding)
        }

        binding.recyclerView.isVisible = true
        binding.lottie.isVisible = false
        viewModel.loadAllExercises(this,binding)


        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        binding.searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        binding.searchView.setIconifiedByDefault(false)
        binding.searchView.isFocusable = true
        binding.searchView.isIconified = false
        binding.searchView.requestFocusFromTouch()

        


        binding.backButton.setOnClickListener {
            startActivity(Intent(this,DashBoardActivity::class.java))
            finish()
        }



        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) {
                    notEmpty(query)
                } else {
                    empty()
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isNotEmpty()) {
                    notEmpty(newText)
                } else {
                    empty()
                }
                return false
            }
        })


    }

    private fun empty() {
        viewModel.loadAllExercises(this,binding)
    }

    private fun notEmpty(newText:String) {
        viewModel.searchExercise(this,newText,binding)
    }


    override fun onBackPressed() {
        startActivity(Intent(this,DashBoardActivity::class.java))
        finish()
    }

}