package com.fastrata.eimprovement.features.suggestionsystem.ui.create

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.FragmentSuggestionSystemStep2Binding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.features.suggestionsystem.data.model.StatusImplementation
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemCreateModel
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.utils.HawkUtils
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class SuggestionSystemStep2Fragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: FragmentSuggestionSystemStep2Binding? = null
    private val binding get() = _binding!!
    private var data: SuggestionSystemCreateModel? = null
    private var ssNo: String? = ""
    private var ssAction: String? = ""
    private lateinit var datePickerSudah: DatePickerCustom
    private lateinit var datePickerAkan : DatePickerCustom
    private var source: String = SS_CREATE
    lateinit var fromDate: Date
    lateinit var toDate: Date
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    private var edtProses : String = ""
    private var edtResult : String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuggestionSystemStep2Binding.inflate(inflater, container, false)

        ssNo = arguments?.getString(SS_DETAIL_DATA)
        ssAction = arguments?.getString(ACTION_DETAIL_DATA)

        source = if (ssNo == "") SS_CREATE else SS_DETAIL_DATA

        data = HawkUtils().getTempDataCreateSs(source)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSuggestionSystemStep2Binding.bind(view)

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

        initComponent(binding)

        when (ssAction){
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
            problem.isEnabled = false
            suggestion.isEnabled = false

            rbStatus1.isClickable = false
            etFromStatus1.isEnabled = false
            etToStatus1.isEnabled = false

            rbStatus2.isClickable = false
            etFromStatus2.isEnabled = false
            etToStatus2.isEnabled = false
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

    private fun initComponent(binding: FragmentSuggestionSystemStep2Binding) {

        binding.apply {
            etFromStatus1.setOnClickListener {
                datePickerSudah.showDialog(object : DatePickerCustom.Callback {
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
                datePickerSudah.showDialog(object : DatePickerCustom.Callback {
                    override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                        val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                        val mon = month + 1
                        val monthStr = if (mon < 10) "0$mon" else "$mon"
                        etToStatus1.setText("$year-$monthStr-$dayStr")
                        toDate = sdf.parse(etToStatus1.text.toString())
                        if (etFromStatus1.text.isNullOrEmpty()){
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                resources.getString(R.string.wrong_field),
                                R.drawable.ic_close, R.color.red_500)
                                etFromStatus1.requestFocus()
                        }else{
                            fromDate = sdf.parse(etFromStatus1.text.toString())
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

            etFromStatus2.setOnClickListener {
                datePickerAkan.showDialog(object : DatePickerCustom.Callback {
                    override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                        val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                        val mon = month + 1
                        val monthStr = if (mon < 10) "0$mon" else "$mon"
                        etFromStatus2.setText("$year-$monthStr-$dayStr")
                        fromDate = sdf.parse(etFromStatus2.text.toString())
                        etToStatus2.text!!.clear()
                    }
                })
            }

            etToStatus2.setOnClickListener {
                datePickerAkan.showDialog(object : DatePickerCustom.Callback {
                    override fun onDateSelected(dayOfMonth: Int, month: Int, year: Int) {
                        val dayStr = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                        val mon = month + 1
                        val monthStr = if (mon < 10) "0$mon" else "$mon"
                        etToStatus2.setText("$year-$monthStr-$dayStr")
                        toDate = sdf.parse(etToStatus2.text.toString())
                        if (etFromStatus2.text.isNullOrEmpty()){
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                resources.getString(R.string.wrong_field),
                                R.drawable.ic_close, R.color.red_500)
                            etFromStatus2.requestFocus()
                        }else{
                            fromDate = sdf.parse(etFromStatus2.text.toString())
                            if (!toDate.after(fromDate)){
                                SnackBarCustom.snackBarIconInfo(
                                    root, layoutInflater, resources, root.context,
                                    resources.getString(R.string.wrong_field),
                                    R.drawable.ic_close, R.color.red_500)
                                etToStatus2.text!!.clear()
                            }
                        }
                    }
                })
            }

            // init false condition
            etFromStatus1.isEnabled = false
            etToStatus1.isEnabled = false
            etFromStatus2.isEnabled = false
            etToStatus2.isEnabled = false

            setLogic(binding)

            if (data?.problem != null){
                getData(binding)
            }

            setData(binding)
        }
    }

    private fun setLogic(binding: FragmentSuggestionSystemStep2Binding) {
        binding.run {
            edtProses = data?.proses.toString()
            edtResult = data?.result.toString()

            rbStatus1.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    etFromStatus1.isEnabled = true
                    etToStatus1.isEnabled = true

                    etFromStatus2.isEnabled = false
                    etToStatus2.isEnabled = false
                    etFromStatus2.setText("")
                    etToStatus2.setText("")
                    HawkUtils().setStatusSuggestion(true)
                }
            }

            rbStatus2.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    etFromStatus1.isEnabled = false
                    etToStatus1.isEnabled = false
                    etFromStatus1.setText("")
                    etToStatus1.setText("")

                    etFromStatus2.isEnabled = true
                    etToStatus2.isEnabled = true
                    when (ssAction) {
                        ADD, EDIT -> {
                            edtProses = ""
                            edtResult = ""
                        }
                    }
                    HawkUtils().setStatusSuggestion(false)
                }
            }
        }
    }

    private fun getData(binding: FragmentSuggestionSystemStep2Binding) {
        binding.apply {

            problem.setText(data?.problem.toString())
            suggestion.setText(data?.suggestion.toString())

            if (data?.statusImplementation?.status == 1) {
                rbStatus1.isChecked = true
                rbStatus2.isChecked = false

                etFromStatus1.setText(data?.statusImplementation?.from)
                etToStatus1.setText(data?.statusImplementation?.to)

                etFromStatus2.setText("")
                etToStatus2.setText("")
            } else {
                rbStatus1.isChecked = false
                rbStatus2.isChecked = true

                etFromStatus1.setText("")
                etToStatus1.setText("")

                etFromStatus2.setText(data?.statusImplementation?.from)
                etToStatus2.setText(data?.statusImplementation?.to)
            }

        }
    }

    private fun setData(binding: FragmentSuggestionSystemStep2Binding) {
        (activity as SuggestionSystemCreateWizard).setSsCreateCallback(object : SuggestionSystemCreateCallback {
            override fun onDataPass(): Boolean {
                var stat: Boolean
                binding.apply {
                    val tempStatus: Int?
                    lateinit var tempFrom: String
                    lateinit var tempTo: String

                    if (rbStatus1.isChecked) {
                        tempStatus = 1
                        tempFrom = etFromStatus1.text.toString()
                        tempTo = etToStatus1.text.toString()
                    } else {
                        tempStatus = 0
                        tempFrom = etFromStatus2.text.toString()
                        tempTo = etToStatus2.text.toString()
                    }

                    val status = StatusImplementation(
                        status = tempStatus,
                        from = tempFrom,
                        to = tempTo
                    )

                    when{
                        problem.text.isNullOrEmpty() -> {
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                resources.getString(R.string.problem_empty),
                                R.drawable.ic_close, R.color.red_500)
                            problem.requestFocus()
                            stat = false
                        }

                        suggestion.text.isNullOrEmpty() -> {
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                resources.getString(R.string.suggest_empty),
                                R.drawable.ic_close, R.color.red_500)
                            suggestion.requestFocus()
                            stat = false
                        }

                        !rbStatus1.isChecked && !rbStatus2.isChecked -> {
                            Timber.e("Hasus ada yg dipilih")
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                resources.getString(R.string.wrong_field),
                                R.drawable.ic_close, R.color.red_500)
                            stat = false
                        }

                        rbStatus1.isChecked && (etFromStatus1.text.isNullOrEmpty() || etToStatus1.text.isNullOrEmpty()) -> {
                            Timber.e("RB Status 1 / Sudah & Edit Text harus diisi")
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                resources.getString(R.string.wrong_field),
                                R.drawable.ic_close, R.color.red_500)
                            etToStatus1.requestFocus()
                            stat = false
                        }

                        rbStatus2.isChecked && (etFromStatus2.text.isNullOrEmpty() || etToStatus2.text.isNullOrEmpty()) -> {
                            Timber.e("RB Status 2 / Akan & Edit Text harus diisi")
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                resources.getString(R.string.wrong_field),
                                R.drawable.ic_close, R.color.red_500)
                            etToStatus2.requestFocus()
                            stat = false
                        }

                        else -> {
                            HawkUtils().setTempDataCreateSs(
                                id = data?.id,
                                ssNo = data?.ssNo,
                                date = data?.date,
                                title = data?.title,
                                listCategory = data?.categoryImprovement,
                                name = data?.name,
                                nik = data?.nik,
                                branchCode = data?.branchCode,
                                branch = data?.branch,
                                subBranch = data?.subBranch,
                                department = data?.department,
                                directMgr = data?.directMgr,
                                suggestion = suggestion.text.toString(),
                                problem = problem.text.toString(),
                                statusImplementation = status,
                                teamMember = data?.teamMember,
                                attachment = data?.attachment,
                                statusProposal = data?.statusProposal,
                                headId = data?.headId,
                                userId = data?.userId,
                                orgId = data?.orgId,
                                warehouseId = data?.warehouseId,
                                proses = edtProses,
                                result = edtResult,
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
