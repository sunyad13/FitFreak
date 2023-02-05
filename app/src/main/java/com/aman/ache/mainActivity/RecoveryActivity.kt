package com.aman.ache.mainActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.aman.ache.R
import com.aman.ache.databinding.ActivityRecoveryBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class RecoveryActivity : AppCompatActivity() {

    private lateinit var binding:ActivityRecoveryBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = ActivityRecoveryBinding.inflate(layoutInflater)
        binding = view
        setContentView(view.root)


        binding.backButton.setOnClickListener {
            finish()
        }


        binding.emailEt.addTextChangedListener {
            binding.success.isVisible = false
        }

        binding.send.setOnClickListener {
            binding.send.isClickable = false
            val email = binding.emailEt.text.toString().trim()
            if(email.isEmpty()){
                Snackbar.make(binding.root,"Please provide the email", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(resources.getColor(R.color.red))
                    .setTextColor(resources.getColor(R.color.white))
                    .show()
                binding.send.isClickable = true
            }else{
                sendEmail(email)
            }

        }


    }

    private fun sendEmail(email:String) {
        binding.text.text = "Sending request..."
        binding.progBar.isVisible = true

            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener {
                    Handler(Looper.getMainLooper()).postDelayed({
                    binding.success.isVisible = true
                        binding.text.text = "Get Instructions"
                        binding.progBar.isVisible = false
                    },3000)
                }
                .addOnFailureListener {e->
                    Handler(Looper.getMainLooper()).postDelayed({
                        binding.text.text = "Get Instructions"
                        binding.progBar.isVisible = false
                    },3000)
                    Snackbar.make(binding.root,e.message.toString(), Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(resources.getColor(R.color.red))
                        .setTextColor(resources.getColor(R.color.white))
                        .show()
                }

        binding.backButton.isClickable = true
        binding.send.isClickable = true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}