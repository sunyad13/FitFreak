package com.aman.ache.dashBoardActivity.category


import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import com.aman.ache.R
import com.aman.ache.dashBoardActivity.category.fragments.CatDetail
import com.aman.ache.dashBoardActivity.secondary.adpater.DetailAdapter
import com.aman.ache.dashBoardActivity.secondary.model.DetailDataClass
import com.aman.ache.databinding.FragmentDetailBinding
import com.aman.ache.databinding.FragmentExerciseBinding
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class CategoryViewModel : ViewModel() {

    private val db = Firebase.firestore
    val detailList = mutableListOf<DetailDataClass>()
    private lateinit var detailAdapter: DetailAdapter

    private lateinit var chestImage:String
    private lateinit var shoulderImage:String
    private lateinit var armImage:String
    private lateinit var backImage:String
    private lateinit var legImage:String


     fun setUpDetailRV(type:Int,context: CatDetail,binding: FragmentDetailBinding){
        when(type){
          0 -> {
              binding.partImage.setImageResource(R.drawable.chest)
              binding.typeText.text = "Chest"
              var ref = db.collection("beginner").document("choicesdocument").collection("choices")
                  .document("withequipment").collection("bodyparts").document("achest").collection("chestbodyparts")

                    ref.get()
                        .addOnSuccessListener { result->
                            for(doc in result){
                                val tempObj = doc.toObject(DetailDataClass::class.java)
                                detailList.add(tempObj)
                            }
                        }
                        .addOnFailureListener {
                            Toast.makeText(context.requireContext(), it.message, Toast.LENGTH_SHORT).show()

                        }
              ref = db.collection("advance").document("choicesdocument").collection("choices")
                  .document("withequipment").collection("bodyparts").document("achest").collection("chestbodyparts")
              ref.get()
                  .addOnSuccessListener { result->
                      for(doc in result){
                          val tempObj = doc.toObject(DetailDataClass::class.java)
                          detailList.add(tempObj)
                      }
                  }
                  .addOnFailureListener {
                      Toast.makeText(context.requireContext(), it.message, Toast.LENGTH_SHORT).show()

                  }


              ref = db.collection("advance").document("choicesdocument").collection("choices")
                  .document("withequipment").collection("bodyparts").document("achest").collection("chestbodyparts")
              ref.get()
                  .addOnSuccessListener { result->
                      for(doc in result){
                          val tempObj = doc.toObject(DetailDataClass::class.java)
                          detailList.add(tempObj)
                      }
                      binding.detailShimmerLay.isVisible = false
                      binding.detailShimmerLay.stopShimmer()
                      binding.recyclerView.isVisible = true
                      detailAdapter = DetailAdapter(detailList,context)
                      binding.recyclerView.adapter = detailAdapter
                  }
                  .addOnFailureListener {
                      Toast.makeText(context.requireContext(), it.message, Toast.LENGTH_SHORT).show()
                  }
          }

          1 -> {
              binding.partImage.setImageResource(R.drawable.shoulder)
              binding.typeText.text = "Shoulder"
              var ref = db.collection("beginner").document("choicesdocument").collection("choices")
                  .document("withequipment").collection("bodyparts").document("bshoulder").collection("shoulderbodyparts")

              ref.get()
                  .addOnSuccessListener { result->
                      for(doc in result){
                          val tempObj = doc.toObject(DetailDataClass::class.java)
                          detailList.add(tempObj)
                      }
                  }
                  .addOnFailureListener {
                      Toast.makeText(context.requireContext(), it.message, Toast.LENGTH_SHORT).show()

                  }
              ref = db.collection("intermediate").document("choicesdocument").collection("choices")
                  .document("withequipment").collection("bodyparts").document("bshoulder").collection("shoulderbodyparts")
              ref.get()
                  .addOnSuccessListener { result->
                      for(doc in result){
                          val tempObj = doc.toObject(DetailDataClass::class.java)
                          detailList.add(tempObj)
                      }
                  }
                  .addOnFailureListener {
                      Toast.makeText(context.requireContext(), it.message, Toast.LENGTH_SHORT).show()

                  }


              ref = db.collection("advance").document("choicesdocument").collection("choices")
                  .document("withequipment").collection("bodyparts").document("bshoulder").collection("shoulderbodyparts")
              ref.get()
                  .addOnSuccessListener { result->
                      for(doc in result){
                          val tempObj = doc.toObject(DetailDataClass::class.java)
                          detailList.add(tempObj)
                      }
                      binding.detailShimmerLay.isVisible = false
                      binding.detailShimmerLay.stopShimmer()
                      binding.recyclerView.isVisible = true
                      detailAdapter = DetailAdapter(detailList,context)
                      binding.recyclerView.adapter = detailAdapter
                  }
                  .addOnFailureListener {
                      Toast.makeText(context.requireContext(), it.message, Toast.LENGTH_SHORT).show()

                  }
          }

          2 -> {
              binding.partImage.setImageResource(R.drawable.arm)
              binding.typeText.text = "Arm"
              var ref = db.collection("beginner").document("choicesdocument").collection("choices")
                  .document("withequipment").collection("bodyparts").document("carm").collection("armbodyparts")

              ref.get()
                  .addOnSuccessListener { result->
                      for(doc in result){
                          val tempObj = doc.toObject(DetailDataClass::class.java)
                          detailList.add(tempObj)
                      }
                  }
                  .addOnFailureListener {
                      Toast.makeText(context.requireContext(), it.message, Toast.LENGTH_SHORT).show()

                  }
              ref = db.collection("intermediate").document("choicesdocument").collection("choices")
                  .document("withequipment").collection("bodyparts").document("carm").collection("armbodyparts")
              ref.get()
                  .addOnSuccessListener { result->
                      for(doc in result){
                          val tempObj = doc.toObject(DetailDataClass::class.java)
                          detailList.add(tempObj)
                      }
                  }
                  .addOnFailureListener {
                      Toast.makeText(context.requireContext(), it.message, Toast.LENGTH_SHORT).show()

                  }


              ref = db.collection("advance").document("choicesdocument").collection("choices")
                  .document("withequipment").collection("bodyparts").document("carm").collection("armbodyparts")
              ref.get()
                  .addOnSuccessListener { result->
                      for(doc in result){
                          val tempObj = doc.toObject(DetailDataClass::class.java)
                          detailList.add(tempObj)
                      }
                      binding.detailShimmerLay.isVisible = false
                      binding.detailShimmerLay.stopShimmer()
                      binding.recyclerView.isVisible = true
                      detailAdapter = DetailAdapter(detailList,context)
                      binding.recyclerView.adapter = detailAdapter
                  }
                  .addOnFailureListener {
                      Toast.makeText(context.requireContext(), it.message, Toast.LENGTH_SHORT).show()

                  }
          }

          3 -> {
              binding.partImage.setImageResource(R.drawable.back)
              binding.typeText.text = "Back"
              var ref = db.collection("beginner").document("choicesdocument").collection("choices")
                  .document("withequipment").collection("bodyparts").document("dback").collection("backbodyparts")

              ref.get()
                  .addOnSuccessListener { result->
                      for(doc in result){
                          val tempObj = doc.toObject(DetailDataClass::class.java)
                          detailList.add(tempObj)
                      }
                  }
                  .addOnFailureListener {
                      Toast.makeText(context.requireContext(), it.message, Toast.LENGTH_SHORT).show()

                  }
              ref = db.collection("intermediate").document("choicesdocument").collection("choices")
                  .document("withequipment").collection("bodyparts").document("dback").collection("backbodyparts")
              ref.get()
                  .addOnSuccessListener { result->
                      for(doc in result){
                          val tempObj = doc.toObject(DetailDataClass::class.java)
                          detailList.add(tempObj)
                      }
                  }
                  .addOnFailureListener {
                      Toast.makeText(context.requireContext(), it.message, Toast.LENGTH_SHORT).show()

                  }


              ref = db.collection("advance").document("choicesdocument").collection("choices")
                  .document("withequipment").collection("bodyparts").document("dback").collection("backbodyparts")
              ref.get()
                  .addOnSuccessListener { result->
                      for(doc in result){
                          val tempObj = doc.toObject(DetailDataClass::class.java)
                          detailList.add(tempObj)
                      }
                      binding.detailShimmerLay.isVisible = false
                      binding.detailShimmerLay.stopShimmer()
                      binding.recyclerView.isVisible = true
                      detailAdapter = DetailAdapter(detailList,context)
                      binding.recyclerView.adapter = detailAdapter
                  }
                  .addOnFailureListener {
                      Toast.makeText(context.requireContext(), it.message, Toast.LENGTH_SHORT).show()

                  }
          }

          else -> {
              binding.partImage.setImageResource(R.drawable.legs)
              binding.typeText.text = "Legs"
              var ref = db.collection("beginner").document("choicesdocument").collection("choices")
                  .document("withequipment").collection("bodyparts").document("eleg").collection("legbodyparts")

              ref.get()
                  .addOnSuccessListener { result->
                      for(doc in result){
                          val tempObj = doc.toObject(DetailDataClass::class.java)
                          detailList.add(tempObj)
                      }
                  }
                  .addOnFailureListener {
                      Toast.makeText(context.requireContext(), it.message, Toast.LENGTH_SHORT).show()

                  }
              ref = db.collection("intermediate").document("choicesdocument").collection("choices")
                  .document("withequipment").collection("bodyparts").document("eleg").collection("legbodyparts")
              ref.get()
                  .addOnSuccessListener { result->
                      for(doc in result){
                          val tempObj = doc.toObject(DetailDataClass::class.java)
                          detailList.add(tempObj)
                      }
                  }
                  .addOnFailureListener {
                      Toast.makeText(context.requireContext(), it.message, Toast.LENGTH_SHORT).show()

                  }


              ref = db.collection("advance").document("choicesdocument").collection("choices")
                  .document("withequipment").collection("bodyparts").document("eleg").collection("legbodyparts")
              ref.get()
                  .addOnSuccessListener { result->
                      for(doc in result){
                          val tempObj = doc.toObject(DetailDataClass::class.java)
                          detailList.add(tempObj)
                      }
                      binding.detailShimmerLay.isVisible = false
                      binding.detailShimmerLay.stopShimmer()
                      binding.recyclerView.isVisible = true
                      detailAdapter = DetailAdapter(detailList,context)
                      binding.recyclerView.adapter = detailAdapter
                  }
                  .addOnFailureListener {
                      Toast.makeText(context.requireContext(), it.message, Toast.LENGTH_SHORT).show()
                  }
          }
        }


    }


    fun getImages(type: Int, context: CatDetail, binding: FragmentDetailBinding){

        when(type){
            0 -> {
                val ref = db.collection("beginner").document("choicesdocument").collection("choices")
                    .document("withequipment").collection("bodyparts").document("achest")

                ref.get()
                    .addOnSuccessListener { doc ->
                        chestImage = doc.getString("image")!!
                        Glide.with(context).load(chestImage).into(binding.imageView)
                    }

            }

            1->{
               val ref = db.collection("beginner").document("choicesdocument").collection("choices")
                    .document("withequipment").collection("bodyparts").document("bshoulder")


                ref.get()
                    .addOnSuccessListener { doc ->
                        shoulderImage = doc.getString("image")!!
                        Glide.with(context).load(shoulderImage).into(binding.imageView)
                    }
            }

            2-> {
                val ref = db.collection("beginner").document("choicesdocument").collection("choices")
                    .document("withequipment").collection("bodyparts").document("carm")

                ref.get()
                    .addOnSuccessListener { doc ->
                        armImage = doc.getString("image")!!
                        Glide.with(context).load(armImage).into(binding.imageView)
                    }
            }

            3 -> {
                val ref = db.collection("beginner").document("choicesdocument").collection("choices")
                    .document("withequipment").collection("bodyparts").document("dback")

                ref.get()
                    .addOnSuccessListener { doc ->
                        backImage = doc.getString("image")!!
                        Glide.with(context).load(backImage).into(binding.imageView)
                    }
            }

            else -> {
                val ref = db.collection("beginner").document("choicesdocument").collection("choices")
                    .document("withequipment").collection("bodyparts").document("eleg")

                ref.get()
                    .addOnSuccessListener { doc ->
                        legImage = doc.getString("image")!!
                        Glide.with(context).load(legImage).into(binding.imageView)

                    }
            }


        }


    }

    fun setUpLoadedViews(type: Int, context: CatDetail, binding: FragmentDetailBinding) {

        detailAdapter = DetailAdapter(detailList,context)
        binding.recyclerView.adapter = detailAdapter
        when(type){
            0 -> {
                Glide.with(context).load(chestImage).into(binding.imageView)
                binding.partImage.setImageResource(R.drawable.chest)
                binding.typeText.text = "Chest"
            }

            1->{
                Glide.with(context).load(shoulderImage).into(binding.imageView)
                binding.partImage.setImageResource(R.drawable.shoulder)
                binding.typeText.text = "Shoulder"
            }

            2-> {
                Glide.with(context).load(armImage).into(binding.imageView)
                binding.partImage.setImageResource(R.drawable.arm)
                binding.typeText.text = "Arm"
            }

            3 -> {
                Glide.with(context).load(backImage).into(binding.imageView)
                binding.partImage.setImageResource(R.drawable.back)
                binding.typeText.text = "Back"
            }

            else -> {
                Glide.with(context).load(legImage).into(binding.imageView)
                binding.partImage.setImageResource(R.drawable.legs)
                binding.typeText.text = "Legs"
            }


        }

    }









    /*
        EXERCISE FRAGMENT
     */

    fun setUpExerciseFragment(binding: FragmentExerciseBinding, position: Int) {
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