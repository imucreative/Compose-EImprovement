package com.fastrata.eimprovement.features.projectimprovement.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.FragmentProjectImprovementStep1Binding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.features.projectimprovement.callback.ProjectImprovementSystemCreateCallback
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementCreateModel
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.utils.HawkUtils
import timber.log.Timber
import javax.inject.Inject

class ProjectImprovStep1Fragment: Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding : FragmentProjectImprovementStep1Binding? = null
    private val binding get() = _binding!!
    private var data : ProjectImprovementCreateModel? = null
    private var source: String = PI_CREATE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectImprovementStep1Binding.inflate(inflater,container,false)

        data = HawkUtils().getTempDataCreatePi()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProjectImprovementStep1Binding.bind(view)
        binding.apply {
            piNo.setText(data?.piNo)
            year.setText("2020")
            createdDate.setText("22 Sep 2021")
            branch.setText(data?.branch)
            department.setText(data?.department)
            subBranch.setText(data?.subBranch)

            title.setText(data?.title)

            Timber.w("##### $data")
            setData()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
                                "Title must be fill before next",
                                R.drawable.ic_close, R.color.red_500)
                            title.requestFocus()
                            stat = false
                        }
                        else -> {
                            HawkUtils().setTempDataCreatePi(
                                piNo = piNo.text.toString(),
                                year = year.text.toString(),
                                date = createdDate.text.toString(),
                                branch = branch.text.toString(),
                                department = department.text.toString(),
                                subBranch = subBranch.text.toString(),
                                title = title.text.toString()
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