package com.fastrata.eimprovement.features.ChangesPoint.ui.create

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fastrata.eimprovement.databinding.ItemChangesRewardBinding
import com.fastrata.eimprovement.features.ChangesPoint.data.model.ChangePointRewardModel


class ChangesRewardAdapter : RecyclerView.Adapter<ChangesRewardAdapter.ChangesRewardAdapterViewHolder>() {

    private var list = ArrayList<ChangePointRewardModel>()

    fun setList(data: ArrayList<ChangePointRewardModel>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    private lateinit var ChangesRewardCallback: ChangeRewardCallback
    fun setChangeRewardCallback(ChangesRewardCallback: ChangeRewardCallback) {
        this.ChangesRewardCallback = ChangesRewardCallback
    }

    inner class ChangesRewardAdapterViewHolder(private val binding: ItemChangesRewardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ChangePointRewardModel) {


            binding.removeTeamMember.setOnClickListener {
                ChangesRewardCallback.removeClicked(data)
            }

            binding.apply {
                reward.text = data.hadiah
                Point.text = data.nilai
                desc.text = data.desc
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
    fun removeClicked(data: ChangePointRewardModel)
}