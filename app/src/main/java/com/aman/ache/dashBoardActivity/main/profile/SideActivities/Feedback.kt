package com.aman.ache.dashBoardActivity.main.profile.SideActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.aman.ache.databinding.ActivityFeedbackBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime


class Feedback : AppCompatActivity() {
    private lateinit var binding: ActivityFeedbackBinding
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = ActivityFeedbackBinding.inflate(layoutInflater)
        binding = view
        setContentView(view.root)


        val auth = FirebaseAuth.getInstance()

        val user = auth.currentUser


        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.editText.addTextChangedListener {
            binding.error.isVisible = false
            binding.success.isVisible = false
        }

        binding.submitButton.setOnClickListener {
            if( binding.editText.text.toString().isEmpty()){
                binding.error.isVisible = true
            }else{
                binding.submitButton.isClickable = false
                binding.text.text = "Submitting..."
                binding.progressBar.isVisible = true


                Handler(Looper.getMainLooper()).postDelayed({
                    val hashmap = hashMapOf(
                        LocalDateTime.now().toString() to binding.editText.text.toString().trim()
                    )

                    db.collection("Feedback").document(user!!.uid)
                        .set(hashmap, SetOptions.merge())
                        .addOnSuccessListener {_: Void? ->
                            binding.submitButton.isClickable = true
                            binding.progressBar.isVisible = false
                            binding.text.text = "Submit"
                            binding.success.isVisible = true
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                            binding.submitButton.isClickable = true
                            binding.progressBar.isVisible = false
                            binding.text.text = "Submit"
                        }

                },3000)
            }
        }

    }
}