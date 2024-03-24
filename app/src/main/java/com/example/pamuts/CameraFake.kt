package com.example.pamuts

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pamuts.databinding.CameraFakeBinding

class CameraFake : AppCompatActivity() {

    private lateinit var binding: CameraFakeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CameraFakeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cameraButton.setOnClickListener {
            val intent = Intent(this@CameraFake, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}