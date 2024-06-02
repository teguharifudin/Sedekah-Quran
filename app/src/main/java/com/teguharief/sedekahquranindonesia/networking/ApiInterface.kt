package com.teguharief.sedekahquranindonesia.networking

import retrofit2.http.GET
import com.teguharief.sedekahquranindonesia.model.main.ModelSurah
import com.teguharief.sedekahquranindonesia.model.main.ModelAyat
import com.teguharief.sedekahquranindonesia.model.main.ModelDoa
import retrofit2.Call
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.ArrayList

interface ApiInterface {
    @GET("quran/data.json")
    fun getListSurah(): Call<ArrayList<ModelSurah>>

    @GET("quran/{nomor}.json")
    fun getDetailSurah(
        @Path("nomor") nomor: String
    ): Call<ArrayList<ModelAyat>>

    @GET("quran/doa.json")
    fun getListDoa(): Call<ArrayList<ModelDoa>>
}