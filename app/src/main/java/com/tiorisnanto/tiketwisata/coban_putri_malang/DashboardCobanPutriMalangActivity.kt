package com.tiorisnanto.tiketwisata.coban_putri_malang

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tiorisnanto.tiketwisata.R
import com.tiorisnanto.tiketwisata.databinding.ActivityDashboardCobanPutriMalangBinding

class DashboardCobanPutriMalangActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardCobanPutriMalangBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardCobanPutriMalangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGoData.setOnClickListener {
            startActivity(Intent(this, DataCobanPutriMalangActivity::class.java))
        }

    }
}