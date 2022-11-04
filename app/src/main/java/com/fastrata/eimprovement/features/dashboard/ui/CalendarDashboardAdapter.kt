package com.fastrata.eimprovement.features.dashboard.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.ItemCalendarDashboardBinding
import com.fastrata.eimprovement.features.dashboard.ui.data.CalendarDashboardModel
import com.fastrata.eimprovement.utils.CP
import com.fastrata.eimprovement.utils.PI
import com.fastrata.eimprovement.utils.SS
import com.fastrata.eimprovement.utils.Tools

class CalendarDashboardAdapter: RecyclerView.Adapter<CalendarDashboardAdapter.CalendarDashboardViewHolder>() {
    private var list = ArrayList<CalendarDashboardModel>()

    fun setList(data: List<CalendarDashboardModel>?) {
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

    private lateinit var calendarDashboardCallback: CalendarDashboardCallback
    fun setSuggestionSystemCallback(calendarDashboardCallback: CalendarDashboardCallback) {
        this.calendarDashboardCallback = calendarDashboardCallback
    }

    inner class CalendarDashboardViewHolder(private val binding: ItemCalendarDashboardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CalendarDashboardModel) {

            binding.root.setOnClickListener {
                calendarDashboardCallback.onItemClicked(data)
            }

            binding.apply {
                typeNo.text = data.docNo
                title.text = data.title
                status.text = data.status.status
                type.text = data.docType
                date.text = data.createdDate

                status.setTextColor(
                    when (data.status.id) {
                        5 -> ContextCompat.getColor(binding.root.context, R.color.blue_800)
                        4, 9 -> ContextCompat.getColor(binding.root.context, R.color.yellow_800)
                        10 -> ContextCompat.getColor(binding.root.context, R.color.green_800)
                        12 -> ContextCompat.getColor(binding.root.context, R.color.red_800)
                        else -> Color.BLACK
                    }
                )

                when (data.docType) {
                    SS -> {
                        type.setTextColor(ContextCompat.getColor(binding.root.context, R.color.yellow_800))
                        proposalIndicator.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.yellow_800))
                    }
                    PI -> {
                        type.setTextColor(ContextCompat.getColor(binding.root.context, R.color.blue_800))
                        proposalIndicator.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.blue_800))
                    }
                    CP -> {
                        type.setTextColor(ContextCompat.getColor(binding.root.context, R.color.green_800))
                        proposalIndicator.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.green_800))
                    }
                    else -> {
                        type.setTextColor(Color.BLACK)
                        proposalIndicator.setBackgroundColor(Color.BLACK)
                    }
                }
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CalendarDashboardAdapter.CalendarDashboardViewHolder {
        val items = ItemCalendarDashboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarDashboardViewHolder(items)
    }

    override fun onBindViewHolder(
        holder: CalendarDashboardAdapter.CalendarDashboardViewHolder,
        position: Int
    ) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}