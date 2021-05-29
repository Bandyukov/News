package com.example.qnews

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.acrivity_spalsh.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acrivity_spalsh)

        imageVIewNewsIcon.alpha = 0f
        imageVIewNewsIcon.animate().setDuration(2000L).alpha(1f).withEndAction {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }



    }
}
