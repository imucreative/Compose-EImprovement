package com.fastrata.eimprovement.features.projectimprovement.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fastrata.eimprovement.databinding.ItemAkarMasalahPiBinding
import com.fastrata.eimprovement.features.projectimprovement.callback.AkarMasalahCallback
import com.fastrata.eimprovement.features.projectimprovement.data.model.AkarMasalahModel

class AkarMasalahAdapter : RecyclerView.Adapter<AkarMasalahAdapter.AkarMasalahViewHolder>() {

    private var list = ArrayList<AkarMasalahModel>()

    fun setList(data : ArrayList<AkarMasalahModel>){
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    private lateinit var akarmslhcallback : AkarMasalahCallback
    fun setAkarMslhCallback(akarMasalahCallback: AkarMasalahCallback){
        this.akarmslhcallback = akarMasalahCallback
    }

    inner class AkarMasalahViewHolder(private val binding: ItemAkarMasalahPiBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(Data : AkarMasalahModel) {

            binding.root.setOnClickListener {
                akarmslhcallback.onItemClicked(Data)
            }

            binding.apply {
                penyebabMasalah.text = Data.pnybmslh
                w1Pi.text = Data.w1
                w2Pi.text = Data.w2
                w3Pi.text = Data.w3
                w4Pi.text = Data.w4
                w5Pi.text = Data.w5
                akarPrioritas.text = Data.akarmslsh
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AkarMasalahViewHolder {
        val items = ItemAkarMasalahPiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AkarMasalahViewHolder(items)
    }

    override fun onBindViewHolder(holder: AkarMasalahAdapter.AkarMasalahViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}