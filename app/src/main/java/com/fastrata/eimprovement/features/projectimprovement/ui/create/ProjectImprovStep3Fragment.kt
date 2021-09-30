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
            identifikasiMasalah.isEnabled = false
            menetapkanTarget.isEnabled = false
        }
    }

    private fun getData() {
        binding.apply {
            if (data?.identification != null) {
                identifikasiMasalah.setText(data?.identification.toString())
                menetapkanTarget.setText(data?.setTarget.toString())
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
                                "Identification must be fill before next",
                                R.drawable.ic_close, R.color.red_500)
                            identifikasiMasalah.requestFocus()
                            stat = false

                        }
                        menetapkanTarget.text.isNullOrEmpty() -> {
                            SnackBarCustom.snackBarIconInfo(
                                root, layoutInflater, resources, root.context,
                                "Target must be fill before next",
                                R.drawable.ic_close, R.color.red_500)
                            menetapkanTarget.requestFocus()
                            stat = false

                        }
                        else -> {
                            HawkUtils().setTempDataCreatePi(
                                id = data?.id,
                                piNo = data?.piNo,
                                date = data?.createdDate,
                                title = data?.title,
                                branch = data?.branch,
                                subBranch = data?.subBranch,
                                department = data?.department,
                                years = data?.years,
                                statusImplementation = data?.statusImplementation,
                                identification = identifikasiMasalah.text.toString(),
                                target = menetapkanTarget.text.toString(),
                                sebabMasalah = data?.problem,
                                akarMasalah = data?.akarMasalah,
                                nilaiOutput = data?.outputValue,
                                perhitunganNqi = data?.nqi,
                                teamMember = data?.teamMember,
                                categoryFixingItem = data?.categoryFixing,
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
