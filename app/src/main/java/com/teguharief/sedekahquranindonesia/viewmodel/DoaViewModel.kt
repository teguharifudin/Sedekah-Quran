package com.teguharief.sedekahquranindonesia.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teguharief.sedekahquranindonesia.model.main.ModelDoa
import com.teguharief.sedekahquranindonesia.networking.ApiInterface
import com.teguharief.sedekahquranindonesia.networking.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class DoaViewModel : ViewModel() {
    private val modelDoaMutableLiveData = MutableLiveData<ArrayList<ModelDoa>>()

    fun setDoa() {
        val apiService: ApiInterface = ApiService.getQuran()
        val call = apiService.getListDoa()

        call.enqueue(object : Callback<ArrayList<ModelDoa>> {
            override fun onResponse(call: Call<ArrayList<ModelDoa>>, response: Response<ArrayList<ModelDoa>>) {
                if (!response.isSuccessful) {
                    Log.e("response", response.toString())
                } else if (response.body() != null) {
                    val items: ArrayList<ModelDoa> = ArrayList(response.body())
                    modelDoaMutableLiveData.postValue(items)
                }
            }

            override fun onFailure(call: Call<ArrayList<ModelDoa>>, t: Throwable) {
                Log.e("failure", t.toString())
            }
        })
    }

    fun getDoa(): LiveData<ArrayList<ModelDoa>> = modelDoaMutableLiveData
}