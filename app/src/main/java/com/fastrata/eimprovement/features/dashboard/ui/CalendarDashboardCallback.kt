package com.fastrata.eimprovement.features.dashboard.ui

import com.fastrata.eimprovement.features.dashboard.ui.data.CalendarDashboardModel

interface CalendarDashboardCallback {
    fun onItemClicked(data: CalendarDashboardModel)
}