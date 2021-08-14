package com.binishmatheww.parenting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.binishmatheww.parenting.fragments.SplashScreenFragment

class LauncherActivity : AppCompatActivity() {

    private var onBackPressed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        if(savedInstanceState == null){
            replaceFragment(SplashScreenFragment())
        }

    }


    override fun onBackPressed() {

        if(supportFragmentManager.backStackEntryCount > 1){

            super.onBackPressed()

        }
        else{

            if(onBackPressed){

                finish()

            }
            else{

                onBackPressed = true
                Toast.makeText(this,"Press again to exit",Toast.LENGTH_SHORT).show()

                Handler(Looper.getMainLooper()).postDelayed({
                    onBackPressed = false
                },1000)

            }

        }

    }

}