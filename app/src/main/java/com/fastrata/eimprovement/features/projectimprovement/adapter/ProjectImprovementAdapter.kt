package com.fastrata.eimprovement.features.projectimprovement.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fastrata.eimprovement.databinding.ItemProjectImprovmentBinding
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementModel
import com.fastrata.eimprovement.features.projectimprovement.callback.ProjectSystemCallback
import java.util.ArrayList

class ProjectImprovementAdapter : RecyclerView.Adapter<ProjectImprovementAdapter.ProjectImprovementViewHolder>() {

    private var list = ArrayList<ProjectImprovementModel>()

    fun setList(data: ArrayList<ProjectImprovementModel>) {
        list.clear()
        list.addAll(data)
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
                statusPi.text = data.status
                kategoriPi.text = data.category
                datePi.text = data.date
                branchPi.text = data.branch
                subBranchPi.text = data.subBranch
                createbyPi.text = data.createdBy
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