package com.aman.ache.onBoarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.aman.ache.R
import com.aman.ache.databinding.ActivityOnBoardingBinding

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding:ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = ActivityOnBoardingBinding.inflate(layoutInflater)
        binding = view
        setContentView(view.root)


    }
}