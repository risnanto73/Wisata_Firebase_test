package com.tiorisnanto.tiketwisata.coban_putri_malang

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tiorisnanto.tiketwisata.R
import com.tiorisnanto.tiketwisata.adapter.ListAdapter
import com.tiorisnanto.tiketwisata.databinding.ActivityDataCobanPutriMalangBinding
import com.tiorisnanto.tiketwisata.model.DataWisata
import java.util.*
import kotlin.collections.ArrayList

class DataCobanPutriMalangActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDataCobanPutriMalangBinding

    private lateinit var dataList : ArrayList<DataWisata>
    private lateinit var recView : RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataCobanPutriMalangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recView = binding.recWisata
        recView.layoutManager = LinearLayoutManager(this)
        recView.setHasFixedSize(true)

        dataList = arrayListOf<DataWisata>()
        getData()


    }

    private fun getData() {
        val ref = FirebaseDatabase.getInstance().getReference("Data_Wisata_Coban_Putri_Malang")
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children){
                    val data = dataSnapshot.getValue(DataWisata::class.java)
                    dataList.add(data!!)
                }
                val adapter = ListAdapter(dataList)
                recView.adapter = adapter
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }


        })
    }

    fun fabClicked(view: View) {
        startActivity(Intent(this,DetailCobanPutriMalangActivity::class.java))
    }
}