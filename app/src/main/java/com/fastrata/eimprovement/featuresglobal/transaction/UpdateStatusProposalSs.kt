package com.fastrata.eimprovement.featuresglobal.transaction

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemCreateModel
import com.fastrata.eimprovement.features.suggestionsystem.ui.SuggestionSystemViewModel
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.features.suggestionsystem.data.model.StatusImplementation
import com.fastrata.eimprovement.featuresglobal.data.model.StatusProposalItem
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.workers.service
import kotlinx.coroutines.coroutineScope
import timber.log.Timber

class UpdateStatusProposalSs(
    private val listSsViewModel: SuggestionSystemViewModel,
    private val context: Context,
    ) {

    suspend fun getDetailDataSs(
        id: Int,
        ssNo: String,
        statusProposal: StatusProposalItem,
        userNameSubmit: Int,
        status: (Boolean) -> Unit
    ) {
        val dataCreateModel = SuggestionSystemCreateModel(
            id = id,
            ssNo = ssNo,
            date = "",
            title = "",
            categoryImprovement = null,
            name = "",
            nik = "",
            branchCode = "",
            branch = "",
            subBranch = "",
            department = "",
            directMgr = "",
            directMgrNik = "",
            suggestion = "",
            problem = "",
            statusImplementation = StatusImplementation(status = 0, from = "", to = ""),
            teamMember = null,
            attachment = null,
            statusProposal = statusProposal,
            proses = "",
            result = "",
            headId = 0,
            userId = userNameSubmit,
            orgId = 0,
            warehouseId = 0,
            historyApproval = null,
            score = 0,
            activityType = SS,
            submitType = 1,
            comment = ""
        )
        updateSs(dataCreateModel, context) { status(it) }
    }

    // update for edit and update proposal
    suspend fun updateSs(
        data: SuggestionSystemCreateModel,
        context: Context,
        complete: (Boolean) -> Unit
    ) {
        HelperLoading.displayLoadingWithText(context, "", false)

        try {
            coroutineScope {
                service().submitUpdateSs(data).let {
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
            Timber.d("###-- Error putSubmitUpdateSs")
        }
    }

    // submit for create
    fun submitSs(
        data: SuggestionSystemCreateModel,
        action: String,
        context: Context,
        owner: LifecycleOwner,
        complete: (Boolean) -> Unit
    ){
        try {
            listSsViewModel.setPostSubmitCreateSs(data, action)
            listSsViewModel.postSubmitCreateSs.observeEvent(owner) { resultObserve ->
                resultObserve.observe(owner) { result ->
                    if (result != null) {
                        when (result.status) {
                            Result.Status.LOADING -> {
                                HelperLoading.displayLoadingWithText(context, "", false)
                                Timber.d("###-- Loading postSubmitCreateSs")
                            }
                            Result.Status.SUCCESS -> {
                                HelperLoading.hideLoading()

                                Timber.e("${result.data?.message}")

                                Toast.makeText(context, result.data?.message, Toast.LENGTH_LONG)
                                    .show()

                                complete(true)

                                Timber.d("###-- Success postSubmitCreateSs")
                            }
                            Result.Status.ERROR -> {
                                HelperLoading.hideLoading()
                                Toast.makeText(context, result.message, Toast.LENGTH_LONG).show()

                                complete(false)

                                Timber.d("###-- Error postSubmitCreateSs")
                            }

                        }
                    }
                }
            }
        }catch (err : Exception){
            HelperLoading.hideLoading()
            Toast.makeText(context, err.message, Toast.LENGTH_LONG).show()

            complete(false)

            Timber.d("###-- Error postSubmitCreateSs")
        }
    }
}