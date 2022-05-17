package com.example.deasa12

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.deasa12.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.*

class SplashScreenActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GlobalScope.launch(Dispatchers.Default) {
            changeSizeText()
        }
    }

    private suspend fun changeSizeText() {
        var i = 15
        while (i <= 48) {
            delay(70)
            withContext(Dispatchers.Main) {
                binding.tvTeatle.textSize = i.toFloat()
            }
            if (i == 48) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            i++
        }
    }
}