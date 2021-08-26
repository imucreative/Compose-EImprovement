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
    fun setAkarMslhCallback(AkarMasalahCallback: AkarMasalahCallback){
        this.akarmslhcallback = AkarMasalahCallback
    }

    inner class AkarMasalahViewHolder(private  val binding: ItemAkarMasalahPiBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data : AkarMasalahModel){
            binding.root.setOnClickListener {
                akarmslhcallback.onItemClicked(data)
            }

            binding.apply {
                whyTerakhir.text = data.akarmslsh
                imprvementDilakukan.text = data.improvement
                detilLangkah.text = data.detail
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