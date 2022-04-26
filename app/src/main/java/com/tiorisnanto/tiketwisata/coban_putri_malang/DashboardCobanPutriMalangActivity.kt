package com.tiorisnanto.tiketwisata.coban_putri_malang

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth.getInstance
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.tiorisnanto.tiketwisata.R
import com.tiorisnanto.tiketwisata.databinding.ActivityDashboardCobanPutriMalangBinding
import com.tiorisnanto.tiketwisata.model.DataWisata
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

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

    override fun onResume() {
        super.onResume()
        totalPendapatan()
        totalPengunjungHariIni()
        totalPengunjungDewasaHariIni()
        totalPengunjungAnakHariIni()
    }

    override fun onRestart() {
        super.onRestart()
        totalPendapatan()
        totalPengunjungHariIni()
        totalPengunjungDewasaHariIni()
        totalPengunjungAnakHariIni()
    }

    private fun totalPengunjungAnakHariIni() {
        val date = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter
                .ofPattern("dd")
                .withZone(ZoneOffset.systemDefault())
                .format(Instant.now())
        } else {
            val date = SimpleDateFormat("dd")
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
                    if (dataWisata!!.hari == date) {
                        total = dataWisata?.jumlahAnak.toString().toInt() + total
                    }
                }
                binding.tvTotalPengunjungAnakHariIni.text = total.toString() + " Anak"
            }

        })

    }

    private fun totalPengunjungDewasaHariIni() {
        val date = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter
                .ofPattern("dd")
                .withZone(ZoneOffset.systemDefault())
                .format(Instant.now())
        } else {
            val date = SimpleDateFormat("dd")
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
                for (data in p0.children) {
                    val dataWisata = data.getValue(DataWisata::class.java)
                    if (dataWisata?.hari == date) {
                        total = dataWisata?.jumlahDewasa.toString().toInt() + total
                    }
                }
                binding.tvTotalPengunjungDewasaHariIni.text = total.toString() + " Orang"
            }

        })

    }

    private fun totalPengunjungHariIni() {
        val date = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter
                .ofPattern("dd")
                .withZone(ZoneOffset.systemDefault())
                .format(Instant.now())
        } else {
            val date = SimpleDateFormat("dd")
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
                for (data in p0.children) {
                    val dataWisata = data.getValue(DataWisata::class.java)
                    if (dataWisata!!.hari == date) {
                        total = dataWisata.jumlahAnak.toString().toInt() + dataWisata.jumlahDewasa.toString().toInt() + total
                    }
                }
                binding.tvTotalPengunjungHariIni.text = total.toString() + " Pengunjung"
            }

        })
    }

    private fun totalPendapatan() {

        val date = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter
                .ofPattern("dd")
                .withZone(ZoneOffset.systemDefault())
                .format(Instant.now())
        } else {
            val date = SimpleDateFormat("dd")
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
                for (data in p0.children) {
                    val dataWisata = data.getValue(DataWisata::class.java)
                    if (dataWisata != null) {
                        if (dataWisata.hari == date) {
                            total = total.toInt() + dataWisata.totalHarga.toString().toInt()
                        }
                    }
                }
                binding.tvTotalPendapatanHariIni.text = "Rp. $total"
            }

        })

    }

}