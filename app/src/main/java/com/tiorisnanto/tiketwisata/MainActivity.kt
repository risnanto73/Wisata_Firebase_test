package com.tiorisnanto.tiketwisata

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.tiorisnanto.tiketwisata.coban_putri_malang.DashboardCobanPutriMalangActivity
import com.tiorisnanto.tiketwisata.databinding.ActivityMainBinding
import com.tiorisnanto.tiketwisata.model.DataWisata
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCobanPutriMalang.setOnClickListener {
            startActivity(Intent(this, DashboardCobanPutriMalangActivity::class.java))
        }
    }

}