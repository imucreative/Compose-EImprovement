package com.fastrata.eimprovement.ui.adapter

import com.fastrata.eimprovement.ui.model.AttachmentItem

interface AttachmentCallback {
    fun removeClicked(data: AttachmentItem)
    fun showAttachment(data: AttachmentItem)
}