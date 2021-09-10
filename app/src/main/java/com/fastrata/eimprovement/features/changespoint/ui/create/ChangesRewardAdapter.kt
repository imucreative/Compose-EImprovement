package com.fastrata.eimprovement.features.changespoint.ui.create

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fastrata.eimprovement.databinding.ItemChangesRewardBinding
import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointRewardItem

class ChangesRewardAdapter : RecyclerView.Adapter<ChangesRewardAdapter.ChangesRewardAdapterViewHolder>() {

    private var list = ArrayList<ChangePointRewardItem>()

    fun setList(data: ArrayList<ChangePointRewardItem>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    private lateinit var ChangesRewardCallback: ChangeRewardCallback
    fun setChangeRewardCallback(ChangesRewardCallback: ChangeRewardCallback) {
        this.ChangesRewardCallback = ChangesRewardCallback
    }

    inner class ChangesRewardAdapterViewHolder(private val binding: ItemChangesRewardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ChangePointRewardItem) {


            binding.removeTeamMember.setOnClickListener {
                ChangesRewardCallback.removeClicked(data)
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

    override fun onBindViewHolder(holder: ChangesRewardAdapterViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

}

interface ChangeRewardCallback {
    fun removeClicked(data: ChangePointRewardItem)
}