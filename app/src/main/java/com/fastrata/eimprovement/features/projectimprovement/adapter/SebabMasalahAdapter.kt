package com.fastrata.eimprovement.features.projectimprovement.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fastrata.eimprovement.databinding.ItemSebabMasalahPiBinding
import com.fastrata.eimprovement.features.projectimprovement.callback.SebabMasalahCallback
import com.fastrata.eimprovement.features.projectimprovement.data.model.SebabMasalahItem
import com.fastrata.eimprovement.utils.Tools
import com.fastrata.eimprovement.utils.ViewAnimation

class SebabMasalahAdapter : RecyclerView.Adapter<SebabMasalahAdapter.SebabMasalahViewHolder>() {

    private var list = ArrayList<SebabMasalahItem?>()

    fun setList(data : ArrayList<SebabMasalahItem?>?){
        list.clear()
        if (data != null) {
            list.addAll(data)
        }
        notifyDataSetChanged()
    }

    private fun toggleLayoutExpand(show: Boolean, view: View, lyt_expand: View): Boolean {
        Tools.toggleArrow(show, view)
        if (show) {
            ViewAnimation().expand(lyt_expand)
        } else {
            ViewAnimation().collapse(lyt_expand)
        }
        return show
    }

    private lateinit var sebabmslhcallback : SebabMasalahCallback
    fun setSebabMslhCallback(sebabMasalahCallback: SebabMasalahCallback){
        this.sebabmslhcallback = sebabMasalahCallback
    }

    inner class SebabMasalahViewHolder(private val binding: ItemSebabMasalahPiBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: SebabMasalahItem, position: Int) {

            binding.root.setOnClickListener {
                sebabmslhcallback.onItemClicked(data)
            }

            binding.removeSebabMasalah.setOnClickListener {
                sebabmslhcallback.onItemRemoved(data, position)
            }

            var expand = false
            binding.btnExpand.setOnClickListener {
                val show = toggleLayoutExpand(!expand, binding.btnExpand, binding.lytExpand)
                expand = show
            }

            binding.linearWhyExpand.setOnClickListener {
                val show = toggleLayoutExpand(!expand, binding.btnExpand, binding.lytExpand)
                expand = show
            }

            binding.apply {
                penyebabMasalah.text = data.penyebab
                w1Pi.text = data.w1
                w2Pi.text = data.w2
                w3Pi.text = data.w3
                w4Pi.text = data.w4
                w5Pi.text = data.w5
                akarPrioritas.text = data.prioritas
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SebabMasalahViewHolder {
        val items = ItemSebabMasalahPiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SebabMasalahViewHolder(items)
    }

    override fun onBindViewHolder(holder: SebabMasalahAdapter.SebabMasalahViewHolder, position: Int) {
        list[position]?.let { holder.bind(it, position) }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}