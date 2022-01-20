package com.fastrata.eimprovement.features.projectimprovement.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.FragmentProjectImprovementStep2Binding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.features.projectimprovement.callback.ProjectImprovementSystemCreateCallback
import com.fastrata.eimprovement.features.projectimprovement.data.model.*
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.utils.HawkUtils
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ProjectImprovStep2Fragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: FragmentProjectImprovementStep2Binding? = null
    private val binding get() = _binding!!
    private var data : ProjectImprovementCreateModel? = null
    private var piNo: String? = ""
    private var action: String? = ""
    private var source: String = PI_CREATE
    private var edtResult : String = ""
    private val formatDateDisplay = SimpleDateFormat("dd/MM/yyyy")
    private val formatDateOriginalValue = SimpleDateFormat("yyyy-MM-dd")
    private lateinit var fromDate: Date
    private lateinit var toDate: Date
    private lateinit var fromIdentifikasi: Date
    private lateinit var toIdentifikasi: Date
    private lateinit var fromAnalisa: Date
    private lateinit var toAnalisa: Date
    private lateinit var fromAnalisaAkarMasalah: Date
    private lateinit var toAnalisaAkarMasalah: Date
    private lateinit var fromMenyusunRencana: Date
    private lateinit var toMenyusunRencana: Date
    private lateinit var fromImplementasiRencana: Date
    private lateinit var toImplementasiRencana: Date
    private lateinit var fromAnalisaPeriksaEvaluasi: Date
    private lateinit var toAnalisaPeriksaEvaluasi: Date
    private lateinit var datePickerSudah: DatePickerCustom
    private lateinit var datePickerAkan: DatePickerCustom

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectImprovementStep2Binding.inflate(inflater, container, false)

        piNo = arguments?.getString(PI_DETAIL_DATA)
        action = arguments?.getString(ACTION_DETAIL_DATA)

        source = if (piNo == "") PI_CREATE else PI_DETAIL_DATA

        data = HawkUtils().getTempDataCreatePi(source)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProjectImprovementStep2Binding.bind(view)

        datePickerSudah = activity?.let {
            DatePickerCustom(
                context = binding.root.context,themeDark = true,
                minDateIsCurrentDate = false,it.supportFragmentManager
            )
        }!!
        datePickerAkan = activity?.let {
            DatePickerCustom(
                context = binding.root.context,themeDark = true,
                minDateIsCurrentDate = true,it.supportFragmentManager
            )
        }!!
        setLogic()

        if (data?.statusImplementationModel != null) {
            getData()
        }

        initComponent()
        setData()

        when (action){
            APPROVE, DETAIL -> disableForm()
        }

        when {
            conditionImplementation() -> disableForm()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun disableForm() {
        binding.apply {
            rbStatus1.isClickable = false
            etFromStatus1.isEnabled = false
            etToStatus1.isEnabled = false

            rbStatus2.isClickable = false
            etFromIdentifikasi.isEnabled = false
            etToIdentifikasi.isEnabled = false

            etFromAnalisaData.isEnabled = false
            etToAnalisaData.isEnabled = false

            etFromAnalisaAkarMasalah.isEnabled = false
            etToAnalisaAkarMasalah.isEnabled = false

            etFromMenyusunRencanaPenanggulanganMasalah.isEnabled = false
            etToMenyusunRencanaPenanggulanganMasalah.isEnabled = false

            etFromImplementasiRencanaPerbaikan.isEnabled = false
            etToImplementasiRencanaPerbaikan.isEnabled = false

            etFromAnalisaPeriksaDanEvaluasi.isEnabled = false
            etToAnalisaPeriksaDanEvaluasi.isEnabled = false
        }
    }

    private fun conditionImplementation(): Boolean {
        return when (data?.statusProposal?.id) {
            6, 9 -> {
                true
            }
            else -> {
                false
            }
        }
    }

    private fun setLogic() {
        binding.run {
            edtResult = if (data?.implementationResult == "null" || data?.implementationResult == null) "" else data?.implementationResult.toString()

            etFromStatus1.isEnabled = false
            etToStatus1.isEnabled = false

            linearLayoutAkan.visibility = View.GONE

            rbStatus1.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    etFromStatus1.isEnabled = true
                    etToStatus1.isEnabled = true

                    linearLayoutAkan.visibility = View.GONE
                    HawkUtils().setStatusImplementation(true)
                }
            }

            rbStatus2.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    etFromStatus1.isEnabled = false
                    etToStatus1.isEnabled = false
                    etFromStatus1.setText("")
                    etToStatus1.setText("")

                    when (action) {
                        ADD, EDIT -> {
                            edtResult = ""
                        }
                    }
                    linearLayoutAkan.visibility = View.VISIBLE
                    HawkUtils().setStatusImplementation(false)
                }
            }
        }
    }

    private fun initComponent() {
        binding.apply {
            etFromStatus1.setOnClickListener {
                datePickerSudah.showDialog(object : DatePickerCustom.Callback {
                    override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                        val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                        val mon = month + 1
                        val monthStr = if (mon < 10) "0$mon" else "$mon"

                        fromDate = formatDateOriginalValue.parse("$year-$monthStr-$dayStr")
                        etFromStatus1.setText(formatDateDisplay.format(fromDate))

                        etToStatus1.text!!.clear()
                    }
                })
            }

            etToStatus1.setOnClickListener {
                activity?.let {
                    datePickerSudah.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"

                            toDate = formatDateOriginalValue.parse("$year-$monthStr-$dayStr")

                            if (etFromStatus1.text.isNullOrEmpty() || !toDate.after(fromDate)){
                                SnackBarCustom.snackBarIconInfo(
                                    root, layoutInflater, resources, root.context,
                                    resources.getString(R.string.wrong_field),
                                    R.drawable.ic_close, R.color.red_500)
                                etToStatus1.text!!.clear()
                            }else{
                                etToStatus1.setText(formatDateDisplay.format(toDate))
                            }

                        }
                    })
                }
            }

            etFromIdentifikasi.setOnClickListener {
                activity?.let {
                    datePickerAkan.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"

                            fromIdentifikasi = formatDateOriginalValue.parse("$year-$monthStr-$dayStr")
                            etFromIdentifikasi.setText(formatDateDisplay.format(fromIdentifikasi))

                            etToIdentifikasi.text!!.clear()
                        }
                    })
                }
            }

            etToIdentifikasi.setOnClickListener {
                activity?.let {
                    datePickerAkan.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"

                            toIdentifikasi = formatDateOriginalValue.parse("$year-$monthStr-$dayStr")

                            if (etFromIdentifikasi.text.isNullOrEmpty() || !toIdentifikasi.after(fromIdentifikasi)){
                                SnackBarCustom.snackBarIconInfo(
                                    root, layoutInflater, resources, root.context,
                                    resources.getString(R.string.wrong_field),
                                    R.drawable.ic_close, R.color.red_500)
                                etToIdentifikasi.text!!.clear()
                            } else {
                                etToIdentifikasi.setText(formatDateDisplay.format(toIdentifikasi))
                            }
                        }
                    })
                }
            }

            etFromAnalisaData.setOnClickListener {
                activity?.let {
                    datePickerAkan.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"

                            fromAnalisa = formatDateOriginalValue.parse("$year-$monthStr-$dayStr")
                            etFromAnalisaData.setText(formatDateDisplay.format(fromAnalisa))

                            etToAnalisaData.text!!.clear()
                        }
                    })
                }
            }

            etToAnalisaData.setOnClickListener {
                activity?.let {
                    datePickerAkan.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"

                            toAnalisa = formatDateOriginalValue.parse("$year-$monthStr-$dayStr")

                            if (etFromAnalisaData.text.isNullOrEmpty() || !toAnalisa.after(fromAnalisa)){
                                SnackBarCustom.snackBarIconInfo(
                                    root, layoutInflater, resources, root.context,
                                    resources.getString(R.string.wrong_field),
                                    R.drawable.ic_close, R.color.red_500)
                                etToAnalisaData.text!!.clear()
                            } else {
                                etToAnalisaData.setText(formatDateDisplay.format(toAnalisa))
                            }
                        }
                    })
                }
            }

            etFromAnalisaAkarMasalah.setOnClickListener {
                activity?.let {
                    datePickerAkan.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"

                            fromAnalisaAkarMasalah = formatDateOriginalValue.parse("$year-$monthStr-$dayStr")
                            etFromAnalisaAkarMasalah.setText(formatDateDisplay.format(fromAnalisaAkarMasalah))

                            etToAnalisaAkarMasalah.text!!.clear()
                        }
                    })
                }
            }

            etToAnalisaAkarMasalah.setOnClickListener {
                activity?.let {
                    datePickerAkan.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"

                            toAnalisaAkarMasalah = formatDateOriginalValue.parse("$year-$monthStr-$dayStr")

                            if (etFromAnalisaAkarMasalah.text.isNullOrEmpty() || !toAnalisaAkarMasalah.after(fromAnalisaAkarMasalah)){
                                SnackBarCustom.snackBarIconInfo(
                                    root, layoutInflater, resources, root.context,
                                    resources.getString(R.string.wrong_field),
                                    R.drawable.ic_close, R.color.red_500)
                                etToAnalisaAkarMasalah.text!!.clear()
                            } else {
                                etToAnalisaAkarMasalah.setText(formatDateDisplay.format(toAnalisaAkarMasalah))
                            }
                        }
                    })
                }
            }

            etFromMenyusunRencanaPenanggulanganMasalah.setOnClickListener {
                activity?.let {
                    datePickerAkan.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"

                            fromMenyusunRencana = formatDateOriginalValue.parse("$year-$monthStr-$dayStr")
                            etFromMenyusunRencanaPenanggulanganMasalah.setText(formatDateDisplay.format(fromMenyusunRencana))

                            etToMenyusunRencanaPenanggulanganMasalah.text!!.clear()
                        }
                    })
                }
            }

            etToMenyusunRencanaPenanggulanganMasalah.setOnClickListener {
                activity?.let {
                    datePickerAkan.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"

                            toMenyusunRencana = formatDateOriginalValue.parse("$year-$monthStr-$dayStr")

                            if (etFromMenyusunRencanaPenanggulanganMasalah.text.isNullOrEmpty() || !toMenyusunRencana.after(fromMenyusunRencana)){
                                SnackBarCustom.snackBarIconInfo(
                                    root, layoutInflater, resources, root.context,
                                    resources.getString(R.string.wrong_field),
                                    R.drawable.ic_close, R.color.red_500)
                                etToMenyusunRencanaPenanggulanganMasalah.text!!.clear()
                            } else {
                                etToMenyusunRencanaPenanggulanganMasalah.setText(formatDateDisplay.format(toMenyusunRencana))
                            }
                        }
                    })
                }
            }

            etFromImplementasiRencanaPerbaikan.setOnClickListener {
                activity?.let {
                    datePickerAkan.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"

                            fromImplementasiRencana = formatDateOriginalValue.parse("$year-$monthStr-$dayStr")
                            etFromImplementasiRencanaPerbaikan.setText(formatDateDisplay.format(fromImplementasiRencana))

                            etToImplementasiRencanaPerbaikan.text!!.clear()
                        }
                    })
                }
            }

            etToImplementasiRencanaPerbaikan.setOnClickListener {
                activity?.let {
                    datePickerAkan.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"

                            toImplementasiRencana = formatDateOriginalValue.parse("$year-$monthStr-$dayStr")

                            if (etFromImplementasiRencanaPerbaikan.text.isNullOrEmpty() || !toImplementasiRencana.after(fromImplementasiRencana)){
                                SnackBarCustom.snackBarIconInfo(
                                    root, layoutInflater, resources, root.context,
                                    resources.getString(R.string.wrong_field),
                                    R.drawable.ic_close, R.color.red_500)
                                etToImplementasiRencanaPerbaikan.text!!.clear()
                            } else {
                                etToImplementasiRencanaPerbaikan.setText(formatDateDisplay.format(toImplementasiRencana))
                            }
                        }
                    })
                }
            }

            etFromAnalisaPeriksaDanEvaluasi.setOnClickListener {
                activity?.let {
                    datePickerAkan.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"

                            fromAnalisaPeriksaEvaluasi = formatDateOriginalValue.parse("$year-$monthStr-$dayStr")
                            etFromAnalisaPeriksaDanEvaluasi.setText(formatDateDisplay.format(fromAnalisaPeriksaEvaluasi))

                            etToAnalisaPeriksaDanEvaluasi.text!!.clear()
                        }
                    })
                }
            }

            etToAnalisaPeriksaDanEvaluasi.setOnClickListener {
                activity?.let {
                    datePickerAkan.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"

                            toAnalisaPeriksaEvaluasi = formatDateOriginalValue.parse("$year-$monthStr-$dayStr")

                            if (etFromAnalisaPeriksaDanEvaluasi.text.isNullOrEmpty() || !toAnalisaPeriksaEvaluasi.after(fromAnalisaPeriksaEvaluasi)){
                                SnackBarCustom.snackBarIconInfo(
                                    root, layoutInflater, resources, root.context,
                                    resources.getString(R.string.wrong_field),
                                    R.drawable.ic_close, R.color.red_500)
                                etToAnalisaPeriksaDanEvaluasi.text!!.clear()
                            } else {
                                etToAnalisaPeriksaDanEvaluasi.setText(formatDateDisplay.format(toAnalisaPeriksaEvaluasi))
                            }
                        }
                    })
                }
            }
        }
    }

    private fun getData() {
        binding.apply {
            if (data?.statusImplementationModel?.sudah?.from != "") {
                val dataFromDateToString = data?.statusImplementationModel?.sudah?.from
                val startDate = if (dataFromDateToString == "") "" else {
                    fromDate = formatDateOriginalValue.parse(dataFromDateToString)
                    formatDateDisplay.format(fromDate)
                }

                val dataToDateToString = data?.statusImplementationModel?.sudah?.to
                val endDate = if (dataToDateToString == "") "" else {
                    toDate = formatDateOriginalValue.parse(dataToDateToString)
                    formatDateDisplay.format(toDate)
                }

                rbStatus1.isChecked = true
                rbStatus2.isChecked = false

                etFromStatus1.setText(startDate)
                etToStatus1.setText(endDate)

                linearLayoutAkan.visibility = View.GONE
            } else {

                val dataStartIdentifikasiMasalah = data?.statusImplementationModel?.akan?.startIdentifikasiMasalah
                val startIdentifikasi = if (dataStartIdentifikasiMasalah == "") "" else {
                    fromIdentifikasi = formatDateOriginalValue.parse(dataStartIdentifikasiMasalah)
                    formatDateDisplay.format(fromIdentifikasi)
                }

                val dataEndIdentifikasiMasalah = data?.statusImplementationModel?.akan?.endIdentifikasiMasalah
                val endIdentifikasi = if (dataEndIdentifikasiMasalah == "") "" else {
                    toIdentifikasi = formatDateOriginalValue.parse(dataEndIdentifikasiMasalah)
                    formatDateDisplay.format(toIdentifikasi)
                }
                // ==============================================================================================================

                val dataStartAnalisaData = data?.statusImplementationModel?.akan?.startAnalisaData
                val startAnalisa = if (dataStartAnalisaData == "") "" else {
                    fromAnalisa = formatDateOriginalValue.parse(dataStartAnalisaData)
                    formatDateDisplay.format(fromAnalisa)
                }

                val dataEndAnalisaData = data?.statusImplementationModel?.akan?.endAnalisaData
                val endAnalisa = if (dataEndAnalisaData == "") "" else {
                    toAnalisa = formatDateOriginalValue.parse(dataEndAnalisaData)
                    formatDateDisplay.format(toAnalisa)
                }
                // ==============================================================================================================

                val dataStartAnalisaAkarMasalah = data?.statusImplementationModel?.akan?.startAnalisaAkarMasalah
                val startAnalisaAkarMasalah = if (dataStartAnalisaAkarMasalah == "") "" else {
                    fromAnalisaAkarMasalah = formatDateOriginalValue.parse(dataStartAnalisaAkarMasalah)
                    formatDateDisplay.format(fromAnalisaAkarMasalah)
                }

                val dataEndAnalisaAkarMasalah = data?.statusImplementationModel?.akan?.endAnalisaAkarMasalah
                val endAnalisaAkarMasalah = if (dataEndAnalisaAkarMasalah == "") "" else {
                    toAnalisaAkarMasalah = formatDateOriginalValue.parse(dataEndAnalisaAkarMasalah)
                    formatDateDisplay.format(toAnalisaAkarMasalah)
                }
                // ==============================================================================================================

                val dataStartMenyusunRencana = data?.statusImplementationModel?.akan?.startMenyusunRencana
                val startMenyusunRencana = if (dataStartMenyusunRencana == "") "" else {
                    fromMenyusunRencana = formatDateOriginalValue.parse(dataStartMenyusunRencana)
                    formatDateDisplay.format(fromMenyusunRencana)
                }

                val dataEndMenyusunRencana = data?.statusImplementationModel?.akan?.endMenyusunRencana
                val endMenyusunRencana = if (dataEndMenyusunRencana == "") "" else {
                    toMenyusunRencana = formatDateOriginalValue.parse(dataEndMenyusunRencana)
                    formatDateDisplay.format(toMenyusunRencana)
                }
                // ==============================================================================================================

                val dataStartImplementasiRencana = data?.statusImplementationModel?.akan?.startImplementasiRencana
                val startImplementasiRencana = if (dataStartImplementasiRencana == "") "" else {
                    fromImplementasiRencana = formatDateOriginalValue.parse(dataStartImplementasiRencana)
                    formatDateDisplay.format(fromImplementasiRencana)
                }

                val dataEndImplementasiRencana = data?.statusImplementationModel?.akan?.endImplementasiRencana
                val endImplementasiRencana = if (dataEndImplementasiRencana == "") "" else {
                    toImplementasiRencana = formatDateOriginalValue.parse(dataEndImplementasiRencana)
                    formatDateDisplay.format(toImplementasiRencana)
                }
                // ==============================================================================================================

                val dataStartAnalisPeriksaEvaluasi = data?.statusImplementationModel?.akan?.startAnalisPeriksaEvaluasi
                val startAnalisaPeriksaEvaluasi = if (dataStartAnalisPeriksaEvaluasi == "") "" else {
                    fromAnalisaPeriksaEvaluasi = formatDateOriginalValue.parse(dataStartAnalisPeriksaEvaluasi)
                    formatDateDisplay.format(fromAnalisaPeriksaEvaluasi)
                }

                val dataEndAnalisPeriksaEvaluasi = data?.statusImplementationModel?.akan?.endAnalisPeriksaEvaluasi
                val endAnalisaPeriksaEvaluasi = if (dataEndAnalisPeriksaEvaluasi == "") "" else {
                    toAnalisaPeriksaEvaluasi = formatDateOriginalValue.parse(dataEndAnalisPeriksaEvaluasi)
                    formatDateDisplay.format(toAnalisaPeriksaEvaluasi)
                }
                // ==============================================================================================================

                linearLayoutAkan.visibility = View.VISIBLE

                rbStatus1.isChecked = false
                rbStatus2.isChecked = true

                etFromStatus1.setText("")
                etToStatus1.setText("")

                etFromIdentifikasi.setText(startIdentifikasi)
                etToIdentifikasi.setText(endIdentifikasi)

                etFromAnalisaData.setText(startAnalisa)
                etToAnalisaData.setText(endAnalisa)

                etFromAnalisaAkarMasalah.setText(startAnalisaAkarMasalah)
                etToAnalisaAkarMasalah.setText(endAnalisaAkarMasalah)

                etFromMenyusunRencanaPenanggulanganMasalah.setText(startMenyusunRencana)
                etToMenyusunRencanaPenanggulanganMasalah.setText(endMenyusunRencana)

                etFromImplementasiRencanaPerbaikan.setText(startImplementasiRencana)
                etToImplementasiRencanaPerbaikan.setText(endImplementasiRencana)

                etFromAnalisaPeriksaDanEvaluasi.setText(startAnalisaPeriksaEvaluasi)
                etToAnalisaPeriksaDanEvaluasi.setText(endAnalisaPeriksaEvaluasi)
            }

        }
    }

    private fun setData() {
        (activity as ProjectImprovementCreateWizard).setPiCreateCallback(object :
            ProjectImprovementSystemCreateCallback {
            override fun onDataPass(): Boolean {
                var stat: Boolean
                binding.apply {
                    var sudah: StatusImplementationPiDoneModel? = null
                    var akan: StatusImplementationPiWantModel? = null

                    //if (rbStatus1.isChecked) {}

                    sudah = StatusImplementationPiDoneModel(
                        from = if (etFromStatus1.text.toString() == "") "" else formatDateOriginalValue.format(fromDate),
                        to = if (etToStatus1.text.toString() == "") "" else formatDateOriginalValue.format(toDate)
                    )

                    akan = StatusImplementationPiWantModel(
                        startIdentifikasiMasalah = if (etFromIdentifikasi.text.toString() == "") "" else formatDateOriginalValue.format(fromIdentifikasi),
                        endIdentifikasiMasalah = if (etToIdentifikasi.text.toString() == "") "" else formatDateOriginalValue.format(toIdentifikasi),

                        startAnalisaData = if (etFromAnalisaData.text.toString() == "") "" else formatDateOriginalValue.format(fromAnalisa),
                        endAnalisaData = if (etToAnalisaData.text.toString() == "") "" else formatDateOriginalValue.format(toAnalisa),

                        startAnalisaAkarMasalah = if (etFromAnalisaAkarMasalah.text.toString() == "") "" else formatDateOriginalValue.format(fromAnalisaAkarMasalah),
                        endAnalisaAkarMasalah = if (etToAnalisaAkarMasalah.text.toString() == "") "" else formatDateOriginalValue.format(toAnalisaAkarMasalah),

                        startMenyusunRencana = if (etFromMenyusunRencanaPenanggulanganMasalah.text.toString() == "") "" else formatDateOriginalValue.format(fromMenyusunRencana),
                        endMenyusunRencana = if (etToMenyusunRencanaPenanggulanganMasalah.text.toString() == "") "" else formatDateOriginalValue.format(toMenyusunRencana),

                        startImplementasiRencana = if (etFromImplementasiRencanaPerbaikan.text.toString() == "") "" else formatDateOriginalValue.format(fromImplementasiRencana),
                        endImplementasiRencana = if (etToImplementasiRencanaPerbaikan.text.toString() == "") "" else formatDateOriginalValue.format(toImplementasiRencana),

                        startAnalisPeriksaEvaluasi = if (etFromAnalisaPeriksaDanEvaluasi.text.toString() == "") "" else formatDateOriginalValue.format(fromAnalisaPeriksaEvaluasi),
                        endAnalisPeriksaEvaluasi = if (etToAnalisaPeriksaDanEvaluasi.text.toString() == "") "" else formatDateOriginalValue.format(toAnalisaPeriksaEvaluasi)
                    )

                    val statusImplementation = StatusImplementationPiModel(
                        sudah = sudah,
                        akan = akan
                    )
//                    HawkUtils().setTempDataCreatePi(
//                        id = data?.id,
//                        piNo = data?.piNo,
//                        date = data?.date,
//                        title = data?.title,
//                        branch = data?.branch,
//                        subBranch = data?.subBranch,
//                        department = data?.department,
//                        years = data?.years,
//                        statusImplementationModel = statusImplementation,
//                        identification = data?.identification,
//                        target = data?.target,
//                        sebabMasalah = data?.sebabMasalah,
//                        akarMasalah = data?.akarMasalah,
//                        nilaiOutput = data?.nilaiOutput,
//                        nqiModel = data?.nqiModel,
//                        teamMember = data?.teamMember,
//                        categoryFixing = data?.categoryFixing,
//                        hasilImplementasi = data?.implementationResult,
//                        attachment = data?.attachment,
//                        statusProposal = data?.statusProposal,
//                        headId = data?.headId,
//                        userId = data?.userId,
//                        orgId = data?.orgId,
//                        warehouseId = data?.warehouseId,
//                        historyApproval = data?.historyApproval,
//                        activityType = data?.activityType,
//                        submitType = data?.submitType,
//                        comment = data?.comment,
//                        source = source
//                    )
//                    stat = true

                    Timber.e("# $statusImplementation")
                    when {
                        //statusimplemen1
//                        rbStatus1.isChecked ->{
//                            SnackBarCustom.snackBarIconInfo(
//                                root, layoutInflater, resources, root.context,
//                                resources.getString(R.string.wrong_field),
//                                R.drawable.ic_close, R.color.red_500)
//                                etFromStatus1.requestFocus()
//                            stat = false
//                        }
                        rbStatus1.isChecked && etToStatus1.text.isNullOrEmpty() ->{
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                resources.getString(R.string.wrong_field),
                                R.drawable.ic_close, R.color.red_500)
                                etToStatus1.requestFocus()
                            stat = false
                        }

                        rbStatus2.isChecked && etToIdentifikasi.text.isNullOrEmpty() &&
                                etToAnalisaData.text.isNullOrEmpty() &&
                                etToAnalisaAkarMasalah.text.isNullOrEmpty() &&
                                etToMenyusunRencanaPenanggulanganMasalah.text.isNullOrEmpty() &&
                                etToAnalisaPeriksaDanEvaluasi.text.isNullOrEmpty() -> {
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                resources.getString(R.string.wrong_field),
                                R.drawable.ic_close, R.color.red_500)
                            stat = false
                        }

                        else -> {
                            HawkUtils().setTempDataCreatePi(
                                id = data?.id,
                                piNo = data?.piNo,
                                date = data?.date,
                                title = data?.title,
                                branch = data?.branch,
                                subBranch = data?.subBranch,
                                department = data?.department,
                                years = data?.years,
                                statusImplementationModel = statusImplementation,
                                identification = data?.identification,
                                target = data?.target,
                                sebabMasalah = data?.sebabMasalah,
                                akarMasalah = data?.akarMasalah,
                                nilaiOutput = data?.nilaiOutput,
                                nqiModel = data?.nqiModel,
                                teamMember = data?.teamMember,
                                categoryFixing = data?.categoryFixing,
                                hasilImplementasi = edtResult,
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