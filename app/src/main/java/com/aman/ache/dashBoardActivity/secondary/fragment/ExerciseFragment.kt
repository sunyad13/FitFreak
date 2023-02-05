package com.aman.ache.dashBoardActivity.secondary.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.aman.ache.dashBoardActivity.secondary.viewmodel.SecondaryViewModel
import com.aman.ache.databinding.FragmentExerciseBinding

/*
    -> This Fragment will hold the two Fragment in the viewPager i.e:-
                   *MainExerciseFragment.
                   *InfoExerciseFragment

 */
class ExerciseFragment : Fragment() {
    private lateinit var binding:FragmentExerciseBinding
    private val args:ExerciseFragmentArgs by navArgs()
       private val shareViewModel: SecondaryViewModel by activityViewModels()


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
        shareViewModel.setUpExerciseFragment(binding,position)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.youtubeView.release()
    }

}