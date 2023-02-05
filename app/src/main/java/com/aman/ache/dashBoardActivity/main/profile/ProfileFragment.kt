package com.aman.ache.dashBoardActivity.main.profile

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.aman.ache.R
import com.aman.ache.SplashActivity
import com.aman.ache.dashBoardActivity.main.profile.SideActivities.Feedback
import com.aman.ache.dashBoardActivity.main.profile.SideActivities.Report
import com.aman.ache.dashBoardActivity.main.training.viewmodels.TrainingViewModel
import com.aman.ache.databinding.FragmentProfileBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class ProfileFragment : Fragment() {
    private lateinit var binding:FragmentProfileBinding
    private val viewModel : TrainingViewModel by activityViewModels()
    private val auth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore
    private lateinit var logoutAlertDialog:AlertDialog


    private lateinit var storagePermissions:Array<String>
    private lateinit var cameraPermissions:Array<String>



    //storage
    private lateinit var storage:FirebaseStorage
    private lateinit var storageRef:StorageReference
    private val storagePath = "Users_Profile_Imgs/"
    private lateinit var cameraUri: Uri



    //CODES
    private val CAMERA_REQUEST_CODE: Int = 100
    private val STORAGE_REQUEST_CODE = 200
    private val IMAGE_PICK_GALLERY_CODE = 300
    private val IMAGE_PICK_CAMERA_CODE = 400


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.isProfileALive = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view  = FragmentProfileBinding.inflate(inflater,container,false)
        binding = view
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getProfileData(binding, this)

        binding.shareText.text = "Share Blood sweat & Cheers with your friends and other fitness aspirant"

        storage = FirebaseStorage.getInstance()
        storageRef = storage.reference



        storagePermissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        cameraPermissions =
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)


        binding.feedback.setOnClickListener {
            val intent = Intent(requireContext(), Feedback::class.java)
            startActivity(intent)
        }

        binding.bug.setOnClickListener {
            val intent = Intent(requireContext(), Report::class.java)
            startActivity(intent)
        }

        binding.share.setOnClickListener {

                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.aman.ache")
                    type = "text/plain"

                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }

        binding.rate.setOnClickListener {
            val uri = Uri.parse("https://play.google.com/store/apps/details?id=com.aman.ache")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }


            binding.moreOptions.setOnClickListener {
                moreOptions()
            }

        }



    private fun moreOptions() {


        val alert = AlertDialog.Builder(activity,R.style.AlertDialogTheme)
        val view = layoutInflater.inflate(R.layout.more_option,null)

        alert.setView(view)

        val alertDialog = alert.create()
        alertDialog.setCanceledOnTouchOutside(true)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val edit = view.findViewById<LinearLayout>(R.id.edit)
        val logout = view.findViewById<LinearLayout>(R.id.logout)
        val help = view.findViewById<LinearLayout>(R.id.help)


        edit.setOnClickListener {
            alertDialog.dismiss()
            editClicked()
        }


        logout.setOnClickListener {
            alertDialog.dismiss()
            logoutClicked()
        }

        help.setOnClickListener {
            alertDialog.dismiss()
            val intent = Intent(requireContext(),Feedback::class.java)
            startActivity(intent)
        }

        alertDialog.show()
        alertDialog.window!!.setLayout(700,1000)

    }


    private fun editClicked() {

        val bottomSheetDialog = BottomSheetDialog(requireView().context,R.style.BottomSheetTheme)
        val sheetView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_layout,
            view?.findViewById(R.id.bottom_sheet)
        )

        bottomSheetDialog.setContentView(sheetView)
        bottomSheetDialog.show()


        val profile = sheetView.findViewById<LinearLayout>(R.id.ll1)
        val name = sheetView.findViewById<LinearLayout>(R.id.ll2)
        val personalInfo = sheetView.findViewById<LinearLayout>(R.id.ll4)


        profile.setOnClickListener {
            bottomSheetDialog.dismiss()
            changeProfile()
        }

        name.setOnClickListener {
            bottomSheetDialog.dismiss()
            changeName()
        }

        personalInfo.setOnClickListener {
            bottomSheetDialog.dismiss()
            personalInfoClicked()
        }


    }

    private fun personalInfoClicked() {
        val bottomSheetDialog = BottomSheetDialog(requireView().context,R.style.BottomSheetTheme)
        val sheetView = LayoutInflater.from(context).inflate(R.layout.personal_info,
            view?.findViewById(R.id.bottom_sheet)
        )

        bottomSheetDialog.setContentView(sheetView)
        bottomSheetDialog.show()


        val nameEt = sheetView.findViewById<EditText>(R.id.nameEt)
        val ageEt = sheetView.findViewById<EditText>(R.id.ageET)
        val heightEt = sheetView.findViewById<EditText>(R.id.heightEt)
        val weightEt = sheetView.findViewById<EditText>(R.id.weightEt)


                nameEt.setText(binding.nameTV.text)
                ageEt.setText(binding.ageTV.text)
                weightEt.setText(binding.weightTV.text)
                heightEt.setText(binding.heightTV.text)


        val cancel = sheetView.findViewById<Button>(R.id.cancel_button)
        val edit = sheetView.findViewById<Button>(R.id.edit_btn)

        val error = sheetView.findViewById<TextView>(R.id.error)


        cancel.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        nameEt.addTextChangedListener {
            error.text = "Fields can't be empty"
            error.isVisible = false
        }

        edit.setOnClickListener {

            val name = nameEt.text.toString().trim()
            val age = ageEt.text.toString().trim()
            val height = heightEt.text.toString().trim()
            val weight = weightEt.text.toString().trim()

            if(name.isEmpty() || age.isEmpty() || height.isEmpty() || weight.isEmpty()){
                error.isVisible = true
            }else{
                cancel.isClickable = false
                edit.isClickable = false
                binding.msgLl.isVisible = true
                binding.msgTv.isVisible = true
                binding.msgTv.text = "Updating info"
                binding.progressBar.isVisible = true

                bottomSheetDialog.dismiss()
                Handler(Looper.getMainLooper()).postDelayed({
                    val ref = db.collection("Users").document(auth.uid!!)
                    ref.update(mapOf(
                        "name" to name,
                        "age" to age,
                        "height" to height,
                        "weight" to weight
                    ))
                        .addOnSuccessListener { _: Void? ->
                            if(viewModel.isProfileALive) {
                                binding.msgLl.isVisible = false
                                binding.msgTv.isVisible = false
                                binding.progressBar.isVisible = false
                                Snackbar.make(
                                    binding.root,
                                    "Info updated successfully",
                                    Snackbar.LENGTH_SHORT
                                )
                                    .setBackgroundTint(resources.getColor(R.color.colorGreen))
                                    .setTextColor(resources.getColor(R.color.white))
                                    .show()
                            }
                        }
                        .addOnFailureListener {
                            if(viewModel.isProfileALive) {
                                binding.msgLl.isVisible = false
                                binding.msgTv.isVisible = false
                                binding.progressBar.isVisible = false
                                Snackbar.make(
                                    binding.root,
                                    "Failed to update, please try again",
                                    Snackbar.LENGTH_SHORT
                                )
                                    .setBackgroundTint(resources.getColor(R.color.red))
                                    .setTextColor(resources.getColor(R.color.white))
                                    .show()
                            }
                        }
                },3000)

            }
        }


    }










    private fun changeName() {
        val bottomSheetDialog = BottomSheetDialog(requireView().context,R.style.BottomSheetTheme)
        val sheetView = LayoutInflater.from(context).inflate(R.layout.change_name_sheet,
            view?.findViewById(R.id.bottom_sheet)
        )

        bottomSheetDialog.setContentView(sheetView)
        bottomSheetDialog.show()

        val editText = sheetView.findViewById<EditText>(R.id.edit_text)
        val cancel = sheetView.findViewById<Button>(R.id.cancel_button)
        val change = sheetView.findViewById<Button>(R.id.change_btn)
        val error = sheetView.findViewById<TextView>(R.id.error)


        editText.addTextChangedListener {
            error.text = "Name can't be empty"
            error.isVisible = false
        }


        cancel.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        change.setOnClickListener {
            val name = editText.text.toString()
            if(name.isEmpty()){
                error.isVisible = true
            }else{
                bottomSheetDialog.dismiss()
                binding.msgLl.isVisible = true
                binding.msgTv.isVisible = true
                binding.msgTv.text = "Updating name"
                binding.progressBar.isVisible = true

                val ref = db.collection("Users").document(auth.uid!!)

                Handler(Looper.getMainLooper()).postDelayed({
                    ref.update("name",name)
                        .addOnSuccessListener { _: Void? ->
                            if(viewModel.isProfileALive) {
                            binding.msgLl.isVisible = false
                            binding.msgTv.isVisible = false
                            binding.progressBar.isVisible = false

                                Snackbar.make(
                                    binding.root,
                                    "Name updated successfully",
                                    Snackbar.LENGTH_SHORT
                                )
                                    .setBackgroundTint(resources.getColor(R.color.colorGreen))
                                    .setTextColor(resources.getColor(R.color.white))
                                    .show()
                            }

                        }
                        .addOnFailureListener {
                            if(viewModel.isProfileALive) {
                            binding.msgLl.isVisible = false
                            binding.msgTv.isVisible = false
                            binding.progressBar.isVisible = false

                                Snackbar.make(
                                    binding.root,
                                    "Failed to update, please try again!!",
                                    Snackbar.LENGTH_SHORT
                                )
                                    .setBackgroundTint(resources.getColor(R.color.red))
                                    .setTextColor(resources.getColor(R.color.white))
                                    .show()
                            }
                        }
                },3000)


            }
        }
    }

    private fun changeProfile() {

        val bottomSheetDialog = BottomSheetDialog(requireView().context,R.style.BottomSheetTheme)
        val sheetView = LayoutInflater.from(context).inflate(R.layout.change_profile,
            view?.findViewById(R.id.bottom_sheet)
        )

        bottomSheetDialog.setContentView(sheetView)
        bottomSheetDialog.show()

        val gallery = sheetView.findViewById<LinearLayout>(R.id.gallery)
        val camera = sheetView.findViewById<LinearLayout>(R.id.camera)

        gallery.setOnClickListener {
            pickFromGallery(bottomSheetDialog)
        }

        camera.setOnClickListener {
            pickFromCamera(bottomSheetDialog)
        }

    }

    private fun pickFromGallery(bottomSheetDialog: BottomSheetDialog) {
        if(!checkStoragePermission()){
            bottomSheetDialog.dismiss()
            requestStoragePermission()
        }else{
            bottomSheetDialog.dismiss()
            makeGalleryIntent()
        }
    }


    private fun requestStoragePermission() {
        requestPermissions(storagePermissions,STORAGE_REQUEST_CODE)
    }


    private fun pickFromCamera(bottomSheetDialog: BottomSheetDialog) {

        if(!checkCameraPermission())
        {
            bottomSheetDialog.dismiss()
            requestCameraPermission()
        }
        else
        {
            bottomSheetDialog.dismiss()
            makeCameraIntent()
        }


    }

    private fun makeCameraIntent() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "Temp Pic")
        values.put(MediaStore.Images.Media.DESCRIPTION, "Temp Description")

       cameraUri = requireActivity()!!.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)!!

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri)
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE)
    }

    private fun requestCameraPermission() {
        requestPermissions(cameraPermissions, CAMERA_REQUEST_CODE)
    }

    private fun checkCameraPermission():Boolean{
        val result = ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        val result1 = ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        return result && result1
    }


    //for checking the storage permission
    private fun checkStoragePermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }


    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            STORAGE_REQUEST_CODE -> {
                if(grantResults.isNotEmpty()){
                    val writeStorageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    if (writeStorageAccepted) {
                        makeGalleryIntent()
                    }else{
                            Snackbar.make(
                                binding.root,
                                "Please enable permissions",
                                Snackbar.LENGTH_SHORT
                            )
                                .setBackgroundTint(resources.getColor(R.color.colorGreen))
                                .setTextColor(resources.getColor(R.color.white))
                                .show()
                    }
                }
            }

            CAMERA_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()) {
                    val cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    val writeStorageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED
                    if (cameraAccepted && writeStorageAccepted) {
                        makeCameraIntent()
                    }else{
                        Snackbar.make(
                            binding.root,
                            "Please enable permissions",
                            Snackbar.LENGTH_SHORT
                        )
                            .setBackgroundTint(resources.getColor(R.color.colorGreen))
                            .setTextColor(resources.getColor(R.color.white))
                            .show()
                    }
                }
            }

        }


    }

    private fun makeGalleryIntent() {
        val gIntent = Intent(Intent.ACTION_PICK)
        gIntent.setType("image/*")
        startActivityForResult(gIntent,IMAGE_PICK_GALLERY_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == RESULT_OK){
            if(requestCode == IMAGE_PICK_GALLERY_CODE && data!=null){
                val imageUri = data.data
                uploadProfilePhoto(imageUri)
            }
            if (requestCode==IMAGE_PICK_CAMERA_CODE){
                uploadProfilePhoto(cameraUri)
            }
        super.onActivityResult(requestCode, resultCode, data)
        }

    }

    private fun uploadProfilePhoto(imageUri: Uri?) {

        binding.msgLl.isVisible = true
        binding.msgTv.isVisible = true
        binding.msgTv.text = "Uploading profile"
        binding.progTv.isVisible = true
        binding.progressBar.isVisible = true

        val filePathAndName = storagePath+"_"+auth.uid!!
        val sRef = storageRef.child(filePathAndName)
        sRef.putFile(imageUri!!)
            .addOnSuccessListener {
              //image has been uploaded now save the image link in the firestore
                val uriTask = it.storage.downloadUrl
                while(!uriTask.isSuccessful){}
                val downloadUri = uriTask.result!!

                if(uriTask.isSuccessful){
                    val ref = db.collection("Users").document(auth.uid!!)
                        ref.update("profile",downloadUri.toString())
                            .addOnCompleteListener {
                                if(viewModel.isProfileALive){
                                    binding.msgLl.isVisible = false
                                    binding.msgTv.isVisible = false
                                    binding.progTv.isVisible = false
                                    binding.progressBar.isVisible = false
                                    Snackbar.make(binding.root,"Profile updated successfully", Snackbar.LENGTH_SHORT)
                                        .setBackgroundTint(resources.getColor(R.color.colorGreen))
                                        .setTextColor(resources.getColor(R.color.white))
                                        .show()
                                }

                            }
                            .addOnFailureListener {
                                if(viewModel.isProfileALive){
                                    binding.msgLl.isVisible = false
                                    binding.msgTv.isVisible = false
                                    binding.progTv.isVisible = false
                                    binding.progressBar.isVisible = false
                                    Snackbar.make(binding.root,"Failed to upload, please try again!!", Snackbar.LENGTH_SHORT)
                                        .setBackgroundTint(resources.getColor(R.color.red))
                                        .setTextColor(resources.getColor(R.color.white))
                                        .show()
                                }

                            }
                }

            }
            .addOnFailureListener {
                if(viewModel.isProfileALive){
                    Snackbar.make(binding.root,"Failed to upload, please try again!!", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(resources.getColor(R.color.red))
                        .setTextColor(resources.getColor(R.color.white))
                        .show()
                }

            }
            .addOnProgressListener {
                if(viewModel.isProfileALive) {
                    val progressCounter = (100.00 * it.bytesTransferred) / it.totalByteCount
                    binding.progTv.text = progressCounter.toUInt().toString() + " %"
                }
            }


    }


    private fun logoutClicked() {
        val alert = AlertDialog.Builder(activity,R.style.AlertDialogTheme)
        val view = layoutInflater.inflate(R.layout.logout_dialog,null)

        alert.setView(view)

        logoutAlertDialog = alert.create()
        logoutAlertDialog.setCanceledOnTouchOutside(false)
        logoutAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val cancel = view.findViewById<TextView>(R.id.cancel_button)
        val logout = view.findViewById<TextView>(R.id.logout_btn)

        cancel.setOnClickListener {
            logoutAlertDialog.dismiss()
        }

        logoutAlertDialog.show()
        logoutAlertDialog.window!!.setLayout(700,700)

        logout.setOnClickListener {
            logoutAlertDialog.dismiss()
            binding.msgLl.isVisible = true
            binding.msgTv.isVisible = true
            binding.msgTv.text = "Logging out"
            binding.progressBar.isVisible = true

            Handler(Looper.getMainLooper()).postDelayed({
                auth.signOut()
                val user  = auth.currentUser
                if(user==null){
                    startActivity(Intent(requireContext(),SplashActivity::class.java))
                    requireActivity().finish()
                }
            },3000)
        }
    }




    override fun onStart() {
        super.onStart()
        viewModel.isProfileALive = true
    }

    override fun onResume() {
        super.onResume()
        viewModel.isProfileALive = true
    }

    override fun onPause() {
        super.onPause()
        viewModel.isProfileALive = false
    }

    override fun onStop() {
        super.onStop()
        viewModel.isProfileALive = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.isProfileALive = false

    }



    override fun onDestroy() {
        super.onDestroy()
        viewModel.isProfileALive = false
    }
}

