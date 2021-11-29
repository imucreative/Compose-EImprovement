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

    fun setList(data: List<MutasiModel>){
        val convertToArrayList = Tools.listToArrayList(data)
        if (convertToArrayList != null){
            list.addAll(convertToArrayList)
        }
        notifyDataSetChanged()
    }

    fun clear(){
        list.clear()
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
                if (data.nominal_debit == 0){
                    point.text = Tools.doubleToRupiah(data.nominal_kredit.toDouble(),2)
                    point.setTextColor(Color.GREEN)
                    typeReward.text = "KR"
                    typeReward.setTextColor(Color.GREEN)
                }else{
                    point.text = Tools.doubleToRupiah(data.nominal_debit.toDouble(),2)
                    typeReward.text = "DB"
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