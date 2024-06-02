package com.teguharief.sedekahquranindonesia.model.main

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ModelDoa : Serializable {
    @SerializedName("judul")
    lateinit var judul: String

    @SerializedName("arab")
    lateinit var arab: String

    @SerializedName("arti")
    lateinit var arti: String
}