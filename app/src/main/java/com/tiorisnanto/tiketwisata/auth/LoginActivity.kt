package com.tiorisnanto.tiketwisata.auth

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.tiorisnanto.tiketwisata.MainActivity
import com.tiorisnanto.tiketwisata.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        //Konfigurasi Progess Dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Loggin In...")
        progressDialog.setCanceledOnTouchOutside(false)

        auth = FirebaseAuth.getInstance()
        checkUser()

        binding.btnLogin.setOnClickListener {
            email = binding.edtEmail.text.toString().trim()
            password = binding.edtPassword.text.toString().trim()

            //Validasi Data
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.edtEmail.setError("Email Salah")
            } else if (TextUtils.isEmpty(password)) {
                //Tidak masukan Password
                binding.edtPassword.error = "Tolong Masukan Password"
            } else {
                firebaseLogin()
            }
        }
    }

    private fun firebaseLogin() {
        progressDialog.show()
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //Login berhasil
                progressDialog.dismiss()
                //get User ingfo
                val firebaseUser = auth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Welcome back $email", Toast.LENGTH_SHORT).show()

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                //Login Gagal
                progressDialog.dismiss()
                Toast.makeText(this, "Login Gagal ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUser() {
        val firebaseUser = auth.currentUser
        if (firebaseUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}