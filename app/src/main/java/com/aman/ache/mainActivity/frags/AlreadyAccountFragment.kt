package com.aman.ache.mainActivity.frags



import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.aman.ache.R
import com.aman.ache.databinding.FragmentAlreadyAccountBinding
import com.aman.ache.mainActivity.RecoveryActivity
import com.aman.ache.mainActivity.mainviewmodel.MainViewModel
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.*


class AlreadyAccountFragment : Fragment() {
    private val RC_SIGN_IN = 100
    private lateinit var binding:FragmentAlreadyAccountBinding
    private val sharedViewModel:MainViewModel by activityViewModels()
    private lateinit var auth: FirebaseAuth


    private lateinit var email:String
    private lateinit var pass:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentAlreadyAccountBinding.inflate(inflater,container,false)
        binding = view
        return view.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //init the auth
        auth = FirebaseAuth.getInstance()

        binding.backButton.setOnClickListener{
                sendUserToWelcomeFrag()
        }

        binding.toSignup.setOnClickListener {
            sendUserToNewAccountPage()
        }

        binding.forgotPass.setOnClickListener {
            startActivity(Intent(requireContext(),RecoveryActivity::class.java))
        }

        sharedViewModel.getGso(this)



        binding.login.setOnClickListener {
            email  = binding.emailEt.text.toString().trim()
            pass  = binding.passwordEt.text.toString().trim()

            if(email.isEmpty() ||  pass.isEmpty()){
                Snackbar.make(binding.root,"Please provide all the details",Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(resources.getColor(R.color.red))
                    .setTextColor(resources.getColor(R.color.white))
                    .show()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Snackbar.make(binding.root,"Invalid email",Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(resources.getColor(R.color.red))
                    .setTextColor(resources.getColor(R.color.white))
                    .show()
            }else{
                registerUser()
            }


        }

        binding.google.setOnClickListener {
            val intent = sharedViewModel.getSignInIntent()
            startActivityForResult(intent,RC_SIGN_IN)
        }


    }





    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
               sharedViewModel.firebaseAuthWithGoogle(account,this,binding)
            } catch (e: ApiException) {
                Snackbar.make(binding.root,e.message.toString(),Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(requireContext().resources.getColor(R.color.red))
                    .setTextColor(requireContext().resources.getColor(R.color.white))
                    .show()
            }
        }
    }




    private fun registerUser() {
        sharedViewModel.logInWithFireBase(email,pass,this,binding)
    }

    //navigate  to welcome frag
    private fun sendUserToWelcomeFrag() {
        findNavController().navigate(R.id.action_alreadyAccountFragment_to_mainFragment)
    }


    //navigate user to NewAccountFrag
    private fun sendUserToNewAccountPage() {
        findNavController().navigate(R.id.action_alreadyAccountFragment_to_createAccountFragment)
    }


}


