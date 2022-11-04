package com.fastrata.eimprovement.featuresglobal.transaction

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointCreateModel
import com.fastrata.eimprovement.features.changespoint.ui.ChangesPointCreateViewModel
import com.fastrata.eimprovement.featuresglobal.data.model.StatusProposalItem
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.workers.service
import kotlinx.coroutines.coroutineScope
import timber.log.Timber

class UpdateStatusProposalCp(
    private val listCpViewModel: ChangesPointCreateViewModel,
    private val context: Context,
) {
    suspend fun getDetailDataCp(
        id: Int,
        cpNo: String,
        statusProposal: StatusProposalItem,
        userNameSubmit: Int,
        status: (Boolean) -> Unit
    ) {
        val dataCreateModel = ChangePointCreateModel(
            id = id,
            cpNo = cpNo,
            saldo = 0,
            name = "",
            nik = "",
            branch = "",
            subBranch = "",
            branchCode = "",
            department = "",
            position = "",
            date = "",
            description = "",
            reward = null,
            statusProposal = statusProposal,
            headId = 0,
            userId = userNameSubmit,
            orgId = 0,
            warehouseId = 0,
            historyApproval = null,
            activityType = CP,
            submitType = 1,
            comment = ""
        )

        updateCp(dataCreateModel, context) { status(it) }
    }

    suspend fun updateCp(
        data: ChangePointCreateModel,
        context: Context,
        complete: (Boolean) -> Unit
    ){
        try {
            coroutineScope {
                service().submitUpdateCp(data).let {
                    if (it.isSuccessful) {
                        HelperLoading.hideLoading()

                        Toast.makeText(context, it.body()?.message, Toast.LENGTH_LONG).show()
                        complete(true)
                    } else {
                        complete(false)

                        HelperLoading.hideLoading()
                        Toast.makeText(context, it.body()?.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

        } catch (err: Exception) {
            HelperLoading.hideLoading()
            complete(false)
            Toast.makeText(context, err.message, Toast.LENGTH_LONG).show()
            Timber.d("###-- Error putSubmitUpdateCp")
        }
    }

    fun submitCp(
        data: ChangePointCreateModel,
        context: Context,
        owner: LifecycleOwner,
        complete: (Boolean) -> Unit
    ){
        try {
            listCpViewModel.setPostSubmitCreateCp(data)
            listCpViewModel.postSubmitCreateCp.observeEvent(owner) { resultObserve ->
                resultObserve.observe(owner) { result ->
                    if (result != null) {
                        when (result.status) {
                            Result.Status.LOADING -> {
                                HelperLoading.displayLoadingWithText(context, "", false)
                                Timber.d("###-- Loading postSubmitCreateCp")
                            }
                            Result.Status.SUCCESS -> {
                                HelperLoading.hideLoading()

                                Timber.e("${result.data?.message}")

                                Toast.makeText(context, result.data?.message, Toast.LENGTH_LONG)
                                    .show()

                                complete(true)

                                Timber.d("###-- Success postSubmitCreateCp")
                            }
                            Result.Status.ERROR -> {
                                HelperLoading.hideLoading()
                                Toast.makeText(context, result.data?.message, Toast.LENGTH_LONG)
                                    .show()

                                complete(true)

                                Timber.d("###-- Error postSubmitCreateCp")
                            }

                        }
                    }
                }
            }
        }catch (err: Exception){
            HelperLoading.hideLoading()
            Toast.makeText(context, err.message, Toast.LENGTH_LONG).show()

            complete(true)

            Timber.d("###-- Error postSubmitCreateCp")
        }
    }
}