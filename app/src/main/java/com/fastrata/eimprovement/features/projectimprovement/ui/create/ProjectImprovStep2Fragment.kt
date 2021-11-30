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
    private lateinit var datePickerSudah: DatePickerCustom
    private lateinit var datePickerAkan: DatePickerCustom
    private var data : ProjectImprovementCreateModel? = null
    private var piNo: String? = ""
    private var action: String? = ""
    lateinit var fromDate: Date
    lateinit var toDate: Date
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    private var source: String = PI_CREATE

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
        getData()
        initComponent()
        setData()

        if ((action == APPROVE) || (action == DETAIL)) {
            disableForm()
        }

        when (data?.statusProposal?.id) {
            4, 9 -> {
                binding.rbStatus1.isClickable = false
                binding.rbStatus2.isClickable = false
            }
            5, 6 -> {
                disableForm()
            }
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

    private fun setLogic() {
        binding.run {
            etFromStatus1.isEnabled = false
            etToStatus1.isEnabled = false
            linearLayoutAkan.visibility = View.GONE
            rbStatus1.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    etFromStatus1.isEnabled = true
                    etToStatus1.isEnabled = true
                    HawkUtils().setStatusImplementation(true)
                    linearLayoutAkan.visibility = View.GONE
                }
            }

            rbStatus2.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    HawkUtils().setStatusImplementation(false)
                    etFromStatus1.isEnabled = false
                    etToStatus1.isEnabled = false
                    etFromStatus1.setText("")
                    etToStatus1.setText("")

                    linearLayoutAkan.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initComponent() {
        binding.apply {
            etFromStatus1.setOnClickListener {datePickerSudah.showDialog(object : DatePickerCustom.Callback {
                override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                    val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                    val mon = month + 1
                    val monthStr = if (mon < 10) "0$mon" else "$mon"
                    etFromStatus1.setText("$year-$monthStr-$dayStr")
                    fromDate = sdf.parse(etFromStatus1.text.toString())
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
                            etToStatus1.setText("$year-$monthStr-$dayStr")
                            toDate = sdf.parse(etToStatus1.text.toString())
                            if(etFromStatus1.text.isNullOrEmpty()){
                                 SnackBarCustom.snackBarIconInfo(
                                    root, layoutInflater, resources, root.context,
                                    resources.getString(R.string.wrong_field),
                                    R.drawable.ic_close, R.color.red_500)
                                etFromStatus1.requestFocus()
                            }else{
                                if (!toDate.after(fromDate)){
                                    SnackBarCustom.snackBarIconInfo(
                                        root, layoutInflater, resources, root.context,
                                        resources.getString(R.string.wrong_field),
                                        R.drawable.ic_close, R.color.red_500)
                                    etToStatus1.text!!.clear()
                                }
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
                            etFromIdentifikasi.setText("$year-$monthStr-$dayStr")
                            fromDate = sdf.parse(etFromIdentifikasi.text.toString())
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
                            etToIdentifikasi.setText("$year-$monthStr-$dayStr")
                            toDate= sdf.parse(etToIdentifikasi.text.toString())
//                            if(etFromIdentifikasi.text.isNullOrEmpty()){
//                                SnackBarCustom.snackBarIconInfo(
//                                    root, layoutInflater, resources, root.context,
//                                    resources.getString(R.string.wrong_field),
//                                    R.drawable.ic_close, R.color.red_500)
//                                etFromIdentifikasi.requestFocus()
//                            }else{
                                if (!toDate.after(fromDate)){
                                    SnackBarCustom.snackBarIconInfo(
                                        root, layoutInflater, resources, root.context,
                                        resources.getString(R.string.wrong_field),
                                        R.drawable.ic_close, R.color.red_500)
                                    etToIdentifikasi.text!!.clear()
                                }
//                            }
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
                            etFromAnalisaData.setText("$year-$monthStr-$dayStr")
                            fromDate = sdf.parse(etFromAnalisaData.text.toString())
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
                            etToAnalisaData.setText("$year-$monthStr-$dayStr")
                            toDate = sdf.parse(etToAnalisaData.text.toString())
//                            if(etFromAnalisaData.text.isNullOrEmpty()){
//                                SnackBarCustom.snackBarIconInfo(
//                                    root, layoutInflater, resources, root.context,
//                                    resources.getString(R.string.wrong_field),
//                                    R.drawable.ic_close, R.color.red_500)
//                                etFromAnalisaData.requestFocus()
//                            }else{
                                if (!toDate.after(fromDate)){
                                    SnackBarCustom.snackBarIconInfo(
                                        root, layoutInflater, resources, root.context,
                                        resources.getString(R.string.wrong_field),
                                        R.drawable.ic_close, R.color.red_500)
                                    etToAnalisaData.text!!.clear()
                                }
//                            }
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
                            etFromAnalisaAkarMasalah.setText("$year-$monthStr-$dayStr")
                            fromDate = sdf.parse(etFromAnalisaAkarMasalah.text.toString())
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
                            etToAnalisaAkarMasalah.setText("$year-$monthStr-$dayStr")
                            toDate = sdf.parse(etToAnalisaAkarMasalah.text.toString())
//                            if(etFromAnalisaAkarMasalah.text.isNullOrEmpty()){
//                                SnackBarCustom.snackBarIconInfo(
//                                    root, layoutInflater, resources, root.context,
//                                    resources.getString(R.string.wrong_field),
//                                    R.drawable.ic_close, R.color.red_500)
//                                etFromAnalisaAkarMasalah.requestFocus()
//                            }else{
                                if (!toDate.after(fromDate)){
                                    SnackBarCustom.snackBarIconInfo(
                                        root, layoutInflater, resources, root.context,
                                        resources.getString(R.string.wrong_field),
                                        R.drawable.ic_close, R.color.red_500)
                                    etToAnalisaAkarMasalah.text!!.clear()
                                }
//                            }
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
                            etFromMenyusunRencanaPenanggulanganMasalah.setText("$year-$monthStr-$dayStr")
                            fromDate = sdf.parse(etFromMenyusunRencanaPenanggulanganMasalah.text.toString())
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
                            etToMenyusunRencanaPenanggulanganMasalah.setText("$year-$monthStr-$dayStr")
                            toDate= sdf.parse(etToMenyusunRencanaPenanggulanganMasalah.text.toString())
//                            if(etFromMenyusunRencanaPenanggulanganMasalah.text.isNullOrEmpty()){
//                                SnackBarCustom.snackBarIconInfo(
//                                    root, layoutInflater, resources, root.context,
//                                    resources.getString(R.string.wrong_field),
//                                    R.drawable.ic_close, R.color.red_500)
//                                etFromMenyusunRencanaPenanggulanganMasalah.requestFocus()
//                            }else{
                                if (!toDate.after(fromDate)){
                                    SnackBarCustom.snackBarIconInfo(
                                        root, layoutInflater, resources, root.context,
                                        resources.getString(R.string.wrong_field),
                                        R.drawable.ic_close, R.color.red_500)
                                    etToMenyusunRencanaPenanggulanganMasalah.text!!.clear()
                                }
//                            }
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
                            etFromImplementasiRencanaPerbaikan.setText("$year-$monthStr-$dayStr")
                            fromDate = sdf.parse(etFromImplementasiRencanaPerbaikan.text.toString())
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
                            etToImplementasiRencanaPerbaikan.setText("$year-$monthStr-$dayStr")
                            toDate= sdf.parse(etToImplementasiRencanaPerbaikan.text.toString())
//                            if(etFromImplementasiRencanaPerbaikan.text.isNullOrEmpty()){
//                                SnackBarCustom.snackBarIconInfo(
//                                    root, layoutInflater, resources, root.context,
//                                    resources.getString(R.string.wrong_field),
//                                    R.drawable.ic_close, R.color.red_500)
//                                etFromImplementasiRencanaPerbaikan.requestFocus()
//                            }else{
                                if (!toDate.after(fromDate)){
                                    SnackBarCustom.snackBarIconInfo(
                                        root, layoutInflater, resources, root.context,
                                        resources.getString(R.string.wrong_field),
                                        R.drawable.ic_close, R.color.red_500)
                                    etToImplementasiRencanaPerbaikan.text!!.clear()
                                }
//                            }
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
                            etFromAnalisaPeriksaDanEvaluasi.setText("$year-$monthStr-$dayStr")
                            fromDate = sdf.parse(etFromAnalisaPeriksaDanEvaluasi.text.toString())
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
                            etToAnalisaPeriksaDanEvaluasi.setText("$year-$monthStr-$dayStr")
                            toDate= sdf.parse(etToAnalisaPeriksaDanEvaluasi.text.toString())
//                            if(etFromAnalisaPeriksaDanEvaluasi.text.isNullOrEmpty()){
//                                SnackBarCustom.snackBarIconInfo(
//                                    root, layoutInflater, resources, root.context,
//                                    resources.getString(R.string.wrong_field),
//                                    R.drawable.ic_close, R.color.red_500)
//                                etFromAnalisaPeriksaDanEvaluasi.requestFocus()
//                            }else{
                                if (!toDate.after(fromDate)){
                                    SnackBarCustom.snackBarIconInfo(
                                        root, layoutInflater, resources, root.context,
                                        resources.getString(R.string.wrong_field),
                                        R.drawable.ic_close, R.color.red_500)
                                    etToAnalisaPeriksaDanEvaluasi.text!!.clear()
                                }
//                            }
                        }
                    })
                }
            }
        }
    }

    private fun getData() {
        binding.apply {
            if (data?.statusImplementationModel?.sudah?.from != "") {
                rbStatus1.isChecked = true
                rbStatus2.isChecked = false


                etFromStatus1.setText(data?.statusImplementationModel?.sudah?.from)
                etToStatus1.setText(data?.statusImplementationModel?.sudah?.to)

                linearLayoutAkan.visibility = View.GONE
            } else {
                linearLayoutAkan.visibility = View.VISIBLE

                rbStatus1.isChecked = false
                rbStatus2.isChecked = true

                etFromStatus1.setText("")
                etToStatus1.setText("")

                etFromIdentifikasi.setText(data?.statusImplementationModel?.akan?.startIdentifikasiMasalah)
                etToIdentifikasi.setText(data?.statusImplementationModel?.akan?.endIdentifikasiMasalah)

                etFromAnalisaData.setText(data?.statusImplementationModel?.akan?.startAnalisaData)
                etToAnalisaData.setText(data?.statusImplementationModel?.akan?.endAnalisaData)

                etFromAnalisaAkarMasalah.setText(data?.statusImplementationModel?.akan?.startAnalisaAkarMasalah)
                etToAnalisaAkarMasalah.setText(data?.statusImplementationModel?.akan?.endAnalisaAkarMasalah)

                etFromMenyusunRencanaPenanggulanganMasalah.setText(data?.statusImplementationModel?.akan?.startMenyusunRencana)
                etToMenyusunRencanaPenanggulanganMasalah.setText(data?.statusImplementationModel?.akan?.endMenyusunRencana)

                etFromImplementasiRencanaPerbaikan.setText(data?.statusImplementationModel?.akan?.startImplementasiRencana)
                etToImplementasiRencanaPerbaikan.setText(data?.statusImplementationModel?.akan?.endImplementasiRencana)

                etFromAnalisaPeriksaDanEvaluasi.setText(data?.statusImplementationModel?.akan?.startAnalisPeriksaEvaluasi)
                etToAnalisaPeriksaDanEvaluasi.setText(data?.statusImplementationModel?.akan?.endAnalisPeriksaEvaluasi)
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

//                    if (rbStatus1.isChecked) {
                        sudah = StatusImplementationPiDoneModel(
                            from = etFromStatus1.text.toString(),
                            to = etToStatus1.text.toString())
//                    } else {
                        akan = StatusImplementationPiWantModel(
                            startIdentifikasiMasalah = etFromIdentifikasi.text.toString(),
                            endIdentifikasiMasalah = etToIdentifikasi.text.toString(),

                            startAnalisaData = etFromAnalisaData.text.toString(),
                            endAnalisaData = etToAnalisaData.text.toString(),

                            startAnalisaAkarMasalah = etFromAnalisaAkarMasalah.text.toString(),
                            endAnalisaAkarMasalah = etToAnalisaAkarMasalah.text.toString(),

                            startMenyusunRencana = etFromMenyusunRencanaPenanggulanganMasalah.text.toString(),
                            endMenyusunRencana = etToMenyusunRencanaPenanggulanganMasalah.text.toString(),

                            startImplementasiRencana = etFromImplementasiRencanaPerbaikan.text.toString(),
                            endImplementasiRencana = etToImplementasiRencanaPerbaikan.text.toString(),

                            startAnalisPeriksaEvaluasi = etFromAnalisaPeriksaDanEvaluasi.text.toString(),
                            endAnalisPeriksaEvaluasi = etToAnalisaPeriksaDanEvaluasi.text.toString()
                        )
//                    }

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


