package com.fastrata.eimprovement.features.changespoint.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fastrata.eimprovement.databinding.ItemChangesPointBinding
import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointModel

class ChangesPointAdapter : RecyclerView.Adapter<ChangesPointAdapter.ChangesPointViewHolder>() {

    private var list = ArrayList<ChangePointModel>()

    fun setList(data: ArrayList<ChangePointModel>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    private lateinit var changesPointCallback: ChangesPointCallback
    fun setChangeRewardCallback(changesPointCallback: ChangesPointCallback) {
        this.changesPointCallback = changesPointCallback
    }

    inner class ChangesPointViewHolder(private val binding: ItemChangesPointBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ChangePointModel) {

            binding.root.setOnClickListener {
                changesPointCallback.onItemClicked(data)
            }

            binding.apply {
                nopenukaran.text = data.no_penukaran
                statusCp.text = data.status
                descCp.text = data.deskripsi
                branch.text = data.cabang
                subBranch.text = data.cabang
                date.text = data.tgl_input
                Total.text = data.total
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChangesPointViewHolder {
        val items = ItemChangesPointBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChangesPointViewHolder(items)
    }

    override fun onBindViewHolder(holder: ChangesPointViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}