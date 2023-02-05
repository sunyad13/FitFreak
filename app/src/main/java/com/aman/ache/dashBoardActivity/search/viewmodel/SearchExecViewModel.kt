package com.aman.ache.dashBoardActivity.search.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.aman.ache.dashBoardActivity.secondary.model.DetailDataClass
import com.aman.ache.databinding.FragmentExerciseBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class SearchExecViewModel : ViewModel() {
    lateinit var obj: DetailDataClass
    private val db = Firebase.firestore
    var flag = 1

    fun getObject(execName: String,binding: FragmentExerciseBinding) {

        if(flag==1){
            val ref = db.collection("Exercises").document(execName)
            ref.get()
                .addOnSuccessListener { doc->
                    obj = doc.toObject(DetailDataClass::class.java)!!
                    setUpViews(binding)
                }
            flag = 2
        }else{
            setUpViews(binding)
        }


    }

    private fun setUpViews(binding: FragmentExerciseBinding) {
        binding.youtubeView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = obj.link
                youTubePlayer.loadVideo(videoId, 0f)
                youTubePlayer.play()
            }
        })

        binding.s1.text = obj.s1
        binding.s2.text = obj.s2
        binding.s3.text = obj.s3
        binding.s4.text = obj.s4

    }



}