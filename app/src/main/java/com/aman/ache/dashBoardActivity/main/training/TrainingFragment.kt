package com.aman.ache.dashBoardActivity.main.training


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.aman.ache.dashBoardActivity.category.CategoryActivity
import com.aman.ache.dashBoardActivity.main.training.adapter.TrainingRVAdapter
import com.aman.ache.dashBoardActivity.main.training.viewmodels.TrainingViewModel
import com.aman.ache.dashBoardActivity.search.SearchActivity
import com.aman.ache.databinding.FragmentTrainingBinding



class TrainingFragment : Fragment() {
    private lateinit var binding:FragmentTrainingBinding
    private val viewModel : TrainingViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.isTrainingAlive = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        val view  = FragmentTrainingBinding.inflate(inflater,container,false)
        binding = view
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


            binding.profileImage.isVisible = false
            binding.smallProfileShimmerLay.isVisible = true
            binding.smallProfileShimmerLay.startShimmer()
            viewModel.getTrainingData(binding,requireContext())



        binding.profileImage.setOnClickListener {
            val action  = TrainingFragmentDirections.actionTrainingFragmentToProfileFragment()
            findNavController().navigate(action)
        }


        binding.searchButton.setOnClickListener {
            val intent = Intent(requireContext(),SearchActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }


        binding.recyclerView.adapter = TrainingRVAdapter(requireContext())

        binding.cardView1.setOnClickListener {
            val intent = Intent(requireContext(), CategoryActivity::class.java)
            intent.putExtra("type",0) // int value
            startActivity(intent)
        }

        binding.cardView2.setOnClickListener {
            val intent = Intent(requireContext(), CategoryActivity::class.java)
            intent.putExtra("type",1) // int value
            startActivity(intent)
        }
        binding.cardView3.setOnClickListener {
            val intent = Intent(requireContext(), CategoryActivity::class.java)
            intent.putExtra("type",2) // int value
            startActivity(intent)
        }
        binding.cardView4.setOnClickListener {
            val intent = Intent(requireContext(), CategoryActivity::class.java)
            intent.putExtra("type",3) // int value
            startActivity(intent)
        }
        binding.cardView5.setOnClickListener {
            val intent = Intent(requireContext(), CategoryActivity::class.java)
            intent.putExtra("type",4) // int value
            startActivity(intent)
        }


    }



    override fun onStart() {
        super.onStart()
        viewModel.isTrainingAlive = true
    }

    override fun onResume() {
        super.onResume()
        viewModel.isTrainingAlive = true
    }

    override fun onPause() {
        super.onPause()
        viewModel.isTrainingAlive = false
    }

    override fun onStop() {
        super.onStop()
        viewModel.isTrainingAlive = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.isTrainingAlive = false
    }



    override fun onDestroy() {
        super.onDestroy()
        viewModel.isTrainingAlive = false
    }



}