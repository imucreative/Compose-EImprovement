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
    private lateinit var datePicker: DatePickerCustom
    private var data : ProjectImprovementCreateModel? = null
    private var piNo: String? = ""
    private var action: String? = ""
    lateinit var fromDate: Date
    lateinit var toDate: Date
    val sdf = SimpleDateFormat("dd-MM-yyyy")
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

        datePicker = activity?.let {
            DatePickerCustom(
                context = binding.root.context,themeDark = true,
                minDateIsCurrentDate = true, it.supportFragmentManager
            )
        }!!

        setLogic()
        getData()
        initComponent()
        setData()

        if (action == APPROVE) {
            disableForm()
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
            etFromStatus1.setOnClickListener {datePicker.showDialog(object : DatePickerCustom.Callback {
                override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                    val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                    val mon = month + 1
                    val monthStr = if (mon < 10) "0$mon" else "$mon"
                    etFromStatus1.setText("$dayStr-$monthStr-$year")
                    fromDate = sdf.parse(etFromStatus1.text.toString())
                    etToStatus1.text!!.clear()
                }
            })
            }

            etToStatus1.setOnClickListener {
                activity?.let {
                    datePicker.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"
                            etToStatus1.setText("$dayStr-$monthStr-$year")
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
                    datePicker.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"
                            etFromIdentifikasi.setText("$dayStr-$monthStr-$year")
                            fromDate = sdf.parse(etFromIdentifikasi.text.toString())
                            etToIdentifikasi.text!!.clear()
                        }
                    })
                }
            }

            etToIdentifikasi.setOnClickListener {
                activity?.let {
                    datePicker.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"
                            etToIdentifikasi.setText("$dayStr-$monthStr-$year")
                            toDate= sdf.parse(etToIdentifikasi.text.toString())
                            if(etFromIdentifikasi.text.isNullOrEmpty()){
                                SnackBarCustom.snackBarIconInfo(
                                    root, layoutInflater, resources, root.context,
                                    resources.getString(R.string.wrong_field),
                                    R.drawable.ic_close, R.color.red_500)
                                etFromIdentifikasi.requestFocus()
                            }else{
                                if (!toDate.after(fromDate)){
                                    SnackBarCustom.snackBarIconInfo(
                                        root, layoutInflater, resources, root.context,
                                        resources.getString(R.string.wrong_field),
                                        R.drawable.ic_close, R.color.red_500)
                                    etToIdentifikasi.text!!.clear()
                                }
                            }
                        }
                    })
                }
            }

            etFromAnalisaData.setOnClickListener {
                activity?.let {
                    datePicker.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"
                            etFromAnalisaData.setText("$dayStr-$monthStr-$year")
                            fromDate = sdf.parse(etFromAnalisaData.text.toString())
                            etToAnalisaData.text!!.clear()
                        }
                    })
                }
            }

            etToAnalisaData.setOnClickListener {
                activity?.let {
                    datePicker.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"
                            etToAnalisaData.setText("$dayStr-$monthStr-$year")
                            toDate = sdf.parse(etToAnalisaData.text.toString())
                            if(etFromAnalisaData.text.isNullOrEmpty()){
                                SnackBarCustom.snackBarIconInfo(
                                    root, layoutInflater, resources, root.context,
                                    resources.getString(R.string.wrong_field),
                                    R.drawable.ic_close, R.color.red_500)
                                etFromAnalisaData.requestFocus()
                            }else{
                                if (!toDate.after(fromDate)){
                                    SnackBarCustom.snackBarIconInfo(
                                        root, layoutInflater, resources, root.context,
                                        resources.getString(R.string.wrong_field),
                                        R.drawable.ic_close, R.color.red_500)
                                    etToAnalisaData.text!!.clear()
                                }
                            }
                        }
                    })
                }
            }

            etFromAnalisaAkarMasalah.setOnClickListener {
                activity?.let {
                    datePicker.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"
                            etFromAnalisaAkarMasalah.setText("$dayStr-$monthStr-$year")
                            fromDate = sdf.parse(etFromAnalisaAkarMasalah.text.toString())
                            etToAnalisaAkarMasalah.text!!.clear()
                        }
                    })
                }
            }

            etToAnalisaAkarMasalah.setOnClickListener {
                activity?.let {
                    datePicker.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"
                            etToAnalisaAkarMasalah.setText("$dayStr-$monthStr-$year")
                            toDate = sdf.parse(etToAnalisaAkarMasalah.text.toString())
                            if(etFromAnalisaAkarMasalah.text.isNullOrEmpty()){
                                SnackBarCustom.snackBarIconInfo(
                                    root, layoutInflater, resources, root.context,
                                    resources.getString(R.string.wrong_field),
                                    R.drawable.ic_close, R.color.red_500)
                                etFromAnalisaAkarMasalah.requestFocus()
                            }else{
                                if (!toDate.after(fromDate)){
                                    SnackBarCustom.snackBarIconInfo(
                                        root, layoutInflater, resources, root.context,
                                        resources.getString(R.string.wrong_field),
                                        R.drawable.ic_close, R.color.red_500)
                                    etToAnalisaAkarMasalah.text!!.clear()
                                }
                            }
                        }
                    })
                }
            }

            etFromMenyusunRencanaPenanggulanganMasalah.setOnClickListener {
                activity?.let {
                    datePicker.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"
                            etFromMenyusunRencanaPenanggulanganMasalah.setText("$dayStr-$monthStr-$year")
                            fromDate = sdf.parse(etFromMenyusunRencanaPenanggulanganMasalah.text.toString())
                            etToMenyusunRencanaPenanggulanganMasalah.text!!.clear()
                        }
                    })
                }
            }

            etToMenyusunRencanaPenanggulanganMasalah.setOnClickListener {
                activity?.let {
                    datePicker.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"
                            etToMenyusunRencanaPenanggulanganMasalah.setText("$dayStr-$monthStr-$year")
                            toDate= sdf.parse(etToMenyusunRencanaPenanggulanganMasalah.text.toString())
                            if(etFromMenyusunRencanaPenanggulanganMasalah.text.isNullOrEmpty()){
                                SnackBarCustom.snackBarIconInfo(
                                    root, layoutInflater, resources, root.context,
                                    resources.getString(R.string.wrong_field),
                                    R.drawable.ic_close, R.color.red_500)
                                etFromMenyusunRencanaPenanggulanganMasalah.requestFocus()
                            }else{
                                if (!toDate.after(fromDate)){
                                    SnackBarCustom.snackBarIconInfo(
                                        root, layoutInflater, resources, root.context,
                                        resources.getString(R.string.wrong_field),
                                        R.drawable.ic_close, R.color.red_500)
                                    etToMenyusunRencanaPenanggulanganMasalah.text!!.clear()
                                }
                            }
                        }
                    })
                }
            }

            etFromImplementasiRencanaPerbaikan.setOnClickListener {
                activity?.let {
                    datePicker.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"
                            etFromImplementasiRencanaPerbaikan.setText("$dayStr-$monthStr-$year")
                            fromDate = sdf.parse(etFromImplementasiRencanaPerbaikan.text.toString())
                            etToImplementasiRencanaPerbaikan.text!!.clear()
                        }
                    })
                }
            }

            etToImplementasiRencanaPerbaikan.setOnClickListener {
                activity?.let {
                    datePicker.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"
                            etToImplementasiRencanaPerbaikan.setText("$dayStr-$monthStr-$year")
                            toDate= sdf.parse(etToImplementasiRencanaPerbaikan.text.toString())
                            if(etFromImplementasiRencanaPerbaikan.text.isNullOrEmpty()){
                                SnackBarCustom.snackBarIconInfo(
                                    root, layoutInflater, resources, root.context,
                                    resources.getString(R.string.wrong_field),
                                    R.drawable.ic_close, R.color.red_500)
                                etFromImplementasiRencanaPerbaikan.requestFocus()
                            }else{
                                if (!toDate.after(fromDate)){
                                    SnackBarCustom.snackBarIconInfo(
                                        root, layoutInflater, resources, root.context,
                                        resources.getString(R.string.wrong_field),
                                        R.drawable.ic_close, R.color.red_500)
                                    etToImplementasiRencanaPerbaikan.text!!.clear()
                                }
                            }
                        }
                    })
                }
            }

            etFromAnalisaPeriksaDanEvaluasi.setOnClickListener {
                activity?.let {
                    datePicker.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"
                            etFromAnalisaPeriksaDanEvaluasi.setText("$dayStr-$monthStr-$year")
                            fromDate = sdf.parse(etFromAnalisaPeriksaDanEvaluasi.text.toString())
                            etToAnalisaPeriksaDanEvaluasi.text!!.clear()
                        }
                    })
                }
            }

            etToAnalisaPeriksaDanEvaluasi.setOnClickListener {
                activity?.let {
                    datePicker.showDialog(object : DatePickerCustom.Callback {
                        override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                            val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                            val mon = month + 1
                            val monthStr = if (mon < 10) "0$mon" else "$mon"
                            etToAnalisaPeriksaDanEvaluasi.setText("$dayStr-$monthStr-$year")
                            toDate= sdf.parse(etToAnalisaPeriksaDanEvaluasi.text.toString())
                            if(etFromAnalisaPeriksaDanEvaluasi.text.isNullOrEmpty()){
                                SnackBarCustom.snackBarIconInfo(
                                    root, layoutInflater, resources, root.context,
                                    resources.getString(R.string.wrong_field),
                                    R.drawable.ic_close, R.color.red_500)
                                etFromAnalisaPeriksaDanEvaluasi.requestFocus()
                            }else{
                                if (!toDate.after(fromDate)){
                                    SnackBarCustom.snackBarIconInfo(
                                        root, layoutInflater, resources, root.context,
                                        resources.getString(R.string.wrong_field),
                                        R.drawable.ic_close, R.color.red_500)
                                    etToAnalisaPeriksaDanEvaluasi.text!!.clear()
                                }
                            }
                        }
                    })
                }
            }
        }
    }

    private fun getData() {
        binding.apply {
            if (data?.statusImplementation?.akan == null) {
                rbStatus1.isChecked = true
                rbStatus2.isChecked = false


                etFromStatus1.setText(data?.statusImplementation?.sudah?.from)
                etToStatus1.setText(data?.statusImplementation?.sudah?.to)

                linearLayoutAkan.visibility = View.GONE
            } else {
                linearLayoutAkan.visibility = View.VISIBLE

                rbStatus1.isChecked = false
                rbStatus2.isChecked = true

                etFromStatus1.setText("")
                etToStatus1.setText("")

                etFromIdentifikasi.setText(data?.statusImplementation?.akan?.identifikasiMasalah?.from)
                etToIdentifikasi.setText(data?.statusImplementation?.akan?.identifikasiMasalah?.to)

                etFromAnalisaData.setText(data?.statusImplementation?.akan?.analisaData?.from)
                etToAnalisaData.setText(data?.statusImplementation?.akan?.analisaData?.to)

                etFromAnalisaAkarMasalah.setText(data?.statusImplementation?.akan?.analisaAkarMasalah?.from)
                etToAnalisaAkarMasalah.setText(data?.statusImplementation?.akan?.analisaAkarMasalah?.to)

                etFromMenyusunRencanaPenanggulanganMasalah.setText(data?.statusImplementation?.akan?.menyusunRencana?.from)
                etToMenyusunRencanaPenanggulanganMasalah.setText(data?.statusImplementation?.akan?.menyusunRencana?.to)

                etFromImplementasiRencanaPerbaikan.setText(data?.statusImplementation?.akan?.implementasiRencana?.from)
                etToImplementasiRencanaPerbaikan.setText(data?.statusImplementation?.akan?.implementasiRencana?.to)

                etFromAnalisaPeriksaDanEvaluasi.setText(data?.statusImplementation?.akan?.analisPeriksaEvaluasi?.from)
                etToAnalisaPeriksaDanEvaluasi.setText(data?.statusImplementation?.akan?.analisPeriksaEvaluasi?.to)
            }

        }
    }

    private fun setData() {
        (activity as ProjectImprovementCreateWizard).setPiCreateCallback(object :
            ProjectImprovementSystemCreateCallback {
            override fun onDataPass(): Boolean {
                var stat: Boolean
                binding.apply {
                    var sudah: Sudah? = null
                    var akan: Akan? = null

                    if (rbStatus1.isChecked) {
                        sudah = Sudah(
                            from = etFromStatus1.text.toString(),
                            to = etToStatus1.text.toString()
                        )
                    } else {
                        akan = Akan(
                            identifikasiMasalah = IdentifikasiMasalah(
                                from = etFromIdentifikasi.text.toString(),
                                to = etToIdentifikasi.text.toString()
                            ),
                            analisaData = AnalisaData(
                                from = etFromAnalisaData.text.toString(),
                                to = etToAnalisaData.text.toString()
                            ),
                            analisaAkarMasalah = AnalisaAkarMasalah(
                                from = etFromAnalisaAkarMasalah.text.toString(),
                                to = etToAnalisaAkarMasalah.text.toString()
                            ),
                            menyusunRencana = MenyusunRencana(
                                from = etFromMenyusunRencanaPenanggulanganMasalah.text.toString(),
                                to = etToMenyusunRencanaPenanggulanganMasalah.text.toString()
                            ),
                            implementasiRencana = ImplementasiRencana(
                                from = etFromImplementasiRencanaPerbaikan.text.toString(),
                                to = etToImplementasiRencanaPerbaikan.text.toString()
                            ),
                            analisPeriksaEvaluasi = AnalisPeriksaEvaluasi(
                                from = etFromAnalisaPeriksaDanEvaluasi.text.toString(),
                                to = etToAnalisaPeriksaDanEvaluasi.text.toString()
                            )

                        )
                    }

                    val statusImplementation = StatusImplementationPi(
                        sudah = sudah,
                        akan = akan
                    )

                    Timber.e("# $statusImplementation")
                    when {
                        //statusimplemen1
                        rbStatus1.isChecked && etFromStatus1.text.isNullOrEmpty() ->{
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                resources.getString(R.string.wrong_field),
                                R.drawable.ic_close, R.color.red_500)
                                etFromStatus1.requestFocus()
                            stat = false
                        }
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
                                statusImplementation = statusImplementation,
                                identification = data?.identification,
                                target = data?.target,
                                sebabMasalah = data?.sebabMasalah,
                                akarMasalah = data?.akarMasalah,
                                nilaiOutput = data?.nilaiOutput,
                                nqi = data?.nqi,
                                teamMember = data?.teamMember,
                                categoryFixing = data?.categoryFixing,
                                hasilImplementasi = data?.implementationResult,
                                attachment = data?.attachment,
                                statusProposal = data?.statusProposal,
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


