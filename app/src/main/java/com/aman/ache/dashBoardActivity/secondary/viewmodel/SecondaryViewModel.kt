package com.aman.ache.dashBoardActivity.secondary.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import com.aman.ache.R
import com.aman.ache.dashBoardActivity.secondary.adpater.BodyPartsAdapter
import com.aman.ache.dashBoardActivity.secondary.adpater.DetailAdapter
import com.aman.ache.dashBoardActivity.secondary.fragment.BodyPartsFragment
import com.aman.ache.dashBoardActivity.secondary.fragment.DetailFragment
import com.aman.ache.dashBoardActivity.secondary.model.BodyDataClass
import com.aman.ache.dashBoardActivity.secondary.model.DetailDataClass
import com.aman.ache.databinding.FragmentBodyPartsBinding
import com.aman.ache.databinding.FragmentDetailBinding
import com.aman.ache.databinding.FragmentExerciseBinding
import com.bumptech.glide.Glide
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class SecondaryViewModel : ViewModel() {

    /* references to data base */
    //reference to the database
    private val dbRef = Firebase.firestore

    //this will hold the reference till beginner-> choicedocument ->choice-> withequipment-> bodyparts
    private lateinit var bodyPartRef: CollectionReference
    //Lists made for body part fragment.
    private  val bodyPartsList = mutableListOf<BodyDataClass>()
    private lateinit var bodyPartImage:String
    private lateinit var about:String


    //adapter ref variable is made
    private lateinit var bodyPartsAdapter: BodyPartsAdapter


    //reference to the detail like chest, back,etc
    private lateinit var detailRef: CollectionReference

    //Lists made for detail fragment.
    val detailList = mutableListOf<DetailDataClass>()
    private lateinit var detailAdapter:DetailAdapter

                 /*

                       BODY PARTS FRAGMENT

                 */



            /* with Equipment */
                    fun makeBodyPartReference(type: Int) {
                         //here type means 0->beginner, 1->intermediate 2->advance.
                bodyPartRef = when (type){
                    0 -> {
                        dbRef.collection("beginner").document("choicesdocument").collection("choices")
                            .document("withequipment").collection("bodyparts")

                    }

                    1 -> {
                        dbRef.collection("intermediate").document("choicesdocument").collection("choices")
                            .document("withequipment").collection("bodyparts")

                    }
                    else  -> {
                        dbRef.collection("advance").document("choicesdocument").collection("choices")
                            .document("withequipment").collection("bodyparts")
                    }

                }
                    }

                    fun getBodyParts(
                        context: Context,
                        binding: FragmentBodyPartsBinding,
                        ref: BodyPartsFragment,
                        type:Int     // 0-> beginner, 1-> inter, else -> advance
                    ) {
                        bodyPartRef.get()
                            .addOnSuccessListener { result ->
                                    for (document in result) {
                                        val tempObj = document.toObject(BodyDataClass::class.java)
                                        bodyPartsList.add(tempObj)
                                    }
                                    binding.bodyShimmerLay.stopShimmer()
                                    binding.bodyShimmerLay.isVisible = false
                                    binding.recyclerView.isVisible = true
                                    bodyPartsAdapter = BodyPartsAdapter(bodyPartsList, context, ref,type)
                                    binding.recyclerView.adapter = bodyPartsAdapter

                            }
                    }


                    fun setupToolbar(type:Int,binding: FragmentBodyPartsBinding,context: Context) {
                        val ref:DocumentReference
                        when(type){
                            0 -> {
                                ref =  dbRef.collection("beginner").document("choicesdocument")
                                    .collection("choices").document("withequipment")

                                ref.get()
                                    .addOnSuccessListener { doc->
                                        bodyPartImage = doc.getString("image").toString()
                                        Glide.with(context).load(bodyPartImage).into(binding.imageView)
                                        about = doc.getString("about").toString()
                                        binding.toolbar.title = about

                                    }

                            }
                            1 -> {
                                 ref =  dbRef.collection("intermediate").document("choicesdocument")
                                    .collection("choices").document("withequipment")

                                ref.get()
                                    .addOnSuccessListener { doc->
                                        bodyPartImage = doc.getString("image").toString()
                                        Glide.with(context).load(bodyPartImage).into(binding.imageView)
                                        about = doc.getString("about").toString()
                                        binding.toolbar.title = about
                                    }

                            }
                            else -> {
                                 ref =  dbRef.collection("advance").document("choicesdocument")
                                    .collection("choices").document("withequipment")

                                ref.get()
                                    .addOnSuccessListener { doc->
                                        bodyPartImage = doc.getString("image").toString()
                                        Glide.with(context).load(bodyPartImage).into(binding.imageView)
                                        about = doc.getString("about").toString()
                                        binding.toolbar.title = about
                                    }
                            }
                        }
                    }


                fun getLoadedImage(type: Int, binding: FragmentBodyPartsBinding, context: Context) {
                    when(type){
                        0 -> {


                                    Glide.with(context).load(bodyPartImage).into(binding.imageView)
                                    binding.toolbar.title = about


                        }
                        1 -> {
                            Glide.with(context).load(bodyPartImage).into(binding.imageView)
                            binding.toolbar.title = about

                        }
                        else -> {
                            Glide.with(context).load(bodyPartImage).into(binding.imageView)
                            binding.toolbar.title = about
                        }
                    }

                }

                    fun getLoadedList(binding: FragmentBodyPartsBinding){
                        binding.bodyShimmerLay.stopShimmer()
                        binding.bodyShimmerLay.isVisible = false
                        binding.recyclerView.isVisible = true
                        binding.recyclerView.adapter = bodyPartsAdapter
                    }


                    fun isListEmpty() = bodyPartsList.isEmpty()



    /*

        Detail Fragment

     */

    fun makeDetailRef(title:String,binding: FragmentDetailBinding,context: DetailFragment) {
        when(title){
            "Chest" ->{
                detailRef = bodyPartRef.document("achest").collection("chestbodyparts")
                binding.partImage.setImageResource(R.drawable.chest)
                binding.typeText.text = title
                binding.durationTextView.text = bodyPartsList[0].duration
            }
            "Shoulder" ->{
                detailRef = bodyPartRef.document("bshoulder").collection("shoulderbodyparts")
                binding.partImage.setImageResource(R.drawable.shoulder)
                binding.typeText.text = title
                binding.durationTextView.text = bodyPartsList[1].duration
            }
            "Arm" ->{
                detailRef = bodyPartRef.document("carm").collection("armbodyparts")
                binding.partImage.setImageResource(R.drawable.arm)
                binding.typeText.text = title
                binding.durationTextView.text = bodyPartsList[2].duration
            }
            "Back" ->{
                detailRef = bodyPartRef.document("dback").collection("backbodyparts")
                binding.partImage.setImageResource(R.drawable.back)
                binding.typeText.text = title
                binding.durationTextView.text = bodyPartsList[3].duration
            }
            else -> {
                detailRef = bodyPartRef.document("eleg").collection("legbodyparts")
                binding.partImage.setImageResource(R.drawable.legs)
                binding.typeText.text = title
                binding.durationTextView.text = bodyPartsList[4].duration
            }
        }

                    detailRef.get()
                .addOnSuccessListener { result->
                    for(doc in result){
                        val tempObj = doc.toObject(DetailDataClass::class.java)
                        detailList.add(tempObj)
                    }
                    binding.detailShimmerLay.stopShimmer()
                    binding.detailShimmerLay.isVisible = false
                    binding.recyclerView.isVisible = true
                    detailAdapter = DetailAdapter(detailList,context)
                    binding.recyclerView.adapter = detailAdapter
                }
                .addOnFailureListener {
                    Toast.makeText(context.requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }

    }

    fun setUpRecyclerView(binding: FragmentDetailBinding,context: DetailFragment,type:String) {
        detailAdapter = DetailAdapter(detailList,context)
        binding.recyclerView.adapter = detailAdapter

        when(type){
            "Chest" ->{
                binding.partImage.setImageResource(R.drawable.chest)
                binding.typeText.text = type
                binding.durationTextView.text = bodyPartsList[0].duration
            }
            "Shoulder" ->{
                binding.partImage.setImageResource(R.drawable.shoulder)
                binding.typeText.text = type
                binding.durationTextView.text = bodyPartsList[1].duration
            }
            "Arm" ->{
                binding.partImage.setImageResource(R.drawable.arm)
                binding.typeText.text = type
                binding.durationTextView.text = bodyPartsList[2].duration
            }
            "Back" ->{
                binding.partImage.setImageResource(R.drawable.back)
                binding.typeText.text = type
                binding.durationTextView.text = bodyPartsList[3].duration
            }
            else -> {
                binding.partImage.setImageResource(R.drawable.legs)
                binding.typeText.text = type
                binding.durationTextView.text = bodyPartsList[4].duration
            }

        }

    }


    /*

            Exercise Fragment

     */


    fun setUpExerciseFragment(binding: FragmentExerciseBinding,position:Int) {

        val obj = detailList[position]
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