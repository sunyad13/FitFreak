package com.aman.ache.dashBoardActivity.main.training.viewmodels


import android.content.Context
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import com.aman.ache.R
import com.aman.ache.dashBoardActivity.main.profile.ProfileFragment
import com.aman.ache.dashBoardActivity.main.training.TrainingFragment
import com.aman.ache.databinding.FragmentProfileBinding
import com.aman.ache.databinding.FragmentTrainingBinding
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class TrainingViewModel : ViewModel() {
    var isProfileALive = true
    var isTrainingAlive = true



    // firebase
    private val db = Firebase.firestore
    private val auth = Firebase.auth


    //data
     var name = ""
     var email= ""
     var profile= ""
     var age= ""
     var weight= ""
     var height= ""
     var bmi= ""
     var protein= ""




    fun getTrainingData(binding: FragmentTrainingBinding,context: Context) {

                val ref = db.collection("Users").document(auth.uid!!)
                ref.addSnapshotListener { snap, e ->
                    if (snap != null && snap.exists()) {

                        name = snap.get("name").toString()

                        profile = snap.get("profile").toString()


                        if(isTrainingAlive){
                            setUpTrainingFrag(binding,context)
                        }

                    }
                }

    }

    private  fun setUpTrainingFrag(binding: FragmentTrainingBinding, context: Context) {
                     binding.nameTV.text = name

                     binding.smallProfileShimmerLay.stopShimmer()
                     binding.smallProfileShimmerLay.isVisible = false
                     binding.profileImage.isVisible = true

                        try {
                            if (profile.isEmpty()) {
                                binding.profileImage.setImageResource(R.drawable.dp)
                            }else{
                                Glide.with(context).load(profile).centerCrop().into(binding.profileImage)
                            }
                        } catch (e: Exception) {
                            binding.profileImage.setImageResource(R.drawable.dp)
                        }

    }




    fun getProfileData(binding: FragmentProfileBinding,context: ProfileFragment) {

        // name, email, profile, age,           weight, height.

        val ref = db.collection("Users").document(auth.uid!!)
                        ref.addSnapshotListener { snap, e ->
                                if (snap != null && snap.exists()) {
                                    name = snap.get("name").toString()

                                    age = snap.get("age").toString()

                                    weight = snap.get("weight").toString()

                                    email = snap.get("email").toString()


                                    height = snap.get("height").toString()

                                    val meter = height.toFloat() * 0.3048
                                    val kg = weight.toFloat()
                                    val bmiV = kg / (meter * meter)

                                    bmi = bmiV.toString().substring(0, 4)


                                    val proteinV = kg * 2.20462 * 0.36
                                    protein = proteinV.toString().substring(0, 4)

                                    profile = snap.get("profile").toString()

                                    if(isProfileALive){
                                        setupProfileFrag(binding,context)
                                    }

                                }
                             }

       }

    private  fun setupProfileFrag(binding: FragmentProfileBinding,context: ProfileFragment) {
                    binding.nameTV.text = name
                    binding.emailTV.text = email
                    binding.weightTV.text = weight
                    binding.heightTV.text = height
                    binding.ageTV.text = age
                    binding.bmiTv.text = bmi
                    binding.proteinTv.text = protein

                    try {
                            if (profile == "") {
                                binding.profilePic.setImageResource(R.drawable.dp)
                            }else{
                                Glide.with(context).load(profile).centerCrop().into(binding.profilePic)
                            }
                       } catch (e: Exception) {
                            binding.profilePic.setImageResource(R.drawable.dp)
                         }
                }

}
