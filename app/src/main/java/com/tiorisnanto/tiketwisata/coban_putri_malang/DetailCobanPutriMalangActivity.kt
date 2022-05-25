package com.tiorisnanto.tiketwisata.coban_putri_malang

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dantsu.escposprinter.EscPosCharsetEncoding
import com.dantsu.escposprinter.EscPosPrinter
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.tiorisnanto.tiketwisata.databinding.ActivityDetailCobanPutriMalangBinding
import com.tiorisnanto.tiketwisata.model.DataWisata
import kotlinx.android.synthetic.main.activity_detail_coban_putri_malang.*
import java.io.*
import java.util.*


class DetailCobanPutriMalangActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailCobanPutriMalangBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCobanPutriMalangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            val jumlahDewasa = binding.txtCountDewasa.text.toString()
            if (jumlahDewasa == "0") {
                binding.txtCountDewasa.error = "Jumlah Dewasa Tidak Boleh Kosong"
            } else {
                saveData()
            }

        }
        increaseInteger()
        decreaseInteger()
        setValuesToViews()
    }

    private fun setValuesToViews() {

        if (intent.hasExtra("id")) {
            binding.txtHitungDewasa.text = intent.getStringExtra("hitungDewasa")
            binding.txtHitungAnak.text = intent.getStringExtra("hitungAnak")
            binding.txtCountDewasa.text = intent.getStringExtra("jumlahDewasa")
            binding.txtCountAnak.text = intent.getStringExtra("jumlahAnak")
            binding.txtHargaAnak.text = intent.getStringExtra("totalHargaAnak")
            binding.txtHargaDewasa.text = intent.getStringExtra("totalHargaDewasa")
            binding.txtHargaTotal.text = intent.getStringExtra("totalHarga")
            binding.txtCountTotal.text = intent.getStringExtra("jumlah")


            binding.btnMinAnak.visibility = View.GONE
            binding.btnMinDewasa.visibility = View.GONE
            binding.btnPlusAnak.visibility = View.GONE
            binding.btnPlusDewasa.visibility = View.GONE
            binding.btnSave.visibility = View.GONE
            binding.btnPrint.visibility = View.VISIBLE

            binding.btnPrint.setOnClickListener {
                doPrint()
            }
        }


    }

    private fun doPrint() {

    }

    private fun saveData() {
        val ref = Firebase.database.reference
        val id = ref.push().key
        val tahun = binding.year.text.toString()
        val bulan = binding.month.text.toString()
        val hari = binding.day.text.toString()
        val time = binding.time.text.toString()
        val hitungDewasa = binding.txtHitungDewasa.text.toString()
        val hitungAnak = binding.txtHitungAnak.text.toString()
        val jumlahDewasa = binding.txtCountDewasa.text.toString()
        val jumlahAnak = binding.txtCountAnak.text.toString()
        val totalHargaDewasa = binding.txtHargaDewasa.text.toString()
        val totalHargaAnak = binding.txtHargaAnak.text.toString()
        val jumlah = binding.txtCountTotal.text.toString()
        val totalHarga = binding.txtHargaTotal.text.toString()

        val idPengunjung = intent.getStringExtra("id")


        val text1 = "Tiket Coban Putri Malang Valid pada Tanggal "
        val year = tahun + "-" + bulan + "-" + hari + " jam " + time
        val text2 = " dan anda pengujung ke " + idPengunjung

        val combine = text1 + year + text2
        val qrCodeWriter = QRCodeWriter()
        val bitMatrix = qrCodeWriter.encode(combine, BarcodeFormat.QR_CODE, 512, 512)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bmp.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
            }
        }

        val stream = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()

        binding.imgQrCode.setImageBitmap(
            BitmapFactory.decodeByteArray(
                byteArray,
                0,
                byteArray.size
            )
        )

        val qrCode = byteArray.toString()

        val data = DataWisata(
            id!!,
            tahun,
            bulan,
            hari,
            time,
            hitungDewasa,
            hitungAnak,
            jumlahDewasa,
            jumlahAnak,
            totalHargaDewasa,
            totalHargaAnak,
            jumlah,
            totalHarga,
            qrCode,

            )

        val database = Firebase.database
        val myRef = database.getReference("Data_Wisata_Coban_Putri_Malang")
        myRef.push().setValue(data)

        binding.btnPrint.visibility = android.view.View.GONE
        finish()
    }

    private fun decreaseInteger() {
        binding.btnMinDewasa.setOnClickListener {
            val hargaDewasa = 10000
            val hargaAnak = 5000
            if (binding.txtHitungDewasa.text.toString().toInt() > 0) {
                binding.txtHitungDewasa.text =
                    (binding.txtHitungDewasa.text.toString().toInt() - 1).toString()
                binding
                    .txtCountDewasa.text = binding.txtHitungDewasa.text.toString()
                binding.txtHargaDewasa.text =
                    (binding.txtHitungDewasa.text.toString().toInt() * hargaDewasa).toString()
                binding.txtCountTotal.text =
                    (binding.txtHitungDewasa.text.toString()
                        .toInt() + binding.txtHitungAnak.text.toString()
                        .toInt()).toString()
                binding.txtHargaTotal.text =
                    (binding.txtHitungDewasa.text.toString()
                        .toInt() * hargaDewasa + binding.txtHitungAnak.text.toString()
                        .toInt() * hargaAnak).toString()
            }
        }

        binding.btnMinAnak.setOnClickListener {
            val hargaAnak = 5000
            val hargaDewasa = 10000
            if (binding.txtHitungAnak.text.toString().toInt() > 0) {
                binding.txtHitungAnak.text =
                    (binding.txtHitungAnak.text.toString().toInt() - 1).toString()
                binding.txtCountAnak.text = binding.txtHitungAnak.text.toString()
                binding.txtHargaAnak.text =
                    (binding.txtHitungAnak.text.toString().toInt() * hargaAnak).toString()
                binding.txtCountTotal.text =
                    (binding.txtHitungDewasa.text.toString()
                        .toInt() + binding.txtHitungAnak.text.toString()
                        .toInt()).toString()
                binding.txtHargaTotal.text =
                    (binding.txtHitungDewasa.text.toString()
                        .toInt() * hargaDewasa + binding.txtHitungAnak.text.toString()
                        .toInt() * hargaAnak).toString()
            }
        }

    }

    private fun increaseInteger() {
        binding.btnPlusDewasa.setOnClickListener {
            val hargaDewasa = 10000
            val hargaAnak = 5000
            if (binding.txtHitungDewasa.text.toString().toInt() >= 0) {
                binding.txtHitungDewasa.text =
                    (binding.txtHitungDewasa.text.toString().toInt() + 1).toString()
                binding
                    .txtCountDewasa.text = binding.txtHitungDewasa.text.toString()
                binding.txtHargaDewasa.text =
                    (binding.txtHitungDewasa.text.toString().toInt() * hargaDewasa).toString()
                binding.txtCountTotal.text =
                    (binding.txtHitungDewasa.text.toString()
                        .toInt() + binding.txtHitungAnak.text.toString()
                        .toInt()).toString()
                binding.txtHargaTotal.text =
                    (binding.txtHitungDewasa.text.toString()
                        .toInt() * hargaDewasa + binding.txtHitungAnak.text.toString()
                        .toInt() * hargaAnak).toString()
            }
        }

        binding.btnPlusAnak.setOnClickListener {
            val hargaAnak = 5000
            val hargaDewasa = 10000
            if (binding.txtHitungAnak.text.toString().toInt() >= 0) {
                binding.txtHitungAnak.text =
                    (binding.txtHitungAnak.text.toString().toInt() + 1).toString()
                binding.txtCountAnak.text = binding.txtHitungAnak.text.toString()
                binding.txtHargaAnak.text =
                    (binding.txtHitungAnak.text.toString().toInt() * hargaAnak).toString()
                binding.txtCountTotal.text =
                    (binding.txtHitungDewasa.text.toString()
                        .toInt() + binding.txtHitungAnak.text.toString()
                        .toInt()).toString()
                binding.txtHargaTotal.text =
                    (binding.txtHitungDewasa.text.toString()
                        .toInt() * hargaDewasa + binding.txtHitungAnak.text.toString()
                        .toInt() * hargaAnak).toString()
            }
        }

    }

}