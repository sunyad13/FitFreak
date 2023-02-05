package com.aman.ache.dashBoardActivity.secondary.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.aman.ache.dashBoardActivity.secondary.SecondaryActivity
import com.aman.ache.dashBoardActivity.secondary.adpater.OnBodyPartItemClick
import com.aman.ache.dashBoardActivity.secondary.viewmodel.SecondaryViewModel
import com.aman.ache.databinding.FragmentBodyPartsBinding


/*
  First Fragment in the secondary Activity.
 */

// title and image is passed to the detail fragment.

class BodyPartsFragment  :Fragment(), OnBodyPartItemClick {
    private lateinit var binding:FragmentBodyPartsBinding
    private val shareViewModel:SecondaryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentBodyPartsBinding.inflate(inflater,container,false)
        binding = view
        return view.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //setting up the Collapsing (toolbar title and image)
        setUpToolbar()
        binding.bodyShimmerLay.startShimmer()
        shareViewModel.makeBodyPartReference(SecondaryActivity.type)
        setUpRecyclerView()

        if(shareViewModel.detailList.isNotEmpty()){
            shareViewModel.detailList.clear()
        }

        //when back button is pressed on the toolbar then return to the dashboard.
        binding.toolbar.setNavigationOnClickListener {
            backButtonClicked()
        }

    }


    private fun setUpRecyclerView() {
        if(shareViewModel.isListEmpty()){
            shareViewModel.getBodyParts(requireContext(),binding,this,SecondaryActivity.type)
        }else{
            shareViewModel.getLoadedList(binding)
        }
    }


    private fun setUpToolbar(){
        if(shareViewModel.isListEmpty()){
            shareViewModel.setupToolbar(SecondaryActivity.type,binding,requireContext())
        }else{
            shareViewModel.getLoadedImage(SecondaryActivity.type,binding,requireContext())
        }
    }

    //interface declared inside the BodyPartsAdapter.
    override fun onItemClick(title: String,image:String) {
        //here title means chest ,leg ,shoulder ,etc. and image of that body part
        val action = BodyPartsFragmentDirections.actionBodyPartsFragmentToDetailFragment(title,image)
        findNavController().navigate(action)
    }

    private fun backButtonClicked() {
        requireActivity().onBackPressed()
        requireActivity().finish()
    }


}