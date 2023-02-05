package com.aman.ache.dashBoardActivity.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.aman.ache.dashBoardActivity.search.viewmodel.SearchExecViewModel
import com.aman.ache.databinding.FragmentExerciseBinding


class SearchExec : AppCompatActivity() {
    private val viewModel: SearchExecViewModel by viewModels()
    private lateinit var binding:FragmentExerciseBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = FragmentExerciseBinding.inflate(layoutInflater)
        binding = view
        setContentView(view.root)

        val intent = intent
        val execName = intent.getStringExtra("send")!!

        //adding the youtube as lifecycle owner so that it can be lifecycle aware.
        lifecycle.addObserver(binding.youtubeView)

        viewModel.getObject(execName,binding)
    }

}
