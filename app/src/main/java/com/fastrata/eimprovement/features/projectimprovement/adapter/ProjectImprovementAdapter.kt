package com.fastrata.eimprovement.features.projectimprovement.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.ItemProjectImprovmentBinding
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementModel
import com.fastrata.eimprovement.features.projectimprovement.callback.ProjectSystemCallback
import com.fastrata.eimprovement.utils.Tools
import java.util.ArrayList

class ProjectImprovementAdapter : RecyclerView.Adapter<ProjectImprovementAdapter.ProjectImprovementViewHolder>() {

    private var list = ArrayList<ProjectImprovementModel>()

    fun setList(data: List<ProjectImprovementModel>?) {
        val convertToArrayList = Tools.listToArrayList(data)
        if (convertToArrayList != null) {
            list.addAll(convertToArrayList)
        }
        notifyDataSetChanged()
    }

    fun clear(){
        list.clear()
        notifyDataSetChanged()
    }

    private lateinit var projectSystemCallback: ProjectSystemCallback
    fun setProjectImprovementSystemCallback(projectSystemCallback: ProjectSystemCallback) {
        this.projectSystemCallback = projectSystemCallback

    }

    inner class ProjectImprovementViewHolder(private val binding: ItemProjectImprovmentBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ProjectImprovementModel) {

            binding.root.setOnClickListener {
                projectSystemCallback.onItemClicked(data)
            }

            binding.apply {
                nomorPi.text = data.piNo
                judulPi.text = data.title
                statusPi.text = data.status.status
                datePi.text = data.date
                branchPi.text = data.branch
                subBranchPi.text = data.subBranch
                createbyPi.text = data.createdBy

                statusPi.setTextColor(
                    when (data.status.id) {
                        5 -> ContextCompat.getColor(binding.root.context, R.color.blue_800)
                        4, 9 -> ContextCompat.getColor(binding.root.context, R.color.yellow_800)
                        10 -> ContextCompat.getColor(binding.root.context, R.color.green_800)
                        12 -> ContextCompat.getColor(binding.root.context, R.color.red_800)
                        else -> Color.BLACK
                    }
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectImprovementViewHolder {
        val items = ItemProjectImprovmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProjectImprovementViewHolder(items)
    }

    override fun onBindViewHolder(holder: ProjectImprovementViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

}