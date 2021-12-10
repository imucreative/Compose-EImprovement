package com.fastrata.eimprovement.featuresglobal.transaction

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointCreateModel
import com.fastrata.eimprovement.features.changespoint.ui.ChangesPointCreateViewModel
import com.fastrata.eimprovement.utils.*
import timber.log.Timber

class UpdateStatusProposalCp(
    private val listCpViewModel: ChangesPointCreateViewModel,
    private val context: Context,
    private val owner: LifecycleOwner
) {
    fun getDetailDataCp(
        id: Int,
        userId: Int,
        userNameSubmit: Int,
        status: (Boolean) -> Unit
    ) {
        try {
            listCpViewModel.setDetailCp(id, userId)

            listCpViewModel.getDetailCp.observeEvent(owner) { resultObserve ->
                resultObserve.observe(owner, { result ->
                    if (result != null) {
                        when (result.status) {
                            Result.Status.LOADING -> {
                                Timber.d("###-- Loading getDetailRpItem to submit ")
                            }
                            Result.Status.SUCCESS -> {
                                val dataCreateModel = ChangePointCreateModel(
                                    id = result.data?.data?.get(0)?.id,
                                    cpNo = result.data?.data?.get(0)?.cpNo,
                                    saldo = result.data?.data?.get(0)?.saldo,
                                    name = result.data?.data?.get(0)?.name,
                                    nik = result.data?.data?.get(0)?.nik,
                                    branch = result.data?.data?.get(0)?.branch,
                                    subBranch = result.data?.data?.get(0)?.subBranch,
                                    branchCode = result.data?.data?.get(0)?.branchCode,
                                    department = result.data?.data?.get(0)?.department,
                                    position = result.data?.data?.get(0)?.position,
                                    date = result.data?.data?.get(0)?.date,
                                    description = result.data?.data?.get(0)?.description,
                                    reward = result.data?.data?.get(0)?.reward,
                                    statusProposal = result.data?.data?.get(0)?.statusProposal,

                                    headId = result.data?.data?.get(0)?.headId,
                                    userId = userNameSubmit,
                                    orgId = result.data?.data?.get(0)?.orgId,
                                    warehouseId = result.data?.data?.get(0)?.warehouseId,
                                    historyApproval = result.data?.data?.get(0)?.historyApproval,

                                    activityType = CP,
                                    submitType = 1,
                                    comment = ""
                                )

                                updateCp(dataCreateModel, context, owner) { status(it) }

                                Timber.d("###-- Success getDetailRpItem to submit $dataCreateModel")
                            }
                            Result.Status.ERROR -> {
                                status(false)

                                Toast.makeText(context, "Error : ${result.message}", Toast.LENGTH_LONG).show()
                                Timber.d("###-- Error getDetailRpItem to submit ")
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