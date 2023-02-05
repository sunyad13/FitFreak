package com.aman.ache.mainActivity.mainviewmodel

import  android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.doOnDetach
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModel
import com.aman.ache.R
import com.aman.ache.dashBoardActivity.DashBoardActivity
import com.aman.ache.databinding.FragmentAlreadyAccountBinding
import com.aman.ache.databinding.FragmentCreateAccountFragmentBinding
import com.aman.ache.mainActivity.MainActivity
import com.aman.ache.mainActivity.frags.AlreadyAccountFragment
import com.aman.ache.mainActivity.frags.CreateAccountFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainViewModel:ViewModel() {
    private lateinit var gso: GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var user:FirebaseUser

    //base reference to the fire store
    private val db = Firebase.firestore


    // user info
    private lateinit var name:String
    private lateinit var email:String
    private lateinit var age:String
    private lateinit var height:String
    private lateinit var weight:String






    //  -> FOR NEW ACCOUNT FRAGMENT

    //for firebase registration
    fun registerOnFirebase( email:String, password:String,context: CreateAccountFragment,hashMap: HashMap<String,String>,binding:FragmentCreateAccountFragmentBinding){
        binding.progBar.isVisible = true
        binding.text.text = "Signing Up..."
        binding.signup.isClickable = false
        binding.loginBtn.isClickable = false
        binding.backButton.isClickable = false
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task->
            if(task.isSuccessful){
                Handler(Looper.getMainLooper()).postDelayed({

                    // save user's personal info...
                    user = auth.currentUser!!

                    hashMap["uid"] = user.uid

                    db.collection("Users").document(user.uid)
                        .set(hashMap)
                        .addOnFailureListener { e ->
                            Toast.makeText(context.requireContext(), e.message, Toast.LENGTH_SHORT).show() }

                    //user has signed in successfully
                    Snackbar.make(binding.root,"Account created !!", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(context.resources.getColor(R.color.colorGreen))
                        .setTextColor(context.resources.getColor(R.color.white))
                        .show()
                    // go to dashboard activity
                    val intent = Intent(context.requireContext(),DashBoardActivity::class.java)
                    context.startActivity(intent)
                    context.requireActivity().finish()

                },3000)

            }else{
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.progBar.isVisible = false
                    binding.text.text = "Sign Up"
                    binding.signup.isClickable = true
                    binding.loginBtn.isClickable = true
                    binding.backButton.isClickable = true
                    Snackbar.make(binding.root,"Authentication Failed",Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(context.resources.getColor(R.color.red))
                        .setTextColor(context.resources.getColor(R.color.white))
                        .show()

                },3000)


            }
        }
    }





    //for google registration
    fun getGso(context: AlreadyAccountFragment){
        gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.server_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(context.requireActivity(),gso)
    }

    fun getSignInIntent(): Intent {
        return googleSignInClient.signInIntent
    }

     fun firebaseAuthWithGoogle(acct: GoogleSignInAccount,context: AlreadyAccountFragment,binding: FragmentAlreadyAccountBinding) {
          binding.text.text = "Logging in"
          binding.progBar.isVisible = true
//          binding.google.isClickable = false
//          binding.forgotPass.isClickable = false
//          binding.login.isClickable = false
         val credential = GoogleAuthProvider.getCredential(acct.idToken, null);
         auth.signInWithCredential(credential)
             .addOnSuccessListener {
                 userExist(context,binding)
             }.addOnFailureListener {
                 Snackbar.make(binding.root,"Authentication Failed",Snackbar.LENGTH_SHORT)
                     .setBackgroundTint(context.resources.getColor(R.color.red))
                     .setTextColor(context.resources.getColor(R.color.white))
                     .show()
             }

    }

    private fun userExist(context: AlreadyAccountFragment,binding:FragmentAlreadyAccountBinding) {
        var ref = db.collection("Users").document(auth.uid!!)
            ref.get()
                .addOnSuccessListener {
                    Handler(Looper.getMainLooper()).postDelayed({
                        if(it.exists()){
                            val intent = Intent(context.requireContext(),DashBoardActivity::class.java)
                            context.startActivity(intent)
                            context.requireActivity().finish()
                        }else{
                            binding.text.text = "Log in"
                            binding.progBar.isVisible = false
                            name = auth.currentUser!!.displayName!!
                            email = auth.currentUser!!.email!!

                            val bottomSheetDialog = BottomSheetDialog(context.requireContext(),R.style.BottomSheetTheme)
                            val sheetView = LayoutInflater.from(context.requireContext()).inflate(R.layout.let_us_know_more_bottom,
                                binding.root.findViewById(R.id.bottom_sheet)
                            )

                            bottomSheetDialog.setContentView(sheetView)
                            bottomSheetDialog.show()


                            val ageEt = sheetView.findViewById<EditText>(R.id.ageET)
                            val weightEt = sheetView.findViewById<EditText>(R.id.weightEt)
                            val heightEt = sheetView.findViewById<EditText>(R.id.heightEt)
                            val cont = sheetView.findViewById<Button>(R.id.Continue)
                            val text = sheetView.findViewById<TextView>(R.id.error)

                            cont.setOnClickListener {
                                age = ageEt.text.toString().trim()
                                weight = weightEt.text.toString().trim()
                                height = heightEt.text.toString().trim()

                                weightEt.addTextChangedListener {
                                    text.isVisible = false
                                }
                                ageEt.addTextChangedListener {
                                    text.isVisible = false
                                }
                                heightEt.addTextChangedListener {
                                    text.isVisible = false
                                }

                                if(age.isEmpty() || weight.isEmpty() || height.isEmpty()){
                                    text.isVisible = true
                                }else{
                                    bottomSheetDialog.dismiss()

                                    val hashMap = hashMapOf(
                                        "name" to name ,
                                        "email" to email,
                                        "age" to age,
                                        "height" to height,
                                        "weight" to weight,
                                    )

                                    binding.text.text = "Logging in..."
                                    binding.progBar.isVisible = true

                                    ref = db.collection("Users").document(auth.uid!!)
                                    ref.set(hashMap)
                                        .addOnSuccessListener {
                                            Handler(Looper.getMainLooper()).postDelayed({
                                                val intent = Intent(context.requireContext(),DashBoardActivity::class.java)
                                                context.startActivity(intent)
                                                context.requireActivity().finish()
                                            },2000)

                                        }
                                }

                            }


                        }
                    },3000)

                }
                .addOnFailureListener {
                    Snackbar.make(binding.root,"Please try again",Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(context.resources.getColor(R.color.red))
                        .setTextColor(context.resources.getColor(R.color.white))
                        .show()
                }
    }














    //logging in with email and password
    fun logInWithFireBase(email: String, password: String, context: AlreadyAccountFragment, binding: FragmentAlreadyAccountBinding) {
        binding.progBar.isVisible = true
        binding.login.isClickable = false
        binding.google.isClickable = false
        binding.toSignup.isClickable = false
        binding.forgotPass.isClickable = false
        binding.text.text = "Logging in"
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task->
            if(task.isSuccessful){
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(context.requireContext(), DashBoardActivity::class.java)
                    context.startActivity(intent)
                    context.requireActivity().finish()
                },3000)

            }
        }.addOnFailureListener {
            Handler(Looper.getMainLooper()).postDelayed({
                binding.progBar.isVisible = false
                binding.login.isClickable = true
                binding.google.isClickable = true
                binding.toSignup.isClickable = true
                binding.forgotPass.isClickable = true
                binding.text.text = "Log in"
                Snackbar.make(binding.root,it.message.toString(),Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(context.resources.getColor(R.color.red))
                    .setTextColor(context.resources.getColor(R.color.white))
                    .show()
            },3000)

        }
    }

}