package com.example.socialmediaintegration

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

@Suppress("DEPRECATION")
@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_splash)



        val intent = Intent(this@SplashScreen,MainActivity::class.java)
        Handler().postDelayed({
            startActivity(intent)
            finish() // Finish the current activity to prevent going back to the splash screen
        }, 1000)
    }
}