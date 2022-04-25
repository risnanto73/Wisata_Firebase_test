package com.tiorisnanto.tiketwisata.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.tiorisnanto.tiketwisata.R
import com.tiorisnanto.tiketwisata.model.DataWisata
import kotlinx.android.synthetic.main.row_coban.view.*
import java.io.ByteArrayOutputStream

class ListAdapter(private val dataWisata: List<DataWisata>) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private lateinit var mListener : onItemClickListener

    interface onItemClickListener {
        fun onItemClick(dataWisata: DataWisata)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_coban, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataWisata.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(dataWisata[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dataWisata: DataWisata) {
            with(itemView) {
                row_year.text = dataWisata.tahun
                row_month.text = dataWisata.bulan
                row_day.text = dataWisata.hari
                row_hour.text = dataWisata.jam

                val date = "Tiket Valid Pada" + "${dataWisata.tahun}-${dataWisata.bulan}-${dataWisata.hari} dan anda adalah pengunjung ke ${dataWisata.id}"
                val imgCoder = itemView.findViewById<ImageView>(R.id.row_img)
                val qrCodeWriter = QRCodeWriter()
                val bitMatrix = qrCodeWriter.encode(date, BarcodeFormat.QR_CODE, 512, 512)
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

                imgCoder.setImageBitmap(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size))

                val qrCode = byteArray

                Glide.with(context)
                    .load(qrCode)
                    .into(imgCoder)
            }
        }
    }
}