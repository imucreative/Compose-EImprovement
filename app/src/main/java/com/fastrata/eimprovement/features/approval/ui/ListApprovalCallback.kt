package com.fastrata.eimprovement.features.approval.ui

import com.fastrata.eimprovement.features.approval.data.model.ApprovalModel

interface ListApprovalCallback {
    fun onItemClicked(data: ApprovalModel)
}