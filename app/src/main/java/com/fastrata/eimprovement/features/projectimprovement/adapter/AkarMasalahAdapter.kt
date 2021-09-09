package com.fastrata.eimprovement.features.projectimprovement.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fastrata.eimprovement.databinding.ItemAkarMasalahPiBinding
import com.fastrata.eimprovement.features.projectimprovement.callback.AkarMasalahCallback
import com.fastrata.eimprovement.features.projectimprovement.data.model.AkarMasalahItem

class AkarMasalahAdapter : RecyclerView.Adapter<AkarMasalahAdapter.AkarMasalahViewHolder>() {

    private var list = ArrayList<AkarMasalahItem>()

    fun setList(data : ArrayList<AkarMasalahItem>){
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    private lateinit var akarmslhcallback : AkarMasalahCallback
    fun setAkarMslhCallback(AkarMasalahCallback: AkarMasalahCallback){
        this.akarmslhcallback = AkarMasalahCallback
    }

    inner class AkarMasalahViewHolder(private  val binding: ItemAkarMasalahPiBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data : AkarMasalahItem){
            binding.root.setOnClickListener {
                akarmslhcallback.onItemClicked(data)
            }

            binding.apply {
                whyTerakhir.text = data.kenapa
                imprvementDilakukan.text = data.aksi
                detilLangkah.text = data.detail_langkah
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AkarMasalahViewHolder{
        val items = ItemAkarMasalahPiBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AkarMasalahViewHolder(items)
    }

    override fun onBindViewHolder(holder: AkarMasalahAdapter.AkarMasalahViewHolder, positon: Int){
        holder.bind(list[positon])
    }

    override fun getItemCount(): Int {
        return list.size
    }


}