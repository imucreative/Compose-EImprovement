package com.fastrata.eimprovement.featuresglobal.adapter

import com.fastrata.eimprovement.featuresglobal.data.model.AttachmentItem

interface AttachmentCallback {
    fun removeClicked(data: AttachmentItem)
    fun showAttachment(data: AttachmentItem)
}