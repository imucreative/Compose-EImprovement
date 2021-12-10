package com.fastrata.eimprovement.featuresglobal.transaction

import android.content.Context
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.lifecycle.LifecycleOwner
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemCreateModel
import com.fastrata.eimprovement.features.suggestionsystem.ui.SuggestionSystemViewModel
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.utils.*
import timber.log.Timber

class UpdateStatusProposalSs(
    private val listSsViewModel: SuggestionSystemViewModel,
    private val context: Context,
    private val owner: LifecycleOwner
    ) {

    fun getDetailDataSs(
        id: Int,
        userId: Int,
        userNameSubmit: Int,
        status: (Boolean) -> Unit
    ) {
        try {
            listSsViewModel.setDetailSs(id, userId)

            listSsViewModel.getDetailSsItem.observeEvent(owner) { resultObserve ->
                resultObserve.observe(owner, { result ->
                    if (result != null) {
                        when (result.status) {
                            Result.Status.LOADING -> {
                                Timber.d("###-- Loading getDetailSsItem to submit ")
                            }
                            Result.Status.SUCCESS -> {

                                val dataCreateModel = SuggestionSystemCreateModel(
                                    id = result.data?.data?.get(0)?.id,
                                    ssNo = result.data?.data?.get(0)?.ssNo,
                                    date = result.data?.data?.get(0)?.date,
                                    title = result.data?.data?.get(0)?.title,
                                    categoryImprovement = result.data?.data?.get(0)?.categoryImprovement,
                                    name = result.data?.data?.get(0)?.name,
                                    nik = result.data?.data?.get(0)?.nik,
                                    branchCode = result.data?.data?.get(0)?.branchCode,
                                    branch = result.data?.data?.get(0)?.branch,
                                    subBranch = "",
                                    department = result.data?.data?.get(0)?.department,
                                    directMgr = result.data?.data?.get(0)?.directMgr,
                                    suggestion = result.data?.data?.get(0)?.suggestion?.let {
                                        HtmlCompat.fromHtml(
                                            it, HtmlCompat.FROM_HTML_MODE_LEGACY
                                        ).toString()
                                    },
                                    problem = result.data?.data?.get(0)?.problem?.let {
                                        HtmlCompat.fromHtml(
                                            it, HtmlCompat.FROM_HTML_MODE_LEGACY
                                        ).toString()
                                    },
                                    statusImplementation = result.data?.data?.get(0)?.statusImplementation,
                                    teamMember = result.data?.data?.get(0)?.teamMember,
                                    attachment = result.data?.data?.get(0)?.attachment,
                                    statusProposal = result.data?.data?.get(0)?.statusProposal,
                                    proses = result.data?.data?.get(0)?.proses,
                                    result = result.data?.data?.get(0)?.result,

                                    headId = result.data?.data?.get(0)?.headId,
                                    userId = userNameSubmit,
                                    orgId = result.data?.data?.get(0)?.orgId,
                                    warehouseId = result.data?.data?.get(0)?.warehouseId,
                                    historyApproval = result.data?.data?.get(0)?.historyApproval,
                                    score = result.data?.data?.get(0)?.score,

                                    activityType = SS,
                                    submitType = 1,
                                    comment = ""
                                )

                                updateSs(dataCreateModel, context, owner) { status(it) }

                                Timber.d("###-- Success getDetailSsItem to submit ")
                            }
                            Result.Status.ERROR -> {
                                status(false)

                                Toast.makeText(context, "Error : ${result.message}", Toast.LENGTH_LONG).show()
                                Timber.d("###-- Error getDetailSsItem to submit ")
                            }

                        }
                    }
                })
            }
        } catch (e: Exception) {
            status(false)
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            Timber.d("###-- Error onCreate")
        }
    }

    // update for edit and update proposal
    fun updateSs(
        data: SuggestionSystemCreateModel,
        context: Context,
        owner: LifecycleOwner,
        complete: (Boolean) -> Unit
    ) {
        try {
            listSsViewModel.setPostSubmitUpdateSs(data)
            listSsViewModel.putSubmitUpdateSs.observeEvent(owner) { resultObserve ->
                resultObserve.observe(owner, { result ->
                    if (result != null) {
                        when (result.status) {
                            Result.Status.LOADING -> {
                                HelperLoading.displayLoadingWithText(context, "", false)
                                Timber.d("###-- Loading putSubmitUpdateSs")
                            }
                            Result.Status.SUCCESS -> {
                                HelperLoading.hideLoading()

                                Timber.e("${result.data?.message}")

                                Toast.makeText(context, result.data?.message, Toast.LENGTH_LONG).show()

                                Timber.d("###-- Success putSubmitUpdateSs")

                                complete(true)
                            }
                            Result.Status.ERROR -> {
                                HelperLoading.hideLoading()
                                Toast.makeText(context, result.message, Toast.LENGTH_LONG).show()
                                Timber.d("###-- Error putSubmitUpdateSs")

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
            Timber.d("###-- Error putSubmitUpdateSs")
        }
    }

    // submit for create
    fun submitSs(
        data: SuggestionSystemCreateModel,
        context: Context,
        owner: LifecycleOwner,
        complete: (Boolean) -> Unit
    ){
        try {
            listSsViewModel.setPostSubmitCreateSs(data)
            listSsViewModel.postSubmitCreateSs.observeEvent(owner) { resultObserve ->
                resultObserve.observe(owner, { result ->
                    if (result != null) {
                        when (result.status) {
                            Result.Status.LOADING -> {
                                HelperLoading.displayLoadingWithText(context, "", false)
                                Timber.d("###-- Loading postSubmitCreateSs")
                            }
                            Result.Status.SUCCESS -> {
                                HelperLoading.hideLoading()

                                Timber.e("${result.data?.message}")

                                Toast.makeText(context, result.data?.message, Toast.LENGTH_LONG).show()

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
                })
            }
        }catch (err : Exception){
            HelperLoading.hideLoading()
            Toast.makeText(context, err.message, Toast.LENGTH_LONG).show()

            complete(false)

            Timber.d("###-- Error postSubmitCreateSs")
        }
    }
}