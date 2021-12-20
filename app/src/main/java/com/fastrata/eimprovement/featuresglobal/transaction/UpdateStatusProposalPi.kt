package com.fastrata.eimprovement.featuresglobal.transaction

import android.content.Context
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.lifecycle.LifecycleOwner
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementCreateModel
import com.fastrata.eimprovement.features.projectimprovement.ui.ProjectImprovementViewModel
import com.fastrata.eimprovement.utils.*
import timber.log.Timber

class UpdateStatusProposalPi(
    private val listPiViewModel: ProjectImprovementViewModel,
    private val context: Context,
    private val owner: LifecycleOwner
    ) {
    fun getDetailDataPi(
        id: Int,
        userId: Int,
        userNameSubmit: Int,
        status: (Boolean) -> Unit
    ) {
        try {
            listPiViewModel.setDetailPi(id, userId)

            listPiViewModel.getDetailPiItem.observeEvent(owner) { resultObserve ->
                resultObserve.observe(owner, { result ->
                    if (result != null) {
                        when (result.status) {
                            Result.Status.LOADING -> {
                                Timber.d("###-- Loading getDetailPiItem to submit ")
                            }
                            Result.Status.SUCCESS -> {

                                val dataCreateModel = ProjectImprovementCreateModel(
                                    id = result.data?.data?.get(0)?.id,
                                    piNo = result.data?.data?.get(0)?.piNo,
                                    department = result.data?.data?.get(0)?.department,
                                    years = result.data?.data?.get(0)?.years,
                                    date = result.data?.data?.get(0)?.date,
                                    branchCode = result.data?.data?.get(0)?.branchCode,
                                    branch = result.data?.data?.get(0)?.branch,
                                    subBranch = result.data?.data?.get(0)?.subBranch,
                                    title = result.data?.data?.get(0)?.title,
                                    statusImplementationModel = result.data?.data?.get(0)?.statusImplementationModel,
                                    identification = result.data?.data?.get(0)?.identification?.let {
                                        HtmlCompat.fromHtml(
                                            it, HtmlCompat.FROM_HTML_MODE_LEGACY
                                        ).toString()
                                    },
                                    target = result.data?.data?.get(0)?.target?.let {
                                        HtmlCompat.fromHtml(
                                            it, HtmlCompat.FROM_HTML_MODE_LEGACY
                                        ).toString()
                                    },
                                    sebabMasalah = result.data?.data?.get(0)?.sebabMasalah,
                                    akarMasalah = result.data?.data?.get(0)?.akarMasalah,
                                    nilaiOutput = result.data?.data?.get(0)?.nilaiOutput?.let {
                                        HtmlCompat.fromHtml(
                                            it, HtmlCompat.FROM_HTML_MODE_LEGACY
                                        ).toString()
                                    },
                                    nqiModel = result.data?.data?.get(0)?.nqiModel,
                                    teamMember = result.data?.data?.get(0)?.teamMember,
                                    categoryFixing = result.data?.data?.get(0)?.categoryFixing,
                                    implementationResult = result.data?.data?.get(0)?.implementationResult?.let {
                                        HtmlCompat.fromHtml(
                                            it, HtmlCompat.FROM_HTML_MODE_LEGACY
                                        ).toString()
                                    },
                                    attachment = result.data?.data?.get(0)?.attachment,
                                    statusProposal = result.data?.data?.get(0)?.statusProposal,
                                    nik = result.data?.data?.get(0)?.nik,
                                    headId = result.data?.data?.get(0)?.headId,
                                    directMgr = result.data?.data?.get(0)?.directMgr,
                                    directMgrNik = result.data?.data?.get(0)?.directMgrNik,
                                    userId = userNameSubmit,
                                    orgId = result.data?.data?.get(0)?.orgId,
                                    warehouseId = result.data?.data?.get(0)?.warehouseId,
                                    historyApproval = result.data?.data?.get(0)?.historyApproval,
                                    score = result.data?.data?.get(0)?.score,

                                    activityType = PI,
                                    submitType = 1,
                                    comment = ""
                                )

                                updatePi(dataCreateModel, context, owner) { status(it) }

                                Timber.d("###-- Success getDetailPiItem to submit ")
                            }
                            Result.Status.ERROR -> {
                                status(false)

                                Toast.makeText(context, "Error : ${result.message}", Toast.LENGTH_LONG).show()
                                Timber.d("###-- Error getDetailPiItem to submit ")
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
    fun updatePi(
        data: ProjectImprovementCreateModel,
        context: Context,
        owner: LifecycleOwner,
        complete: (Boolean) -> Unit
    ){
        try {
            listPiViewModel.setPostSubmitUpdatePi(data)
            listPiViewModel.putSubmitUpdatePi.observeEvent(owner) { resultObserve ->
                resultObserve.observe(owner, { result ->
                    if (result != null) {
                        when (result.status) {
                            Result.Status.LOADING -> {
                                HelperLoading.displayLoadingWithText(context, "", false)
                                Timber.d("###-- Loading putSubmitUpdatePi")
                            }
                            Result.Status.SUCCESS -> {
                                HelperLoading.hideLoading()

                                Timber.e("${result.data?.message}")

                                Toast.makeText(context, result.data?.message, Toast.LENGTH_LONG).show()

                                Timber.d("###-- Success putSubmitUpdatePi")

                                complete(true)
                            }
                            Result.Status.ERROR -> {
                                HelperLoading.hideLoading()
                                Toast.makeText(context, result.message, Toast.LENGTH_LONG).show()
                                Timber.d("###-- Error putSubmitUpdatePi")

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
            Timber.d("###-- Error putSubmitUpdatePi")
        }
    }

    // submit for create
    fun submitPi(
        data: ProjectImprovementCreateModel,
        action: String,
        context: Context,
        owner: LifecycleOwner,
        complete: (Boolean) -> Unit
    ) {
        try {
            listPiViewModel.setPostSubmitCreatePi(data, action)
            listPiViewModel.postSubmitCreatePi.observeEvent(owner) { resultObserve ->
                resultObserve.observe(owner, { result ->
                    if (result != null) {
                        when (result.status) {
                            Result.Status.LOADING -> {
                                HelperLoading.displayLoadingWithText(context, "", false)
                                Timber.d("###-- Loading postSubmitCreatePi")
                            }
                            Result.Status.SUCCESS -> {
                                HelperLoading.hideLoading()

                                Timber.e("result : ${result.data?.message}")
                                Timber.e("result success : ${result.data?.success}")
                                Toast.makeText(context, result.data?.message.toString(), Toast.LENGTH_LONG).show()

                                complete(true)

                                Timber.d("###-- Success postSubmitCreatePi")
                            }
                            Result.Status.ERROR -> {
                                HelperLoading.hideLoading()
                                Toast.makeText(context, result.message, Toast.LENGTH_LONG).show()

                                complete(false)

                                Timber.d("###-- Success postSubmitCreatePi")
                            }
                        }
                    }
                })
            }
        }catch (err : Exception){
            HelperLoading.hideLoading()
            Toast.makeText(context, err.message, Toast.LENGTH_LONG).show()

            complete(false)

            Timber.d("###-- Success postSubmitCreatePi")
        }
    }
}