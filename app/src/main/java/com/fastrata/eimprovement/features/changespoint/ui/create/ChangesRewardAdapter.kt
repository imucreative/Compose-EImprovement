package com.fastrata.eimprovement.features.changespoint.ui.create

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fastrata.eimprovement.databinding.ItemChangesRewardBinding
import com.fastrata.eimprovement.features.changespoint.data.model.ChangeRewardCallback
import com.fastrata.eimprovement.features.changespoint.data.model.RewardItem

class ChangesRewardAdapter : RecyclerView.Adapter<ChangesRewardAdapter.ChangesRewardAdapterViewHolder>() {

    private var list = ArrayList<RewardItem?>()

    fun setListReward(data: ArrayList<RewardItem?>?) {
        list.clear()
        if (data != null) {
            list.addAll(data)
        }
        notifyDataSetChanged()
    }

    private lateinit var call: ChangeRewardCallback
    fun setChangeRewardCallback(callback: ChangeRewardCallback) {
        this.call = callback
    }

    inner class ChangesRewardAdapterViewHolder(private val binding: ItemChangesRewardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: RewardItem) {

            binding.removeReward.setOnClickListener {
                call.removeClicked(data)
            }

            binding.apply {
                reward.text = data.hadiah
                Point.text = data.nilai
                desc.text = data.keterangan
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChangesRewardAdapterViewHolder {
        val items = ItemChangesRewardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChangesRewardAdapterViewHolder(items)
    }

    override fun onBindViewHolder(holder: ChangesRewardAdapter.ChangesRewardAdapterViewHolder, position: Int) {
       list[position]?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}

