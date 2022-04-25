package com.tiorisnanto.tiketwisata.model

class DataWisata {

    var id : String? = null
    var tahun: String? = null
    var bulan: String? = null
    var hari: String? = null
    var jam: String? = null
    var hitungDewasa: String? = null
    var hitungAnak: String? = null
    var jumlahDewasa: String? = null
    var jumlahAnak: String? = null
    var totalHargaDewasa: String? = null
    var totalHargaAnak: String? = null
    var jumlah: String? = null
    var totalHarga: String? = null
    var imgQrCode : String? = null

    constructor() {}
    constructor(id: String,tahun: String, bulan: String, hari: String, jam: String, hitungDewasa: String, hitungAnak: String, jumlahDewasa: String, jumlahAnak: String, totalHargaDewasa: String, totalHargaAnak: String, jumlah: String, totalHarga: String, imgQrCode: String) {
        this.id = id
        this.tahun = tahun
        this.bulan = bulan
        this.hari = hari
        this.jam = jam
        this.hitungDewasa = hitungDewasa
        this.hitungAnak = hitungAnak
        this.jumlahDewasa = jumlahDewasa
        this.jumlahAnak = jumlahAnak
        this.totalHargaDewasa = totalHargaDewasa
        this.totalHargaAnak = totalHargaAnak
        this.jumlah = jumlah
        this.totalHarga = totalHarga
        this.imgQrCode = imgQrCode
    }
}