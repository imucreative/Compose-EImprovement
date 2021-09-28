package com.fastrata.eimprovement.features.projectimprovement.ui.create

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.FragmentProjectImprovementStep6Binding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.features.projectimprovement.callback.ProjectImprovementSystemCreateCallback
import com.fastrata.eimprovement.features.projectimprovement.data.model.*
import com.fastrata.eimprovement.utils.HawkUtils
import com.fastrata.eimprovement.utils.SnackBarCustom
import com.fastrata.eimprovement.utils.Tools
import java.util.*
import javax.inject.Inject

class ProjectImprovStep6Fragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: FragmentProjectImprovementStep6Binding? = null
    private val binding get() = _binding!!
    private var data : ProjectImprovementCreateModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectImprovementStep6Binding.inflate(layoutInflater, container, false)

        data = HawkUtils().getTempDataCreatePi()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProjectImprovementStep6Binding.bind(view)

        setLogicEstimasi()
        setLogicAktual()
        getData()
        setData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
            if (data?.outputValue != null) {
                outputValue.setText(data?.outputValue.toString())

                data?.nqi?.benefit?.nilai_estimasi?.let { estimasiBenefit.setText(it.toString()) }
                data?.nqi?.benefit?.keterangan_estimasi?.let { estimasiBenefitKeterangan.setText(it) }
                data?.nqi?.cost?.nilai_estimasi?.let { estimasiCost.setText(it.toString()) }
                data?.nqi?.cost?.keterangan_estimasi?.let { estimasiCostKeterangan.setText(it) }
                data?.nqi?.nqi?.nilai_estimasi?.let { estimasiNqiTotal.setText(it.toString()) }

                data?.nqi?.benefit?.nilai_aktual?.let { aktualBenefit.setText(it.toString()) }
                data?.nqi?.benefit?.keterangan_aktual?.let { aktualBenefitKeterangan.setText(it) }
                data?.nqi?.cost?.nilai_aktual?.let { aktualCost.setText(it.toString()) }
                data?.nqi?.cost?.keterangan_aktual?.let { aktualCostKeterangan.setText(it) }
                data?.nqi?.nqi?.nilai_aktual?.let { aktualNqiTotal.setText(it.toString()) }
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
                                "Nilai output & benefit must be fill before next",
                                R.drawable.ic_close, R.color.red_500)
                            outputValue.requestFocus()
                            stat = false

                        }
                        estimasiBenefit.text.isNullOrEmpty() -> {
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                "Benefit must be fill before next",
                                R.drawable.ic_close, R.color.red_500)
                            estimasiBenefit.requestFocus()
                            stat = false

                        }
                        estimasiBenefitKeterangan.text.isNullOrEmpty() -> {
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                "Benefit Keterangan must be fill before next",
                                R.drawable.ic_close, R.color.red_500)
                            estimasiBenefitKeterangan.requestFocus()
                            stat = false

                        }
                        estimasiCost.text.isNullOrEmpty() -> {
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                "Cost must be fill before next",
                                R.drawable.ic_close, R.color.red_500)
                            estimasiCost.requestFocus()
                            stat = false

                        }
                        estimasiCostKeterangan.text.isNullOrEmpty() -> {
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                "Cost Keterangan must be fill before next",
                                R.drawable.ic_close, R.color.red_500)
                            estimasiCostKeterangan.requestFocus()
                            stat = false

                        }
                        estimasiNqiTotal.text.isNullOrEmpty() -> {
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                "NQI must be fill before next",
                                R.drawable.ic_close, R.color.red_500)
                            estimasiNqiTotal.requestFocus()
                            stat = false
                        }
                        aktualBenefit.text.isNullOrEmpty() -> {
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                "NQI must be fill before next",
                                R.drawable.ic_close, R.color.red_500)
                            aktualBenefit.requestFocus()
                            stat = false
                        }
                        aktualBenefitKeterangan.text.isNullOrEmpty()-> {
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                "NQI must be fill before next",
                                R.drawable.ic_close, R.color.red_500)
                            aktualBenefitKeterangan.requestFocus()
                            stat = false
                        }
                        aktualCost.text.isNullOrEmpty()-> {
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                "NQI must be fill before next",
                                R.drawable.ic_close, R.color.red_500)
                            aktualCost.requestFocus()
                            stat = false
                        }
                        aktualCostKeterangan.text.isNullOrEmpty() -> {
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                "NQI must be fill before next",
                                R.drawable.ic_close, R.color.red_500)
                            aktualCostKeterangan.requestFocus()
                            stat = false
                        }
                        else -> {
                            val estimasiBenefit = estimasiBenefit.text.toString()
                            val estimasiBenefitKeterangan = estimasiBenefitKeterangan.text.toString()
                            val estimasiCost = estimasiCost.text.toString()
                            val estimasiCostKeterangan = estimasiCostKeterangan.text.toString()
                            val estimasiCqiTotal = estimasiNqiTotal.text.toString()

                            val aktualBenefit = aktualBenefit.text.toString()
                            val aktualBenefitKeterangan = aktualBenefitKeterangan.text.toString()
                            val aktualCost = aktualCost.text.toString()
                            val aktualCostKeterangan = aktualCostKeterangan.text.toString()
                            val aktualNqiTotal = aktualNqiTotal.text.toString()

                            val benefit = Benefit(
                                nilai_estimasi = estimasiBenefit.toInt(),
                                keterangan_estimasi = estimasiBenefitKeterangan,
                                nilai_aktual = aktualBenefit.toInt(),
                                keterangan_aktual = aktualBenefitKeterangan
                            )

                            val cost = Cost(
                                nilai_estimasi = estimasiCost.toInt(),
                                keterangan_estimasi = estimasiCostKeterangan,
                                nilai_aktual = aktualCost.toInt(),
                                keterangan_aktual = aktualCostKeterangan
                            )

                            val nqiTotal = NQI(
                                nilai_estimasi = estimasiCqiTotal.toInt(),
                                nilai_aktual = aktualNqiTotal.toInt(),
                            )

                            val nqi = NQIModel(
                                benefit = benefit,
                                cost = cost,
                                nqi = nqiTotal
                            )

                            HawkUtils().setTempDataCreatePi(
                                nilaiOutput = outputValue.text.toString(),
                                perhitunganNqi = nqi
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