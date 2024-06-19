package com.bangkit2024.tourid.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bangkit2024.tourid.databinding.ActivitySplashScreenActivtyBinding
import com.bangkit2024.tourid.ui.slider.SliderActivity

class SplashScreenActivty : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenActivtyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySplashScreenActivtyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStarted.setOnClickListener {
            val intent = Intent(this, SliderActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}
