package com.aman.ache.mainActivity.frags

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.aman.ache.R
import com.aman.ache.databinding.FragmentMainBinding


class MainFragment : Fragment() {
   private lateinit var binding:FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        val view = FragmentMainBinding.inflate(inflater,container,false)
        binding = view
        return view.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //if user taps on first button
        binding.AlreadyAMemberTv.setOnClickListener {
            gotoAlreadyMember()
        }

        //if user taps on second button
        binding.NewAccountTv.setOnClickListener {
            goToNewAccountFrag()
        }

    }

    //navigate to Already member frag
    private fun gotoAlreadyMember() {
        findNavController().navigate(R.id.action_mainFragment_to_alreadyAccountFragment)
    }

    //navigate to New Account frag
    private fun goToNewAccountFrag() {
        findNavController().navigate(R.id.action_mainFragment_to_createAccountFragment)
    }


}