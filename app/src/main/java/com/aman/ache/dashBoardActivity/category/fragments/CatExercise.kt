package com.aman.ache.dashBoardActivity.category.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.aman.ache.R
import com.aman.ache.dashBoardActivity.category.CategoryViewModel
import com.aman.ache.dashBoardActivity.secondary.fragment.ExerciseFragmentArgs
import com.aman.ache.databinding.FragmentExerciseBinding


class CatExercise : Fragment() {
    private lateinit var binding: FragmentExerciseBinding
    private val args: CatExerciseArgs by navArgs()
    private val viewModel:CategoryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        val view = FragmentExerciseBinding.inflate(inflater,container,false)
        binding = view
        return view.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initialising args
        val position = args.position

        //adding the youtube as lifecycle owner so that it can be lifecycle aware.
        lifecycle.addObserver(binding.youtubeView)

        setUpViews(position)

    }

    private fun setUpViews(position:Int) {
        viewModel.setUpExerciseFragment(binding,position)
    }

}