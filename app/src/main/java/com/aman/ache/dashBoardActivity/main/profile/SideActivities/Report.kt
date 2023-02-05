package com.aman.ache.dashBoardActivity.main.profile.SideActivities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.aman.ache.R
import com.aman.ache.databinding.ActivityReportBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime

class Report : AppCompatActivity() {
    private lateinit var binding: ActivityReportBinding
    private val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = ActivityReportBinding.inflate(layoutInflater)
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

        binding.reportButton.setOnClickListener {
            if( binding.editText.text.toString().isEmpty()){
                binding.error.isVisible = true
            }else{
                binding.reportButton.isClickable = false
                binding.text.text = "Reporting..."
                binding.progressBar.isVisible = true


                Handler(Looper.getMainLooper()).postDelayed({
                    val hashmap = hashMapOf(
                        LocalDateTime.now().toString() to binding.editText.text.toString().trim()
                    )

                db.collection("Report").document(user!!.uid)
                    .set(hashmap, SetOptions.merge())
                    .addOnSuccessListener {_: Void? ->
                        binding.reportButton.isClickable = true
                        binding.progressBar.isVisible = false
                        binding.text.text = "Report"
                        binding.success.isVisible = true
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                                binding.reportButton.isClickable = true
                                binding.progressBar.isVisible = false
                                binding.text.text = "Report"
                    }

                },3000)
            }
        }

    }
}