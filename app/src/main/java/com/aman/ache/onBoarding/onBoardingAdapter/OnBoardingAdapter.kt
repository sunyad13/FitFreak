package com.amansharma.fitnessfrequency.onBoardingActivity.onBoardingAdapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.aman.ache.onBoarding.childfrags.FirstFragment
import com.aman.ache.onBoarding.childfrags.SecondFragment
import com.aman.ache.onBoarding.childfrags.ThirdFragment


//adapter used to  hold the onBoarding fragments with the lottie animations.
class OnBoardingAdapter(fragment:Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
       return when(position){
           0 -> FirstFragment()
           1 -> SecondFragment()
           else -> ThirdFragment()
       }
    }


}