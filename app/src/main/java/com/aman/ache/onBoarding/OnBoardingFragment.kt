package com.aman.ache.onBoarding



import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.aman.ache.R
import com.aman.ache.databinding.FragmentOnBoardingfragmentBinding
import com.aman.ache.mainActivity.MainActivity
import com.amansharma.fitnessfrequency.onBoardingActivity.onBoardingAdapter.OnBoardingAdapter
import com.google.android.material.tabs.TabLayoutMediator


class OnBoardingFragment : Fragment() {
    private lateinit var binding:FragmentOnBoardingfragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentOnBoardingfragmentBinding.inflate(inflater,container,false)
        binding = view
        return view.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //the OnBoardingAdapter will load up the three fragments.
        binding.viewpager.adapter = OnBoardingAdapter(this)


        //tab layout to show the dots going along with the change in fragments.
        val tabLayoutMediator = TabLayoutMediator(
            binding.tabLayout,
            binding.viewpager
        ) { tab, position ->
            //want to display nothing except three dots therefore have this body blank
        }
        //calling attach() to finally attach viewpager and tab layout
        tabLayoutMediator.attach()

        //handling the button clicks
        binding.nextButton.setOnClickListener {
            buttonTapped()
        }

        binding.skip.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        //when ever the user scrolls or the fragment is changed by any kind of thing this function is called
        binding.viewpager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                //setting up the text of the button whenever the fragment changes.
                when(position){
                    0-> binding.nextButton.text = getString(R.string.next_button_text)
                    1-> binding.nextButton.text = getString(R.string.next_button_text)
                    else-> binding.nextButton.text = getString(R.string.get_started_text)
                }
            }

        })
    }

    //whenever the button is clicked this function is called
    //to set the current fragment upon the screen.
    private fun buttonTapped() {
        when(binding.viewpager.currentItem){
            0 -> binding.viewpager.setCurrentItem(1,true)
            1 -> binding.viewpager.setCurrentItem(2, true)

            //when user is on on the last fragment and button is clicked
            // then setting the is_alive variable to false and that is observed within the onBoarding Activity
            //and sent to the main activity
            2 -> {
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }
    }

}