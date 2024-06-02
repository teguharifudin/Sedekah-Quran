package com.teguharief.sedekahquranindonesia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.teguharief.sedekahquranindonesia.R
import com.teguharief.sedekahquranindonesia.model.main.ModelDoa
import kotlinx.android.synthetic.main.list_item_doa.view.*
import java.util.*

class DoaAdapter : RecyclerView.Adapter<DoaAdapter.ViewHolder>() {
    private val modelDoaList = ArrayList<ModelDoa>()

    fun setAdapter(items: ArrayList<ModelDoa>) {
        modelDoaList.clear()
        modelDoaList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_doa, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = modelDoaList[position]

        holder.tvJudul.text = data.judul
        holder.tvArab.text = data.arab
        holder.tvArti.text = data.arti
    }

    override fun getItemCount(): Int {
        return modelDoaList.size
    }

    //Class Holder
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvJudul: TextView
        var tvArab: TextView
        var tvArti: TextView

        init {
            tvJudul = itemView.tvJudul
            tvArab = itemView.tvArab
            tvArti = itemView.tvArti
        }
    }
}