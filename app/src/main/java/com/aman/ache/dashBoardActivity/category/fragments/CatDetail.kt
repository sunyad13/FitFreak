package com.aman.ache.dashBoardActivity.category.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.aman.ache.dashBoardActivity.category.CategoryActivity
import com.aman.ache.dashBoardActivity.category.CategoryViewModel
import com.aman.ache.dashBoardActivity.secondary.adpater.OnDetailItemClick
import com.aman.ache.databinding.FragmentDetailBinding


class CatDetail : Fragment() , OnDetailItemClick {
    private lateinit var binding: FragmentDetailBinding
    private val viewModel:CategoryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentDetailBinding.inflate(inflater,container,false)
        binding = view
        return view.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dIv.isVisible = false
        binding.durationTextView.isVisible = false
        if(viewModel.detailList.isEmpty()){
            binding.detailShimmerLay.isVisible = true
            binding.recyclerView.isVisible = false
            binding.detailShimmerLay.startShimmer()
            viewModel.setUpDetailRV(CategoryActivity.type,this,binding)
            viewModel.getImages(CategoryActivity.type,this,binding)
        }else{
            binding.recyclerView.isVisible = true
            binding.detailShimmerLay.isVisible = false
            viewModel.setUpLoadedViews(CategoryActivity.type,this,binding)
        }

        binding.backButton.setOnClickListener {
            viewModel.detailList.clear()
            requireActivity().onBackPressed()
            requireActivity().finish()
        }
    }

    override fun onItemClick(position: Int) {
        val action = CatDetailDirections.actionCatDetailToCatExercise(position)
        findNavController().navigate(action)
    }


}