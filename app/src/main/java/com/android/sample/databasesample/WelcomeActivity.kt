package com.android.sample.databasesample

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.sample.databasesample.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("APP", "Welcome")
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setContentView(R.layout.activity_welcome)

        Handler(Looper.getMainLooper()).postDelayed({
            goToDisplay()
//            goToMain()
        }, 3000)
    }

    fun goToDisplay(){
        val displayIntent  = Intent(this, DisplayActivity::class.java)
        startActivity(displayIntent)
        finish()
    }

    fun goToMain(){
        val mainIntent  = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
        finish()
    }

}