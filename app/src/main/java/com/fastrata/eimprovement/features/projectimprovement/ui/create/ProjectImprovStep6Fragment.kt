package com.fastrata.eimprovement.features.projectimprovement.ui.create

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.FragmentProjectImprovementStep6Binding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.features.projectimprovement.callback.ProjectImprovementSystemCreateCallback
import com.fastrata.eimprovement.features.projectimprovement.data.model.*
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.utils.HawkUtils
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class ProjectImprovStep6Fragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: FragmentProjectImprovementStep6Binding? = null
    private val binding get() = _binding!!
    private var data : ProjectImprovementCreateModel? = null
    private var piNo: String? = ""
    private var action: String? = ""
    private var source: String = PI_CREATE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectImprovementStep6Binding.inflate(layoutInflater, container, false)

        piNo = arguments?.getString(PI_DETAIL_DATA)
        action = arguments?.getString(ACTION_DETAIL_DATA)

        source = if (piNo == "") PI_CREATE else PI_DETAIL_DATA

        data = HawkUtils().getTempDataCreatePi(source)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProjectImprovementStep6Binding.bind(view)

        setLogic()
        setLogicEstimasi()
        setLogicAktual()
        getData()
        setData()

        if ((action == APPROVE) || (action == DETAIL)) {
            disableForm()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun disableForm() {
        binding.apply {
            outputValue.isEnabled = false
            estimasiBenefit.isEnabled = false
            estimasiBenefitKeterangan.isEnabled = false
            estimasiCost.isEnabled = false
            estimasiCostKeterangan.isEnabled = false
            estimasiNqiTotal.isEnabled = false

            aktualBenefit.isEnabled = false
            aktualBenefitKeterangan.isEnabled = false
            aktualCost.isEnabled = false
            aktualCostKeterangan.isEnabled = false
            aktualNqiTotal.isEnabled = false
        }
    }

    private fun setLogic() {
        Timber.e("Id Status proposal :  ${data?.statusProposal?.id}")
        binding.apply {
            when {
                data?.statusProposal?.id == STATUS_IMPLEMENTASI -> {
                    estimasiBenefit.isEnabled = false
                    edtLayoutEstimasiBenefit.boxBackgroundColor = ContextCompat.getColor(requireContext(), R.color.grey_10)
                    estimasiBenefitKeterangan.isEnabled = false
                    edtLayoutEstimasiBenefitKeterangan.boxBackgroundColor = ContextCompat.getColor(requireContext(), R.color.grey_10)
                    estimasiCost.isEnabled = false
                    edtLayoutEstimasiCost.boxBackgroundColor = ContextCompat.getColor(requireContext(), R.color.grey_10)
                    estimasiCostKeterangan.isEnabled = false
                    edtLayoutEstimasiCostKeterangan.boxBackgroundColor = ContextCompat.getColor(requireContext(), R.color.grey_10)
                    estimasiNqiTotal.isEnabled = false
                    edtLayoutEstimasiNqi.boxBackgroundColor = ContextCompat.getColor(requireContext(), R.color.grey_10)

                    aktualBenefit.isEnabled = true
                    aktualBenefitKeterangan.isEnabled = true
                    aktualCost.isEnabled = true
                    aktualCostKeterangan.isEnabled = true
                }
                data?.statusProposal != null -> {
                    estimasiBenefit.isEnabled = true
                    estimasiBenefitKeterangan.isEnabled = true
                    estimasiCost.isEnabled = true
                    estimasiCostKeterangan.isEnabled = true

                    aktualBenefit.isEnabled = false
                    edtLayoutAktualBenefit.boxBackgroundColor = ContextCompat.getColor(requireContext(), R.color.grey_10)
                    aktualBenefitKeterangan.isEnabled = false
                    edtLayoutAktualBenefitKeterangan.boxBackgroundColor = ContextCompat.getColor(requireContext(), R.color.grey_10)
                    aktualCost.isEnabled = false
                    edtLayoutAktualCost.boxBackgroundColor = ContextCompat.getColor(requireContext(), R.color.grey_10)
                    aktualCostKeterangan.isEnabled = false
                    edtLayoutAktualCostKeterangan.boxBackgroundColor = ContextCompat.getColor(requireContext(), R.color.grey_10)
                    aktualNqiTotal.isEnabled = false
                    edtLayoutAktualNqi.boxBackgroundColor = ContextCompat.getColor(requireContext(), R.color.grey_10)
                }
                else -> {
                    estimasiBenefit.isEnabled = false
                    edtLayoutEstimasiBenefit.boxBackgroundColor = ContextCompat.getColor(requireContext(), R.color.grey_10)
                    estimasiBenefitKeterangan.isEnabled = false
                    edtLayoutEstimasiBenefitKeterangan.boxBackgroundColor = ContextCompat.getColor(requireContext(), R.color.grey_10)
                    estimasiCost.isEnabled = false
                    edtLayoutEstimasiCost.boxBackgroundColor = ContextCompat.getColor(requireContext(), R.color.grey_10)
                    estimasiCostKeterangan.isEnabled = false
                    edtLayoutEstimasiCostKeterangan.boxBackgroundColor = ContextCompat.getColor(requireContext(), R.color.grey_10)
                    estimasiNqiTotal.isEnabled = false
                    edtLayoutEstimasiNqi.boxBackgroundColor = ContextCompat.getColor(requireContext(), R.color.grey_10)

                    aktualBenefit.isEnabled = false
                    edtLayoutAktualBenefit.boxBackgroundColor = ContextCompat.getColor(requireContext(), R.color.grey_10)
                    aktualBenefitKeterangan.isEnabled = false
                    edtLayoutAktualBenefitKeterangan.boxBackgroundColor = ContextCompat.getColor(requireContext(), R.color.grey_10)
                    aktualCost.isEnabled = false
                    edtLayoutAktualCost.boxBackgroundColor = ContextCompat.getColor(requireContext(), R.color.grey_10)
                    aktualCostKeterangan.isEnabled = false
                    edtLayoutAktualCostKeterangan.boxBackgroundColor = ContextCompat.getColor(requireContext(), R.color.grey_10)
                    aktualNqiTotal.isEnabled = false
                    edtLayoutAktualNqi.boxBackgroundColor = ContextCompat.getColor(requireContext(), R.color.grey_10)
                }
            }
        }
    }

    private fun setLogicEstimasi() {
        var timer: Timer? = null
        binding.apply {
            estimasiBenefit.addTextChangedListener(object : TextWatcher{
                override fun afterTextChanged(s: Editable?) {
                    timer = Timer()
                    timer!!.schedule(object : TimerTask() {
                        override fun run() {
                            Handler(Looper.getMainLooper()).post {
                                val sum = Tools.sumValues(estimasiBenefit.text.toString(), estimasiCost.text.toString())
                                estimasiNqiTotal.setText(sum)
                            }
                        }
                    }, 1000)
                }

                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {}

                override fun onTextChanged(
                    s: CharSequence?, start: Int, before: Int, count: Int
                ) {
                    timer?.cancel()
                }
            })

            estimasiCost.addTextChangedListener(object : TextWatcher{
                override fun afterTextChanged(s: Editable?) {
                    timer = Timer()
                    timer!!.schedule(object : TimerTask() {
                        override fun run() {
                            Handler(Looper.getMainLooper()).post {
                                val sum = Tools.sumValues(estimasiCost.text.toString(), estimasiBenefit.text.toString())
                                estimasiNqiTotal.setText(sum)
                            }
                        }
                    }, 1000)
                }

                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {}

                override fun onTextChanged(
                    s: CharSequence?, start: Int, before: Int, count: Int
                ) {
                    timer?.cancel()
                }
            })
        }
    }

    private fun setLogicAktual() {
        var timer: Timer? = null
        binding.apply {
            aktualBenefit.addTextChangedListener(object : TextWatcher{
                override fun afterTextChanged(s: Editable?) {
                    timer = Timer()
                    timer!!.schedule(object : TimerTask() {
                        override fun run() {
                            Handler(Looper.getMainLooper()).post {
                                val sum = Tools.sumValues(aktualBenefit.text.toString(), aktualCost.text.toString())
                                aktualNqiTotal.setText(sum)
                            }
                        }
                    }, 1000)
                }

                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {}

                override fun onTextChanged(
                    s: CharSequence?, start: Int, before: Int, count: Int
                ) {
                    timer?.cancel()
                }
            })

            aktualCost.addTextChangedListener(object : TextWatcher{
                override fun afterTextChanged(s: Editable?) {
                    timer = Timer()
                    timer!!.schedule(object : TimerTask() {
                        override fun run() {
                            Handler(Looper.getMainLooper()).post {
                                val sum = Tools.sumValues(aktualCost.text.toString(), aktualBenefit.text.toString())
                                aktualNqiTotal.setText(sum)
                            }
                        }
                    }, 1000)
                }

                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {}

                override fun onTextChanged(
                    s: CharSequence?, start: Int, before: Int, count: Int
                ) {
                    timer?.cancel()
                }
            })
        }
    }

    private fun getData() {
        binding.apply {
            if (data?.nilaiOutput != null) {
                outputValue.setText(data?.nilaiOutput.toString())

                data?.nqiModel?.estimasiModel?.benefit?.let { estimasiBenefit.setText(it.toString()) }
                data?.nqiModel?.estimasiModel?.benefit_keterangan?.let { estimasiBenefitKeterangan.setText(it) }
                data?.nqiModel?.estimasiModel?.cost?.let { estimasiCost.setText(it.toString()) }
                data?.nqiModel?.estimasiModel?.cost_keterangan?.let { estimasiCostKeterangan.setText(it) }
                data?.nqiModel?.estimasiModel?.nqi?.let { estimasiNqiTotal.setText(it.toString()) }

                data?.nqiModel?.aktualModel?.benefit?.let { aktualBenefit.setText(it.toString()) }
                data?.nqiModel?.aktualModel?.benefit_keterangan?.let { aktualBenefitKeterangan.setText(it) }
                data?.nqiModel?.aktualModel?.cost?.let { aktualCost.setText(it.toString()) }
                data?.nqiModel?.aktualModel?.cost_keterangan?.let { aktualCostKeterangan.setText(it) }
                data?.nqiModel?.aktualModel?.nqi?.let { aktualNqiTotal.setText(it.toString()) }
            }
        }
    }

    private fun setData() {
        (activity as ProjectImprovementCreateWizard).setPiCreateCallback(object :
            ProjectImprovementSystemCreateCallback {
            override fun onDataPass(): Boolean {
                var stat: Boolean
                binding.apply {
                    when {
                        outputValue.text.isNullOrEmpty() -> {
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                resources.getString(R.string.value_estimasi_empty),
                                R.drawable.ic_close, R.color.red_500)
                            outputValue.requestFocus()
                            stat = false
                        }

                        estimasiBenefit.text.isNullOrEmpty() && data?.statusProposal == null -> {
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                resources.getString(R.string.value_estimasi_empty),
                                R.drawable.ic_close, R.color.red_500)
                            estimasiBenefit.requestFocus()
                            stat = false
                        }
                        estimasiBenefitKeterangan.text.isNullOrEmpty() && data?.statusProposal == null -> {
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                resources.getString(R.string.value_estimasi_empty),
                                R.drawable.ic_close, R.color.red_500)
                            estimasiBenefitKeterangan.requestFocus()
                            stat = false
                        }
                        estimasiCost.text.isNullOrEmpty() && data?.statusProposal == null -> {
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                resources.getString(R.string.value_estimasi_empty),
                                R.drawable.ic_close, R.color.red_500)
                            estimasiCost.requestFocus()
                            stat = false
                        }
                        estimasiCostKeterangan.text.isNullOrEmpty() && data?.statusProposal == null -> {
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                resources.getString(R.string.value_estimasi_empty),
                                R.drawable.ic_close, R.color.red_500)
                            estimasiCostKeterangan.requestFocus()
                            stat = false
                        }
                        estimasiNqiTotal.text.isNullOrEmpty() && data?.statusProposal == null -> {
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                resources.getString(R.string.value_estimasi_empty),
                                R.drawable.ic_close, R.color.red_500)
                            estimasiNqiTotal.requestFocus()
                            stat = false
                        }

                        aktualBenefit.text.isNullOrEmpty() && data?.statusProposal?.id == STATUS_IMPLEMENTASI && action == SUBMIT_PROPOSAL -> {
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                resources.getString(R.string.value_Aktual_empty),
                                R.drawable.ic_close, R.color.red_500)
                            aktualBenefit.requestFocus()
                            stat = false
                        }
                        aktualBenefitKeterangan.text.isNullOrEmpty() && data?.statusProposal?.id == STATUS_IMPLEMENTASI && action == SUBMIT_PROPOSAL -> {
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                resources.getString(R.string.value_Aktual_empty),
                                R.drawable.ic_close, R.color.red_500)
                            aktualBenefitKeterangan.requestFocus()
                            stat = false
                        }
                        aktualCost.text.isNullOrEmpty() && data?.statusProposal?.id == STATUS_IMPLEMENTASI && action == SUBMIT_PROPOSAL -> {
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                resources.getString(R.string.value_Aktual_empty),
                                R.drawable.ic_close, R.color.red_500)
                            aktualCost.requestFocus()
                            stat = false
                        }
                        aktualCostKeterangan.text.isNullOrEmpty() && data?.statusProposal?.id == STATUS_IMPLEMENTASI && action == SUBMIT_PROPOSAL -> {
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                resources.getString(R.string.value_Aktual_empty),
                                R.drawable.ic_close, R.color.red_500)
                            aktualCostKeterangan.requestFocus()
                            stat = false
                        }
                        aktualNqiTotal.text.isNullOrEmpty() && data?.statusProposal?.id == STATUS_IMPLEMENTASI && action == SUBMIT_PROPOSAL -> {
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                resources.getString(R.string.value_Aktual_empty),
                                R.drawable.ic_close, R.color.red_500)
                            aktualNqiTotal.requestFocus()
                            stat = false
                        }
                        else -> {
                            var estimasiModel: NqiEstimasiModel? = null
                            var aktualModel: NqiAktualModel? = null

                            val estimasiBenefit = estimasiBenefit.text.toString()
                            val estimasiBenefitKeterangan = estimasiBenefitKeterangan.text.toString()
                            val estimasiCost = estimasiCost.text.toString()
                            val estimasiCostKeterangan = estimasiCostKeterangan.text.toString()
                            val estimasiNqiTotal = estimasiNqiTotal.text.toString()

                            estimasiModel = NqiEstimasiModel(
                                benefit = if (estimasiBenefit.isNotEmpty()) estimasiBenefit.toInt() else null,
                                benefit_keterangan = if (estimasiBenefitKeterangan.isNotEmpty()) estimasiBenefitKeterangan else null,
                                cost = if (estimasiCost.isNotEmpty()) estimasiCost.toInt() else null,
                                cost_keterangan = if (estimasiCostKeterangan.isNotEmpty()) estimasiCostKeterangan else null,
                                nqi = if (estimasiNqiTotal.isNotEmpty()) estimasiNqiTotal.toInt() else null
                            )

                            val aktualBenefit = aktualBenefit.text.toString()
                            val aktualBenefitKeterangan = aktualBenefitKeterangan.text.toString()
                            val aktualCost = aktualCost.text.toString()
                            val aktualCostKeterangan = aktualCostKeterangan.text.toString()
                            val aktualNqiTotal = aktualNqiTotal.text.toString()

                            aktualModel = NqiAktualModel(
                                benefit = if (aktualBenefit.isNotEmpty()) aktualBenefit.toInt() else null,
                                benefit_keterangan = if (aktualBenefitKeterangan.isNotEmpty()) aktualBenefitKeterangan else null,
                                cost = if (aktualCost.isNotEmpty()) aktualCost.toInt() else null,
                                cost_keterangan = if (aktualCostKeterangan.isNotEmpty()) aktualCostKeterangan else null,
                                nqi = if (aktualNqiTotal.isNotEmpty()) aktualNqiTotal.toInt() else null,
                            )

                            val nqi = NqiModel(
                                estimasiModel = estimasiModel,
                                aktualModel = aktualModel
                            )

                            HawkUtils().setTempDataCreatePi(
                                id = data?.id,
                                piNo = data?.piNo,
                                date = data?.date,
                                title = data?.title,
                                branch = data?.branch,
                                subBranch = data?.subBranch,
                                department = data?.department,
                                years = data?.years,
                                statusImplementationModel = data?.statusImplementationModel,
                                identification = data?.identification,
                                target = data?.target,
                                sebabMasalah = data?.sebabMasalah,
                                akarMasalah = data?.akarMasalah,
                                nilaiOutput = outputValue.text.toString(),
                                nqiModel = nqi,
                                teamMember = data?.teamMember,
                                categoryFixing = data?.categoryFixing,
                                hasilImplementasi = data?.implementationResult,
                                attachment = data?.attachment,
                                statusProposal = data?.statusProposal,
                                headId = data?.headId,
                                userId = data?.userId,
                                orgId = data?.orgId,
                                warehouseId = data?.warehouseId,
                                historyApproval = data?.historyApproval,
                                activityType = data?.activityType,
                                submitType = data?.submitType,
                                comment = data?.comment,
                                source = source
                            )
                            stat = true
                        }
                    }
                }
                return stat
            }
        })
    }
}