package com.fastrata.eimprovement.featuresglobal.transaction

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementCreateModel
import com.fastrata.eimprovement.features.projectimprovement.data.model.StatusImplementationPiDoneModel
import com.fastrata.eimprovement.features.projectimprovement.data.model.StatusImplementationPiModel
import com.fastrata.eimprovement.features.projectimprovement.data.model.StatusImplementationPiWantModel
import com.fastrata.eimprovement.features.projectimprovement.ui.ProjectImprovementViewModel
import com.fastrata.eimprovement.featuresglobal.data.model.StatusProposalItem
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.workers.service
import kotlinx.coroutines.coroutineScope
import timber.log.Timber

class UpdateStatusProposalPi(
    private val listPiViewModel: ProjectImprovementViewModel,
    private val context: Context,
    ) {

    suspend fun getDetailDataPi(
        id: Int,
        piNo: String,
        statusProposal: StatusProposalItem,
        userNameSubmit: Int,
        status: (Boolean) -> Unit
    ) {
        val dataCreateModel = ProjectImprovementCreateModel(
            id = id,
            piNo = piNo,
            department = "",
            years = "",
            date = "",
            branchCode = "",
            branch = "",
            subBranch = "",
            title = "",
            statusImplementationModel = StatusImplementationPiModel(
                sudah = StatusImplementationPiDoneModel(from = "", to = ""),
                akan = StatusImplementationPiWantModel(
                    startIdentifikasiMasalah = "", endIdentifikasiMasalah = "",
                    startAnalisaData = "", endAnalisaData = "",
                    startAnalisaAkarMasalah = "", endAnalisaAkarMasalah = "",
                    startMenyusunRencana = "", endMenyusunRencana = "",
                    startImplementasiRencana = "", endImplementasiRencana = "",
                    startAnalisPeriksaEvaluasi = "", endAnalisPeriksaEvaluasi = ""
                )
            ),
            identification = "",
            target = "",
            sebabMasalah = null,
            akarMasalah = null,
            nilaiOutput = "",
            nqiModel = null,
            teamMember = null,
            categoryFixing = null,
            implementationResult = "",
            attachment = null,
            statusProposal = statusProposal,
            nik = "",
            headId = 0,
            directMgr = "",
            directMgrNik = "",
            userId = userNameSubmit,
            orgId = 0,
            warehouseId = 0,
            historyApproval = null,
            score = 0,
            activityType = PI,
            submitType = 1,
            comment = ""
        )

        updatePi(dataCreateModel, context) { status(it) }
    }

    // update for edit and update proposal
    suspend fun updatePi(
        data: ProjectImprovementCreateModel,
        context: Context,
        complete: (Boolean) -> Unit
    ){
        HelperLoading.displayLoadingWithText(context, "", false)

        try {
            coroutineScope {
                service().submitUpdatePi(data).let {
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