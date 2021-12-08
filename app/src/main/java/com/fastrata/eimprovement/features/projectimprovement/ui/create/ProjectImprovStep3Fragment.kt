package com.fastrata.eimprovement.features.projectimprovement.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.FragmentProjectImprovementStep3Binding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.features.projectimprovement.callback.ProjectImprovementSystemCreateCallback
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementCreateModel
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.utils.HawkUtils
import javax.inject.Inject

class ProjectImprovStep3Fragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: FragmentProjectImprovementStep3Binding? = null
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
        _binding = FragmentProjectImprovementStep3Binding.inflate(inflater, container, false)

        piNo = arguments?.getString(PI_DETAIL_DATA)
        action = arguments?.getString(ACTION_DETAIL_DATA)

        source = if (piNo == "") PI_CREATE else PI_DETAIL_DATA

        data = HawkUtils().getTempDataCreatePi(source)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProjectImprovementStep3Binding.bind(view)

        getData()
        setData()

        when (action) {
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
            identifikasiMasalah.isEnabled = false
            menetapkanTarget.isEnabled = false
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

    private fun getData() {
        binding.apply {
            if (data?.identification != null) {
                identifikasiMasalah.setText(data?.identification.toString())
                menetapkanTarget.setText(data?.target.toString())
            }
        }
    }

    private fun setData() {
        (activity as ProjectImprovementCreateWizard).setPiCreateCallback(object : ProjectImprovementSystemCreateCallback {
            override fun onDataPass(): Boolean {
                var stat: Boolean
                binding.apply {
                    when {
                        identifikasiMasalah.text.isNullOrEmpty() -> {
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                resources.getString(R.string.indentification_empty),
                                R.drawable.ic_close, R.color.red_500)
                            identifikasiMasalah.requestFocus()
                            stat = false

                        }
                        menetapkanTarget.text.isNullOrEmpty() -> {
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                resources.getString(R.string.set_target_empty),
                                R.drawable.ic_close, R.color.red_500)
                            menetapkanTarget.requestFocus()
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
                                statusImplementationModel = data?.statusImplementationModel,
                                identification = identifikasiMasalah.text.toString(),
                                target = menetapkanTarget.text.toString(),
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
