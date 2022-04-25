package com.tiorisnanto.tiketwisata.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tiorisnanto.tiketwisata.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}