package com.aman.ache

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.aman.ache.dashBoardActivity.DashBoardActivity
import com.aman.ache.databinding.ActivitySplashBinding
import com.aman.ache.mainActivity.MainActivity
import com.aman.ache.onBoarding.OnBoardingActivity
import com.google.firebase.auth.FirebaseAuth


/*  This will be the launcher Activity
    activity created to check whether user is logged in or not. plus, he/she is opening app for the first time or not
*/
private const val SPLASH_TIMER=3000L  //one second for now.
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var sharedPreferences:SharedPreferences
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = ActivitySplashBinding.inflate(layoutInflater)
        binding = view
        setContentView(view.root)

        //initializing firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        //getting sharedPreference if any available else def true value
        sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE)
        val isFirstTime = sharedPreferences.getBoolean("sharedPreferences",true)


        //handler is used to delay the execution of the code inside it by 1 seconds(for now)
        Handler(Looper.getMainLooper()).postDelayed({

            val intent: Intent
           if(isFirstTime){
                //right now if user is opening app for the first time then we
                //  update the shared preferences by false as user opened the app
                      val editor:SharedPreferences.Editor = sharedPreferences.edit()
                      editor.putBoolean("sharedPreferences",false)
                      editor.apply()

                //  and he/she will be send to the MainActivity
              intent = Intent(this, OnBoardingActivity::class.java)
                startActivity(intent)

                //and remove the splash activity from back stack
                finish()
           }
            //if the user already have opened the app for the first time then.
            else{
                //getting current user and checking whether he or she is logged in or not
                val user = firebaseAuth.currentUser
                //if user have signed in earlier send him/her to Dashboard Activity
                if(user!=null){
                    intent = Intent(this, DashBoardActivity::class.java)
                    startActivity(intent)

                    //and remove the splash activity from back stack
                    finish()
                }else{
                    //if not signed in send him/her to main Activity
                    intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                    //and remove the splash activity from back stack
                    finish()
                }
            }
        }, SPLASH_TIMER)

    }
}