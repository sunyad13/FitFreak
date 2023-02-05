package com.aman.ache.dashBoardActivity.secondary.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aman.ache.R
import com.aman.ache.dashBoardActivity.secondary.adpater.OnDetailItemClick
import com.aman.ache.dashBoardActivity.secondary.viewmodel.SecondaryViewModel
import com.aman.ache.databinding.FragmentDetailBinding
import com.bumptech.glide.Glide


//passes the position of the item in the detail list to Exercise fragment.
class DetailFragment : Fragment(),OnDetailItemClick {
    private lateinit var binding:FragmentDetailBinding
    private val args:DetailFragmentArgs by navArgs()
    private val shareViewModel: SecondaryViewModel by activityViewModels()


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

        setUpToolbar()
        makeDetailRef()


        binding.backButton.setOnClickListener {
            shareViewModel.detailList.clear()
            backButtonClicked()
        }


    }

    private fun backButtonClicked() {
        requireActivity().onBackPressed()
    }


    private fun makeDetailRef() {
        if(shareViewModel.detailList.isEmpty()){
            binding.detailShimmerLay.isVisible = true
            binding.detailShimmerLay.startShimmer()
            binding.recyclerView.isVisible = false
            shareViewModel.makeDetailRef(args.title,binding,this)
        }else{
            binding.detailShimmerLay.isVisible = false
            binding.recyclerView.isVisible = true
            setUpRecyclerView()
        }

    }

    //below the toolbar we have recycler view which will show the name of
    //the exercises for the specific body part.
    private fun setUpRecyclerView() {
            shareViewModel.setUpRecyclerView(binding,this,args.title)
    }

    //setting up the image and the title using the args.
    //like having the image of chest/leg/shoulder etc with name of that body part written.
    private fun setUpToolbar(){
        Glide.with(requireContext()).load(args.image).into(binding.imageView)
    }

    override fun onItemClick(position: Int) {
        //here we passed the position of the item in recycler view item
        //so that in MainExerciseFragment we can access that object from detail list.(loved it ❤️)
        val action = DetailFragmentDirections.actionDetailFragmentToExerciseFragment(position)
        findNavController().navigate(action)
    }


}