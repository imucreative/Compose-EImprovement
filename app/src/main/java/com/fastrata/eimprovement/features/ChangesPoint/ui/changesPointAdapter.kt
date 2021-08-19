package com.fastrata.eimprovement.features.ChangesPoint.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fastrata.eimprovement.databinding.ItemChangesPointBinding
import com.fastrata.eimprovement.features.ChangesPoint.data.model.ChangePointRewardModel
import com.fastrata.eimprovement.features.ChangesPoint.data.model.ChangePointSystemModel
import com.fastrata.eimprovement.features.ChangesPoint.ui.create.ChangeRewardCallback


class changesPointAdapter : RecyclerView.Adapter<changesPointAdapter.ChangesPointViewHolder>() {

    private var list = ArrayList<ChangePointSystemModel>()

    fun setList(data: ArrayList<ChangePointSystemModel>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    private lateinit var changesPointCallback: ChangesPointCallback
    fun setChangeRewardCallback(changesPointCallback: ChangesPointCallback) {
        this.changesPointCallback = changesPointCallback
    }

    inner class ChangesPointViewHolder(private val binding: ItemChangesPointBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ChangePointSystemModel) {

            binding.root.setOnClickListener {
                changesPointCallback.onItemClicked(data)
            }

            binding.apply {
                nopenukaran.text = data.nocp
                statusCp.text = data.status
                descCp.text = data.desc
                branch.text = data.branch
                subBranch.text = data.branch
                date.text = data.date
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