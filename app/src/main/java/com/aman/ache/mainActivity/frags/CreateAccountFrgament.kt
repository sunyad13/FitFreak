package com.aman.ache.mainActivity.frags

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.aman.ache.R
import com.aman.ache.databinding.FragmentCreateAccountFragmentBinding
import com.aman.ache.mainActivity.mainviewmodel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth


class CreateAccountFragment : Fragment() {
    private lateinit var binding:FragmentCreateAccountFragmentBinding
    private val sharedViewModel: MainViewModel by activityViewModels()
    private lateinit var auth:FirebaseAuth

    
    //user's info
    private lateinit var email:String
    private  lateinit var password:String
    private lateinit var  fName:String
    private lateinit var  lName:String
    private lateinit var age:String
    private lateinit var gender:String
    private lateinit var height:String
    private lateinit var weight:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        val view = FragmentCreateAccountFragmentBinding.inflate(inflater,container,false)
        binding = view
        return view.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initializing fire base auth
        auth = FirebaseAuth.getInstance()


        binding.backButton.setOnClickListener {
            sendToWelcomePage()
        }

        binding.loginBtn.setOnClickListener {
            sendToLoginPage()
        }


        binding.signup.setOnClickListener {

            email = binding.emailEt.text.toString().trim()
            fName = binding.firstName.text.toString().trim()
            lName = binding.lastName.text.toString().trim()
            password = binding.passwordEt.text.toString().trim()

            if(email.isEmpty() || fName.isEmpty() || lName.isEmpty() || password.isEmpty()){
                Snackbar.make(binding.root,"Please provide all the details",Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(resources.getColor(R.color.red))
                    .setTextColor(resources.getColor(R.color.white))
                    .show()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Snackbar.make(binding.root,"Invalid email",Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(resources.getColor(R.color.red))
                    .setTextColor(resources.getColor(R.color.white))
                    .show()
            }else if(!validateGender()){

            }else{
                val selectedGen = binding.root.findViewById<RadioButton>(binding.genderRadio.checkedRadioButtonId)
                gender = selectedGen.text.toString()
                registerUser()
            }



        }



    }

    private fun registerUser() {

        val bottomSheetDialog = BottomSheetDialog(requireView().context,R.style.BottomSheetTheme)
        val sheetView = LayoutInflater.from(context).inflate(R.layout.let_us_know_more_bottom,
            view?.findViewById(R.id.bottom_sheet)
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

                val hashMap = hashMapOf<String,String>(
                    "name" to fName+" "+lName ,
                    "email" to email,
                    "gender" to gender,
                    "age" to age,
                    "height" to height,
                    "weight" to weight,
                    "pass" to password
                )

                sharedViewModel.registerOnFirebase(email,password,this,hashMap,binding)

            }
        }

    }

    private fun validateGender(): Boolean {
        return if (binding.genderRadio.checkedRadioButtonId == -1) {
            Snackbar.make(binding.root,"Please select a gender",Snackbar.LENGTH_SHORT)
                .setBackgroundTint(resources.getColor(R.color.red))
                .setTextColor(resources.getColor(R.color.white))
                .show()
            false
        } else {
            true
        }
    }


    //navigate user LoginPage
    private fun sendToLoginPage() {
        findNavController().navigate(R.id.action_createAccountFragment_to_alreadyAccountFragment)
    }

//    navigate user to welcome frag
    private fun sendToWelcomePage() {
        findNavController().navigate(R.id.action_createAccountFragment_to_mainFragment)
    }


}
