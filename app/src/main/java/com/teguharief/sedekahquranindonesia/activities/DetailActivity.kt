package com.teguharief.sedekahquranindonesia.activities

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.media.AudioManager
import android.media.MediaPlayer
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
import com.teguharief.sedekahquranindonesia.adapter.DetailAdapter
import com.teguharief.sedekahquranindonesia.model.main.ModelAyat
import com.teguharief.sedekahquranindonesia.model.main.ModelSurah
import com.teguharief.sedekahquranindonesia.viewmodel.SurahViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import java.io.IOException
import java.util.*

class DetailActivity : AppCompatActivity() {
    lateinit var strNomor: String
    lateinit var strNama: String
    lateinit var strArti: String
    lateinit var strType: String
    lateinit var strAyat: String
    lateinit var strKeterangan: String
    lateinit var strAudio: String
    lateinit var modelSurah: ModelSurah
    lateinit var detailAdapter: DetailAdapter
    lateinit var progressDialog: ProgressDialog
    lateinit var surahViewModel: SurahViewModel
    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

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

        modelSurah = intent.getSerializableExtra(DETAIL_SURAH) as ModelSurah
        if (modelSurah != null) {
            strNomor = modelSurah.nomor
            strNama = modelSurah.nama
            strArti = modelSurah.arti
            strType = modelSurah.type
            strAyat = modelSurah.ayat
            strAudio = modelSurah.audio
            strKeterangan = modelSurah.keterangan

            var strBismillah = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ"
            tvBismillah.text = strBismillah
            tvBismillah.visibility = View.GONE

            if (modelSurah.nomor == "1" || modelSurah.nomor == "9") {

            }else{
                tvBismillah.visibility = View.VISIBLE
            }

            fabStop.visibility = View.GONE
            fabPlay.visibility = View.VISIBLE

            //Set text
            tvHeader.text = strNama
            tvTitle.text = strNama
            tvSubTitle.text = strArti
            tvInfo.text = "$strType - $strAyat Ayat "

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) tvKet.text =
                Html.fromHtml(strKeterangan, Html.FROM_HTML_MODE_COMPACT) else {
                tvKet.text = Html.fromHtml(strKeterangan)
            }

            val mediaPlayer = MediaPlayer()

            fabPlay.setOnClickListener {
                try {
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
                    mediaPlayer.setDataSource(strAudio)
                    mediaPlayer.prepare()
                    mediaPlayer.start()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                fabPlay.visibility = View.GONE
                fabStop.visibility = View.VISIBLE
            }

            fabStop.setOnClickListener {
                mediaPlayer.stop()
                mediaPlayer.reset()
                fabPlay.visibility = View.VISIBLE
                fabStop.visibility = View.GONE
            }
        }

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Mohon Tunggu")
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Sedang menampilkan data...")

        detailAdapter = DetailAdapter()
        rvAyat.setHasFixedSize(true)
        rvAyat.layoutManager = LinearLayoutManager(this)
        rvAyat.adapter = detailAdapter
    }

    private fun setViewModel() {
        progressDialog.setIcon(R.mipmap.ic_info)
        progressDialog.show()
        surahViewModel = ViewModelProvider(this, NewInstanceFactory()).get(SurahViewModel::class.java)
        surahViewModel.setDetailSurah(strNomor)
        surahViewModel.getDetailSurah()
            .observe(this, { modelAyat: ArrayList<ModelAyat> ->
                if (modelAyat.size != 0) {
                    detailAdapter.setAdapter(modelAyat)
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

    companion object {
        const val DETAIL_SURAH = "DETAIL_SURAH"
    }
}