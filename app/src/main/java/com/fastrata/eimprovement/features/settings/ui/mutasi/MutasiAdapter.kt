package com.fastrata.eimprovement.features.settings.ui.mutasi

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fastrata.eimprovement.databinding.ItemMutasiBinding
import com.fastrata.eimprovement.features.settings.ui.mutasi.data.model.MutasiModel
import com.fastrata.eimprovement.utils.Tools

class MutasiAdapter : RecyclerView.Adapter<MutasiAdapter.MutasiAdapterViewHolder>(){

    private var list = ArrayList<MutasiModel>()

    fun setList(data: ArrayList<MutasiModel>){
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    inner class MutasiAdapterViewHolder(private val binding :ItemMutasiBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(data :MutasiModel){

            binding.apply {
                date.text  = data.date

                noReward.text = data.document_no
                status.text = data.stat
                type.text = data.tipe
                created.text = data.created_by
                desc.text = data.desc
                if (data.tipe_point == "KR"){
                    typeReward.text = data.tipe_point
                    typeReward.setTextColor(Color.GREEN)
                    point.text = Tools.doubleToRupiah(data.nominal.toDouble(),2)
                    point.setTextColor(Color.GREEN)
                }else{
                    point.text = Tools.doubleToRupiah(data.nominal.toDouble(),2)
                    typeReward.text = data.tipe_point
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MutasiAdapterViewHolder {
        val items = ItemMutasiBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MutasiAdapterViewHolder(items)
    }

    override fun onBindViewHolder(holder: MutasiAdapterViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}