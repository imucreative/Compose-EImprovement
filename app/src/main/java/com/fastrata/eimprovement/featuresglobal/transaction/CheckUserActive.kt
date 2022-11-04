package com.fastrata.eimprovement.featuresglobal.transaction

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.featuresglobal.viewmodel.CheckUserViewModel
import com.fastrata.eimprovement.utils.*
import timber.log.Timber

class CheckUserActive(
    private val checkUserViewModel: CheckUserViewModel,
    private val context: Context,
    private val owner: LifecycleOwner
) {
    fun getUserActive(
        userId: Int,
        status: (Boolean) -> Unit
    ) {
        try {
            checkUserViewModel.setCheckUser(userId)
            checkUserViewModel.getCheckUserItem.observeEvent(owner) { resultObserve ->
                resultObserve.observe(owner) { result ->
                    if (result != null) {
                        when (result.status) {
                            Result.Status.LOADING -> {
                                Timber.d("###-- Loading get checkUser")
                            }
                            Result.Status.SUCCESS -> {
                                if (result.data?.data?.size == 0) {
                                    status(false)
                                    Toast.makeText(context, result.data.message, Toast.LENGTH_LONG)
                                        .show()
                                } else {
                                    status(true)
                                }
                                Timber.d("###-- Success checkUser ${result.data}")
                            }
                            Result.Status.ERROR -> {
                                Toast.makeText(
                                    context,
                                    "Error : ${result.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                                Timber.e("###-- Loading error checkUser $result")
                            }
                        }
                    }
                }
            }
        }catch (e : Exception){
            Timber.e("Error checkUser : $e")
            Toast.makeText(context,"Error : $e",Toast.LENGTH_LONG).show()
        }
    }
}