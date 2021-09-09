package com.fastrata.eimprovement.features.projectimprovement.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fastrata.eimprovement.databinding.FragmentProjectImprovementStep1Binding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.features.projectimprovement.callback.ProjectImprovementSystemCreateCallback
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementCreateModel
import com.fastrata.eimprovement.utils.HawkUtils
import javax.inject.Inject

class ProjectImprovStep1Fragment: Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var _binding : FragmentProjectImprovementStep1Binding
    private val binding get() = _binding
    private var data : ProjectImprovementCreateModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProjectImprovementStep1Binding.inflate(layoutInflater,container,false)
        data = HawkUtils().getTempDataCreatePi()
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProjectImprovementStep1Binding.bind(view)
        _binding.apply {
            setData()
        }

    }

    private fun setData() {
        (activity as ProjectImprovementCreateWizard).setpiCreateCallback(object  :
            ProjectImprovementSystemCreateCallback{
            override fun onDataPass(): Boolean {
                var stat : Boolean
                _binding.apply {
                    stat = true
                }
                return stat
            }
            })
    }
}