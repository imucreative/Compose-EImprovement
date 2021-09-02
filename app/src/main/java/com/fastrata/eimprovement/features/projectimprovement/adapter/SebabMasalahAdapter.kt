package com.fastrata.eimprovement.features.projectimprovement.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fastrata.eimprovement.databinding.ItemSebabMasalahPiBinding
import com.fastrata.eimprovement.features.projectimprovement.callback.SebabMasalahCallback
import com.fastrata.eimprovement.features.projectimprovement.data.model.SebabMasalahItem

class SebabMasalahAdapter : RecyclerView.Adapter<SebabMasalahAdapter.SebabMasalahViewHolder>() {

    private var list = ArrayList<SebabMasalahItem>()

    fun setList(data : ArrayList<SebabMasalahItem>){
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    private lateinit var sebabmslhcallback : SebabMasalahCallback
    fun setSebabMslhCallback(sebabMasalahCallback: SebabMasalahCallback){
        this.sebabmslhcallback = sebabMasalahCallback
    }

    inner class SebabMasalahViewHolder(private val binding: ItemSebabMasalahPiBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data : SebabMasalahItem) {

            binding.root.setOnClickListener {
                sebabmslhcallback.onItemClicked(data)
            }

            binding.apply {
                penyebabMasalah.text = data.pnybmslh
                w1Pi.text = data.w1
                w2Pi.text = data.w2
                w3Pi.text = data.w3
                w4Pi.text = data.w4
                w5Pi.text = data.w5
                akarPrioritas.text = data.akarmslsh
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SebabMasalahViewHolder {
        val items = ItemSebabMasalahPiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SebabMasalahViewHolder(items)
    }

    override fun onBindViewHolder(holder: SebabMasalahAdapter.SebabMasalahViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}