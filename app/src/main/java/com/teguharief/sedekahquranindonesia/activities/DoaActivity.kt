package com.teguharief.sedekahquranindonesia.activities

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import androidx.recyclerview.widget.LinearLayoutManager
import com.teguharief.sedekahquranindonesia.R
import com.teguharief.sedekahquranindonesia.adapter.DoaAdapter
import com.teguharief.sedekahquranindonesia.model.main.ModelDoa
import com.teguharief.sedekahquranindonesia.viewmodel.DoaViewModel
import kotlinx.android.synthetic.main.activity_doa.*
import java.io.IOException
import java.util.*

class DoaActivity : AppCompatActivity() {
    lateinit var doaAdapter: DoaAdapter
    lateinit var progressDialog: ProgressDialog
    lateinit var doaViewModel: DoaViewModel
    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doa)

        setInitLayout()
        setViewModel()
    }

    @SuppressLint("RestrictedApi")
    private fun setInitLayout() {
        toolbar.setTitle(null)
        setSupportActionBar(toolbar)
        assert(supportActionBar != null)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        handler = Handler()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Mohon Tunggu")
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Sedang menampilkan data...")

        doaAdapter = DoaAdapter()
        rvDoa.setHasFixedSize(true)
        rvDoa.layoutManager = LinearLayoutManager(this)
        rvDoa.adapter = doaAdapter
    }

    private fun setViewModel() {
        progressDialog.setIcon(R.mipmap.ic_info)
        progressDialog.show()
        doaViewModel = ViewModelProvider(this, NewInstanceFactory()).get(DoaViewModel::class.java)
        doaViewModel.setDoa()
        doaViewModel.getDoa()
            .observe(this, { modelDoa: ArrayList<ModelDoa> ->
                if (modelDoa.size != 0) {
                    doaAdapter.setAdapter(modelDoa)
                    progressDialog.dismiss()
                } else {
                    Toast.makeText(this, "Data Tidak Ditemukan!", Toast.LENGTH_SHORT).show()
                    progressDialog.dismiss()
                }
                progressDialog.dismiss()
            })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}