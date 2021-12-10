package com.fastrata.eimprovement.api

interface AMQPConsumerListener {
    fun onReceive(id:String,message: String)
    fun onCancel()
    fun onError(message:String)
}