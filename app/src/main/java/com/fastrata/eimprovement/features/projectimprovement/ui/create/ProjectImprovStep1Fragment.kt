package com.fastrata.eimprovement.features.projectimprovement.ui.create

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.FragmentProjectImprovementStep1Binding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.features.projectimprovement.callback.ProjectImprovementSystemCreateCallback
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementCreateModel
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.utils.HawkUtils
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

class ProjectImprovStep1Fragment: Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding : FragmentProjectImprovementStep1Binding? = null
    private val binding get() = _binding!!
    private var data : ProjectImprovementCreateModel? = null
    private var piNo: String? = ""
    private var action: String? = ""
    private var source: String = PI_CREATE
    private val formatDateDisplay = SimpleDateFormat("dd/MM/yyyy")
    private val formatDateOriginalValue = SimpleDateFormat("yyyy-MM-dd")
    private lateinit var currentDateTime: LocalDateTime
    private lateinit var yearFormat: String
    private lateinit var parseDateNow: Date
    private lateinit var dateFormat: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectImprovementStep1Binding.inflate(inflater,container,false)

        piNo = arguments?.getString(PI_DETAIL_DATA)
        action = arguments?.getString(ACTION_DETAIL_DATA)

        source = if (piNo == "") PI_CREATE else PI_DETAIL_DATA

        data = HawkUtils().getTempDataCreatePi(source)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProjectImprovementStep1Binding.bind(view)

        currentDateTime = LocalDateTime.now()

        parseDateNow = formatDateOriginalValue.parse(if (source == PI_CREATE) currentDateTime.toString() else data?.date)
        yearFormat = if (source == PI_CREATE) currentDateTime.year.toString() else data?.years.toString()
        dateFormat = formatDateDisplay.format(parseDateNow)

        binding.apply {
            piNo.setText(data?.piNo)
            year.setText(yearFormat)
            createdDate.setText(dateFormat)
            branch.setText(data?.branch)
            department.setText(data?.department)
            subBranch.setText(data?.subBranch)

            title.setText(data?.title)

            setData()

            when (action){
                APPROVE, DETAIL -> disableForm()
            }

            when {
                conditionImplementation() -> disableForm()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun disableForm() {
        binding.apply {
            title.isEnabled = false
            //edtLayoutTitle.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.grey_10))
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

    private fun setData() {
        (activity as ProjectImprovementCreateWizard).setPiCreateCallback(object  :
            ProjectImprovementSystemCreateCallback{
            override fun onDataPass(): Boolean {
                var stat : Boolean
                binding.apply {
                    when {
                        title.text.isNullOrEmpty() -> {
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                resources.getString(R.string.title_empty),
                                R.drawable.ic_close, R.color.red_500)
                            title.requestFocus()
                            stat = false
                        }
                        else -> {
                            val displayDate = formatDateOriginalValue.format(parseDateNow)
                            println(displayDate)
                            HawkUtils().setTempDataCreatePi(
                                id = data?.id,
                                piNo = piNo.text.toString(),
                                date = displayDate,
                                title = title.text.toString(),
                                branch = branch.text.toString(),
                                subBranch = subBranch.text.toString(),
                                department = department.text.toString(),
                                years = year.text.toString(),
                                statusImplementationModel = data?.statusImplementationModel,
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
            }
        )
    }
}