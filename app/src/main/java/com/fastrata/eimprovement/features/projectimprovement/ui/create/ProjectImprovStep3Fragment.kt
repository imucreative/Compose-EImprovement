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
import com.fastrata.eimprovement.utils.HawkUtils
import com.fastrata.eimprovement.utils.SS_CREATE
import com.fastrata.eimprovement.utils.SnackBarCustom
import javax.inject.Inject

class ProjectImprovStep3Fragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: FragmentProjectImprovementStep3Binding? = null
    private val binding get() = _binding!!
    private var data : ProjectImprovementCreateModel? = null
    private var piNo: String? = ""
    private var ssAction: String? = ""
    private var source: String = SS_CREATE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectImprovementStep3Binding.inflate(inflater, container, false)

        data = HawkUtils().getTempDataCreatePi()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProjectImprovementStep3Binding.bind(view)

        getData()
        setData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getData() {
        binding.apply {
            if (data?.identification != null) {
                identification.setText(data?.identification.toString())
                target.setText(data?.setTarget.toString())
            }
        }
    }

    private fun setData() {
        (activity as ProjectImprovementCreateWizard).setPiCreateCallback(object : ProjectImprovementSystemCreateCallback {
            override fun onDataPass(): Boolean {
                var stat: Boolean
                binding.apply {
                    if (identification.text.isNullOrEmpty()) {
                        SnackBarCustom.snackBarIconInfo(
                            root, layoutInflater, resources, root.context,
                            "Identification must be fill before next",
                            R.drawable.ic_close, R.color.red_500)
                        identification.requestFocus()
                        stat = false

                    } else if (target.text.isNullOrEmpty()) {
                        SnackBarCustom.snackBarIconInfo(
                            root, layoutInflater, resources, root.context,
                            "Target must be fill before next",
                            R.drawable.ic_close, R.color.red_500)
                        target.requestFocus()
                        stat = false

                    } else {
                        HawkUtils().setTempDataCreatePi(
                            identification = identification.text.toString(),
                            target = target.text.toString()
                        )
                        stat = true

                    }
                }
                return stat
            }
        })
    }
}