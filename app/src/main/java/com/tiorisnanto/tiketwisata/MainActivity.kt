package com.tiorisnanto.tiketwisata

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.tiorisnanto.tiketwisata.coban_putri_malang.DashboardCobanPutriMalangActivity
import com.tiorisnanto.tiketwisata.databinding.ActivityMainBinding
import com.tiorisnanto.tiketwisata.model.DataWisata
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

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

    override fun onResume() {
        super.onResume()
        getDataMonthSatu()
        getDataMonthPengunjungSatu()
        getDataMonthPendapatanSatu()
    }

    private fun getDataMonthPendapatanSatu() {
        val date = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter
                .ofPattern("MM")
                .withZone(ZoneOffset.systemDefault())
                .format(Instant.now())
        } else {
            val date = SimpleDateFormat("MM")
            date.format(Date())
        }
        val ref = FirebaseDatabase.getInstance().getReference("Data_Wisata_Coban_Putri_Malang")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                //get total pendapatan per bulan
                var totalPendapatan = 0
                for (data in p0.children) {
                    val dataWisata = data.getValue(DataWisata::class.java)
                    if (dataWisata?.bulan == date) {
                        totalPendapatan += dataWisata?.totalHarga!!.toInt()
                    }

                    binding.tvTotalPendapatan.text = "Rp " + totalPendapatan.toString()
                }
            }
        })

    }

    private fun getDataMonthPengunjungSatu() {
        val date = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter
                .ofPattern("MM")
                .withZone(ZoneOffset.systemDefault())
                .format(Instant.now())
        } else {
            val date = SimpleDateFormat("MM")
            date.format(Date())
        }
        val ref = FirebaseDatabase.getInstance().getReference("Data_Wisata_Coban_Putri_Malang")
        ref.addValueEventListener(object :
            com.google.firebase.database.ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                //get total pengunjung per bulan
                var totalPengunjung = 0
                for (data in p0.children) {
                    val dataWisata = data.getValue(DataWisata::class.java)
                    if (dataWisata?.bulan == date) {
                        totalPengunjung += dataWisata?.jumlah!!.toInt()
                    }

                    binding.tvTotalPengunjung.text = totalPengunjung.toString() + " Pengunjung"
                }
            }

        })
    }

    private fun getDataMonthSatu() {
        val date = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter
                .ofPattern("MM")
                .withZone(ZoneOffset.systemDefault())
                .format(Instant.now())
        } else {
            val date = SimpleDateFormat("MM")
            date.format(Date())
        }
        val ref = FirebaseDatabase.getInstance().getReference("Data_Wisata_Coban_Putri_Malang")
        ref.addValueEventListener(object :
            com.google.firebase.database.ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: com.google.firebase.database.DataSnapshot) {
                var total = 0
                for (dataSnapshot in p0.children) {
                    val dataWisata = dataSnapshot.getValue(DataWisata::class.java)
                    if (dataWisata!!.bulan == date) {
                        total = dataWisata.bulan!!.toInt()
                    }
                }
                val month = when (date) {
                    "01" -> "Januari"
                    "02" -> "Februari"
                    "03" -> "Maret"
                    "04" -> "April"
                    "05" -> "Mei"
                    "06" -> "Juni"
                    "07" -> "Juli"
                    "08" -> "Agustus"
                    "09" -> "September"
                    "10" -> "Oktober"
                    "11" -> "November"
                    "12" -> "Desember"
                    else -> "-"
                }
                binding.tvBulan.text = "$month"
            }
        })

    }

}