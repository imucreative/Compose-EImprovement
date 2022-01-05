package com.fastrata.eimprovement.featuresglobal.transaction

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointCreateModel
import com.fastrata.eimprovement.features.changespoint.ui.ChangesPointCreateViewModel
import com.fastrata.eimprovement.featuresglobal.data.model.StatusProposalItem
import com.fastrata.eimprovement.utils.*
import timber.log.Timber

class UpdateStatusProposalCp(
    private val listCpViewModel: ChangesPointCreateViewModel,
    private val context: Context,
    private val owner: LifecycleOwner
) {
    fun getDetailDataCp(
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

        updateCp(dataCreateModel, context, owner) { status(it) }
    }

    fun updateCp(
        data: ChangePointCreateModel,
        context: Context,
        owner: LifecycleOwner,
        complete: (Boolean) -> Unit
    ){
        try {
            listCpViewModel.setPutSubmitUpdateCp(data)
            listCpViewModel.putSubmitUpdateCp.observeEvent(owner) { resultObserve ->
                resultObserve.observe(owner, { result ->
                    if (result != null) {
                        when (result.status) {
                            Result.Status.LOADING -> {
                                HelperLoading.displayLoadingWithText(context, "", false)
                                Timber.d("###-- Loading putSubmitUpdateCp")
                            }
                            Result.Status.SUCCESS -> {
                                HelperLoading.hideLoading()

                                Timber.e("${result.data?.message}")

                                Toast.makeText(context, result.data?.message, Toast.LENGTH_LONG).show()

                                Timber.d("###-- Success putSubmitUpdateCp")

                                complete(true)
                            }
                            Result.Status.ERROR -> {
                                HelperLoading.hideLoading()
                                Toast.makeText(context, result.message, Toast.LENGTH_LONG).show()
                                Timber.d("###-- Error putSubmitUpdateCp")

                                complete(false)
                            }

                        }
                    }
                })
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
                resultObserve.observe(owner, { result ->
                    if (result != null) {
                        when (result.status) {
                            Result.Status.LOADING -> {
                                HelperLoading.displayLoadingWithText(context, "", false)
                                Timber.d("###-- Loading postSubmitCreateCp")
                            }
                            Result.Status.SUCCESS -> {
                                HelperLoading.hideLoading()

                                Timber.e("${result.data?.message}")

                                Toast.makeText(context, result.data?.message, Toast.LENGTH_LONG).show()

                                complete(true)

                                Timber.d("###-- Success postSubmitCreateCp")
                            }
                            Result.Status.ERROR -> {
                                HelperLoading.hideLoading()
                                Toast.makeText(context, result.data?.message, Toast.LENGTH_LONG).show()

                                complete(true)

                                Timber.d("###-- Error postSubmitCreateCp")
                            }

                        }
                    }
                })
            }
        }catch (err: Exception){
            HelperLoading.hideLoading()
            Toast.makeText(context, err.message, Toast.LENGTH_LONG).show()

            complete(true)

            Timber.d("###-- Error postSubmitCreateCp")
        }
    }
}